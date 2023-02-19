package com.bidfrail.provider.ui.chat.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import com.bidfrail.provider.AppConstants;
import com.bidfrail.provider.R;
import com.bidfrail.provider.data.local.sharedpreferences.SharedPreferencesHelper;
import com.bidfrail.provider.databinding.ActivityChatBinding;
import com.bidfrail.provider.fcm.model.Data;
import com.bidfrail.provider.fcm.model.NotificationRequestForSingleDevice;
import com.bidfrail.provider.model.Lead;
import com.bidfrail.provider.modelsheetimageselectorcapture.ImageSelectOrCaptureListener;
import com.bidfrail.provider.modelsheetimageselectorcapture.ImageSelectOrCaptureModalBottomSheet;
import com.bidfrail.provider.ui.base.view.BaseActivity;
import com.bidfrail.provider.ui.chat.adapter.ChatMessageRecyclerViewAdapter;
import com.bidfrail.provider.ui.chat.model.ChatMessage;
import com.bidfrail.provider.ui.chat.model.ChatMessageMultiItem;
import com.bidfrail.provider.ui.chat.modelsheetimageviewer.ImageViewerModalBottomSheet;
import com.bidfrail.provider.ui.chat.modelsheetvoicerecorder.VoiceRecorderListener;
import com.bidfrail.provider.ui.chat.modelsheetvoicerecorder.VoiceRecorderModalBottomSheet;
import com.bidfrail.provider.ui.chat.viewmodel.ChatViewModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.library.adapter.recyclerview.LayoutManagerUtils;
import com.library.adapter.recyclerview.listener.OnRecyclerViewItemChildClick;
import com.library.imagecompressor.Compressor;
import com.library.imagecompressor.CompressorFolderException;
import com.library.navigator.activity.ActivityNavigator;
import com.library.navigator.fragment.FragmentNavigator;
import com.library.utilities.ImageAndVideoUtils;
import com.library.utilities.ImplicitIntentUtils;
import com.library.utilities.ResourcesUtils;
import com.library.utilities.file.FileProviderUtils;
import com.library.utilities.file.FileUtils;
import com.library.utilities.file.MediaFileUtils;
import com.library.utilities.file.MemoryUtils;
import com.library.utilities.file.RealPathUtils;
import com.library.utilities.file.StorageUtils;
import com.library.utilities.listener.AlertDialogListener;
import com.library.utilities.permissionutils.ManagePermission;
import com.library.utilities.permissionutils.PermissionDialog;
import com.library.utilities.permissionutils.PermissionName;
import com.library.utilities.string.StringUtils;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ChatActivity extends BaseActivity<ActivityChatBinding, ChatViewModel> {

    private Lead leadObject;

    private ChatMessageRecyclerViewAdapter chatMessageRecyclerViewAdapter;

    private ArrayList<ChatMessage> chatMessageArrayList = new ArrayList<ChatMessage>();
    private ArrayList<ChatMessageMultiItem> chatMessageMultiItemList = new ArrayList<ChatMessageMultiItem>();

    public String senderId;
    public String receiverId;
    public String message;
    public String messageType;
    public String date;
    public String time;
    public String seen;

    private Uri imageUri;
    private String imageOriginalFilePath;
    private String imageCompressFilePath;

    private Uri audioUri;
    private String audioOriginalFilePath;

    private boolean sendOrRecord = false;   /* false means recording, true means send */
    private boolean isRecording = false;
    private boolean isPlaying = false;

    MediaPlayer mediaPlayer;
    private Handler handler;
    private Runnable runnable;

    ImageView audioPlayStopImageView;
    SeekBar audioPlayStopSeekBar;
    TextView audioDurationTextView;

    private ValueEventListener valueEventListener;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference senderReference, receiverReference;

    private ManagePermission managePermission;
    private static final int MULTIPLE_PERMISSION_REQUEST_CODE = 1001;
    public static final String[] MULTIPLE_PERMISSIONS =
            {
                    PermissionName.READ_EXTERNAL_STORAGE,
                    PermissionName.WRITE_EXTERNAL_STORAGE,
                    PermissionName.RECORD_AUDIO,
                    PermissionName.CAMERA,
            };

    @Inject
    SharedPreferencesHelper sharedPreferencesHelper;

    @Override
    protected String getActivityClassName() {
        return ChatActivity.class.getSimpleName();
    }

    @Override
    protected void doBeforeSetContentView() {
    }

    @Override
    protected ActivityNavigator getActivityNavigator() {
        return new ActivityNavigator(ChatActivity.this);
    }

    @Override
    protected FragmentNavigator getFragmentNavigator() {
        return null;
    }

    @Override
    protected void doInOnCreate(Bundle savedInstanceState) {
    }

    @Override
    protected ChatViewModel getViewModel() {
        return viewModelProvider(ChatViewModel.class);
    }

    @Override
    protected ActivityChatBinding getViewBinding(LayoutInflater inflater) {
        return ActivityChatBinding.inflate(inflater);
    }

    @Override
    protected void setupToolbar() {
        setupToolBar(mViewBinding.toolbar, ResourcesUtils.getColor(getApplicationContext(), R.color.colorToolbarBackground), R.drawable.ic_left_arrow, R.string.chat_toolbar_title);
    }

    @Override
    protected void init() {
        handleIntent(getIntent());
    }

    @Override
    protected void initView() {
        setupChatRecyclerView(this);
    }

    @Override
    protected void addTextChangedListener() {
        mViewBinding.saySomethingEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {
                /*if (text.length() < 1) {
                    mViewBinding.micOrSendImageButton.setImageResource(R.drawable.ic_mic);
                    sendOrRecord = true;
                } else if (text.length() > 0) {
                    mViewBinding.micOrSendImageButton.setImageResource(R.drawable.ic_send);
                    sendOrRecord = true;
                }*/
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String value = mViewBinding.saySomethingEditText.getText().toString();
                if (StringUtils.isEmpty(value)) {
                    mViewBinding.micOrSendImageButton.setImageResource(R.drawable.chat_ic_mic);
                    sendOrRecord = false;
                } else {
                    mViewBinding.micOrSendImageButton.setImageResource(R.drawable.chat_ic_send);
                    sendOrRecord = true;
                }
            }
        });
    }

    @Override
    protected void setOnFocusChangeListener() {
    }

    @Override
    protected void setupObservers() {
        Observer<String> leadErrorObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String error) {
                hideProgressDialog();
                if (error == null) {
                    showShortToast(getString(R.string.something_went_wrong_please_try_again));
                } else {
                    showShortToast(error);
                }
            }
        };
        mViewModel.leadError().observe(this, leadErrorObserver);

        final Observer<Lead> leadSuccessObserver = new Observer<Lead>() {
            @Override
            public void onChanged(Lead lead) {
                hideProgressDialog();
                leadObject = lead;
                setupChat(leadObject);
            }
        };
        mViewModel.leadSuccess().observe(this, leadSuccessObserver);

        Observer<String> sendNotificationErrorObserver = new Observer<String>() {
            @Override
            public void onChanged(@Nullable String error) {
                hideProgressDialog();
                if (error == null) {
                    showShortToast(getString(R.string.something_went_wrong_please_try_again));
                } else {
                    showShortToast(error);
                }
            }
        };
        mViewModel.sendNotificationError().observe(this, sendNotificationErrorObserver);

        final Observer<String> sendNotificationSuccessObserver = new Observer<String>() {
            @Override
            public void onChanged(String successMessage) {
                hideProgressDialog();
                //showShortToast(successMessage);
            }
        };
        mViewModel.sendNotificationSuccess().observe(this, sendNotificationSuccessObserver);
    }

    @Override
    protected void setupListeners() {
        chatMessageRecyclerViewAdapter.setOnRecyclerViewItemChildClick(new OnRecyclerViewItemChildClick<ChatMessageMultiItem>() {
            @Override
            public void OnItemChildClick(View viewChild, ChatMessageMultiItem chatMessageMultiItem, int position) {
                ImageViewerModalBottomSheet imageViewerModalBottomSheet = new ImageViewerModalBottomSheet(chatMessageMultiItem.getMessage().message);
                switch (viewChild.getId()) {
                    case R.id.outgoingImage:
                        imageViewerModalBottomSheet.show(getSupportFragmentManager(),  imageViewerModalBottomSheet.getTag());
                        break;
                    case R.id.outgoingAudioPlayStopImageView:
                        RelativeLayout outgoingAudioRelativeLayout = (RelativeLayout) viewChild.getParent();

                        ImageView outgoingAudioPlayStopImageView = outgoingAudioRelativeLayout.findViewById(R.id.outgoingAudioPlayStopImageView);
                        SeekBar outgoingAudioSeekBar =  outgoingAudioRelativeLayout.findViewById(R.id.outgoingAudioSeekBar);
                        TextView outgoingAudioDurationTextView =  outgoingAudioRelativeLayout.findViewById(R.id.outgoingAudioDurationTextView);

                        playStopAudio(outgoingAudioPlayStopImageView, outgoingAudioSeekBar, outgoingAudioDurationTextView, chatMessageMultiItem.getMessage().getMessage());
                        break;
                    case R.id.incomingImage:
                        imageViewerModalBottomSheet.show(getSupportFragmentManager(),  imageViewerModalBottomSheet.getTag());
                        break;
                    case R.id.incomingAudioPlayStopImageView:
                        RelativeLayout incomingAudioRelativeLayout = (RelativeLayout) viewChild.getParent();
                        ImageView incomingAudioPlayStopImageView = incomingAudioRelativeLayout.findViewById(R.id.incomingAudioPlayStopImageView);
                        SeekBar incomingAudioSeekBar =  incomingAudioRelativeLayout.findViewById(R.id.incomingAudioSeekBar);
                        TextView incomingAudioDurationTextView =  incomingAudioRelativeLayout.findViewById(R.id.incomingAudioDurationTextView);
                        playStopAudio(incomingAudioPlayStopImageView, incomingAudioSeekBar, incomingAudioDurationTextView, chatMessageMultiItem.getMessage().getMessage());
                        break;
                    default:
                        break;
                }
            }
        });

        mViewBinding.micOrSendImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                message = mViewBinding.saySomethingEditText.getText().toString();

                if (sendOrRecord) {
                    Log.e(mTag, "FOR TEXT MESSAGE");

                    messageType = "text";

                    Calendar calendar = Calendar.getInstance();

                    SimpleDateFormat currentDate = new SimpleDateFormat("dd MMMM yyyy");
                    date = currentDate.format(calendar.getTime());

                    SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm:ss a");
                    time = currentTime.format(calendar.getTime());

                    seen = "0";

                    ChatMessage chatMessage = new ChatMessage(senderId, receiverId, message, messageType, date, time, seen);
                    writeChatMessage(chatMessage);
                    mViewBinding.saySomethingEditText.getText().clear();
                } else {
                    Log.e(mTag, "FOR VOICE MESSAGE");

                    String customDirectoryName = AppConstants.AppConfig.APP_NAME;
                    String fileName = MediaFileUtils.getRandomFileName(1);
                    String extension = ".mp3";
                    createAudioFile(getApplicationContext(), customDirectoryName, fileName, extension);

                    VoiceRecorderModalBottomSheet voiceRecorderModalBottomSheet = new VoiceRecorderModalBottomSheet(audioOriginalFilePath, new VoiceRecorderListener() {
                        @Override
                        public void recording(boolean isSend, String outputFile) {
                            if (isSend) {
                                uploadAudioToFirebase(outputFile);
                            } else {
                                System.out.println("=====Is exist=====" + FileUtils.isFileExists(new File(outputFile)));
                                FileUtils.deleteFile(new File(outputFile));
                                System.out.println("=====Is exist=====" + FileUtils.isFileExists(new File(outputFile)));
                            }
                        }
                    });
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    voiceRecorderModalBottomSheet.show(fragmentManager, voiceRecorderModalBottomSheet.getTag());
                }
            }
        });

        mViewBinding.imageFromImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImageSelectOrCaptureModalBottomSheet modalBottomSheet = new ImageSelectOrCaptureModalBottomSheet(new ImageSelectOrCaptureListener() {
                    @Override
                    public void fromGallery() {
                        Intent imageFromGalleryIntent = ImplicitIntentUtils.actionPickIntent(1);
                        imageFromGalleryActivityResultLauncher.launch(imageFromGalleryIntent);
                    }

                    @Override
                    public void fromCamera() {
                        String customDirectoryName = AppConstants.AppConfig.APP_NAME;
                        String fileName = MediaFileUtils.getRandomFileName(1);
                        String extension = "jpg";

                        Intent captureImageIntent = createImageFile(getApplicationContext(), ChatActivity.this, customDirectoryName, fileName, extension);
                        imageFromCameraActivityResultLauncher.launch(captureImageIntent);
                    }
                });
                FragmentManager fragmentManager = getSupportFragmentManager();
                modalBottomSheet.show(fragmentManager, modalBottomSheet.getTag());
            }
        });
    }

    @Override
    public void showProgressBar() {
        if (mViewBinding.progressBar.getVisibility() == View.GONE) {
            mViewBinding.progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgressBar() {
        if (mViewBinding.progressBar.getVisibility() == View.VISIBLE) {
            mViewBinding.progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void showProgressDialog() {
        if (mViewBinding.progressDialog.pleaseWaitProgressBar.getVisibility() == View.GONE) {
            mViewBinding.progressDialog.pleaseWaitProgressBar.setVisibility(View.VISIBLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    @Override
    public void hideProgressDialog() {
        if (mViewBinding.progressDialog.pleaseWaitProgressBar.getVisibility() == View.VISIBLE) {
            mViewBinding.progressDialog.pleaseWaitProgressBar.setVisibility(View.GONE);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        senderReference.removeEventListener(valueEventListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (intent != null) {
            Bundle bundle = activityNavigator.getBundle();
            if (bundle != null) {

                String navigateToScreen = bundle.getString(AppConstants.Screen.Extras.EXTRA_NAVIGATE_TO);
                String title = bundle.getString(AppConstants.Screen.Extras.EXTRA_TITLE);
                String message = bundle.getString(AppConstants.Screen.Extras.EXTRA_MESSAGE);
                String orderType = bundle.getString(AppConstants.Screen.Extras.EXTRA_ORDER_TYPE);
                String orderId = bundle.getString(AppConstants.Screen.Extras.EXTRA_ORDER_ID);
                String dateRequiredForScreen = bundle.getString(AppConstants.Screen.Extras.EXTRA_DATA_REQUIRED);

                showProgressDialog();
                mViewModel.leadDetails(Integer.parseInt(orderId), sharedPreferencesHelper.getProviderId());
            }
        }
    }
    /***********************************************************************************************
     ********************************************Permission*****************************************
     **********************************************************************************************/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MULTIPLE_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            String permission = permissions[i];

                            if (permission.equalsIgnoreCase(PermissionName.READ_EXTERNAL_STORAGE)) {
                                boolean showRationale = managePermission.shouldShowRequestPermissionRationale(permission);
                                if (showRationale) {
                                    Log.e(mTag, "READ_EXTERNAL_STORAGE permission denied");
                                    ActivityCompat.requestPermissions(
                                            this,
                                            MULTIPLE_PERMISSIONS,
                                            MULTIPLE_PERMISSION_REQUEST_CODE);
                                    return;
                                } else {
                                    Log.e(mTag, "READ_EXTERNAL_STORAGE denied and don't ask for it again");
                                    PermissionDialog.permissionDeniedWithNeverAskAgain(
                                            this,
                                            com.library.utilities.R.drawable.permission_ic_storage,
                                            "Storage Permission",
                                            "Kindly allow Storage Permission from Settings, without this permission the app is unable to provide select image feature. Please turn on permissions at [Setting] -> [Permissions]",
                                            permission,
                                            permissionFromSettingActivityResultLauncher,
                                            new AlertDialogListener() {
                                                @Override
                                                public void onCancel() {
                                                    onBackPressed();
                                                }

                                                @Override
                                                public void onSettings() {
                                                }
                                            });
                                    return;
                                }
                            }

                            if (permission.equalsIgnoreCase(PermissionName.WRITE_EXTERNAL_STORAGE)) {
                                boolean showRationale = managePermission.shouldShowRequestPermissionRationale(permission);
                                if (showRationale) {
                                    Log.e(mTag, "WRITE_EXTERNAL_STORAGE permission denied");
                                    ActivityCompat.requestPermissions(
                                            this,
                                            MULTIPLE_PERMISSIONS,
                                            MULTIPLE_PERMISSION_REQUEST_CODE);
                                    return;
                                } else {
                                    Log.e(mTag, "WRITE_EXTERNAL_STORAGE denied and don't ask for it again");
                                    PermissionDialog.permissionDeniedWithNeverAskAgain(
                                            this,
                                            com.library.utilities.R.drawable.permission_ic_storage,
                                            "Storage Permission",
                                            "Kindly allow Storage Permission from Settings, without this permission the app is unable to provide select image feature. Please turn on permissions at [Setting] -> [Permissions]",
                                            permission,
                                            permissionFromSettingActivityResultLauncher,
                                            new AlertDialogListener() {
                                                @Override
                                                public void onCancel() {
                                                    onBackPressed();
                                                }

                                                @Override
                                                public void onSettings() {
                                                }
                                            });
                                    return;
                                }
                            }

                            if (permission.equalsIgnoreCase(PermissionName.CAMERA)) {
                                boolean showRationale = managePermission.shouldShowRequestPermissionRationale(permission);
                                if (showRationale) {
                                    Log.e(mTag, "CAMERA permission denied");
                                    ActivityCompat.requestPermissions(
                                            this,
                                            MULTIPLE_PERMISSIONS,
                                            MULTIPLE_PERMISSION_REQUEST_CODE);
                                    return;
                                } else {
                                    Log.e(mTag, "CAMERA denied and don't ask for it again");
                                    PermissionDialog.permissionDeniedWithNeverAskAgain(
                                            this,
                                            com.library.utilities.R.drawable.permission_ic_camera,
                                            "Camera Permission",
                                            "Kindly allow Camera Permission from Settings, without this permission the app is unable to provide capture image feature. Please turn on permissions at [Setting] -> [Permissions]",
                                            permission,
                                            permissionFromSettingActivityResultLauncher,
                                            new AlertDialogListener() {
                                                @Override
                                                public void onCancel() {
                                                    onBackPressed();
                                                }

                                                @Override
                                                public void onSettings() {
                                                }
                                            });
                                    return;
                                }
                            }

                            if (permission.equalsIgnoreCase(PermissionName.RECORD_AUDIO)) {
                                boolean showRationale = managePermission.shouldShowRequestPermissionRationale(permission);
                                if (showRationale) {
                                    Log.e(mTag, "RECORD_AUDIO permission denied");
                                    ActivityCompat.requestPermissions(
                                            this,
                                            MULTIPLE_PERMISSIONS,
                                            MULTIPLE_PERMISSION_REQUEST_CODE);
                                    return;
                                } else {
                                    Log.e(mTag, "RECORD_AUDIO denied and don't ask for it again");
                                    PermissionDialog.permissionDeniedWithNeverAskAgain(
                                            this,
                                            com.library.utilities.R.drawable.permission_ic_micro_phone,
                                            "Audio Record Permission",
                                            "Kindly allow Audio Record Permission from Settings, without this permission the app is unable to provide audio message feature. Please turn on permissions at [Setting] -> [Permissions]",
                                            permission,
                                            permissionFromSettingActivityResultLauncher,
                                            new AlertDialogListener() {
                                                @Override
                                                public void onCancel() {
                                                    onBackPressed();
                                                }

                                                @Override
                                                public void onSettings() {
                                                }
                                            });
                                    return;
                                }
                            }
                        }
                    }
                    Log.e(mTag, "all permission granted, do the task");
                } else {
                    Log.e(mTag, "Unknown Error");
                }
                break;
            default:
                throw new RuntimeException("unhandled permissions request code: " + requestCode);
        }
    }

    ActivityResultLauncher<Intent> permissionFromSettingActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (managePermission.hasPermission(MULTIPLE_PERMISSIONS)) {
                        Log.e(mTag, "permission already granted");
                    } else {
                        Log.e(mTag, "permission is not granted, request for permission");
                        ActivityCompat.requestPermissions(
                                ChatActivity.this,
                                MULTIPLE_PERMISSIONS,
                                MULTIPLE_PERMISSION_REQUEST_CODE);
                    }
                }
            });
    /***********************************************************************************************
     *********************************************Helper********************************************
     **********************************************************************************************/
    private void setupChatRecyclerView(@NonNull Context context) {
        mViewBinding.chatRecyclerView.setLayoutManager(LayoutManagerUtils.getLinearLayoutManagerVertical(context));
        chatMessageRecyclerViewAdapter = new ChatMessageRecyclerViewAdapter(context);
        chatMessageRecyclerViewAdapter.addArrayList(chatMessageMultiItemList);
        mViewBinding.chatRecyclerView.setAdapter(chatMessageRecyclerViewAdapter);
    }

    private void sendMessageNotification(String fcmToken, String navigateToScreen, String title, String message, String orderType, String orderId, String dateRequiredForScreen) {

        NotificationRequestForSingleDevice notificationRequestForSingleDevice = new NotificationRequestForSingleDevice();
        notificationRequestForSingleDevice.setTo(fcmToken);

        Data data = new Data();
        data.setNavigateToScreen(navigateToScreen);
        data.setTitle(title);
        data.setMessage(message);
        data.setOrderType(orderType);
        data.setOrderId(orderId);
        data.setDateRequiredForScreen(dateRequiredForScreen);

        notificationRequestForSingleDevice.setData(data);

        notificationRequestForSingleDevice.setContentAvailable(true);
        notificationRequestForSingleDevice.setPriority("high");

        mViewModel.sendNotification(notificationRequestForSingleDevice);
    }

    private void writeChatMessage(ChatMessage chatMessage) {
        if (chatMessage != null) {
            Map<String, String> map = new HashMap<String, String>();
            map.put(AppConstants.Chat.OneToOne.SENDER_ID, chatMessage.getSenderId());
            map.put(AppConstants.Chat.OneToOne.RECEIVER_ID, chatMessage.getReceiverId());
            map.put(AppConstants.Chat.OneToOne.MESSAGE, chatMessage.getMessage());
            map.put(AppConstants.Chat.OneToOne.MESSAGE_TYPE, chatMessage.getMessageType());
            map.put(AppConstants.Chat.OneToOne.DATE, chatMessage.getDate());
            map.put(AppConstants.Chat.OneToOne.TIME, chatMessage.getTime());
            map.put(AppConstants.Chat.OneToOne.SEEN, chatMessage.getSeen());

            switch (chatMessage.getMessageType()) {
                case "text":
                    sendMessageNotification("ChatActivity", sharedPreferencesHelper.getName(), chatMessage.getMessage(), leadObject.getOrderType(), String.valueOf(leadObject.getId()), null, null);
                    break;
                case "image":
                    sendMessageNotification("ChatActivity", sharedPreferencesHelper.getName(), "Image message", leadObject.getOrderType(), String.valueOf(leadObject.getId()), null, null);
                    break;
                case "audio":
                    sendMessageNotification("ChatActivity", sharedPreferencesHelper.getName(), "Voice message", leadObject.getOrderType(), String.valueOf(leadObject.getId()), null, null);
                    break;
            }

            senderReference.push().setValue(map);
            receiverReference.push().setValue(map);
        }
        senderReference.addValueEventListener(valueEventListener);
    }

    public void updateMessage(String id, String updateKey, String updateValue) {
        senderReference.child(id).child(updateKey).setValue(updateValue);
    }

    public void uploadImageToFirebase(String realPath) {
        final StorageReference filepath = FirebaseStorage.getInstance().getReference("message_images/" + System.currentTimeMillis() + ".jpg");
        Uri uri = FileProviderUtils.getFileUri(getApplicationContext(), new File(realPath), AppConstants.AppConfig.PACKAGE_NAME);

        showProgressDialog();
        filepath.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String downloadUrl = uri.toString();

                                message = downloadUrl;
                                messageType = "image";

                                Calendar calendar = Calendar.getInstance();

                                SimpleDateFormat currentDate = new SimpleDateFormat("dd MMMM yyyy");
                                date = currentDate.format(calendar.getTime());

                                SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm:ss a");
                                time = currentTime.format(calendar.getTime());

                                seen = "0";

                                ChatMessage chatMessage = new ChatMessage(senderId, receiverId, message, messageType, date, time, seen);
                                writeChatMessage(chatMessage);
                                hideProgressDialog();
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        hideProgressDialog();
                    }
                });
    }

    private Intent createImageFile(Context context, Activity activity, String customDirectoryName, String fileName, String extension) {
        if (StorageUtils.isExternalStorageAvailableAndWriteable()) {
            File directory = FileUtils.createDirectory(context, customDirectoryName);
            File mediaFile = FileUtils.createFile(directory, extension, fileName);
            assert mediaFile != null;
            imageOriginalFilePath = mediaFile.getAbsolutePath();
            imageUri = FileProviderUtils.getFileUri(context, mediaFile, AppConstants.AppConfig.PACKAGE_NAME);
        }
        return ImageAndVideoUtils.cameraIntent(1, imageUri, activity);
    }

    ActivityResultLauncher<Intent> imageFromGalleryActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    int resultCode = result.getResultCode();
                    if (resultCode == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null) {
                            imageUri = data.getData();
                            if (imageUri != null) {

                                imageOriginalFilePath = RealPathUtils.getRealPath(getApplicationContext(), imageUri);

                                File uncompressFile = new File(imageOriginalFilePath);
                                System.out.println("=====UnCompress File Size=====" + MemoryUtils.getReadableFileSize(uncompressFile.length()));
                                System.out.println("=====UnCompress File Real Path=====" + uncompressFile.getAbsolutePath());

                                File compressFile;
                                try {
                                    compressFile = Compressor.getDefault(getApplicationContext()).compressToFile(uncompressFile);
                                    System.out.println("=====Compress File Size=====" + MemoryUtils.getReadableFileSize(compressFile.length()));
                                    System.out.println("=====Compress File Real Path=====" + compressFile.getAbsolutePath());
                                    imageCompressFilePath = compressFile.getPath();

                                    uploadImageToFirebase(imageCompressFilePath);
                                } catch (CompressorFolderException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            });

    ActivityResultLauncher<Intent> imageFromCameraActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    int resultCode = result.getResultCode();
                    if (resultCode == Activity.RESULT_OK) {
                        File uncompressFile = new File(imageOriginalFilePath);
                        System.out.println("=====UnCompress File Size=====" + MemoryUtils.getReadableFileSize(uncompressFile.length()));
                        System.out.println("=====UnCompress File Real Path=====" + uncompressFile.getAbsolutePath());

                        File compressFile;
                        try {
                            compressFile = Compressor.getDefault(getApplicationContext()).compressToFile(uncompressFile);
                            System.out.println("=====Compress File Size=====" + MemoryUtils.getReadableFileSize(compressFile.length()));
                            System.out.println("=====Compress File Real Path=====" + compressFile.getAbsolutePath());
                            imageCompressFilePath = compressFile.getPath();

                            System.out.println("=====Is old exist=====" + FileUtils.isFileExists(uncompressFile));
                            FileUtils.deleteFile(uncompressFile);
                            System.out.println("=====Is old exist=====" + FileUtils.isFileExists(uncompressFile));

                            uploadImageToFirebase(imageCompressFilePath);
                        } catch (CompressorFolderException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

    private void uploadAudioToFirebase(String realPath) {
        final StorageReference filepath = FirebaseStorage.getInstance().getReference("message_audio/" + System.currentTimeMillis() + ".mp3");
        Uri uri = FileProviderUtils.getFileUri(getApplicationContext(), new File(realPath), AppConstants.AppConfig.PACKAGE_NAME);

        showProgressDialog();
        filepath.putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String downloadUrl = uri.toString();

                                message = downloadUrl;
                                messageType = "audio";

                                Calendar calendar = Calendar.getInstance();

                                SimpleDateFormat currentDate = new SimpleDateFormat("dd MMMM yyyy");
                                date = currentDate.format(calendar.getTime());

                                SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm:ss a");
                                time = currentTime.format(calendar.getTime());

                                seen = "0";

                                ChatMessage chatMessage = new ChatMessage(senderId, receiverId, message, messageType, date, time, seen);
                                writeChatMessage(chatMessage);
                                hideProgressDialog();
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        hideProgressDialog();
                    }
                });
    }

    private void createAudioFile(Context context, String customDirectoryName, String fileName, String extension) {
        if (StorageUtils.isExternalStorageAvailableAndWriteable()) {
            File directory = FileUtils.createDirectory(context, customDirectoryName);
            File mediaFile = FileUtils.createFile(directory, extension, fileName);
            assert mediaFile != null;
            audioOriginalFilePath = mediaFile.getAbsolutePath();
            audioUri = FileProviderUtils.getFileUri(context, mediaFile, AppConstants.AppConfig.PACKAGE_NAME);
        }
    }

    public void playStopAudio(ImageView audioPlayStop,
                              SeekBar seekBar,
                              TextView audioDuration,
                              String audioUrl) {

        audioPlayStopImageView  = audioPlayStop;
        audioPlayStopSeekBar    = seekBar;
        audioDurationTextView   = audioDuration;

        if (isPlaying)
        {
            isPlaying = false;

            audioPlayStopImageView.setImageResource(R.drawable.chat_ic_play);
            audioPlayStopSeekBar.setProgress(0);
            audioDurationTextView.setText("00:00");

            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;

            if (handler != null) {
                handler.removeCallbacks(runnable);
            }
        }
        else
        {
            isPlaying = true;

            audioPlayStopImageView.setImageResource(R.drawable.chat_ic_pause);

            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

            try {
                mediaPlayer.setDataSource(audioUrl);
                mediaPlayer.prepare();
            } catch (IOException e) {
                Log.e("TAG", "prepare() failed");
            }

            audioPlayStopSeekBar.setMax(mediaPlayer.getDuration());
            audioPlayStopSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser && mediaPlayer != null && isPlaying) {
                        mediaPlayer.seekTo(progress);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                }
            });

            audioDurationTextView.setText("0:00/" + calculateDuration(mediaPlayer.getDuration()));

            handler = new Handler();
            runnable = new Runnable() {
                @Override
                public void run() {
                    if (mediaPlayer != null) {
                        audioPlayStopSeekBar.setProgress(mediaPlayer.getCurrentPosition());
                        handler.postDelayed(runnable, 50);

                        int miliSeconds = mediaPlayer.getCurrentPosition();
                        if (miliSeconds != 0) {
                            //if audio is playing, showing current time;
                            long minutes = TimeUnit.MILLISECONDS.toMinutes(miliSeconds);
                            long seconds = TimeUnit.MILLISECONDS.toSeconds(miliSeconds);
                            if (minutes == 0) {
                                audioDurationTextView.setText("0:" + seconds + "/" + calculateDuration(mediaPlayer.getDuration()));
                            } else {
                                if (seconds >= 60) {
                                    long sec = seconds - (minutes * 60);
                                    audioDurationTextView.setText(minutes + ":" + sec + "/" + calculateDuration(mediaPlayer.getDuration()));
                                }
                            }
                        } else {
                            //Displaying total time if audio not playing
                            int totalTime = mediaPlayer.getDuration();
                            long minutes = TimeUnit.MILLISECONDS.toMinutes(totalTime);
                            long seconds = TimeUnit.MILLISECONDS.toSeconds(totalTime);
                            if (minutes == 0) {
                                audioDurationTextView.setText("0:" + seconds);
                            } else {
                                if (seconds >= 60) {
                                    long sec = seconds - (minutes * 60);
                                    audioDurationTextView.setText(minutes + ":" + sec);
                                }
                            }
                        }
                    }
                }
            };

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    isPlaying = false;
                    audioPlayStopImageView.setImageResource(R.drawable.chat_ic_play);
                    audioPlayStopSeekBar.setProgress(0);
                    audioDurationTextView.setText("00:00");

                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;

                    if (handler != null) {
                        handler.removeCallbacks(runnable);
                    }
                }
            });

            // listener for if the audio file can't be prepared
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    // The MediaPlayer has moved to the Error state, must be reset!
                    return false;
                }
            });

            // listener for audio file is prepared for playing
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    handler.postDelayed(runnable, 50);
                    mediaPlayer.start();
                }
            });
        }
    }

    private String calculateDuration(int duration) {
        String finalDuration = "";
        long minutes = TimeUnit.MILLISECONDS.toMinutes(duration);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(duration);
        if (minutes == 0) {
            finalDuration = "0:" + seconds;
        } else {
            if (seconds >= 60) {
                long sec = seconds - (minutes * 60);
                finalDuration = minutes + ":" + sec;
            }
        }
        return finalDuration;
    }

    private void setupChat(Lead lead) {
        managePermission = new ManagePermission(this);
        if (managePermission.hasPermission(MULTIPLE_PERMISSIONS)) {
            Log.e(mTag, "permission already granted");
        } else {
            Log.e(mTag, "permission is not granted, request for permission");
            ActivityCompat.requestPermissions(
                    ChatActivity.this,
                    MULTIPLE_PERMISSIONS,
                    MULTIPLE_PERMISSION_REQUEST_CODE);
        }

        senderId = lead.getOrderNumber() + sharedPreferencesHelper.getName();
        receiverId = lead.getOrderNumber() + lead.getUser().getName();

        firebaseDatabase = FirebaseDatabase.getInstance();
        senderReference = firebaseDatabase.getReference("/messages/" + senderId + "_" + receiverId);
        receiverReference = firebaseDatabase.getReference("/messages/" + receiverId + "_" + senderId);

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chatMessageMultiItemList.clear();
                chatMessageArrayList.clear();

                for (DataSnapshot child : dataSnapshot.getChildren()) {

                    GenericTypeIndicator<Map<String, String>>
                            genericTypeIndicator = new GenericTypeIndicator<Map<String, String>>() {
                    };

                    updateMessage(child.getKey(), "seen", "1");
                    Map<String, String> map = child.getValue(genericTypeIndicator);

                    assert map != null;
                    ChatMessage chatMessage =
                            new ChatMessage(
                                    map.get(AppConstants.Chat.OneToOne.SENDER_ID),
                                    map.get(AppConstants.Chat.OneToOne.RECEIVER_ID),
                                    map.get(AppConstants.Chat.OneToOne.MESSAGE),
                                    map.get(AppConstants.Chat.OneToOne.MESSAGE_TYPE),
                                    map.get(AppConstants.Chat.OneToOne.DATE),
                                    map.get(AppConstants.Chat.OneToOne.TIME),
                                    map.get(AppConstants.Chat.OneToOne.SEEN));

                    ChatMessageMultiItem multiItem = new ChatMessageMultiItem();
                    multiItem.setMessage(chatMessage);

                    if (chatMessage.getSenderId().equals(senderId)) {
                        multiItem.setItemType(1);
                    } else {
                        multiItem.setItemType(2);
                    }
                    chatMessageMultiItemList.add(multiItem);
                }


                hideProgressBar();
                if (chatMessageMultiItemList.size() == 0)
                {
                    mViewBinding.errorTextView.setVisibility(View.VISIBLE);
                    mViewBinding.chatRecyclerView.setVisibility(View.GONE);
                }
                else
                {
                    mViewBinding.errorTextView.setVisibility(View.GONE);
                    mViewBinding.chatRecyclerView.setVisibility(View.VISIBLE);
                }

                chatMessageRecyclerViewAdapter.replaceArrayList(chatMessageMultiItemList);
                mViewBinding.chatRecyclerView.scrollToPosition(chatMessageMultiItemList.size() - 1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        };

        senderReference.addValueEventListener(valueEventListener);
    }
}