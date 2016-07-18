package jswadia.msse.asu.edu.booksie;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
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
public class ExpandableStuffAdapter extends BaseExpandableListAdapter
        implements View.OnTouchListener,

        ExpandableListView.OnGroupExpandListener,

        ExpandableListView.OnGroupCollapseListener {
    public TextView currentSelectedTV = null;
    public MoodActivity parent;
    public TextView username;


    private LinkedHashMap<String,String[]> model;

    public ExpandableStuffAdapter(MoodActivity parent) {
        android.util.Log.d(this.getClass().getSimpleName(),"in constuctor so creating new model");
        this.model = new LinkedHashMap<String, String[]>();
        this.parent = parent;
        parent.elview.setOnGroupExpandListener(this);
        parent.elview.setOnGroupCollapseListener(this);
        setModelFromDB();
    }

    private void setModelFromDB(){
        try{
            model.clear();
            BookDB bookdb = new BookDB((Context)parent);

            bookdb.copyDB();
            SQLiteDatabase bkdb = bookdb.openDB();
            bkdb.beginTransaction();
            Cursor cur = bkdb.rawQuery("select moodname from mood;",new String[]{});
            while (cur.moveToNext()){
                String bkmood = cur.getString(0);
                Cursor moods = bkdb.rawQuery(
                        "select description from mood where moodname='"+bkmood+"'",null);

                ArrayList<String> moodArr = new ArrayList<String>();
                while(moods.moveToNext()){
                    moodArr.add(moods.getString(0));
                }
                model.put(bkmood, moodArr.toArray(new String[]{}));
                android.util.Log.d(this.getClass().getSimpleName(),"adding to model: "+
                        bkmood+" with "+ moodArr.toArray(new String[]{}).length+
                        " moods.");
                moods.close();
            }
            cur.close();
            bkdb.endTransaction();
            bkdb.close();
            bookdb.close();
        } catch (Exception ex){
            android.util.Log.w(this.getClass().getSimpleName(),"Exception creating adapter: "+
                    ex.getMessage());
        }
    }

    private void resetModel(LinkedHashMap<String,String[]> aModel){
        setModelFromDB();
        this.notifyDataSetChanged();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        String[] stuffTitles = model.keySet().toArray(new String[] {});
        return model.get(stuffTitles[groupPosition])[childPosition];
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = (String) getChild(groupPosition, childPosition);
        if (convertView == null) {
            android.util.Log.d(this.getClass().getSimpleName(),"in getChildView null so creating new view");
            LayoutInflater inflater = (LayoutInflater) this.parent
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, null);
        }
        TextView txtListChild = (TextView)convertView.findViewById(R.id.lblListItem);
        convertView.setOnTouchListener(this);
        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String[] stuffTitles = model.keySet().toArray(new String[] {});
        //android.util.Log.d(this.getClass().getSimpleName(),"in getChildrenCount for: "+groupPosition+" returning: "+
        //                   model.get(stuffTitles[groupPosition]).length);
        return model.get(stuffTitles[groupPosition]).length;
    }

    @Override
    public Object getGroup(int groupPosition) {
        String[] stuffTitles = model.keySet().toArray(new String[] {});
        //android.util.Log.d(this.getClass().getSimpleName(),"in getGroup returning: "+stuffTitles[groupPosition]);
        return stuffTitles[groupPosition];
    }

    @Override
    public int getGroupCount() {
        String[] stuffTitles = model.keySet().toArray(new String[] {});
        //android.util.Log.d(this.getClass().getSimpleName(),"in getGroupCount returning: "+stuffTitles.length);
        return stuffTitles.length;
    }

    @Override
    public long getGroupId(int groupPosition) {
        //android.util.Log.d(this.getClass().getSimpleName(),"in getGroupPosition returning: "+groupPosition);
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String)getGroup(groupPosition);
        if (convertView == null) {
            android.util.Log.d(this.getClass().getSimpleName(),"in getGroupView null so creating new view");
            LayoutInflater inflater = (LayoutInflater) this.parent
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
        //android.util.Log.d(this.getClass().getSimpleName(),"in getGroupView text is: "+lblListHeader.getText());
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);
        //ImageView img = (ImageView)convertView.findViewById(R.id.group_image);
        //img.setImageResource(R.drawable.ic_book);
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        //android.util.Log.d(this.getClass().getSimpleName(),"in hasStableIds returning false");
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        String[] stuffTitles = model.keySet().toArray(new String[] {});
        //android.util.Log.d(this.getClass().getSimpleName(),"in isChildSelectable?  "+
        //                   model.get(stuffTitles[groupPosition])[childPosition]);
        return true;
    }

    public boolean onTouch(View v, MotionEvent event){
        // when the user touches a song, onTouch is called once for action down and again for action up
        // we only want to do something on one of those actions. event tells us which action.
        if (event.getAction()==MotionEvent.ACTION_DOWN){
            android.util.Log.d(this.getClass().getSimpleName(),"in onTouch called for view of type: " +
                    v.getClass().toString());
            // onTouch is passed the textview's parent view, a linearlayout since thats what we set the
            // event on. Look at its children to find the textview
            String mood = (String) ((TextView)((View)v.getParent()).findViewById(R.id.lblListHeader)).getText();

//                android.util.Log.d(this.getClass().getSimpleName(),"TextView "+((TextView)layView.getChildAt(i)).getText()+" selected.");
                Intent intent = new Intent(parent, BookActivity.class);
                intent.putExtra(parent.getString(R.string.selected), mood);
                //intent.putExtra("current", user);
                parent.startActivity(intent);
                parent.finish();
            }
            // code below never executes. onTouch is called on for textview's linearlayout parent
            if(v instanceof TextView){
                android.util.Log.d(this.getClass().getSimpleName(),"in onTouch called for: " +
                        ((TextView)v).getText());
            }

        return true;
    }

    public void onGroupExpand(int groupPosition){
        android.util.Log.d(this.getClass().getSimpleName(),"in onGroupExpand called for: "+
                model.keySet().toArray(new String[] {})[groupPosition]);
        if (currentSelectedTV != null){
            currentSelectedTV.setBackgroundColor(parent.getResources().getColor(R.color.light_blue));
            currentSelectedTV = null;
        }
        for (int i=0; i< this.getGroupCount(); i++) {
            if(i != groupPosition){
                parent.elview.collapseGroup(i);
            }
        }
    }

    public void onGroupCollapse(int groupPosition){
        android.util.Log.d(this.getClass().getSimpleName(),"in onGroupCollapse called for: "+
                model.keySet().toArray(new String[] {})[groupPosition]);
        if (currentSelectedTV != null){
            currentSelectedTV.setBackgroundColor(parent.getResources().getColor(R.color.light_blue));
            currentSelectedTV = null;
        }
    }

}
