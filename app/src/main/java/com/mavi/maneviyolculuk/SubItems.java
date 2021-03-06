package com.mavi.maneviyolculuk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class SubItems extends AppCompatActivity {

    public DB _db;
    public SQLiteDatabase _dbObj;

    public ListView _listView;
    public String[] _arrBookItems;

    public String Title = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_items);

        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
        _db=new DB(this);
        Title = app_preferences.getString("Title", "");

        try{
            
            this.setTitle(Title);

            _db = new DB(this);

            _db.createDataBase();

            _dbObj = _db.getReadableDatabase();

            Cursor _crs = _dbObj.rawQuery("SELECT Id, ParentCategory, Title FROM tblContents Where ParentCategory='"+ Title +"' ORDER BY Id Asc", null);

            _arrBookItems = new String[_crs.getCount()];

            int i = 0;

            while (_crs.moveToNext()){

                _arrBookItems[i] = _crs.getString(_crs.getColumnIndex("Title"));

                i++;

            }

            _listView = (ListView)findViewById(R.id.listBookSubItems);

            _listView.setAdapter(new ArrayAdapter<String>(this, R.layout.list_white_text, _arrBookItems));

            _listView.setOnItemClickListener(new listClicked());

        } catch (IOException ioe) {

            System.out.println(String.valueOf(ioe));

            throw new Error("Unable to create database");

        }

    }

    private class listClicked implements AdapterView.OnItemClickListener
    {

        @Override
        public void onItemClick(AdapterView<?> Adapter, View view, int position,
                                long arg3) {

            _setValue(_arrBookItems[position]);

            Intent i = new Intent(SubItems.this, Contents.class);
            SubItems.this.startActivity(i);
            overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);

        }

    }

    public void _setValue(String _tmpId)
    {
        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = app_preferences.edit();
        editor.putString("Category", _tmpId);
        editor.commit(); // Very important
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sub_items, menu);
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
