package ru.geekbrains.fragmentnotes.ui;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import ru.geekbrains.fragmentnotes.CardsSource;
import ru.geekbrains.fragmentnotes.CardsSourceFirebaseImpl;
import ru.geekbrains.fragmentnotes.CardsSourceResponse;
import ru.geekbrains.fragmentnotes.NoteData;
import ru.geekbrains.fragmentnotes.R;


public class NotesList extends Fragment {

    protected static boolean isLand;
    CardsSource cardData;
    Adapter adapter;
    Context thisContext;

    public NotesList() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        thisContext = getActivity().getApplicationContext();
        adapter = new Adapter(this);
        cardData = new CardsSourceFirebaseImpl().init(new CardsSourceResponse() {
            @Override
            public void initialized(CardsSource cardsData) {
                adapter.notifyDataSetChanged();
            }
        });

        adapter.setDataSource(cardData);
        return inflater.inflate(R.layout.fragment_notes_list, container, false);
    }

    @Override
    public void onCreateContextMenu(@NonNull ContextMenu menu, @NonNull View v, @Nullable ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater inflater = requireActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_context, menu);

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        int position = adapter.getMenuPosition();
        switch (item.getItemId()) {

            case R.id.delete:
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
                alertBuilder.setPositiveButton("пошла она", (dialogInterface, i) -> {
                    cardData.delete(position);
                    adapter.notifyItemRemoved(position);
                });

                alertBuilder.setTitle("Удаляем эту заметку?");
                alertBuilder.setCancelable(true);
                alertBuilder.setNegativeButton("нее", (dialogInterface, i) -> dialogInterface.dismiss());

                AlertDialog delete = alertBuilder.create();
                delete.show();

                return true;
            case R.id.update:
                AddUpdateFragment fragment = AddUpdateFragment.newInstance(cardData.getCardData(adapter.getMenuPosition()));
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.fragmentContainerView, fragment)
                        .commit();

                getParentFragmentManager().setFragmentResultListener("requestKey", this, (requestKey, bundle) -> {
                    NoteData result = bundle.getParcelable("bundleKey");
                    cardData.update(result, position);
                    adapter.notifyItemChanged(position);
                });
                adapter.notifyItemChanged(position);
                return true;
        }
        return super.onContextItemSelected(item);
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        //  int position = adapter.getMenuPosition();
        switch (id) {
            case R.id.clear:
                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
                alertBuilder.setPositiveButton("let it burn", (dialogInterface, i) -> {
                    cardData.clear();
                    adapter.notifyDataSetChanged();
                });

                alertBuilder.setTitle("Удаляем все заметки?");
                alertBuilder.setCancelable(true);
                alertBuilder.setNegativeButton("не, передумал", (dialogInterface, i) -> dialogInterface.dismiss());
                alertBuilder.setNeutralButton("HELP", (dialogInterface, i) -> {

                });

                AlertDialog delete = alertBuilder.create();
                delete.show();

                return true;
            case R.id.refresh:
                adapter.notifyDataSetChanged();
                return true;
            case R.id.add:
                DialogFragment addByDialog = new AlertAddUpdateFragment();
                addByDialog.show(requireActivity().getSupportFragmentManager(), "TAG");

                getParentFragmentManager().setFragmentResultListener("requestKey", this, (requestKey, bundle) -> {
                    NoteData result = bundle.getParcelable("bundleKey");
                    cardData.addNote(result);

                });

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        isLand = getResources().getConfiguration().orientation
                == Configuration.ORIENTATION_LANDSCAPE;

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
        NotesDescription fragment = NotesDescription.newInstance(noteData);
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragmentContainerView, fragment)
                .commit();

    }

    private void land(NoteData noteData) {
        NotesDescription fragment = NotesDescription.newInstance(noteData);
        requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_description, fragment)
                .commit();
    }
}
