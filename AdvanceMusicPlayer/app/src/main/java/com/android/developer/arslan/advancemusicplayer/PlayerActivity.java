package com.android.developer.arslan.advancemusicplayer;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.gesture.GestureLibraries;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.service.quicksettings.Tile;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.jar.Manifest;

public class PlayerActivity extends AppCompatActivity{

    static MediaPlayer mp;//assigning memory loc once or else multiple songs will play at once
    int position;
    SeekBar sb;
    ArrayList<File> mySongs;
    Thread updateSeekBar;
    Button pause,next,previous;
    TextView songNameText;
    TextView songDuration;
    TextView songCurrentPostion;
    Animation animation;
    ImageView imageView;

    String sname;
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_player_ui);

        songNameText = (TextView) findViewById(R.id.txtSongLabel);
        songDuration= findViewById(R.id.songDuration);
        songCurrentPostion= findViewById(R.id.songCurrentPosition);
        animation= AnimationUtils.loadAnimation(this,R.anim.rotate);
        imageView= findViewById(R.id.album_art);
        imageView.setVisibility(View.VISIBLE);

        getSupportActionBar().hide();


//        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//        getSupportActionBar().setDisplayShowHomeEnabled(false);
//        getSupportActionBar().setTitle("Now Playing");

        pause = (Button)findViewById(R.id.pause);
        previous = (Button)findViewById(R.id.previous);
        next = (Button)findViewById(R.id.next);
        sb=(SeekBar)findViewById(R.id.seekBar);
        sb.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorGrey), PorterDuff.Mode.MULTIPLY);
        sb.getThumb().setColorFilter(getResources().getColor(R.color.colorGrey), PorterDuff.Mode.SRC_IN);


        Intent i = getIntent();
        Bundle b = i.getExtras();
        if(b!=null){
            mySongs = (ArrayList) b.getParcelableArrayList("songs");
            position = b.getInt("pos",0);
            imageView.startAnimation(animation);
            initPlayer(position);
        }

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position < mySongs.size() - 1) {
                    position++;
                } else {
                    position = 0;
                }
                initPlayer(position);
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (position <= 0) {
                    position = mySongs.size() - 1;
                } else {
                    position--;
                }
                initPlayer(position);
            }
        });
    }
    //@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void initPlayer(final int position){
        if(mp!=null && mp.isPlaying()){
            mp.reset();
        }

        sname = mySongs.get(position).getName().replace(".mp3", "").replace(".m4a", "").replace(".wav", "").replace(".m4b", "");
        songNameText.setText(sname);
        songNameText.setSelected(true);

        Uri u = Uri.parse(mySongs.get(position).toString());
        mp = MediaPlayer.create(getApplicationContext(),u);

        mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                String totalTime= createTimeLabel(mp.getDuration());
                songDuration.setText(totalTime);
                mp.start();
                sb.setMax(mp.getDuration());
                pause.setBackgroundResource(R.drawable.pause);
            }
        });

         mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                int curSongPoition = position;
                // code to repeat songs until the
                if (curSongPoition < mySongs.size() - 1) {
                    curSongPoition++;
                    initPlayer(curSongPoition);
                } else {
                    curSongPoition = 0;
                    initPlayer(curSongPoition);
                }
            }
         });

         sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
              @Override
              public void onProgressChanged(SeekBar seekBar, int i,boolean b) {
                  if (b) {
                      mp.seekTo(i);
                      sb.setProgress(i);
                  }
              }
              @Override
              public void onStartTrackingTouch(SeekBar seekBar) {
              }
              @Override
              public void onStopTrackingTouch(SeekBar seekBar) {
//                  mp.seekTo(seekBar.getProgress());
              }
         });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mp != null) {
                    try {
//                        Log.i("Thread ", "Thread Called");
                        // create new message to send to handler
                        if (mp.isPlaying()) {
                            Message msg = new Message();
                            msg.what = mp.getCurrentPosition();
                            handler.sendMessage(msg);
                            Thread.sleep(1000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();

//        sb.getProgressDrawable().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
//        sb.getThumb().setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
//
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
//            Log.i("handler ", "handler called");
            int current_position = msg.what;
            sb.setProgress(current_position);
            String cTime = createTimeLabel(current_position);
            songCurrentPostion.setText(cTime);
        }
    };

    private void play() {
        if (mp != null && !mp.isPlaying()) {
            mp.start();
            imageView.startAnimation(animation);
            pause.setBackgroundResource(R.drawable.pause);
        } else {
            pause();
        }

    }

    private void pause() {
        if (mp.isPlaying()) {
            mp.pause();
            imageView.clearAnimation();
            pause.setBackgroundResource(R.drawable.ic_play_arrow_black_24dp);

        }
    }

    public String createTimeLabel(int duration) {
        String timeLabel = "";
        int min = duration / 1000 / 60;
        int sec = duration / 1000 % 60;

        timeLabel += min + ":";
        if (sec < 10) timeLabel += "0";
        timeLabel += sec;

        return timeLabel;
    }
     @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
           onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}