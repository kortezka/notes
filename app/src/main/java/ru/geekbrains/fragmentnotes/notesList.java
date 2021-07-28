package ru.geekbrains.fragmentnotes;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


public class notesList extends Fragment {

private  static boolean isLand;



    public notesList() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayout layoutView = (LinearLayout) view;
        String[] notes = getResources().getStringArray(R.array.notes);
        isLand = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;
        for (int i = 0; i < notes.length; i++) {
            TextView notesList = new TextView(getContext());

            notesList.setText(notes[i]);
            notesList.setTextSize(80);
            layoutView.addView(notesList);
            //final int k = i;
            Note note = new Note("принести домой", i);
            notesList.setOnClickListener(v -> {
                show(note);
            });
        }
    }

    private void show(Note note) {
        if (isLand){
            land(note);
        }else {
            port(note);
        }

    }

    private void port(Note note) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), notesActivityDescription.class);
        intent.putExtra(notesDescription.ARG_NOTE, note);
        startActivity(intent);
    }
    private void land(Note note){
        notesDescription fragment = notesDescription.newInstance(note);

        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_description,fragment)
                .commit();
    }
}
