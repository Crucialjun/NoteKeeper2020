package com.example.notekeeper2020;

import android.os.Bundle;

import androidx.lifecycle.ViewModel;

public class NoteViewModel extends ViewModel {
    public static final String ORIGINAL_NOTE_COURSE_ID = "com.example.notekeeper2020.ORIGINAL_NOTE_COURSE_ID";
    public static final String ORIGINAL_NOTE_TITLE = "com.example.notekeeper2020.ORIGINAL_NOTE_TITLE";
    public static final String ORIGINAL_NOTE_TEXT = "com.example.notekeeper2020.ORIGINAL_NOTE_TEXT";

    public String mOriginalNoteCourseId;
    public String mOriginalCourseTitle;
    public String mOriginalCourseText;
    public boolean mIsNewlyCreated = true;


    public void saveState(Bundle outState) {
        outState.putString(ORIGINAL_NOTE_COURSE_ID,mOriginalNoteCourseId);
        outState.putString(ORIGINAL_NOTE_TITLE,mOriginalCourseTitle);
        outState.putString(ORIGINAL_NOTE_TEXT,mOriginalCourseText);
    }

    public void restoreState(Bundle inState){
        mOriginalNoteCourseId = inState.getString(ORIGINAL_NOTE_COURSE_ID);
        mOriginalCourseTitle = inState.getString(ORIGINAL_NOTE_TITLE);
        mOriginalCourseText = inState.getString(ORIGINAL_NOTE_TEXT);
    }


}
