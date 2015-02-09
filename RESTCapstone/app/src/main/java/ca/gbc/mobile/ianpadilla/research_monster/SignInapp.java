package ca.gbc.mobile.ianpadilla.research_monster;
//http://www.javacodegeeks.com/2010/10/android-full-app-part-2-using-http-api.html
//http://www.coderanch.com/t/571797/Android/Mobile/Android-Application-Login

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class SignInapp extends ActionBarActivity {

    EditText id, password;
    TextView error;
    Button login;
    private String resp;
    private String errorMessage;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_inapp);

        id = (EditText) findViewById(R.id.student_id);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login_button);
        error = (TextView) findViewById(R.id.errorText);

        /*login.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
                postParameters.add(new BasicNameValuePair("id",id.getText().toString()));
                postParameters.add(new BasicNameValuePair("password",password.getText().toString()));

                try{
                    httpPost.setEntity(new UrlEncodedFormEntity(postParameters));
                }catch(UnsupportedEncodingException e){
                    e.printStackTrace();
                }
                try{
                    HttpResponse response = httpClient.execute(httpPost);
                    int code = response.getStatusLine().getStatusCode();
                    if(code == 200){
                        error.setText("Nice");
                    }
                    else{
                        error.setText(code);
                    }
                }catch(ClientProtocolException e){
                    e.printStackTrace();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        });*/




        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new Thread(new Runnable(){
                   public void run() {
                       final String URL_RM="http://rm.solutionblender.ca/index.php/login";
                       ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
                       postParameters.add(new BasicNameValuePair("id",id.getText().toString()));
                       postParameters.add(new BasicNameValuePair("password",password.getText().toString()));
                       postParameters.add(new BasicNameValuePair("mobile","20 dicks"));
                       String response;
                       try{
                           response = SimpleHttpClient.executeHttpPost(URL_RM,postParameters);
                           /*if(response =200){
                               error.setText("Cool you logged in lel");
                           }
                           else{
                               error.setText(response);
                           }*/
                           String res = response.toString();
                           resp = res.replaceAll("\\s+","");
                       }catch(Exception e){
                            e.printStackTrace();
                           errorMessage = e.getMessage();
                       }
                   }
                }).start();
                try{
                    /*if(code == 200){
                        error.setText("Nice");
                    }
                    else{
                        error.setText(code);
                    }*/
                    Log.w("faggot",errorMessage + "1");
                    Log.w("faggot",resp + "1");

                    if(resp.equals("1")){
                        error.setText(resp.toString());
                    }else{
                        error.setText(resp.toString());
                    }
                    if(null != errorMessage && !errorMessage.isEmpty()){
                        error.setText(errorMessage);
                    }
                }catch(Exception e){
                    error.setText(e.getMessage());
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_inapp, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
