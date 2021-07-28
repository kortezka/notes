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

    private Note note;

    public notesDescription() {
        // Required empty public constructor
    }

    public static notesDescription newInstance(Note note) {
        notesDescription fragment = new notesDescription();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            note = getArguments().getParcelable(ARG_NOTE);
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
        caption.setText(note.getType());
        TextView description = view.findViewById(R.id.description);
        String[] desc = getResources().getStringArray(R.array.bringHome);
        description.setText(desc[note.getDiscription()]);


        //return view;
    }
}