package thehub.life.fd;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getSupportActionBar().hide();
        //Thread for splashScreen
       new Thread(){
            @Override
            public void run() {
                try{
                    sleep(3000);
                    Intent it = new Intent(getApplicationContext(), signup.class);
                    startActivity(it);
                    finish();
                }catch (Exception e){
                    Log.e("Thread Splash Screen",e.getMessage());
                }
            }
        }.start();




    }
}
