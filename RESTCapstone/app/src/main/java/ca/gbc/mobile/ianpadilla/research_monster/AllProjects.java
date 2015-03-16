package ca.gbc.mobile.ianpadilla.research_monster;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;


public class AllProjects extends ActionBarActivity {

    ListView lvProjects;

    ArrayList<Object> projects = new ArrayList<>();

    final String URL_RM="http://rm.solutionblender.ca/projects";
    String resp;
    private String errorMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_projects);

        lvProjects = (ListView)findViewById(R.id.listProjects);
        //ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,res);
        //lvProjects.setAdapter();
        ArrayList<NameValuePair> nvp = new ArrayList<>();
        nvp.add(new BasicNameValuePair("mobile", "1"));
        try{
            //Takes response from web server, then converts it to string
            resp = SimpleHttpClient.executeHttpPost(URL_RM,nvp);
            String res = resp.toString();
            resp = res.replaceAll("\\s+","");
        }catch(Exception e){
            e.printStackTrace();
            errorMessage = e.getMessage();
        }

        try{
            JSONObject jsonObj = new JSONObject(resp);
            String[] project = new String[4];
            while(jsonObj.length() != -1){
                String title = jsonObj.getString("title");
                String desc = jsonObj.getString("description");
                String created_at = jsonObj.getString("created_at");
                String updated_at = jsonObj.getString("updated_at");

                project[0] = title;
                project[1] = desc;
                project[2] = created_at;
                project[3] = updated_at;

                projects.add(project);
            }
        }catch(Exception e){

        }
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
}
