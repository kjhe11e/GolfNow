package com.example.khelle.golf;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.speech.RecognizerIntent;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;
import java.util.Vector;
import java.lang.*;

// This is sloppy code as this needs to be refactored to more common, reusable methods
// This was tested manually; there are several bugs in this code.

//////////////////////////////////////////////////////////////////////////
// TO DO: Keep info on orientation change
// TO DO: Refactor this code into smaller reusable methods/functions
// TO DO: Set drawer list's click listener
//////////////////////////////////////////////////////////////////////////

public class GameActivity extends AppCompatActivity {

    private String[] nav_drawer_menu_items;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private TableLayout mPlayerScorecardTable;
    int currentHole = 0;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private int numberOfPlayers;
    private ArrayList<ArrayList<Integer>> gameScorecard = new ArrayList<ArrayList<Integer>>();
    private int scoresRecorded;
    private boolean readyForNextHole = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        nav_drawer_menu_items = getResources().getStringArray(R.array.nav_drawer_menu_items);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mPlayerScorecardTable = (TableLayout) findViewById(R.id.player_score_table_layout);
        final String[] holeNumbers = {"Hole 1", "Hole 2", "Hole 3", "Hole 4", "Hole 5", "Hole 6",
        "Hole 7", "Hole 8", "Hole 9", "Hole 10", "Hole 11", "Hole 12", "Hole 13", "Hole 14",
        "Hole 15", "Hole 16", "Hole 17", "Hole 18"};
        currentHole = 1;


        // Set adapter for the list view
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, nav_drawer_menu_items));

        ////////////////////////////////////////////////////////////////////////////////////////////
        // *****************************************************************************************
        // * TO DO: set drawer list's onClickListener
        // *****************************************************************************************
        ////////////////////////////////////////////////////////////////////////////////////////////
        // Set drawer list's click listener
        //mDrawerList.setOnItemClickListener(new DrawerItemClickListener());


        Intent intent = getIntent();
        ArrayList<String> playerNames = intent.getStringArrayListExtra("playerNames");
        final int numPlayers = playerNames.size();
        numberOfPlayers = playerNames.size();


        ////////////////////////////////////////////////////////////////////////////////////////////
        // Initialize scorecard view based on number of players:
        ////////////////////////////////////////////////////////////////////////////////////////////
        for(int j =0; j < numPlayers; j++) {
            //String playerName = intent.getStringExtra("player"+j);
            TableRow playerRow = new TableRow(this.getApplicationContext());
            TextView pNameTextView = new TextView(this.getApplicationContext());
            pNameTextView.setText(playerNames.get(j));
            pNameTextView.setTextColor(Color.parseColor("#000000"));
            EditText pScore = new EditText(getApplicationContext());
            pScore.setHint("need score");
            pScore.setHintTextColor(Color.parseColor("#000000"));
            pScore.setTextColor(Color.parseColor("#000000"));

            playerRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT, 1.0f));
            playerRow.addView(pNameTextView);
            playerRow.addView(pScore);
            mPlayerScorecardTable.addView(playerRow);
        }


        ////////////////////////////////////////////////////////////////////////////////////////////
        // Add enterScores speech-2-text button for recording:
        ////////////////////////////////////////////////////////////////////////////////////////////
        Button recordScoresBtn = new Button(this.getApplicationContext());
        recordScoresBtn.setText("Enter scores");
        TableRow recordScoresRow = new TableRow(this.getApplicationContext());
        recordScoresRow.addView(recordScoresBtn);
        recordScoresRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                TableRow.LayoutParams.WRAP_CONTENT, 1.0f));
        mPlayerScorecardTable.addView(recordScoresRow);


        ////////////////////////////////////////////////////////////////////////////////////////////
        // Create ArrayList<Integer> for each player to hold scores in gameScorecard
        ////////////////////////////////////////////////////////////////////////////////////////////
        for(int i = 0; i < numPlayers; i++){
            ArrayList<Integer> playerScorecard = new ArrayList<Integer>();
            gameScorecard.add(i, playerScorecard);
        }


        ////////////////////////////////////////////////////////////////////////////////////////////
        // recordScoresBtn's onClickListener, should do the following:
        // 1) Open speech prompter to get scores via voice
        // 2) Populate the players scores with the scores obtained from step 1
        ////////////////////////////////////////////////////////////////////////////////////////////
        recordScoresBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }

        });


        ////////////////////////////////////////////////////////////////////////////////////////////
        // Set up navigation info (hole number and Next button) below scorecard:
        ////////////////////////////////////////////////////////////////////////////////////////////
        final TextView holeNum = new TextView(this.getApplicationContext());
        holeNum.setText(holeNumbers[currentHole-1]);
        holeNum.setTextColor(Color.parseColor("#000000"));
        Button nextHole = new Button(this.getApplicationContext());
        nextHole.setText("Next");
        nextHole.setTextColor(Color.parseColor("#000000"));
        TableRow navInfoRow = new TableRow(this.getApplicationContext());
        navInfoRow.addView(holeNum);
        navInfoRow.addView(nextHole);
        navInfoRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.MATCH_PARENT, 1.0f));
        mPlayerScorecardTable.addView(navInfoRow);


        ////////////////////////////////////////////////////////////////////////////////////////////
        // nextHole button's onClickListener, should do the following:
        // 1) Save player scores
        // 2) Increment hole number to go to next hole
        ////////////////////////////////////////////////////////////////////////////////////////////
        nextHole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i = 0; i < numPlayers; i++){
                    //get each player's score
                    TableRow playerRow = (TableRow) mPlayerScorecardTable.getChildAt(i);
                    EditText playerScore = (EditText) playerRow.getChildAt(1);
                    String scoreInput = playerScore.getText().toString();
                    try{
                        int pScore = Integer.parseInt(scoreInput);
                        Log.i("", "Logged player score " + scoreInput);
                    } catch(NumberFormatException e){
                        readyForNextHole = false;
                        TextView playerName = (TextView) playerRow.getChildAt(0);
                        String pName = playerName.getText().toString();
                        Toast.makeText(getApplicationContext(), pName + " does not have a valid score", Toast.LENGTH_SHORT).show();
                        Log.i("", scoreInput + " is not a valid score for player " + pName);
                    }
                }

                if(currentHole >= 18){
                    readyForNextHole = false;
                    Toast.makeText(getApplicationContext(), "Game over, congratulations", Toast.LENGTH_SHORT).show();
                    // Process final scores here
                    for(int j = 0; j < numberOfPlayers; j++){
                        TableRow tr = (TableRow) mPlayerScorecardTable.getChildAt(j);
                        TextView player = (TextView) tr.getChildAt(1);  //the "score" column
                        String score = player.getText().toString();
                        Integer pScore = Integer.parseInt(score);
                        gameScorecard.get(j).add(pScore);
                    }
                    Log.i("", "After hole " + currentHole + ", gameScorecard is " + gameScorecard);
                    for(int j = 0; j < numPlayers; j++){
                        //sum up player's score
                        int playerTotalScore = 0;
                        for(int k = 0; k < gameScorecard.get(j).size(); k++){
                            // SHOULD CHECK FOR BAD DATA HERE
                            playerTotalScore += gameScorecard.get(j).get(k);
                        }
                        Log.i("", "Player" + j + " had a score of " + playerTotalScore);
                    }
                    Intent resultsIntent = new Intent(getApplicationContext(), ResultsActivity.class);
                    resultsIntent.putExtra("numberOfPlayers", numberOfPlayers);
                    resultsIntent.putExtra("gameScorecard", gameScorecard);
                    startActivity(resultsIntent);
                }
                else{
                    for(int j = 0; j < numberOfPlayers; j++){
                        TableRow tr = (TableRow) mPlayerScorecardTable.getChildAt(j);
                        TextView player = (TextView) tr.getChildAt(1);  //the "score" column
                        String score = player.getText().toString();
                        Integer pScore = Integer.parseInt(score);
                        gameScorecard.get(j).add(pScore);
                    }
                    Log.i("", "After hole " + currentHole + ", gameScorecard is " + gameScorecard);
                    for(int k = 0; k < numberOfPlayers; k++){
                        int currentTotal = 0;
                        for(int n = 0; n < gameScorecard.get(k).size(); n++){
                            currentTotal += gameScorecard.get(k).get(n);
                        }
                        Log.i("", "Player" + k + " has a current score of " + currentTotal);
                    }
                    currentHole++;
                    holeNum.setText(holeNumbers[currentHole - 1]);
                }
            }
        });
    }


    // showing Google speech input dialog
    private void promptSpeechInput() {
        gameScorecard.clear();
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));

        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }


    // receiving speech input
    // This will populate the players' scores based on the input string of data from the RecognizerIntent
    // Input from RecognizerIntent is a string of data (scores), which will be parsed for scores
    // and assigned to the players in order
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    String scoresToParse = result.get(0);
                    //Toast.makeText(this.getApplicationContext(), scoresToParse, Toast.LENGTH_LONG).show();
                    String[] scoresInput = scoresToParse.split(" ");
                    Log.i("", "Score input is: " + scoresInput);

                    // Get the first n scores for n number of players
                    for(int j = 0, k = 0; j < scoresInput.length && k < numberOfPlayers; j++){
                        String score = scoresInput[j];
                        if(isInteger(score, 10)){
                            int validScore = Integer.parseInt(score);
                            Log.i("", "gameScorecard is " + gameScorecard.toString());
                            gameScorecard.get(j).add(validScore);
                            k++;
                            scoresRecorded = k;
                        }
                    }
                    Log.i("", "onActivityResult: gameScorecard is " + gameScorecard);
                }
                break;
            }
        }

        if(scoresRecorded < numberOfPlayers){
            //prompt them to enter again
            Toast.makeText(this.getApplicationContext(), "Not enough scores recorded, please try again", Toast.LENGTH_LONG).show();
        } else {
            for(int j = 0; j < numberOfPlayers; j++){
                TableRow tr = (TableRow) mPlayerScorecardTable.getChildAt(j);
                TextView player = (TextView) tr.getChildAt(1);  //the "score" column
                player.setText(gameScorecard.get(j).toString());
            }
        }
    }


    // Method that returns boolean whether string is number
    protected boolean isInteger(String s, int radix){
        if(s.isEmpty()){
            return false;
        }
        for(int k = 0; k < s.length(); k++){
            if(k == 0 && s.charAt(k) == '-') {
                if (s.length() == 1) {
                    return false;
                } else {
                    continue;
                }
            }
            if(Character.digit(s.charAt(k), radix) < 0) {
                return false;
            }
        }
        return true;
    }


}

