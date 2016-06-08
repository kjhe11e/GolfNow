package com.example.khelle.golf;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by khelle on 6/8/16.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    // All Static variables

    // Database version
    private static final int DATABASE_VERSION = 1;

    // Database name
    private static final String DATABASE_NAME = "scorecardManager";

    // Players table name
    private static final String TABLE_SCORECARD = "scorecard";

    // Players Table column names
    private static final String KEY_PLAYER = "player";
    private static final String KEY_DATE = "date";
    private static final String KEY_COURSE = "course";
    private static final String KEY_HOLE1 = "hole1";
    private static final String KEY_HOLE2 = "hole2";
    private static final String KEY_HOLE3 = "hole3";
    private static final String KEY_HOLE4 = "hole4";
    private static final String KEY_HOLE5 = "hole5";
    private static final String KEY_HOLE6 = "hole6";
    private static final String KEY_HOLE7 = "hole7";
    private static final String KEY_HOLE8 = "hole8";
    private static final String KEY_HOLE9 = "hole9";
    private static final String KEY_HOLE10 = "hole10";
    private static final String KEY_HOLE11 = "hole11";
    private static final String KEY_HOLE12 = "hole12";
    private static final String KEY_HOLE13 = "hole13";
    private static final String KEY_HOLE14 = "hole14";
    private static final String KEY_HOLE15 = "hole15";
    private static final String KEY_HOLE16 = "hole16";
    private static final String KEY_HOLE17 = "hole17";
    private static final String KEY_HOLE18 = "hole18";
    private static final String KEY_TOTAL_SCORE = "totalScore";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Create Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SCORECARD_TABLE = "CREATE TABLE" + TABLE_SCORECARD + "("
                + KEY_PLAYER + " INTEGER PRIMARY KEY," + KEY_DATE + " TEXT,"
                + KEY_COURSE + " TEXT,"
                + KEY_HOLE1 + " TEXT,"
                + KEY_HOLE2 + " TEXT,"
                + KEY_HOLE3 + " TEXT,"
                + KEY_HOLE4 + " TEXT,"
                + KEY_HOLE5 + " TEXT,"
                + KEY_HOLE6 + " TEXT,"
                + KEY_HOLE7 + " TEXT,"
                + KEY_HOLE8 + " TEXT,"
                + KEY_HOLE9 + " TEXT,"
                + KEY_HOLE10 + " TEXT,"
                + KEY_HOLE11 + " TEXT,"
                + KEY_HOLE12 + " TEXT,"
                + KEY_HOLE13 + " TEXT,"
                + KEY_HOLE14 + " TEXT,"
                + KEY_HOLE15 + " TEXT,"
                + KEY_HOLE16 + " TEXT,"
                + KEY_HOLE17 + " TEXT,"
                + KEY_HOLE18 + " TEXT,"
                + KEY_TOTAL_SCORE + " TEXT" + ")";
        db.execSQL(CREATE_SCORECARD_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCORECARD);

        // Create scorecard again
        onCreate(db);
    }



}
