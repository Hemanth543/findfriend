package thehub.life.fd;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;


/**
 * Created by rajeshkumarsheela on 6/26/17.
 */


public class GPS_Service extends Service{

    private LocationListener listener;
    private LocationManager locationManager;
    private static final String TAG = "GPS_SERVICE";
    private  static Firebase firebase = new Firebase("https://findmyfriend-795e2.firebaseio.com/");


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
       // Firebase.setAndroidContext(this);

        //firebase.child("abc").setValue("1234");
        SharedPreferences sharedPreferences = getSharedPreferences("FD",0);
        final String id = sharedPreferences.getString("user_id","");

        if(!FirebaseApp.getApps(this).isEmpty()) {
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }


        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                firebase.child("location").child(id).setValue(location.getLatitude()+" "+location.getLongitude());
                Toast.makeText(getApplicationContext(),"Phone "+id +" location values "+location.getLatitude()+" "+location.getLongitude(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {
                /*Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);*/

                /*Intent intent=new Intent("android.location.GPS_ENABLED_CHANGE");
                intent.putExtra("enabled", true);
                sendBroadcast(intent);*/
            }
        };

        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        //noinspection MissingPermission
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,3000,0,listener);
        //noinspection MissingPermission
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,3000,0,listener);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(locationManager!=null){
            locationManager.removeUpdates(listener);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

}