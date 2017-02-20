package edu.uwi.sta.assignment2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Currency;

import edu.uwi.sta.assignment2.models.DBHelper;
import edu.uwi.sta.assignment2.models.LocationContract;

public class AllStoredLocations extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_stored_locations);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final ListView lv = (ListView)findViewById(R.id.locations);
        final Context context = this;
        final ArrayList<edu.uwi.sta.assignment2.Location> locations = new ArrayList<>();
        final String [] fields = new String[]{
                LocationContract.LocationEntry._ID, LocationContract.LocationEntry.LAT, LocationContract.LocationEntry.LON
        };
        final ArrayAdapter<edu.uwi.sta.assignment2.Location> adapter = new ArrayAdapter<>(context,android.R.layout.simple_list_item_1);

        lv.setAdapter(adapter);
        (new Thread(new Runnable() {
            @Override
            public void run() {
                SQLiteOpenHelper helper = new DBHelper(context);
                SQLiteDatabase db = helper.getReadableDatabase();
                String sortedOrder = LocationContract.LocationEntry._ID + " DESC";

                final Cursor res = db.query(LocationContract.LocationEntry.TABLE_NAME,fields,null,null,null,null,sortedOrder);
                while (res.moveToNext()){
                    final int ID = res.getInt(res.getColumnIndex(LocationContract.LocationEntry._ID));
                    final Double locLat = res.getDouble(res.getColumnIndex(LocationContract.LocationEntry.LAT));
                    final Double locLon = res.getDouble(res.getColumnIndex(LocationContract.LocationEntry.LON));
                    final edu.uwi.sta.assignment2.Location loc = new edu.uwi.sta.assignment2.Location(ID,locLon,locLat);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            locations.add(loc);
                        }
                    });
                }
                db.close();
            }
        })).start();

    }

}
