package com.example.balajiproperty.sqlite.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.example.balajiproperty.sqlite.helper.BalajiPropertyDbHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MessageSentDao {
    BalajiPropertyDbHelper balajiPropertyDbHelper;

    public MessageSentDao(BalajiPropertyDbHelper balajiPropertyDbHelper) {
        this.balajiPropertyDbHelper = balajiPropertyDbHelper;
    }

    public void addSMSMessage() {
        SQLiteDatabase db = balajiPropertyDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        Date currentTime = Calendar.getInstance().getTime();
        values.put(MessageSent.MessageSentEntry.COLUMN_NAME_TIME, currentTime.getTime());
        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(MessageSent.MessageSentEntry.TABLE_NAME, null, values);
    }

    public List<Integer> readSMSMessage() {
        SQLiteDatabase db = balajiPropertyDbHelper.getReadableDatabase();
        String[] projection = {
                MessageSent.MessageSentEntry.COLUMN_NAME_TIME
        };
        Cursor cursor = db.query(
                MessageSent.MessageSentEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );
        List itemIds = new ArrayList<>();
        while (cursor.moveToNext()) {
            long itemId = cursor.getLong(
                    cursor.getColumnIndexOrThrow(MessageSent.MessageSentEntry.COLUMN_NAME_TIME));
            itemIds.add(itemId);
        }
        cursor.close();
        return itemIds;
    }
}
