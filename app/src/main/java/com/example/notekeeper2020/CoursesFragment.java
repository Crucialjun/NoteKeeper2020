package com.example.notekeeper2020;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.List;


public class CoursesFragment extends Fragment {

    private ArrayAdapter<NoteInfo> mAdapterNotes;
    private CourseRecyclerAdapter mCourseRecyclerAdapter;
    private RecyclerView mRecyclerItems;
    private GridLayoutManager mCoursesLayoutManager;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_courses, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        initializeDisplayContent();
        requireActivity().setTitle("Courses");
    }

    private void initializeDisplayContent() {
//        final ListView listNotes = requireView().findViewById(R.id.list_notes);
//
//        List<NoteInfo> notes = DataManager.getInstance().getNotes();
//
//        mAdapterNotes =
//                new ArrayAdapter<>(requireActivity(),android.R.layout.simple_list_item_1,notes);
//
//        listNotes.setAdapter(mAdapterNotes);
//
//        listNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                NoteInfo note = (NoteInfo) listNotes.getItemAtPosition(position);
//
//                NoteListFragmentDirections.ActionNoteListFragmentToNoteFragment action
//                        = NoteListFragmentDirections.actionNoteListFragmentToNoteFragment();
//                action.setNotePosition(position);
//                Navigation.findNavController(view).navigate(action);
//            }
//        });

        mRecyclerItems = requireView().findViewById(R.id.list_courses);
        mCoursesLayoutManager = new GridLayoutManager(getActivity(),getResources().getInteger(R.integer.course_grid_span));


        List<CourseInfo> courses = DataManager.getInstance().getCourses();
        mCourseRecyclerAdapter = new CourseRecyclerAdapter(getActivity(),courses);
        displayCourses();

    }

    private void displayCourses() {
        mRecyclerItems.setLayoutManager(mCoursesLayoutManager);
        mRecyclerItems.setAdapter(mCourseRecyclerAdapter);

        NavigationView navigationView = requireActivity().findViewById(R.id.nav_view);
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_courses).setChecked(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        mCourseRecyclerAdapter.notifyDataSetChanged();
    }

}