package thehub.life.fd;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class signup extends AppCompatActivity {
    private Button submit;
    private EditText name,phone,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();

        name = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.phone);
        email = (EditText) findViewById(R.id.email);
        submit=(Button)findViewById(R.id.signup);

        SharedPreferences sp = getSharedPreferences("FD",0);
        String data = sp.getString("user_id",null);

        if(data!=null)
        {
            Intent it=new Intent(getApplicationContext(),MapsActivity.class);
            startActivity(it);
            finish();
        }


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sp = getSharedPreferences("FD",0);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("user_id",phone.getText().toString());
                editor.apply();
                editor.commit();

                Intent intent = new Intent(getApplicationContext(),MapsActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

                /*SERVER.REG_USER(
                        name.getText().toString().toLowerCase(),
                        email.getText().toString().toLowerCase(),
                        phone.getText().toString(),
                       getApplicationContext()
                );*/

            }
        });

    }
}
