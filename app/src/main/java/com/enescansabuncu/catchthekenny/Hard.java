package com.enescansabuncu.catchthekenny;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class Hard extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    int hightScore;
    TextView timeText;
    TextView scoreText;
    ImageView imageView1;
    ImageView imageView2;
    TextView  hightTextView;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;
    ImageView imageView7;
    ImageView imageView8;
    ImageView imageView9;
    ImageView[] imageArray;
    Handler handler;
    Runnable runnable;
    int number=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        sharedPreferences=this.getSharedPreferences("com.enescansabuncu.catchthekenny",Context.MODE_PRIVATE);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            hightTextView=findViewById(R.id.hightTextView);
            timeText=findViewById(R.id.timeText);
            scoreText=findViewById(R.id.scoreText);
            imageView1=findViewById(R.id.imageView1);
            imageView2=findViewById(R.id.imageView2);
            imageView3=findViewById(R.id.imageView3);
            imageView4=findViewById(R.id.imageView4);
            imageView5=findViewById(R.id.imageView5);
            imageView6=findViewById(R.id.imageView6);
            imageView7=findViewById(R.id.imageView7);
            imageView8=findViewById(R.id.imageView8);
            imageView9=findViewById(R.id.imageView9);
            imageArray=new ImageView[]{imageView1,imageView2,imageView3,imageView4,imageView5,imageView6,imageView7,imageView8,imageView9};
            hightScore=sharedPreferences.getInt("high score",0);
            hightTextView.setText("High Score"+hightScore);
            new CountDownTimer(20000,2000){

                @Override
                public void onTick(long millisUntilFinished) {
                    timeText.setText("Time:"+millisUntilFinished/1000);
                }

                @Override
                public void onFinish() {
                    timeText.setText("Time off");
                    handler.removeCallbacks(runnable);
                    for(ImageView image:imageArray){
                        image.setVisibility(View.INVISIBLE);

                    }
                    AlertDialog.Builder alert=new AlertDialog.Builder(Hard.this);
                    alert.setTitle("Restart?");
                    alert.setMessage("Are you  sure restart");
                    alert.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent=getIntent();
                            finish();
                            startActivity(intent);

                        }
                    });
                    alert.setNegativeButton("no", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(Hard.this,"game over",Toast.LENGTH_SHORT);
                            Intent intent=new Intent(Hard.this,GameLevel.class);
                            startActivity(intent);
                            finish();
                        }
                    }); alert.show();

                }
            }.start();
            hideImages();
            return insets;

        });
    }
    public void increaseScor(View view){

        number++;
        scoreText.setText("Score:"+number);


        if(number>hightScore){
            sharedPreferences.edit().putInt("high score",number).apply();


        }

    }
    public void hideImages(){

        handler=new Handler();
        runnable=new Runnable() {
            @Override
            public void run() {
                for(ImageView image:imageArray){
                    image.setVisibility(View.INVISIBLE);

                }
                Random random=new Random();
                int i = random.nextInt(9);
                imageArray[i].setVisibility(View.VISIBLE);
                handler.postDelayed(this,200);
            }
        };
        handler.post(runnable);
    }


}