package ru.geekbrains.fragmentnotes;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class notesList extends Fragment {

    protected static boolean isLand;


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

        isLand = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;

        CardsSource cardData = new CardsSourceImpl(getResources()).init();
        Adapter adapter = new Adapter(cardData);
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        adapter.setListener(position -> {
            NoteData noteData = new NoteData(cardData.getCardData(position).getName(),
                    cardData.getCardData(position).getDiscriptionShort(), cardData.getCardData(position).getDiscriptionFull());
            show(noteData);
        });
    }


    private void show(NoteData noteData) {
        if (isLand) {
            land(noteData);
        } else {
            port(noteData);
        }

    }


    private void port(NoteData noteData) {
        notesDescription fragment = notesDescription.newInstance(noteData);
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragmentContainerView, fragment)
                .commit();

    }

    private void land(NoteData noteData) {
        notesDescription fragment = notesDescription.newInstance(noteData);
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_description, fragment)
                .commit();
    }
}
