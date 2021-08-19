package ru.geekbrains.fragmentnotes;

import android.content.res.Resources;

import java.util.ArrayList;
import java.util.List;

public class CardsSourceImpl implements CardsSource {
    public List<NoteData> dataSource;
    private Resources resources;

    public CardsSourceImpl(Resources resources) {
        dataSource = new ArrayList<>(2);
        this.resources = resources;
    }

    public CardsSource init(CardsSourceResponse cardsSourceResponse) {
        String[] notes = resources.getStringArray(R.array.notes);
        String[] descriptionsShort = resources.getStringArray(R.array.descripShort);
        String[] descriptionsFull = resources.getStringArray(R.array.descripFull);
        for (int i = 0; i < descriptionsShort.length; i++) {
            dataSource.add(new NoteData(notes[i], descriptionsShort[i], descriptionsFull[i]));
        }

        if (cardsSourceResponse != null){
            cardsSourceResponse.initialized(this);
        }
        return this;

    }

    @Override
    public NoteData getCardData(int position) {
        return dataSource.get(position);
    }

    @Override
    public int size() {
        return dataSource.size();
    }

    @Override
    public void clear() { dataSource.clear();

    }

    @Override
    public void addNote(NoteData noteData) {
        dataSource.add(noteData);
    }

    @Override
    public void delete(int position) {
        dataSource.remove(position);
    }
    @Override
    public void update(NoteData noteData,int position) {
        dataSource.set(position, noteData);
    }


}

