package com.example.notekeeper2020;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import java.util.List;
import java.util.Objects;

public class NoteFragment extends Fragment {

    public static final String NOTE_INFO = "com.example.notekeeper2020.NOTE_INFO";
    private NoteInfo mNote;
    private Spinner mSpinnerCourses;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_note, container, false);
        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getArguments() != null) {
            NoteFragmentArgs args = NoteFragmentArgs.fromBundle(getArguments());
            mNote = args.getNote();
        }


        mSpinnerCourses = view.findViewById(R.id.spinner_courses);

        List<CourseInfo> courses = DataManager.getInstance().getCourses();

        EditText textNoteTitle = view.findViewById(R.id.text_note_title);
        EditText textNoteText = view.findViewById(R.id.text_note_text);


        displayNotes(mSpinnerCourses,textNoteTitle,textNoteText);

        ArrayAdapter<CourseInfo> adapterCourses =
                new ArrayAdapter<>(requireActivity()
                        ,android.R.layout.simple_spinner_item,courses);
        adapterCourses.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinnerCourses.setAdapter(adapterCourses);

        getActivity().setTitle("Edit Note");



    }

    private void displayNotes(Spinner spinnerCourses, EditText textNoteTitle, EditText textNoteText) {
        List<CourseInfo> courses = DataManager.getInstance().getCourses();
            int courseIndex = courses.indexOf(mNote.getCourse());
            spinnerCourses.setSelection(courseIndex);

            textNoteTitle.setText(mNote.getTitle());
            textNoteText.setText(mNote.getText());

    }


}