package jswadia.msse.asu.edu.booksie;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by japas_000 on 4/29/2015.
 * Copyright 2015 Japa Swadia
 * Right to Use: Public
 * Purpose: SER 598 Mobile Systems course, Android app development, Final Project
 *
 * @author Japa Swadia, Japa.Swadia@asu.edu,
 *         Graduate Student, Software Engineering, CIDSE,
 *         Arizona State University
 * @version 29 Apr 2015
 */
public class BookActivity extends ActionBarActivity {

    public ListView lview;
    private BookListAdapter bookListAdapter;
    public String selectedBook;
    public TextView bknm;
    public TextView usrnm;
    private ArrayList<String> list = new ArrayList<String>();

    ArrayList<String> moodArr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booklist);
        bknm = (TextView) findViewById(R.id.moodTV);
        usrnm = (TextView) findViewById(R.id.userTV2);
        Bundle b = getIntent().getExtras();
        String selectedMood = b.getString("selected");
        bknm.setText(selectedMood);
        String currentuser = b.getString("current");
        usrnm.setText(currentuser);
        lview = (ListView) findViewById(R.id.bklist);
        //list.add(0, selectedMood);
        moodArr = new ArrayList<String>();

        try {
            //bmodel.clear();
            BookDB bookdb = new BookDB(this);

            bookdb.copyDB();
            SQLiteDatabase bkdb = bookdb.openDB();
            bkdb.beginTransaction();
//            moodnm = objects.get(0);
            Cursor cur = bkdb.rawQuery("select bookname from book where bookID in (select bookID from relate where moodID in (select moodID from mood where moodname = ?));", new String[]{selectedMood});
            while (cur.moveToNext()) {
                String booknm = cur.getString(0);

                moodArr.add(booknm);

                //bmodel.put(booknm, moodArr.toArray(new String[]{}));
                android.util.Log.d(this.getClass().getSimpleName(), "adding to model: " +
                        booknm + " at " + moodArr.toArray(new String[]{}).length +
                        " position.");
                //moods.close();
            }
            cur.close();
            bkdb.endTransaction();
            bkdb.close();
            bookdb.close();

            //Intent i = new Intent(this, BookActivity.class);


        } catch (Exception ex) {
            android.util.Log.w(this.getClass().getSimpleName(), "Exception creating adapter: " +
                    ex.getMessage());
        }
        bookListAdapter = new BookListAdapter(BookActivity.this, android.R.layout.simple_list_item_1, moodArr);
        lview.setAdapter(bookListAdapter);

        lview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onClick(View v) {
                /*String book = (String) ((TextView)((View)v.getParent()).findViewById(R.id.lblListItem2)).getText();
                Intent intent = new Intent(
                        BookActivity.this,
                        DetailsActivity.class
                );
                intent.putExtra(BookActivity.this.getString(R.string.selected), book);
                startActivity(intent);*/
            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                System.out.println(moodArr.get(position));

                String book = moodArr.get(position);
                Intent intent = new Intent(
                        BookActivity.this,
                        DetailsActivity.class
                );
                intent.putExtra(BookActivity.this.getString(R.string.selected), book);
                startActivity(intent);
                Toast t = Toast.makeText(getApplicationContext(), "view book details", Toast.LENGTH_SHORT);
                t.show();
            }
        });
        /*lview.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast t = Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_SHORT);
                t.show();
                android.util.Log.w(this.getClass().getSimpleName(),"In on itm selected");
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast t = Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_SHORT);
                t.show();
            }
        });*/
        //username = (TextView)findViewById(R.id.userTV);


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

    public void logoutBook(View view)
    {

        Log.d("BookActivity", "logout clicked");
        Intent i = new Intent(BookActivity.this, LoginActivity.class);
        startActivity(i);



    }

    @Override
    public void onBackPressed()
    {

        BookActivity.this.finish();

    }


}
