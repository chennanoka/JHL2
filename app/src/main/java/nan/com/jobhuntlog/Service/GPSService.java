package nan.com.jobhuntlog.Service;

import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.concurrent.Semaphore;

/**
 * Created by NAN on 2017-01-12.
 */
public class GPSService extends Service implements LocationListener {

    public UpdateGPSThread mworkingThread;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mworkingThread=new UpdateGPSThread();
        mworkingThread.Start();
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
    public boolean test(){
        return true;
    }
}


class UpdateGPSParameters
{
    public double latitude;
    public double longitude;
}

class UpdateGPSThread extends Thread{
    public UpdateGPSParameters mupdateobject;
    private volatile boolean isRunning = true;
    private Semaphore moplock;
    private AutoResetEvent msignal;
    public UpdateGPSThread(){
        msignal = new AutoResetEvent (false);
        moplock = new Semaphore (1);
    }
    @Override
    public void run() {
        while(isRunning) {
            try {
                msignal.waitOne();
                moplock.acquire();



            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                moplock.release();
            }
        }
    }
    public void pudate(UpdateGPSParameters parameters){
        try {
            moplock.acquire();
            mupdateobject.latitude=parameters.latitude;
            mupdateobject.longitude=parameters.longitude;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            moplock.release();
            msignal.set();
        }

    }
    public void Stop() {
        isRunning = false;
    }
    public void Start(){
        this.start();
    }
}
