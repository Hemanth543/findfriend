package thehub.life.fd;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Hashtable;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String user_id;
    private  static Firebase firebase = new Firebase("https://findmyfriend-795e2.firebaseio.com/");
    MarkerOptions mark;
    Hashtable<String,Marker> markers = new Hashtable<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        //Start service
        startService(new Intent(getBaseContext(), GPS_Service.class));
        SharedPreferences sharedPreferences = getSharedPreferences("FD",0);
        user_id = sharedPreferences.getString("user_id","");

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng position1 = new LatLng(Double.parseDouble(String.valueOf(17.385044)),Double.parseDouble(String.valueOf(78.486671)));
        mMap.addMarker(new MarkerOptions().position(position1).title("THE HUB"));
        float zoomLevel = 18.0f;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position1, zoomLevel));


        firebase.child("location").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
               /* String location = dataSnapshot.getValue().toString();
                String[] coor = location.split(" ");
                LatLng position1 = new LatLng(Double.parseDouble(coor[0]),Double.parseDouble(coor[1]));
                //mMap.clear();
                mMap.addMarker(new MarkerOptions().position(position1).title(dataSnapshot.getKey().toString()));
                float zoomLevel = 15.0f;
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position1, zoomLevel));*/
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
               // Toast.makeText(getApplicationContext(),dataSnapshot.getValue().toString(),Toast.LENGTH_SHORT).show();
                String location = dataSnapshot.getValue().toString();
                String[] coor = location.split(" ");
                LatLng position1 = new LatLng(Double.parseDouble(coor[0]),Double.parseDouble(coor[1]));
                //mMap.clear();

                Marker m = markers.get(dataSnapshot.getKey().toString());
                if(m!=null){
                    m.setPosition(position1);
                    Toast.makeText(getApplicationContext(),"updation",Toast.LENGTH_SHORT);
                }
                else{
                    Marker m2 = mMap.addMarker(new MarkerOptions()
                            .position(position1)
                            .title(dataSnapshot.getKey().toString())
                            .snippet(dataSnapshot.getKey().toString())
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.images))
                    );
                    markers.put(dataSnapshot.getKey().toString(),m2);
                    float zoomLevel = 15.0f;

                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position1, zoomLevel));
                    Toast.makeText(getApplicationContext(),"new",Toast.LENGTH_SHORT);
                }



            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        /*firebase.child("location").child(user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String location = dataSnapshot.getValue().toString();
                String[] coor = location.split(" ");
                LatLng position1 = new LatLng(Double.parseDouble(coor[0]),Double.parseDouble(coor[1]));
                mMap.clear();
                mMap.addMarker(new MarkerOptions().position(position1).title(user_id));
                float zoomLevel = 11.0f;
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position1, zoomLevel));
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }

        });*/
    }
}
