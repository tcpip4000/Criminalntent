package net.ertechnology.criminalintent;

import android.content.Context;

import java.util.UUID;
import java.util.Date;

/**
 * Created by fulano on 7/16/14.
 */
public class Crime {

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;

    public Crime() {
        mId = UUID.randomUUID();
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }

    @Override
    public String toString() {
        return mTitle;
    }

    public long save(Context context) {
        long result;
        CrimeDBHelper cdbHelper = new CrimeDBHelper(context);
        if (this.existsInDB(context)) {
            result = cdbHelper.updateCrime(this);
        } else {
            result = cdbHelper.insertCrime(this);
        }
        return result;
    }

    public boolean existsInDB(Context context) {
        CrimeDBHelper cdbHelper = new CrimeDBHelper(context);
        return cdbHelper.exists(this);
    }
}
