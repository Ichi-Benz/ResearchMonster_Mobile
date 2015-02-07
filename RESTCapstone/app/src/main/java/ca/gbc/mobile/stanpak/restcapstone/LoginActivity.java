package ca.gbc.mobile.stanpak.restcapstone;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;


public class LoginActivity extends Activity {

    EditText id, password;
    Button login;
    TextView errorMessage;

    private String resp;
    private String error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        id = (EditText)findViewById(R.id.editTxtStudentNum);
        password = (EditText)findViewById(R.id.editTxtPassword);

        login = (Button)findViewById(R.id.btnLogin);

        errorMessage = (TextView)findViewById(R.id.txtError);

        login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
                        postParameters.add(new BasicNameValuePair("id",id.getText().toString()));
                        postParameters.add(new BasicNameValuePair("password",password.getText().toString()));

                        String response = null;
                        try{
                            response = SimpleHttpClient.executeHttpPost("http://rm.solutionblender.ca/login",postParameters);
                            String res = response.toString();
                            resp = res.replaceAll("\\s+","");
                        } catch (Exception e){
                            e.printStackTrace();
                            error = e.getMessage();
                        }
                    }
                }).start();

                try {
                    if(resp.equals("200")) {
                        errorMessage.setText("Correct");
                    } else {
                        errorMessage.setText("Wrong");
                    }
                    if (null!= error && !error.isEmpty()) {
                        errorMessage.setText(error);
                    }
                } catch (Exception e) {
                    errorMessage.setText(e.getMessage());
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
