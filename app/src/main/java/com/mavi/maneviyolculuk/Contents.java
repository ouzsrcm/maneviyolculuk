package com.mavi.maneviyolculuk;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.alemdar.seyrusuluk.R;

public class Contents extends ActionBarActivity {

    String Category = "";
    private DB _db;

    final static float STEP = 70;
    private TextView _tv, textview;
    float mRatio = 1.0f;
    int mBaseDist;
    float mBaseRatio;
    float fontsize = 13;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contents);

        SharedPreferences app_preferences = PreferenceManager.getDefaultSharedPreferences(this);
        _db=new DB(this);

        Category = app_preferences.getString("Category", "");

        Cursor _crs = _getRecord(Category);

        if (_crs != null)
        {

            this.setTitle(Category);

            _crs.moveToFirst();

            _tv = (TextView)findViewById(R.id.tvItemContent);
            _tv.setTextIsSelectable(true);
            _tv.setText(Html.fromHtml(_crs.getString(0)));

        }

        _tv.setTextSize(mRatio + 13);

    }

    private String[] SELECT = {"Content"};

    private Cursor _getRecord(String Baslik){

        SQLiteDatabase db = _db.getReadableDatabase();
        Cursor cursor = db.query("tblContents", SELECT,"Title='"+ Baslik+"'", null, null, null, null);
        return cursor;

    }

    public boolean onTouchEvent(MotionEvent event) {

        //max de�er atama
        _tv = (TextView)findViewById(R.id.tvItemContent);

            if (event.getPointerCount() == 2) {
                int action = event.getAction();
                int pureaction = action & MotionEvent.ACTION_MASK;
                if (pureaction == MotionEvent.ACTION_POINTER_DOWN) {
                    mBaseDist = getDistance(event);
                    mBaseRatio = mRatio;
                } else {
                    float delta = (getDistance(event) - mBaseDist) / STEP;
                    float multi = (float) Math.pow(2, delta);
                    mRatio = Math.min(1024.0f, Math.max(0.1f, mBaseRatio * multi));
                    _tv.setTextSize(mRatio + 13);
                }
            }

        return true;

    }

    int getDistance(MotionEvent event) {
        int dx = (int) (event.getX(0) - event.getX(1));
        int dy = (int) (event.getY(0) - event.getY(1));
        return (int) (Math.sqrt(dx * dx + dy * dy));
    }

    public boolean onTouch(View v, MotionEvent event) {

        return false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.contents, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        TextView _tempTv = (TextView)findViewById(R.id.tvItemContent);
        _tempTv.setTextIsSelectable(true);
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent i = new Intent(Contents.this, Items.class);
            Contents.this.startActivity(i);
        } /*else if (id == R.id.fontsize13){
            _tempTv.setTextSize(13);
        } else if (id == R.id.fontsize15){
            _tempTv.setTextSize(15);
        } else if (id == R.id.fontsize17){
            _tempTv.setTextSize(17);
        } else if (id == R.id.fontsize19){
            _tempTv.setTextSize(19);
        } else if (id == R.id.fontsize21){
            _tempTv.setTextSize(21);
        }*/

        return super.onOptionsItemSelected(item);
    }
}
