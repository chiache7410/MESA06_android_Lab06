package tw.org.iii.lab06;

import android.os.Handler;
import android.os.Message;
import android.os.health.UidHealthStats;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private TextView tv;
    private UIHandler uiHandler;
    private Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timer = new Timer();
        uiHandler = new UIHandler();
        tv = (TextView)findViewById(R.id.tv);
    }
    public void test1(View v) {
        MyThread mt1 = new MyThread();
        mt1.start();
    }
    public void test2(View v) {
        timer.schedule(new MyTask(), 1000, 1000);
    }
    private class MyTask extends TimerTask {
        int i;
        @Override
        public void run() {
            Log.i("brad","i = " + i++);
        }
    }
    private class MyThread extends Thread {
        @Override
        public void run() {
            for (int i=0; i<10; i++) {
                //Log.v("brad","i=" + i);
                //tv.setText("i=" + i);
                Message msg = new Message();
                Bundle data = new Bundle();
                data.putInt("key1",i);
                msg.setData(data);
                uiHandler.sendMessage(msg);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private class UIHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            int i = data.getInt("key1");
            tv.setText("i = " + i);
        }
    }
}
