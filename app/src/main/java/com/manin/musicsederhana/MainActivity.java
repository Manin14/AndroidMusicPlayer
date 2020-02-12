package com.manin.musicsederhana;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSeekBar;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    AppCompatButton btnPlay,btnPause,btnStop=null;
    private MediaPlayer mediaPlayer=null;

    AppCompatSeekBar seekBar=null;
    Handler han=null;

    Runnable mRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        deklarasi();
    }

    private void deklarasi() {
        btnPlay=findViewById(R.id.id_play);
        btnPlay.setOnClickListener(this);

        btnPause=findViewById(R.id.id_pause);
        btnPause.setOnClickListener(this);

        btnStop=findViewById(R.id.id_stop);
        btnStop.setOnClickListener(this);

        stateAwal();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.id_play:
                playAudio();
                break;

            case R.id.id_pause:
                pauseAudio();
                break;

            case R.id.id_stop:
                stopAudio();
                break;
        }

    }




    //method method
    private  void stateAwal(){
        //Kondisi awal sebelum musik dimainkan, hanya tombol play saja yang dapat diklik.
        btnPlay.setEnabled(true);
        btnPause.setEnabled(false);
        btnStop.setEnabled(false);
    }

    //Method untuk memainkan musik
    private void playAudio(){

        //Menentukan resource audio yang akan dijalankan
        mediaPlayer = MediaPlayer.create(this, R.raw.lagunya);

        //Kondisi Button setelah tombol play di klik
        btnPlay.setEnabled(false);
        btnPause.setEnabled(true);
        btnStop.setEnabled(true);

        //Menjalankan Audio / Musik
        try{
            mediaPlayer.prepare();
            //seekBarMethod();
            //sekkbar gak jalan
           // int mCurrentPosition = mediaPlayer.getCurrentPosition() / 1000;
            //seekBar.setProgress(mCurrentPosition);
            //han.postDelayed((Runnable) this,1000);
        }catch (IllegalStateException ex){
            ex.printStackTrace();
        }catch (IOException ex1){
            ex1.printStackTrace();
        }
        mediaPlayer.start();





        //Setelah audio selesai dimainkan maka kondisi Button akan kembali seperti awal
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stateAwal();
            }
        });

    }

    //Method untuk mengentikan musik
    private void pauseAudio(){

        //Jika audio sedang dimainkan, maka audio dapat di pause
        if(mediaPlayer.isPlaying()){
            if(mediaPlayer != null){
                mediaPlayer.pause();
               btnPause.setText("Lanjutkan");
            }
        }else {

            //Jika audio sedang di pause, maka audio dapat dilanjutkan kembali
            if(mediaPlayer != null){
                mediaPlayer.start();
                btnPause.setText("Pause");
            }
        }

    }

    //Method untuk mengakhiri musik
    private void stopAudio(){

        mediaPlayer.stop();
        try {
            //Menyetel audio ke status awal
            mediaPlayer.prepare();
            mediaPlayer.seekTo(0);
        }catch (Throwable t){
            t.printStackTrace();
        }
        stateAwal();

    }


    private  void seekBarMethod(){
        seekBar.setMax(mediaPlayer.getDuration()/1000);

        mRunnable = new Runnable() {
            @Override
            public void run() {
                if(mediaPlayer!=null){
                    int mCurrentPosition = mediaPlayer.getCurrentPosition()/1000; // In milliseconds
                    seekBar.setProgress(mCurrentPosition);
                    getAudioStats();
                }
              han.postDelayed(mRunnable,1000);
            }
        };
        han.postDelayed(mRunnable,1000);
    }


    protected void getAudioStats(){
        int duration  = mediaPlayer.getDuration()/1000; // In milliseconds
        int due = (mediaPlayer.getDuration() - mediaPlayer.getCurrentPosition())/1000;
        int pass = duration - due;

      //  mPass.setText("" + pass + " seconds");
       // mDuration.setText("" + duration + " seconds");
        //mDue.setText("" + due + " seconds");
    }
}
