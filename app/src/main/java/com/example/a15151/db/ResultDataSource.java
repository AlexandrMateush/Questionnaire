package com.example.a15151.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;


import com.example.a15151.db.creation.DatabaseHelper;
import com.example.a15151.db.creation.ResultEntry;
import com.example.a15151.entitys.Result;

import java.util.ArrayList;
import java.util.List;


public class ResultDataSource {

    private SQLiteDatabase database;
    private final DatabaseHelper dbHelper;

    public ResultDataSource(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void addResult(Result result) {
        ContentValues values = new ContentValues();
        values.put(ResultEntry.COLUMN_LEVEL, result.getLevel());
        values.put(ResultEntry.COLUMN_LAST_SCORE, result.getLastScore());
        values.put(ResultEntry.COLUMN_BEST_SCORE, result.getBestScore());

        database.insert(ResultEntry.TABLE_NAME, null, values);
    }

    public List<Result> getResultsForLevel(int level) {
        List<Result> results = new ArrayList<>();
        String[] projection = {
                ResultEntry.COLUMN_LEVEL,
                ResultEntry.COLUMN_LAST_SCORE,
                ResultEntry.COLUMN_BEST_SCORE
        };
        String selection = ResultEntry.COLUMN_LEVEL + " = ?";
        String[] selectionArgs = { String.valueOf(level) };

        Cursor cursor = database.query(
                ResultEntry.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                @SuppressLint("Range") int lastScore = cursor.getInt(cursor.getColumnIndex(ResultEntry.COLUMN_LAST_SCORE));
                @SuppressLint("Range") int bestScore = cursor.getInt(cursor.getColumnIndex(ResultEntry.COLUMN_BEST_SCORE));
                results.add(new Result(level, lastScore, bestScore));
                cursor.moveToNext();
            }
            cursor.close();
        }

        return results;
    }
    public void updateResult(Result result) {
        ContentValues values = new ContentValues();
        values.put(ResultEntry.COLUMN_LAST_SCORE, result.getLastScore());
        values.put(ResultEntry.COLUMN_BEST_SCORE, result.getBestScore());

        String selection = ResultEntry.COLUMN_LEVEL + " = ?";
        String[] selectionArgs = { String.valueOf(result.getLevel()) };

        database.update(
                ResultEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );
    }
    public void deleteAllResults() {
        database.delete(ResultEntry.TABLE_NAME, null, null);
    }
    public void resetAllScores() {
        open();
        deleteAllResults();
        close();
    }

}

