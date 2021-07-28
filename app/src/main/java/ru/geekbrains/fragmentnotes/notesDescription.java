package ru.geekbrains.fragmentnotes;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class notesDescription extends Fragment {

    public static final String ARG_NOTE = "NOTE";

    private NoteData noteData;

    public notesDescription() {
        // Required empty public constructor
    }

    public static notesDescription newInstance(NoteData noteData) {
        notesDescription fragment = new notesDescription();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, noteData);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            noteData = getArguments().getParcelable(ARG_NOTE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_notes_description, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView caption = view.findViewById(R.id.caption);
        TextView description = view.findViewById(R.id.description);
        caption.setText(noteData.getName());
        description.setText(noteData.getDiscriptionFull());

    }


}