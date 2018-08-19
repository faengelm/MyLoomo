package com.gmail.faengelm.myloomo.Services;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.util.Log;

import com.gmail.faengelm.myloomo.MainActivity;
import com.segway.robot.sdk.base.bind.ServiceBinder;
import com.segway.robot.sdk.voice.Speaker;
import com.segway.robot.sdk.voice.VoiceException;
import com.segway.robot.sdk.voice.tts.TtsListener;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class SoundsService {

    public static SoundsService instance;
    private Speaker mSpeaker;
    private boolean mSpeakerReady;
    private int mSpeakerLanguage;
    private int mRecognitionLanguage;

    private MainActivity mainActivity;
    private MainActivity activity;

    MediaPlayer player = new MediaPlayer();


    public SoundsService(MainActivity activity) {
        this.mainActivity = activity;
        instance = this;
    }

    public static SoundsService getInstance() {
        return instance;
    }


    private static final String TAG = "Sounds";

    private ServiceBinder.BindStateListener mSpeakerBindStateListener = new ServiceBinder.BindStateListener() {
        @Override
        public void onBind() {
            sayIt("Hello, my name is Loomo");
        }

        @Override
        public void onUnbind(String reason) {
        }
    };

    private TtsListener  mTtsListener = new TtsListener() {
        @Override
        public void onSpeechStarted(String s) {
            Log.d(TAG, "onSpeechStarted() called with: s = [" + s + "]");
        }

        @Override
        public void onSpeechFinished(String s) {
            Log.d(TAG, "onSpeechFinished() called with: s = [" + s + "]");
        }

        @Override
        public void onSpeechError(String s, String s1) {
            Log.d(TAG, "onSpeechError() called with: s = [" + s + "], s1 = [" + s1 + "]");
            //    Message msg = mHandler.obtainMessage(ACTION_SHOW_MSG, "speech error: " + s1);
            //    mHandler.sendMessage(msg);
        }
    }; // mTtsListener


    public void sayIt(String text) {
        mSpeaker = Speaker.getInstance();
        mSpeaker.bindService(mainActivity, mSpeakerBindStateListener);
        //  onResume();

        String  mText= text;

        mSpeakerReady=true; // for debug
        if (mSpeakerReady == true) {
            try {
                mSpeakerLanguage = mSpeaker.getLanguage();
                mSpeaker.speak(mText, mTtsListener);

            } catch (VoiceException e) {
                e.printStackTrace();
            }
        };
    } // sayIt()


    public void setCry() {
        try{
            MILLISECONDS.sleep(2000);
            playVoice(3);
            MILLISECONDS.sleep(11000);
            player.stop();
        }
        catch (Exception e){
        }
    }

    public void playVoice(int sound) {
        int mSound = sound;
        String fileName = "BeepBoop.mp4";
        try {
            switch (mSound) {
                case 1: {
                    fileName="Low Rider.mp3";
                    break;
                }

                case 2: {
                    fileName="Backup.mp3";
                    break;
                }
                case 3: {
                    fileName="cry.mp3";
                    break;
                }

                case 4: {
                    fileName="circles.mp3";
                    break;
                }

                case 5: {
                    fileName="Stop.mp3";
                    break;
                }

                default: {
                    fileName="beepboop.mp4";
                    break;
                }
            }
            player.reset();

           // AssetManager assetManager = this.activity.getAssets();
            AssetManager assetManager = mainActivity.getAssets();
            AssetFileDescriptor descriptor = assetManager.openFd(fileName);
            player.setDataSource(descriptor.getFileDescriptor(), descriptor.getStartOffset(), descriptor.getLength());

            player.prepare();
            player.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void endVoice() {
        if (player.isPlaying()) {
            player.stop();
            player.reset();
        }
    }

} // End of Sounds
