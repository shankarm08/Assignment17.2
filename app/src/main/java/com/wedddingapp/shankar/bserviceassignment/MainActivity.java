package com.wedddingapp.shankar.bserviceassignment;

import java.util.Date;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {

    MyLocalService localService;

    private boolean isBound = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, MyLocalService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (isBound) {
            unbindService(connection);
            isBound = false;
        }
    }
    public void dispalyDate(View v) {
        if (isBound) {
            Date date = localService.getCurrentDate(); //this is for getting the current date
            Toast.makeText(this, String.valueOf(date), Toast.LENGTH_SHORT).show();
        }
    }
    private ServiceConnection connection = new ServiceConnection() {
        @Override

        // This is called when the connection with the service has been
        // established, giving us the service object we can use to
        // interact with the service.  Because we have bound to a explicit
        // service that we know is running in our own process, we can
        // cast its IBinder to a concrete class and directly access it.
        public void onServiceConnected(ComponentName className, IBinder service) {
            MyLocalService.LocalBinder binder = (MyLocalService.LocalBinder) service;
            localService = binder.getService();
            isBound = true;
        }
        @Override
        // This is called when the connection with the service has been
        // unexpectedly disconnected -- that is, its process crashed.
        // Because it is running in our same process, we should never
        // see this happen.
        public void onServiceDisconnected(ComponentName arg0) {
            isBound = false;
        }
    };
}
