package jswadia.msse.asu.edu.booksie;

import android.app.job.JobInfo;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by japas_000 on 4/29/2015.
 * Copyright 2015 Japa Swadia
 * Right to Use: Public
 * Purpose: SER 598 Mobile Systems course, Android app development
 *
 * @author Japa Swadia, Japa.Swadia@asu.edu,
 *         Graduate Student, Software Engineering, CIDSE,
 *         Arizona State University
 * @version 29 Apr 2015
 *
 */
public class BookListAdapter extends ArrayAdapter<String> implements Serializable{

    //HashMap<String, String[]> bmodel;
    public BookActivity parent;
    public TextView currentSelectedTV = null;
    public String selectedM;
    public String moodnm;
    private Context context;




    public BookListAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);
//        Intent i =getIntent();
        android.util.Log.d(this.getClass().getSimpleName(),"in constructor so creating new model");
        //this.bmodel = new HashMap<>();
        this.parent = parent;
        this.context=context;
        setModelFromDB(objects);



    }


    private void setModelFromDB(List<String> objects){

    }

    private void resetModel(LinkedHashMap<String,String[]> aModel){
        List<String> objects=null;
        setModelFromDB(objects);
        this.notifyDataSetChanged();
    }


    @Override
    public long getItemId(int position) {
        //String item = getItem(position);
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }






}
