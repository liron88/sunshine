package com.example.android.sunshine.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {

    private final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
            Context context = this;
            Intent settingsIntent = new Intent(context, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        if (id == R.id.action_map) {
            showMap();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showMap() {
        final String QUERY_PARAMS = "q";

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String locationValue = sharedPref.getString(getString(R.string.pref_location_key),
                getString(R.string.pref_location_default));

        // Construct a URI for location on map
        Uri.Builder uri = Uri.parse("geo:0,0?").buildUpon();
        // attach the address
        uri.appendQueryParameter(QUERY_PARAMS, locationValue);

        // implicit intent to launch map app
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(uri.build());
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d(LOG_TAG, "Couldn't call " + locationValue + ", no receiving apps installed!");
        }
    }

}
