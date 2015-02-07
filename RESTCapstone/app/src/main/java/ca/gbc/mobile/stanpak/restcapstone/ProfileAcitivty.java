package ca.gbc.mobile.stanpak.restcapstone;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import ca.gbc.mobile.stanpak.restcapstone.RoundImage;


public class ProfileAcitivty extends Activity {

    ImageView imageView1;
    RoundImage roundedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_acitivty);
        imageView1 = (ImageView) findViewById(R.id.imageStudent);
        Bitmap bm = BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher);
        roundedImage = new RoundImage(bm);
        imageView1.setImageDrawable(roundedImage);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile_acitivty, menu);
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
