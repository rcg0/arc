package com.example.asdf;

import android.app.Activity;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.media.MediaRecorder;
import android.media.MediaPlayer;

import java.io.IOException;

import com.google.gson.Gson;

public class AudioRecorderActivity extends Activity
{
    private static final String LOG_TAG = "AudioRecordTest";
    private static String mFileName = null;
    
    private LinearLayout ll = null;
    
    private EditText nameAudioEditText = null;
    private TextView nameAudioTextView = null;

    private RecordButton mRecordButton = null;
    private MediaRecorder mRecorder = null;

    private PlayButton   mPlayButton = null;
    private MediaPlayer   mPlayer = null;
    
    private Button enviar = null;
    private EditText filename = null;
    
    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }

    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }

    class RecordButton extends Button {
        boolean mStartRecording = true;

        OnClickListener clicker = new OnClickListener() {
            public void onClick(View v) {
            	
            	if(filename.getText().toString().compareTo("") == 0){
            		
            		Toast.makeText(AudioRecorderActivity.this, "Debes escribir un nombre de fichero.", Toast.LENGTH_SHORT).show();
            		
            	}else{
            		
            		mFileName = Environment.getExternalStorageDirectory().getAbsolutePath() +
            				"/ARC/"+ filename.getText().toString()+".3gp";    
            	
            		onRecord(mStartRecording);
            		if (mStartRecording) {
            			setText("Parar de grabar");
            		} else {
            			setText("Grabar");
            			mPlayButton.setEnabled(true);
            			enviar.setEnabled(true);
            		}	
            		mStartRecording = !mStartRecording;
            	}
            }
        };

        public RecordButton(Context ctx) {
            super(ctx);
            setText("Grabar");
            setOnClickListener(clicker);
        }
    }

    class PlayButton extends Button {
        boolean mStartPlaying = true;

        OnClickListener clicker = new OnClickListener() {
            public void onClick(View v) {
        
            		onPlay(mStartPlaying);
            		if (mStartPlaying) {
            			setText("Parar de reproducir");
            		} else {
            			setText("Reproducir");
            		}
            		mStartPlaying = !mStartPlaying;
            	}
        };

        public PlayButton(Context ctx) {
            super(ctx);
            setText("Reproducir");
            setOnClickListener(clicker);
        }
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setGravity(Gravity.CENTER_HORIZONTAL);
        /**/
        TextView tv1 = new TextView(this);
        tv1.setText("Grabadora de audio de ARC");
        ll.addView(tv1,
                new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    0));
        /*
        ProgressBar progress = new ProgressBar(this, null, 
                android.R.attr.progressBarStyleHorizontal);
        ll.addView(progress,
                new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    0));
        */
        TextView tv2 = new TextView(this);
        tv1.setText("Nombre del clip de audio: ");
        ll.addView(tv2,
                new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    0));
        /**/
       filename = new EditText(this);
        ll.addView(filename,
                new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    0));
        /**/
        mRecordButton = new RecordButton(this);
        ll.addView(mRecordButton,
            new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                0));
        /**/
        mPlayButton = new PlayButton(this);
        ll.addView(mPlayButton,
            new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                0));
        mPlayButton.setEnabled(false);
        /**/
        enviar = new Button(this);
        enviar.setText("Enviar");
        enviar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
            	
            	 Intent returnIntent = new Intent();
            	 returnIntent.putExtra("recordName",mFileName);
            	 setResult(RESULT_OK,returnIntent);     
            	 finish();
            	 
            }
        });
        
        ll.addView(enviar,
            new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                0));
        enviar.setEnabled(false);
        /**/
        setContentView(ll);
        
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }

        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }
}
