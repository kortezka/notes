package ru.geekbrains.fragmentnotes;

public interface CardsSource {
    CardsSource init(CardsSourceResponse cardsSourceResponse);
    NoteData getCardData(int position);
    int size();
    void clear();
    void addNote(NoteData noteData);
    void delete(int position);
    void update(NoteData noteData,int position);

}
