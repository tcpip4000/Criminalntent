package net.ertechnology.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by fulano on 7/30/14.
 */
public class CrimeDBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "mycrime.sqlite";
    private static final int VERSION = 1;
    private static final String TABLE_CRIME = "crime";
    private static final String COLUMN_CRIME_ID = "id";
    private static final String COLUMN_CRIME_START_DATE = "start_date";
    private static final String COLUMN_CRIME_TITLE = "title";

    public CrimeDBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table crime (id text primary key, title varchar(100), start_date integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // implement upgrade
    }

    public long insertCrime(Crime c) {
        ContentValues cv  = new ContentValues();
        cv.put(COLUMN_CRIME_ID, c.getId().toString());
        cv.put(COLUMN_CRIME_START_DATE, c.getDate().getTime());
        cv.put(COLUMN_CRIME_TITLE, c.getTitle());
        return getWritableDatabase().insert(TABLE_CRIME, null, cv);
    }

    public boolean exists(Crime c) {
        String columns[] = {"id"};
        String selection = " id = '" + c.getId().toString() + "'";
        ContentValues cv  = new ContentValues();
        cv.put(COLUMN_CRIME_ID, c.getId().toString());
        return getReadableDatabase().query(false, TABLE_CRIME, columns, selection, null, null, null, null, null).getCount() > 0;
    }

    public int updateCrime(Crime c) {
        String condition = " id = '" + c.getId().toString() + "'";
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CRIME_START_DATE, c.getDate().getTime());
        cv.put(COLUMN_CRIME_TITLE, c.getTitle());
        return getWritableDatabase().update(TABLE_CRIME, cv, condition, null);
    }

}
