package com.bidfrail.provider.ui.chat.modelsheetvoicerecorder;

import android.content.DialogInterface;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import com.airbnb.lottie.LottieAnimationView;
import com.bidfrail.provider.R;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import java.io.IOException;

public class VoiceRecorderModalBottomSheet extends BottomSheetDialogFragment implements Timer.OnTimerTickListener {

    private final String outputFile;
    private final VoiceRecorderListener voiceRecorderListener;

    private MediaRecorder mediaRecorder;

    private boolean isRecording = false;
    private boolean isPaused = false;

    private Timer timer;

    public VoiceRecorderModalBottomSheet(String outputFile, VoiceRecorderListener listener) {
        this.outputFile = outputFile;
        this.voiceRecorderListener = listener;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_voice_recorder_modal_bottom_sheet, container);
    }

    @Override
    public void onStart() {
        super.onStart();
        BottomSheetDialog dialog = (BottomSheetDialog) getDialog();
        assert dialog != null;

        dialog.getBehavior().setSkipCollapsed(true);
        dialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);

        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(true);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return false;   // if true then on BackPressed not work, if false on BackPressed work
                }
                return false;
            }
        });
    }

    private LottieAnimationView lottieAnimationView;
    private TextView timerTextView;
    private ImageButton cancelImageButton, pausePlayImageButton, doneImageButton;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lottieAnimationView     = view.findViewById(R.id.lottieAnimationView);
        timerTextView           = view.findViewById(R.id.timerTextView);
        cancelImageButton       = view.findViewById(R.id.cancelImageButton);
        pausePlayImageButton    = view.findViewById(R.id.pausePlayImageButton);
        doneImageButton         = view.findViewById(R.id.doneImageButton);

        timer = new Timer(this);

        cancelImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                stopRecording();
                voiceRecorderListener.recording(false, outputFile);
            }
        });

        pausePlayImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPaused)
                {
                    resumeRecording();
                }
                else if (isRecording)
                {
                    pauseRecording();
                }
                else
                {
                    startRecording(outputFile);
                }
            }
        });

        doneImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                stopRecording();
                voiceRecorderListener.recording(true, outputFile);
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        if (isRecording) {
            stopRecording();
        }
    }
    /***********************************************************************************************
     ***************************************Recording Methods***************************************
     **********************************************************************************************/
    private void startRecording(String outputFile) {
        mediaRecorder = new MediaRecorder();

        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        mediaRecorder.setOutputFile(outputFile);

        try {
            mediaRecorder.prepare();
        } catch (IOException e) {
            Log.e("TAG", "prepare() failed" + e.getMessage());
        }

        mediaRecorder.start();

        pausePlayImageButton.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.chat_ic_pause_recording, null));

        isRecording = true;
        isPaused = false;

        timer.start();

        doneImageButton.setVisibility(View.VISIBLE);
    }

    private void pauseRecording() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mediaRecorder.pause();
            isPaused = true;
            timer.pause();
            pausePlayImageButton.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.chat_ic_play_recording, null));
        }
        else
        {
            toast("Pause recording feature not available", Toast.LENGTH_LONG);
        }
    }

    private void resumeRecording() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mediaRecorder.resume();
            isPaused = false;
            timer.start();
            pausePlayImageButton.setImageDrawable(ResourcesCompat.getDrawable(getResources(), R.drawable.chat_ic_pause_recording, null));
        }
        else
        {
            toast("Resume recording feature not available", Toast.LENGTH_LONG);
        }
    }

    private void stopRecording() {
        if(mediaRecorder != null)
        {
            timer.stop();

            mediaRecorder.stop();
            mediaRecorder.release();

            isPaused = false;
            isRecording = false;

            timerTextView.setText("00.00.00");

            mediaRecorder = null;
        }
    }

    @Override
    public void onTimerTick(String duration) {
        timerTextView.setText(duration);
    }

    private void toast(String message, int length) {
        Toast toast = Toast.makeText(getContext(), message, length);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}