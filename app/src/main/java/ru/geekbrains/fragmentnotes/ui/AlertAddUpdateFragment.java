package ru.geekbrains.fragmentnotes.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import ru.geekbrains.fragmentnotes.NoteData;
import ru.geekbrains.fragmentnotes.R;


public class AlertAddUpdateFragment extends DialogFragment {


    public static final String ARG_NOTE = "NOTE";


    private NoteData noteData;
    String id;

    public AlertAddUpdateFragment() {
        // Required empty public constructor
    }

    public static AlertAddUpdateFragment newInstance() {
        AlertAddUpdateFragment fragment = new AlertAddUpdateFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public static AlertAddUpdateFragment newInstance(NoteData noteData) {
        AlertAddUpdateFragment fragment = new AlertAddUpdateFragment();
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
            setHasOptionsMenu(true);

        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_add, menu);

    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.commit:

                return true;
            case R.id.abort:
                FragmentManager fragmentManager;
                fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setCancelable(true);
        return inflater.inflate(R.layout.fragment_add_update, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText caption = view.findViewById(R.id.add_caption);
        EditText dShort = view.findViewById(R.id.add_description_short);
        EditText dFull = view.findViewById(R.id.add_description_full);
        Button commit = view.findViewById(R.id.commit);

        if (noteData != null) {
            caption.setText(noteData.getName());
            dShort.setText(noteData.getDiscriptionShort());
            dFull.setText(noteData.getDiscriptionFull());
            id=noteData.getId();

        }

        commit.setOnClickListener(v -> {
            noteData = new NoteData(caption.getText().toString(), dShort.getText().toString(), dFull.getText().toString());
            noteData.setId(id);
            Bundle result = new Bundle();
            result.putParcelable("bundleKey", noteData);
            getParentFragmentManager().setFragmentResult("requestKey", result);
            dismiss();

          //  ((MainActivity) requireActivity()).onDialogResult(result);
//            getParentFragmentManager().
                    //onDialogResult(result);



        });

    }


}