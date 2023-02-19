package com.bidfrail.provider.fcm;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.bidfrail.provider.AppConstants;
import com.bidfrail.provider.BuildConfig;
import com.bidfrail.provider.R;
import com.bidfrail.provider.TestActivity;
import com.bidfrail.provider.data.local.sharedpreferences.SharedPreferencesHelper;
import com.bidfrail.provider.ui.afterloginregister.fragment.leaddetails.view.LeadDetailsActivity;
import com.bidfrail.provider.ui.afterloginregister.view.AfterLoginActivity;
import com.bidfrail.provider.ui.login.view.LoginActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.library.utilities.ResourcesUtils;

import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    public static String channelId;
    public static String channelName;
    public static String channelDescription;
    public static int notificationId;

    private String navigateToScreen;
    private String title;
    private String message;
    private String orderType;
    private String orderId;
    private String dateRequiredForScreen;

    @Inject
    SharedPreferencesHelper sharedPreferencesHelper;

    /**
     * Monitor token generation
     *
     * There are two scenarios when onNewToken is called:
     *
     * 1) When a new token is generated on initial app startup
     *
     * 2) Whenever an existing token is changed
     * Under #2, there are three scenarios when the existing token is changed:
     * A) App is restored to a new device
     * B) User uninstalls/re-installs the app
     * C) User clears app data
     */
    @Override
    public void onNewToken(@NonNull String token) {
        Log.e(TAG, "Refreshed token: " + token);
        saveRegistrationInSharePreference(token);
        sendRegistrationToServer(token);
    }

    /**
     * Implement this method to save token in share preference.
     *
     * @param token The new token.
     */
    private void saveRegistrationInSharePreference(String token) {
        Log.e(TAG, "Save In Share Preference: " + token);
        sharedPreferencesHelper.setFcmToken(token);
    }

    /**
     * Implement this method to send token to your app server.
     *
     * @param token The new token.
     */
    private void sendRegistrationToServer(String token) {
        Log.e(TAG, "Send To Server: " + token);
    }

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        /*
         * There are two types of messages data messages [data payload] and notification messages [notification payload]. Data
         * messages are handled here in onMessageReceived whether the app is in the foreground or background. Data messages are
         * the type traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
         * is in the foreground. When the app is in the background an automatically generated notification is displayed. When the
         * user taps on the notification they are returned to the app. Messages containing both notification and data payloads are
         * treated as notification messages. The Firebase console always sends notification messages.
         *
         * For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
         */
        Log.e(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Message data payload: " + remoteMessage.getData());

            Map<String, String> data = remoteMessage.getData();

            /*
             * Notification 1 :
             *                  From User to All Providers (all order type)
             *
             * navigate_to = "LeadsFragment"
             * title = "BidFrail"
             * message = "New Job Post"
             * order_type = "get_bids"   // get_bids or book_now
             * order_id = "1"
             * data_required = null // in this case is null
             *
             * NOTE :  siren only in case of notification 1 else default notification sound
             *
             * Notification 2 :
             *                  From Provider to User (in case of book_now)
             *                  only when provider accept job if provider reject job then not send
             *
             * navigate_to = "OngoingDetailsActivity"
             * title = "BidFrail"
             * message = "Congrats! A provider has been allocated for your job"
             * order_type = "book_now"   // get_bids or book_now
             * order_id = "1"
             * data_required = null // in this case is null
             *
             * Notification 3 :
             *                  From Provider to User (in case of get_bids)
             *                  only when providers bid on job if provider reject job then not send
             *
             * navigate_to = "BidsActivity"
             * title = "BidFrail"
             * message = "Total 5 provider bidded on your job"
             * order_type = "get_bids"   // get_bids or book_now
             * order_id = "1"
             * data_required = null // in this case is null
             *
             * Notification 4 :
             *                  From User to Provider (in case of get_bids)
             *                  when user accept bid then
             *
             * navigate_to = "LeadDetailsActivity"
             * title = "BidFrail"
             * message = "Your bid has been Accepted"
             * order_type = "get_bids"   // get_bids or book_now
             * order_id = "1"
             * data_required = null // in this case is null
             *
             * Notification 5 :
             *                  From User to Provider (in case of get_bids)
             *                  when user send final offer
             *
             * navigate_to = "LeadDetailsActivity"
             * title = "BidFrail"
             * message = "Your have got final offer"
             * order_type = "get_bids"   // get_bids or book_now
             * order_id = "1"
             * data_required = null // in this case is null
             *
             * Notification 6 :
             *                  From Provider to User (in case of get_bids)
             *                  when provider accept final offer if provider reject final offer then not send
             *
             * navigate_to = "OngoingDetailsActivity"
             * title = "BidFrail"
             * message = "Congrats! Your job has been confirmed at the lowest rates using our Bidding Mode"
             * order_type = "get_bids"   // get_bids or book_now
             * order_id = "1"
             * data_required = null // in this case is null
             *
             * Notification 7 :
             *                  From Provider to User (in case of job start)
             *
             * api name : set_provider_status
             *
             * navigate_to = "OngoingDetailsActivity"
             * title = "BidFrail"
             * message = "Congrats! Provider has Started the job"
             * order_type = "get_bids"   // get_bids or book_now
             * order_id = "1"
             * data_required = null // in this case is null
             *
             * Notification 8 :
             *                  From Provider to User (in case of job raise_bill)
             *
             * api name : set_provider_status
             *
             * navigate_to = "OngoingDetailsActivity"
             * title = "BidFrail"
             * message = "Congrats! Provider has Completed the Job"
             * order_type = "get_bids"   // get_bids or book_now
             * order_id = "1"
             * data_required = null // in this case is null
             *
             * Notification 9 :
             *                  From Provider to User (in case of job otp)
             *
             * api name : set_provider_status
             *
             * navigate_to = "OngoingDetailsActivity"
             * title = "BidFrail"
             * message = "Thank You! Your Job ID - 012022112 has been successfully completed"
             * order_type = "get_bids"   // get_bids or book_now
             * order_id = "1"
             * data_required = null // in this case is null
             */
            navigateToScreen = data.get(AppConstants.Screen.Key.KYE_NAVIGATE_TO);

            title = data.get(AppConstants.Screen.Key.KYE_TITLE);
            message = data.get(AppConstants.Screen.Key.KYE_MESSAGE);
            orderType = data.get(AppConstants.Screen.Key.KYE_ORDER_TYPE);
            orderId = data.get(AppConstants.Screen.Key.KYE_ORDER_ID);
            dateRequiredForScreen = data.get(AppConstants.Screen.Key.KYE_DATA_REQUIRED);

            System.out.println("====NAVIGATE_TOE===="+navigateToScreen);
            System.out.println("====TITLE===="+title);
            System.out.println("====MESSAGE===="+message);
            System.out.println("====ORDER_TYPE===="+orderType);
            System.out.println("====ORDER_ID===="+orderId);
            System.out.println("====DATA_REQUIRED===="+dateRequiredForScreen);

            if (message.equals("New Job Post")) {
                channelId = getString(R.string.siren_notification_channel_id);
                channelName = getString(R.string.siren_notification_channel_name);
                channelDescription = getString(R.string.siren_notification_channel_description);
                notificationId = getResources().getInteger(R.integer.siren_notification_id);
            } else {
                channelId = getString(R.string.default_notification_channel_id);
                channelName = getString(R.string.default_notification_channel_name);
                channelDescription = getString(R.string.default_notification_channel_description);
                notificationId = getResources().getInteger(R.integer.default_notification_id);
            }

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            handleNotification(this, channelId, channelName, channelDescription, notificationId, notificationManager);
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.e(TAG, "Message notification payload: " + remoteMessage.getNotification().getBody());
        }
    }

    private void setExtra(Intent intent) {
        intent.putExtra(AppConstants.Screen.Extras.EXTRA_NAVIGATE_TO, navigateToScreen);

        intent.putExtra(AppConstants.Screen.Extras.EXTRA_TITLE, title);
        intent.putExtra(AppConstants.Screen.Extras.EXTRA_MESSAGE, message);
        intent.putExtra(AppConstants.Screen.Extras.EXTRA_ORDER_TYPE, orderType);
        intent.putExtra(AppConstants.Screen.Extras.EXTRA_ORDER_ID, orderId);
        intent.putExtra(AppConstants.Screen.Extras.EXTRA_DATA_REQUIRED,  dateRequiredForScreen);
    }

    private PendingIntent contentPendingIntent(@NonNull Context context) {
        Intent notificationIntent;
        if (sharedPreferencesHelper.getRemember()) {
            if (Objects.equals(navigateToScreen, "LeadDetailsActivity"))
            {
                notificationIntent = new Intent(getApplicationContext(), LeadDetailsActivity.class);
            }
            else
            {
                notificationIntent = new Intent(getApplicationContext(), AfterLoginActivity.class);
            }
        } else {
            notificationIntent = new Intent(getApplicationContext(), LoginActivity.class);
        }

        setExtra(notificationIntent);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent contentPendingIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_MUTABLE);
        return contentPendingIntent;
    }

    public Notification buildNotification(@NonNull Context context, String channelId) {
        int smallIcon = R.drawable.ic_logo;

        Uri NOTIFICATION_SOUND_URI;
        if (Objects.equals(channelId, ResourcesUtils.getString(getApplicationContext(), R.string.siren_notification_channel_id))) {
            NOTIFICATION_SOUND_URI = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + BuildConfig.APPLICATION_ID + "/" + R.raw.australia_sews);
        } else {
            NOTIFICATION_SOUND_URI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, channelId);
        notificationBuilder
                .setContentIntent(contentPendingIntent(context))
                .setSmallIcon(smallIcon)
                .setContentTitle(title)
                .setContentText(message)
                .setSound(NOTIFICATION_SOUND_URI)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_CALL)
                .setAutoCancel(false);;

        return notificationBuilder.build();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setupChannels(String channelId, String channelName, String channelDescription, NotificationManager notificationManager) {
        NotificationChannel notificationChannel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);

        Uri NOTIFICATION_SOUND_URI;
        if (Objects.equals(channelId, ResourcesUtils.getString(getApplicationContext(), R.string.siren_notification_channel_id))) {
            NOTIFICATION_SOUND_URI = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + BuildConfig.APPLICATION_ID + "/" + R.raw.australia_sews);
        } else {
            NOTIFICATION_SOUND_URI = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }

        // Configure the notification channel.
        notificationChannel.setDescription(channelDescription);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.enableVibration(true);
        notificationChannel.setShowBadge(false);
        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);

        AudioAttributes audioAttributes = new AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .setUsage(AudioAttributes.USAGE_ALARM)
                .build();
        notificationChannel.setSound(NOTIFICATION_SOUND_URI, audioAttributes);

        // Register the channel with the system; you can't change the importance or other notification behaviors after this
        notificationManager.createNotificationChannel(notificationChannel);
    }

    public void showNotification(String channelId,
                                 String channelName,
                                 String channelDescription,
                                 int notificationId,
                                 Notification notification,
                                 NotificationManager notificationManager) {
        /*
         * Apps targeting SDK 26 or above (Android O) must implement notification channels and add their
         * notifications to at least one of them. Therefore, confirm if the version is Oreo or higher,
         * then setup notification channel
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setupChannels(channelId, channelName, channelDescription, notificationManager);
        }
        notificationManager.notify(notificationId, notification);
    }

    private void handleNotification(@NonNull Context context,
                                    String channelId,
                                    String channelName,
                                    String channelDescription,
                                    int notificationId,
                                    NotificationManager notificationManager) {

        Notification notification = buildNotification(context, channelId);
        showNotification(channelId, channelName, channelDescription, notificationId, notification, notificationManager);
    }
}