package thehub.life.fd;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by rajeshkumarsheela on 6/25/17.
 */

public class SERVER {

    private final static String REG_URL = "http://78.46.121.79/API/reguser.php";

    public static void REG_USER(final String Username, final String Email, final String Phone, final Context ctx) {

        class RegisterUser extends AsyncTask<String, Void, String> {
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                Log.d("Message",s);
                if (s.equalsIgnoreCase("1")){

                    SharedPreferences sp = ctx.getSharedPreferences("FD",0);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("login",s);
                    editor.apply();
                    editor.commit();

                    Intent intent = new Intent(ctx.getApplicationContext(),MapsActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ctx.startActivity(intent);

                }
            }

            @Override
            protected String doInBackground(String... params) {
                URL url;
                String response = "";
                String sql="";
                try {
                    url = new URL(REG_URL);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setReadTimeout(15000);
                    conn.setConnectTimeout(15000);
                    conn.setRequestMethod("POST");
                    conn.setDoInput(true);
                    conn.setDoOutput(true);

//            Preparing sql query
                    sql="username="+Username+"&password=password"+"&phone="+Phone+"&email="+Email;

                    OutputStream os = conn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                    writer.write(sql);

                    writer.flush();
                    writer.close();
                    os.close();
                    int responseCode=conn.getResponseCode();

                    if (responseCode == HttpsURLConnection.HTTP_OK) {
                        BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        response = br.readLine();
                       // Toast.makeText(ctx.getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        response = "1";
                    }
                    else {
                        response="Error Registering";
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

                return response;
            }
        }
        RegisterUser ru = new RegisterUser();
        ru.execute();
    }

}
