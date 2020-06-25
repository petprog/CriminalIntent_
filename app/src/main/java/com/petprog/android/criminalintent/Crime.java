package com.petprog.android.criminalintent;

import android.text.format.DateFormat;

import java.util.Date;
import java.util.UUID;

public class Crime implements Comparable<UUID> {

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private boolean mSolved;
    private boolean mRequiresPolice;

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

    public String getDate() {

        String date = DateFormat.format("EEEE, MMM d, yyyy", mDate).toString();
        return date;
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

    public boolean isRequiresPolice() {
        return mRequiresPolice;
    }

    public void setRequiresPolice(boolean requiresPolice) {
        mRequiresPolice = requiresPolice;
    }

    @Override
    public int compareTo(UUID id) {
        return this.mId.compareTo(id);
    }
}
