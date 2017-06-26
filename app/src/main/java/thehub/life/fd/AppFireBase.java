package thehub.life.fd;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by rajeshkumarsheela on 6/26/17.
 */

public class AppFireBase extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}