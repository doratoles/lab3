package com.example.lab3project;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.content.Intent;
import android.app.Activity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import java.util.ArrayList;
import java.util.Map;
import java.util.List;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements ExpandableListView.OnChildClickListener {

    private static final String TAG = "MAIN TAG";
    private List<Group> groups = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        createGroups();
        ExpandableListView listView = findViewById(R.id.DemoListView);
        SimpleExpandableListAdapter expListAdapter =
                new SimpleExpandableListAdapter(
                        this,
                        createGroupsList(), //создание group списка
                        R.layout.group_row, //group item layout xml
                        new String[] {"Group Item"}, //key of the group item
                        new int[] {R.id.row_name}, //id groups item

                        createChildList(), //create childsdata describe seond-level data
                        R.layout.child_row, //sub-level layout
                        new String[] {"Sub Item"}, //keys in childdata mapx to display
                        new int[] {R.id.child_row});

        listView.setAdapter(expListAdapter);
        listView.setOnChildClickListener(this);
    }

    private void createGroups() {

        Group webG = new Group("Web");
        Group mapG = new Group("Map");
        Group telG = new Group("Phone");

        webG.addChild("Molfar Tech",WebActivity.class);
        mapG.addChild("Display Object on Map",MapActivity.class);
        telG.addChild("telephone",PhoneActivity.class);

        groups.add(webG);
        groups.add(mapG);
        groups.add(telG);
    }

    @Override
    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

        Group g = groups.get(groupPosition);

        Class<Activity> action = g.actions.get(childPosition);
        if (action != null) {
            Intent intent = new Intent(this,action);
            startActivity(intent);
        }

        return true;
    }

    private static class Group {
        String name;
        List<String> children = new ArrayList<String>();
        List<Class<Activity>> actions = new ArrayList<Class<Activity>>();

        private Group(String name) {
            this.name = name;
        }

        public void addChild(String name, Class action) {
            children.add(name);
            actions.add(action);
        }
    }
    private List<Map<String,String>> createGroupsList() {
        List<Map<String,String>> result = new ArrayList<Map<String,String>>();
        for(Group g : groups) {
            Map<String,String> m = new HashMap<String, String>();
            m.put("Group Item", g.name);
            result.add(m);
        }

        return result;
    }

    private List<List<Map<String,String>>> createChildList() {
        List<List<Map<String,String>>> result = new ArrayList<List<Map<String,String>>>();

        for (Group g: groups) {
            List<Map<String,String>> secList = new ArrayList<Map<String,String>>();
            for (String c: g.children) {
                Map<String,String> child = new HashMap<String,String>();
                child.put("Sub Item", c);
                secList.add(child);
            }
            result.add(secList);
        }

        return result;
    }

    @Override
    public void onContentChanged() {
        Log.i(TAG, "onContentChanged");
        super.onContentChanged();
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
