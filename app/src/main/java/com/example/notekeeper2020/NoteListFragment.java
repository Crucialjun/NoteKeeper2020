package com.example.notekeeper2020;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.Objects;

public class NoteListFragment extends Fragment {

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
                Navigation.findNavController(v).navigate(R.id.action_noteListFragment_to_noteFragment);
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
        final ListView listNotes = requireView().findViewById(R.id.list_notes);

        List<NoteInfo> notes = DataManager.getInstance().getNotes();

        ArrayAdapter<NoteInfo> adapterNotes =
                new ArrayAdapter<>(requireActivity(),android.R.layout.simple_list_item_1,notes);

        listNotes.setAdapter(adapterNotes);

        listNotes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                NoteInfo note = (NoteInfo) listNotes.getItemAtPosition(position);

                Fragment noteFragment = new NoteFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable(NoteFragment.NOTE_INFO,note);
                noteFragment.setArguments(bundle);

                NoteListFragmentDirections.ActionNoteListFragmentToNoteFragment action = NoteListFragmentDirections.actionNoteListFragmentToNoteFragment(note);
                Navigation.findNavController(view).navigate(action);
            }
        });
    }
}