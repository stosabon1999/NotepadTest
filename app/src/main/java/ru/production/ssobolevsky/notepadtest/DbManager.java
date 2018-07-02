package ru.production.ssobolevsky.notepadtest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pro on 28.06.2018.
 */

public class DbManager {

    private DbHelper mDbHelper;

    public DbManager(Context context) {
        mDbHelper = new DbHelper(context);
    }

    public void addNote(MyNote note) {
        SQLiteDatabase database = null;
        try {
            database = mDbHelper.getWritableDatabase();
            ContentValues contentValues = getContentValues(note);
            database.beginTransaction();
            addNoteInternal(database, contentValues);
            database.setTransactionSuccessful();
        } catch (SQLiteException e) {
            Log.wtf("SQL", "SQLiteException");
        } finally {
            if (database != null) {
                if (database.inTransaction()) {
                    database.endTransaction();
                }
                database.close();
            }
        }
    }

    private ContentValues getContentValues(MyNote note) {
        ContentValues values = new ContentValues();
        values.put(MyNote.TITLE, note.getTitle());
        values.put(MyNote.SUBTITLE, note.getSubtitle());
        return values;
    }

    private void addNoteInternal(SQLiteDatabase database, ContentValues contentValues) {
        long a = database.insert(MyNote.NOTE, null, contentValues);
    }

    public List<MyNote> getNotes() {
        SQLiteDatabase database = null;
        List<MyNote> list = new ArrayList<>();
        try {
            database = mDbHelper.getWritableDatabase();
            database.beginTransaction();
            Cursor cursor = database.query(MyNote.NOTE, null, null, null, null, null, null);
            list = parseAllNotes(cursor);
            database.setTransactionSuccessful();
        } catch (SQLiteException e){
            Log.wtf("SQL", "SQLiteException");
        } finally {
            if (database != null) {
                if (database.inTransaction()) {
                    database.endTransaction();
                }
                database.close();
            }
        }

        return list;
    }

    private List<MyNote> parseAllNotes(Cursor cursor) {
        List<MyNote> list = new ArrayList<>();
        if (cursor.moveToFirst()) {
            int title = cursor.getColumnIndex(MyNote.TITLE);
            int subtitle = cursor.getColumnIndex(MyNote.SUBTITLE);
            do {
                MyNote note = new MyNote(cursor.getString(title), cursor.getString(subtitle));
                list.add(note);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public MyNote getNoteByName(String title) {
        SQLiteDatabase database = null;
        MyNote myNote = null;
        try {
           database = mDbHelper.getReadableDatabase();
           database.beginTransaction();
           Cursor cursor = database.query(MyNote.NOTE, null, "title = ?", new String[]{title}, null, null, null);
           myNote = parseNote(cursor);
           cursor.close();
           database.setTransactionSuccessful();
        } catch (SQLiteException e) {
            Log.wtf("SQL", "SQLiteException");
        } finally {
            if (database != null) {
                if (database.inTransaction()) {
                    database.endTransaction();
                }
                database.close();
            }
        }
        return myNote;
    }

    private MyNote parseNote(Cursor cursor) {
        if (cursor.moveToFirst()) {
            return  new MyNote(cursor.getString(cursor.getColumnIndex("title")), cursor.getString(cursor.getColumnIndex("subtitle")));
        }
        return null;
    }

}
