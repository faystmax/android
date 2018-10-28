package android.a3lab.services;

import android.a3lab.ServiceActivity;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class FirstService extends Service {

    final String LOG_TAG = "FirstService";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(LOG_TAG, "onStartCommand");
        someTask(intent.getIntExtra("number", 0));
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");
    }

    private void someTask(final int number) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(LOG_TAG, "Number = " + number);

                Intent intent = new Intent(ServiceActivity.BROADCAST);
                intent.putExtra("number", number);
                sendBroadcast(intent);
            }
        }).start();
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
