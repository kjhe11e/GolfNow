package com.example.khelle.golf;


import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

// TO DO: keep info on orientation change


public class GameActivity extends AppCompatActivity {

    private String[] nav_drawer_menu_items;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private TableLayout mPlayerScorecardTable;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        nav_drawer_menu_items = getResources().getStringArray(R.array.nav_drawer_menu_items);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mPlayerScorecardTable = (TableLayout) findViewById(R.id.player_score_table_layout);


        // Set adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, nav_drawer_menu_items));

        // Set drawer list's click listener
        //mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        Intent intent = getIntent();
        int numPlayers = intent.getIntExtra("numPlayers", 0);


        for(int j =0; j < numPlayers; j++) {
            String playerName = intent.getStringExtra("player"+j);
            TableRow playerRow = new TableRow(this.getApplicationContext());
            TextView pNameTextView = new TextView(this.getApplicationContext());
            pNameTextView.setText(playerName);
            pNameTextView.setTextColor(Color.parseColor("#000000"));
            EditText pScoreSetter = new EditText(getApplicationContext());
            pScoreSetter.setHint("---");
            pScoreSetter.setHintTextColor(Color.parseColor("#000000"));
            pScoreSetter.setTextColor(Color.parseColor("#000000"));

            playerRow.addView(pNameTextView);
            playerRow.addView(pScoreSetter);
            mPlayerScorecardTable.addView(playerRow);

        }


    }

}
