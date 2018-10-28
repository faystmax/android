package android.a3lab;

import android.a1and2lab.R;
import android.a3lab.services.FirstService;
import android.a3lab.services.SecondService;
import android.a3lab.services.ThirdService;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ServiceActivity extends AppCompatActivity {

    private static final String TAG = "ServiceActivity";

    public static final String BROADCAST = "BROADCAST";

    private boolean bound = false;
    private SecondService secondService;
    private ServiceConnection serviceConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        Log.d(TAG, "onCreate: started.");

        initBroadcastReciver();
        initFirstService();
        initSecondService();
        initThirdService();
    }

    private void initBroadcastReciver() {
        BroadcastReceiver myReceiver = new BroadcastReceiver() {

            public void onReceive(Context context, Intent intent) {
                if (intent != null) {
                    Log.d("Received Msg : ", intent.getIntExtra("number", 0) + "");
                }
            }
        };
        registerReceiver(myReceiver, new IntentFilter(BROADCAST));
    }

    private void initFirstService() {
        Button startFirstServiceButton = findViewById(R.id.startFirstServiceButton);
        Button stopFirstServiceButton = findViewById(R.id.stopFirstServiceButton);
        final Intent intent = new Intent(this, FirstService.class);
        startFirstServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("number", 5);
                startService(intent);
            }
        });

        stopFirstServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(intent);
            }
        });
    }


    void initSecondService() {
        Button bindButton = findViewById(R.id.bindButton);
        Button unbindButton = findViewById(R.id.unbindButton);
        Button startSecondServiceButton = findViewById(R.id.startSecondServiceButton);

        final Intent secondServiceIntent = new Intent(this, SecondService.class);

        serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                secondService = ((SecondService.MyBinder) service).getService();
                bound = true;
                Log.d(TAG, "Connected. Bound = " + true);
            }


            @Override
            public void onServiceDisconnected(ComponentName name) {
                bound = false;
                Log.d(TAG, "Disconnected. Bound = " + false);
            }
        };

        bindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bindService(secondServiceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
            }
        });

        unbindButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bound) {
                    return;
                }
                unbindService(serviceConnection);
                bound = false;
            }
        });

        startSecondServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bound) {
                    String result = secondService.someTask(10);
                    Log.d(TAG, "Task finish, result = " + result);
                }
            }
        });
    }

    void initThirdService() {
        Button startThirdServiceButton = findViewById(R.id.startThirdServiceButton);
        final Intent thirdServiceIntent = new Intent(this, ThirdService.class);

        startThirdServiceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thirdServiceIntent.putExtra("number", 15);
                startService(thirdServiceIntent);
            }
        });
    }
}
