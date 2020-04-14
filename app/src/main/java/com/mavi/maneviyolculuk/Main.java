package com.mavi.maneviyolculuk;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn_author = (Button)findViewById(R.id.btn_author);

        btn_author.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Main.this, Author.class);
                Main.this.startActivity(i);
                overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
            }
        });

        Button btn_book_items = (Button)findViewById(R.id.btn_book_items);
        btn_book_items.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(Main.this, Items.class);
                Main.this.startActivity(i2);
                overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
            }
        });
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                Main.this, AlertDialog.THEME_HOLO_LIGHT);

        alertDialog.setMessage("Uygulamadan çıkmak üzeresiniz");
        alertDialog.setPositiveButton("Tamam",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        finish();
                    }
                });
        alertDialog.setNegativeButton("İptal", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        }).show();

    }

}
