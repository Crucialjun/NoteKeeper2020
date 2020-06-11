package com.example.notekeeper2020;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class NoteListFragment extends Fragment {

    private ArrayAdapter<NoteInfo> mAdapterNotes;
    private NoteRecyclerAdapter mNoteRecyclerAdapter;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_note_list, container, false);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NoteListFragmentDirections.ActionNoteListFragmentToNoteFragment
                        action = NoteListFragmentDirections.actionNoteListFragmentToNoteFragment();
                Navigation.findNavController(v).navigate(action);
            }
        });

        return view;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        initializeDisplayContent();
        requireActivity().setTitle("Notes List");
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

        final RecyclerView recyclerNotes = requireView().findViewById(R.id.list_notes);
        final LinearLayoutManager notesLayoutManager = new LinearLayoutManager(getActivity());
        recyclerNotes.setLayoutManager(notesLayoutManager);

        List<NoteInfo> notes = DataManager.getInstance().getNotes();
        mNoteRecyclerAdapter = new NoteRecyclerAdapter(getActivity(),notes);
        recyclerNotes.setAdapter(mNoteRecyclerAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
        mNoteRecyclerAdapter.notifyDataSetChanged();
    }
}