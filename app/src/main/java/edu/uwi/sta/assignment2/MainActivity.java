package edu.uwi.sta.assignment2;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;


public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSION_CODE = 234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean hasPermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        if(!hasPermission){
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSION_CODE);
        }else{
            if(!isServiceSet())
                startBackgroundProcess();
        }

        ListView lv = (ListView)findViewById(R.id.menu_list);
        if (lv != null) {
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("itemid", position);
                    if(position==0){
//                        String message = String.valueOf(position);
//                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(MainActivity.this, AccelerometerActivity.class);
                        i.putExtras(bundle);
                        startActivity(i);
                    }
                    if(position==1){
                        Intent i = new Intent(MainActivity.this, GyroscopeActivity.class);
                        i.putExtras(bundle);
                        startActivity(i);
                    }
                    if(position==2){
                        Intent i = new Intent(MainActivity.this, ProximityActivity.class);
                        i.putExtras(bundle);
                        startActivity(i);
                    }
                    if(position==3){
                        Intent i = new Intent(MainActivity.this, GPSActivity.class);
                        i.putExtras(bundle);
                        startActivity(i);
                    }
                    if(position==4){
                        Intent i = new Intent(MainActivity.this, BluetoothActivity.class);
                        i.putExtras(bundle);
                        startActivity(i);
                    }
                    if(position==5){
                        Intent i = new Intent(MainActivity.this, AllStoredLocations.class);
                        i.putExtras(bundle);
                        startActivity(i);
                    }
                }
            });
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    public boolean isServiceSet(){
        SharedPreferences pref = getSharedPreferences("DCIT", Context.MODE_PRIVATE);
        return pref.getBoolean("service_set", false);
    }

    public void startBackgroundProcess(){
        LocationReciever.setUpService(this);
        SharedPreferences.Editor editor = getSharedPreferences("DCIT",Context.MODE_PRIVATE).edit();
        editor.putBoolean("service_set", true).apply();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        super.onRequestPermissionsResult(requestCode,permissions,grantResults);
        switch (requestCode){
            case MY_PERMISSION_CODE:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Intent i = getIntent();
                    finish();
                    startActivity(i);
                }else{
                    Toast.makeText(this, "Activity was not allowed to Access GPS... existing", Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }
    }
}
