package com.wujincheng.saveinteminfodemo01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.wujincheng.saveinteminfodemo01.util.BaseApp;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static Context context;
    private TextView textView, textView2, tv_color;
    private ImageView dog_fish, fly_dog;
    private static boolean isShow = false;
    private MyHandler handler;
    private PowerManager.WakeLock m_wklk; //设置不会锁屏
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        //设置固定横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setNotWakeLock();
        BaseApp.getInstance().init();
        initView();
        handler = new MyHandler(getMainLooper());

    }

    private void initView(){
        textView = findViewById(R.id.tv_show);
        textView2 = findViewById(R.id.tv_show2);
        tv_color = findViewById(R.id.tv_color);
        dog_fish = findViewById(R.id.dog_fish);
        fly_dog = findViewById(R.id.fly_dog);
    }

    /**
     * 设置app显示时不熄屏。 activity xml android:keepScreenOn="true" 二选一
     */
    @SuppressLint("InvalidWakeLockTag")
    private void setNotWakeLock() {
        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        m_wklk = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "cn");
        m_wklk.acquire();
    }

    public static Context getContext() {
        return context;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
        isShow = true;
        TimeThread timeThread = new TimeThread();
        timeThread.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
        isShow = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    class TimeThread extends Thread {
        @Override
        public void run() {
            super.run();
            do {
                try {
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.what = 1;  //消息(一个整型值)
                    handler.sendMessage(msg);// 每隔1秒发送一个msg给mHandler
                    count += 1;
                    BaseApp.getInstance().setDate_Int("count", count);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (true);
        }

    }


    class MyHandler extends Handler {
        public MyHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            Date date = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat format2 = new SimpleDateFormat("HH:mm:ss");
            String time = formatter.format(date);
            String time2 = format2.format(date);
            BaseApp.getInstance().setDate_String("time", time);
            textView.setText(time);
            Random random = new Random();
            int color[] = new int[3];
            for (int i = 0; i < 3; i++) {
                color[i] = random.nextInt(255);
            }
            if (color == null) {
                for (int i = 0; i < 3; i++) {
                    color[i] = 255;
                }
            }
            RequestOptions options = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE);
            Glide.with(getContext()).load(R.drawable.dog_fish).apply(options).into(dog_fish);
            if (count % 2 == 0) {
                Glide.with(getContext()).load(R.drawable.dog_fish).apply(options).into(dog_fish);
                Glide.with(getContext()).load(R.drawable.fly_dog).apply(options).into(fly_dog);
            } else {
                Glide.with(getContext()).load(R.drawable.dog_fish).apply(options).into(fly_dog);
                Glide.with(getContext()).load(R.drawable.fly_dog).apply(options).into(dog_fish);
            }
            tv_color.setText("这是颜色：" + Arrays.toString(color) + " 和count " + count);
            tv_color.setTextColor(Color.rgb(color[0], color[1], color[2]));
            textView2.setVisibility(View.VISIBLE);
            tv_color.setTextColor(Color.rgb(color[0], color[1], color[2]));
            textView2.setText(time2);
            textView2.setTextColor(Color.rgb(color[0], color[1], color[2]));
        }
    }

}
