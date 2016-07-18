package jswadia.msse.asu.edu.booksie;

import android.content.ContentValues;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by japas_000 on 4/23/2015.
 * Copyright 2015 Japa Swadia
 * Right to Use: Public
 * Purpose: SER 598 Mobile Systems course, Android app development
 *
 * @author Japa Swadia, Japa.Swadia@asu.edu,
 *         Graduate Student, Software Engineering, CIDSE,
 *         Arizona State University
 * @version 23 Apr 2015
 */
public class SignupActivity extends ActionBarActivity {

    private Button signup;
    private EditText uname, pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signup = (Button)findViewById(R.id.button);
        uname = (EditText)findViewById(R.id.editText);
        pwd = (EditText)findViewById(R.id.editTextPwd);
    }

    public void signup(View v)
    {
        try {


            System.out.print("in try block of insertion");
            String unm = uname.getText().toString();
            String pw = pwd.getText().toString();

            if (!unm.equals(" ") && !pw.equals(" ")) {


                Log.w("SignupActivity", "adding new user");
                BookDB bkDB = new BookDB((Context) this);
                bkDB.copyDB();
                SQLiteDatabase bDB = bkDB.openDB();
                bDB.beginTransaction();
                ContentValues cv = new ContentValues();
                cv.put("username", unm);
                cv.put("password", pw);



                bDB.insert("user", null, cv);

                Log.w("SignupActivity", "signup successful");

                bDB.endTransaction();
                bDB.close();
                bkDB.close();

                Toast toast = Toast.makeText(getApplicationContext(), "You have successfully signed up for Booksie!", Toast.LENGTH_LONG);
                toast.show();

                Intent i = new Intent(SignupActivity.this, MoodActivity.class);
                startActivity(i);
            }

            else {
                Toast toast1 = Toast.makeText(getApplicationContext(), "Username/Password Required!", Toast.LENGTH_SHORT);
                toast1.show();
                }
        }

        catch (Exception e)
            {
                e.getMessage();
                e.printStackTrace();
            }


        }

    @Override
    public void onBackPressed()
    {

        SignupActivity.this.finish();

    }

    public void goBack(View view)
    {
        Log.d("SignupActivity", "goBack clicked");
        Intent i = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(i);


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


}
