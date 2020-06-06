package com.example.notekeeper2020;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import java.util.List;

public class NoteFragment extends Fragment {

    public static final String NOTE_POSITION = "com.example.notekeeper2020.NOTE_POSITION";
    public static final int POSITION_NOT_SET = -1;
    private NoteInfo mNote;
    private Spinner mSpinnerCourses;
    private boolean mIsNewNote ;
    private EditText mTextNoteTitle;
    private EditText mTextNoteText;
    private int mNotePosition;
    private boolean mIsCancelling;
    private String mOriginalNoteCourseId;
    private String mOriginalCourseTitle;
    private String mOriginalCourseText;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_note, container, false);
        setHasOptionsMenu(true);
        readDisplayStateValues();
        saveOriginalNoteValues();
        return view;
    }

    private void saveOriginalNoteValues() {
        if(mIsNewNote){
            return;
        }
        mOriginalNoteCourseId = mNote.getCourse().getCourseId();
        mOriginalCourseTitle = mNote.getTitle();
        mOriginalCourseText = mNote.getText();

    }

    private void readDisplayStateValues() {
        if(getArguments() != null) {
            NoteFragmentArgs args = NoteFragmentArgs.fromBundle(getArguments());
            int position = args.getNotePosition();
            mIsNewNote = position == POSITION_NOT_SET;

            if(mIsNewNote){
                createNewNote();
            }else{
                mNote = DataManager.getInstance().getNotes().get(position);
            }

        }
    }

    private void createNewNote() {
        DataManager dm = DataManager.getInstance();
        mNotePosition = dm.createNewNote();
        mNote = dm.getNotes().get(mNotePosition);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mSpinnerCourses = view.findViewById(R.id.spinner_courses);

        List<CourseInfo> courses = DataManager.getInstance().getCourses();

        mTextNoteTitle = view.findViewById(R.id.text_note_title);
        mTextNoteText = view.findViewById(R.id.text_note_text);

        if(!mIsNewNote){
            displayNotes(mSpinnerCourses, mTextNoteTitle, mTextNoteText);
        }

        ArrayAdapter<CourseInfo> adapterCourses =
                new ArrayAdapter<>(requireActivity()
                        ,android.R.layout.simple_spinner_item,courses);
        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinnerCourses.setAdapter(adapterCourses);

        requireActivity().setTitle("Edit Note");



    }

    private void displayNotes(Spinner spinnerCourses, EditText textNoteTitle, EditText textNoteText) {
        List<CourseInfo> courses = DataManager.getInstance().getCourses();
            int courseIndex = courses.indexOf(mNote.getCourse());
            spinnerCourses.setSelection(courseIndex);

            textNoteTitle.setText(mNote.getTitle());
            textNoteText.setText(mNote.getText());

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_note,menu);
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_send_mail){
            sendEmail();
            return true;
        }else if(id == R.id.action_cancel){
            mIsCancelling = true;
            getParentFragmentManager().popBackStack();
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendEmail() {
        CourseInfo course = (CourseInfo) mSpinnerCourses.getSelectedItem();
        String subject = mTextNoteTitle.getText().toString();
        String text = "Check out what i learnt in the PluralSight course "
                + course.getTitle() + "\n  " + mTextNoteText.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("message/rfc2822");
        intent.putExtra(Intent.EXTRA_SUBJECT,subject);
        intent.putExtra(Intent.EXTRA_TEXT,text);
        startActivity(intent);

    }

    @Override
    public void onPause() {
        super.onPause();
        if(mIsCancelling){
            if(mIsNewNote) {
                DataManager.getInstance().removeNote(mNotePosition);
            }else{
                storePreviousNoteValues();
            }
        }else{
            savenote();
        }
    }

    private void storePreviousNoteValues() {
        CourseInfo course = DataManager.getInstance().getCourse(mOriginalNoteCourseId);
        mNote.setCourse(course);
        mNote.setTitle(mOriginalCourseTitle);
        mNote.setText(mOriginalCourseText);
    }

    private void savenote() {
        mNote.setCourse((CourseInfo) mSpinnerCourses.getSelectedItem());
        mNote.setTitle(mTextNoteTitle.getText().toString());
        mNote.setText(mTextNoteText.getText().toString());
    }
}