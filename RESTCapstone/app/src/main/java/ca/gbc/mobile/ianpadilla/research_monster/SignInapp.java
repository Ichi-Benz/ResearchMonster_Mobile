package ca.gbc.mobile.ianpadilla.research_monster;
//http://www.javacodegeeks.com/2010/10/android-full-app-part-2-using-http-api.html
//http://www.coderanch.com/t/571797/Android/Mobile/Android-Application-Login

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import android.content.Intent;
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
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class SignInapp extends ActionBarActivity {

    //UI Elements
    EditText id, password;
    TextView error;
    Button login;
    private String resp;
    private String errorMessage;

    //JSON Node names
    private static final String TAG_ID = "id";
    private static final String TAG_STUDENT_ID = "student_id";
    private static final String TAG_EMAIL = "email";
    private static final String TAG_AVATAR = "avatar";
    private static final String TAG_SUMMARY = "summary";
    private static final String TAG_EXPERIENCE = "experience";
    private static final String TAG_PERMISSIONS = "permissions";
    private static final String TAG_ACTIVATED = "activated";
    private static final String TAG_ACTIVATED_AT = "activated_at";
    private static final String TAG_LAST_LOGIN = "last_login";
    private static final String TAG_FIRST_NAME = "first_name";
    private static final String TAG_LAST_NAME = "last_name";
    private static final String TAG_CREATED_AT = "created_at";
    private static final String TAG_UPDATED_AT = "updated_at";

    //JSONArray
    JSONArray user = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_inapp);

        id = (EditText) findViewById(R.id.student_id);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login_button);
        error = (TextView) findViewById(R.id.errorText);

        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Start thread
                new Thread(new Runnable(){

                   public void run(){

                       final String URL_RM="http://rm.solutionblender.ca/login";
                       ArrayList<NameValuePair> postParameters = new ArrayList<>();
                       postParameters.add(new BasicNameValuePair("id",id.getText().toString()));
                       postParameters.add(new BasicNameValuePair("password",password.getText().toString()));
                       postParameters.add(new BasicNameValuePair("mobile","android"));

                       String response;

                       try{
                           response = SimpleHttpClient.executeHttpPost(URL_RM,postParameters);
                           String res = response.toString();
                           resp = res.replaceAll("\\s+","");
                       }catch(Exception e){
                            e.printStackTrace();
                           errorMessage = e.getMessage();
                       }
                    }
                }).start();

                try{
                    //Log Responses for error check
                    //Log.w("Response: ",errorMessage + "1");
                    //Log.w("Response: ",resp + "1");

                    if(resp.startsWith("<")){
                        error.setText("Incorrect id or password.");
                    }else{
                        JSONObject jsonObj = new JSONObject(resp);
                        String student_id = jsonObj.getString(TAG_STUDENT_ID);
                        String email = jsonObj.getString(TAG_EMAIL);
                        String summary = jsonObj.getString(TAG_SUMMARY);
                        String first_name = jsonObj.getString(TAG_FIRST_NAME);
                        String last_name = jsonObj.getString(TAG_LAST_NAME);

                        Intent intent = new Intent(SignInapp.this, Profile.class);

                        intent.putExtra("student_id",student_id);
                        intent.putExtra("email",email);
                        intent.putExtra("summary",summary);
                        intent.putExtra("first_name",first_name);
                        intent.putExtra("last_name",last_name);

                        startActivity(intent);
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
