package jswadia.msse.asu.edu.booksie;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.io.File;

/**
 * Created by japas_000 on 4/22/2015.
 * Copyright 2015 Japa Swadia
 * Right to Use: Public
 * Purpose: SER 598 Mobile Systems course, Android app development, Final Project
 *
 * @author Japa Swadia, Japa.Swadia@asu.edu,
 *         Graduate Student, Software Engineering, CIDSE,
 *         Arizona State University
 * @version 22 Apr 2015
 */
public class MoodActivity extends ActionBarActivity {

    public ExpandableListView elview;
    public String selectedStudent;
    private ExpandableStuffAdapter myListAdapter;
    public TextView username;
    public String user;
    //public TextView moodname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood);

        elview = (ExpandableListView)findViewById(R.id.lvExp);
        myListAdapter = new ExpandableStuffAdapter(this);
        elview.setAdapter(myListAdapter);
        username = (TextView)findViewById(R.id.userTV);
        //moodname = (TextView)findViewById(R.id.userTV);


        Bundle extras = getIntent().getExtras();
        user = extras.getString("username");
        username.setText(user);


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

    @Override
    public void onBackPressed()
    {

        MoodActivity.this.finish();

    }

    public void logoutClicked(View view)
    {
        Log.d("MoodActivity", "logout clicked");
        Intent i = new Intent(MoodActivity.this, LoginActivity.class);
        startActivity(i);
        MoodActivity.this.finish();



    }

}
