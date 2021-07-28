package ru.geekbrains.fragmentnotes;

public interface CardsSource {
    NoteData getCardData(int position);

    int size();
}
