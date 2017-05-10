package com.course.example.videostreamonathread;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.MediaController;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    private VideoView videoview;

    // Insert your Video URL
    //String VideoURL = "http://www.androidbegin.com/tutorial/AndroidCommercial.3gp";
    String VideoURL = "https://archive.org/download/ksnn_compilation_master_the_internet/ksnn_compilation_master_the_internet_512kb.mp4";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find your VideoView in your video_main.xml layout
        videoview = (VideoView) findViewById(R.id.video);
    }

    @Override
    public void onStart() {
        super.onStart();

        // Create a progressbar
        pDialog = new ProgressDialog(MainActivity.this);
        // Set progressbar title
        pDialog.setTitle("Android Video Streaming");
        // Set progressbar message
        pDialog.setMessage("Buffering...");

        // Show progressbar
        pDialog.show();

        try {
            // Start the MediaController
            MediaController mediacontroller = new MediaController(
                    MainActivity.this);
            mediacontroller.setAnchorView(videoview);
            // Get the URL from String VideoURL
            Uri video = Uri.parse(VideoURL);
            videoview.setMediaController(mediacontroller);
            videoview.setVideoURI(video);

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        videoview.requestFocus();


        //callback happens when the media source is ready for playback
        videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
                pDialog.dismiss();
                Thread t = new Thread(background);
                t.start();
            }

        });
    }

    Runnable background = new Runnable() {
        public void run() {
            videoview.start();
        }
    };
}


