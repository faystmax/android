package android.a3lab.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class ThirdService extends IntentService {
    final String LOG_TAG = "ThirdService";

    public ThirdService() {
        super("ThirdService");
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d(LOG_TAG, "Number = " + intent.getIntExtra("number", 0));
        }
    }
}
