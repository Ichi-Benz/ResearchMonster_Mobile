package ca.gbc.mobile.ianpadilla.research_monster;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;
import android.view.View.OnClickListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;


public class AllProjects extends ActionBarActivity implements OnClickListener {

    ListView lvProjects;

    final String URL_RM="http://research.solutionblender.ca/projects?mobile=1";
    HttpResponse resp;
    private String errorMessage;
    ArrayList<String> titles;
    CustomListAdapter adapter;
    AllProjects mProject = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_projects);


        lvProjects = (ListView)findViewById(R.id.listProjects);
        titles = new ArrayList<String>();
        adapter = new CustomListAdapter(this, R.layout.custom_list, titles);
        lvProjects.setAdapter(adapter);

        new GetProjectsAsyncTask().execute();

    }
    public class GetProjectsAsyncTask extends AsyncTask<String , Void, ArrayList<String>>{

        @Override
        protected ArrayList<String> doInBackground(String... params) {
            ArrayList<String> arrItem = new ArrayList<String>();
            try {

                resp = SimpleHttpClient.executeHttpGet(URL_RM);
                HttpEntity res = resp.getEntity();


                String jsonString = convertStreamToString(res.getContent(), "UTF-8");
                Log.d("JSON", jsonString);
                JSONObject row;
                JSONArray array = new JSONArray(jsonString);

                for (int i = 0; i < array.length(); i++) {
                    row = array.getJSONObject(i);
                    String item = row.getString("title");
                    Log.w("item", item);
                    arrItem.add(item);
                }
                return arrItem;
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
        protected void onPostExecute(ArrayList<String> arrItem){
            super.onPostExecute(arrItem);
            titles.addAll(arrItem);
            adapter.notifyDataSetChanged();
        }
    }
    public static String convertStreamToString( InputStream is, String ecoding ) throws IOException
    {
        StringBuilder sb = new StringBuilder( Math.max( 16, is.available() ) );
        char[] tmp = new char[ 4096 ];

        try {
            InputStreamReader reader = new InputStreamReader( is, ecoding );
            for( int cnt; ( cnt = reader.read( tmp ) ) > 0; )
                sb.append( tmp, 0, cnt );
        } finally {
            is.close();
        }
        return sb.toString();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_all_projects, menu);
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

    @Override
    public void onClick(View v) {

    }
    public void fillString(){
        titles.add("Android App for Ryerson");
        titles.add("Kitty Treat Dispenser");
        titles.add("Web Design for Prestige Worldwide");
        titles.add("Web Design for Prestige Worldwide");
        titles.add("Android App for Ryerson");
    }
}
