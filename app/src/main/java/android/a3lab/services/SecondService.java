package android.a3lab.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class SecondService extends Service {
    MyBinder binder = new MyBinder();

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public String someTask(int number) {
        return "Number = " + number;
    }

    public class MyBinder extends Binder {
        public SecondService getService() {
            return SecondService.this;
        }
    }
}
