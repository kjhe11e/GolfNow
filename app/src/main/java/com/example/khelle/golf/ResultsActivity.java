package com.example.khelle.golf;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ResultsActivity extends AppCompatActivity {

    private String[] nav_drawer_menu_items;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private TableLayout mPlayerScorecardTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        nav_drawer_menu_items = getResources().getStringArray(R.array.nav_drawer_menu_items);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mPlayerScorecardTable = (TableLayout) findViewById(R.id.player_score_table_layout);

        // Set adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, nav_drawer_menu_items));

        // Set drawer list's click listener
        //mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        Intent gameIntent = getIntent();
        int numPlayers = gameIntent.getIntExtra("numPlayers", 0);
        ArrayList<String> playerNames = gameIntent.getStringArrayListExtra("playerNames");
        ArrayList<Integer> finalScores = gameIntent.getIntegerArrayListExtra("totalScores");

        for(int j =0; j < numPlayers; j++) {
            TableRow playerRow = new TableRow(this.getApplicationContext());
            TextView pNameTextView = new TextView(this.getApplicationContext());
            pNameTextView.setText(playerNames.get(j).toString());
            pNameTextView.setTextColor(Color.parseColor("#000000"));
            TextView pScore = new TextView(getApplicationContext());
            pScore.setText(finalScores.get(j).toString());
            pScore.setHintTextColor(Color.parseColor("#000000"));
            pScore.setTextColor(Color.parseColor("#000000"));

            playerRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT, 1.0f));
            playerRow.addView(pNameTextView);
            playerRow.addView(pScore);
            mPlayerScorecardTable.addView(playerRow);
        }



    }
}
