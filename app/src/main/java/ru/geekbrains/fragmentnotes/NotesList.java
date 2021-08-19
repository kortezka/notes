package ru.geekbrains.fragmentnotes;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


public class NotesList extends Fragment {

    protected static boolean isLand;
    CardsSource cardData;
    Adapter adapter;

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
                cardData.delete(position);
                adapter.notifyItemRemoved(position);
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

        switch (id) {
            case R.id.clear:
                cardData.clear();
                adapter.notifyDataSetChanged();
                return true;
            case R.id.refresh:

                return true;
            case R.id.add:
                AddUpdateFragment fragment = AddUpdateFragment.newInstance();
                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(null)
                        .replace(R.id.fragmentContainerView, fragment)
                        .commit();

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

       // cardData = new CardsSourceImpl(getResources()).init(response);

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
