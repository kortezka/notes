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

    public CardsSourceImpl init() {
        String[] notes = resources.getStringArray(R.array.notes);
        String[] descriptionsShort = resources.getStringArray(R.array.descripShort);
        String[] descriptionsFull = resources.getStringArray(R.array.descripFull);
        for (int i = 0; i < descriptionsShort.length; i++) {
            dataSource.add(new NoteData(notes[i], descriptionsShort[i], descriptionsFull[i]));
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
}

