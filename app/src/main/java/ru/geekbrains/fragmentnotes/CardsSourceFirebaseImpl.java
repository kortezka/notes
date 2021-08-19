package ru.geekbrains.fragmentnotes;


import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CardsSourceFirebaseImpl implements CardsSource {

    private static final String CARDS_COLLECTION = "cards";
    private static final String TAG = "[CardsSourceFirebaseImpl]";

    // База данных Firestore
    private FirebaseFirestore store = FirebaseFirestore.getInstance();

    // Коллекция документов
    private CollectionReference collection = store.collection(CARDS_COLLECTION);

    // Загружаемый список карточек
    private List<NoteData> cardsData = new ArrayList<NoteData>();

    @Override
    public CardsSource init( CardsSourceResponse cardsSourceResponse) {
        // Получить всю коллекцию, отсортированную по полю «Дата»
        // При удачном считывании данных загрузим список карточек
        collection.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        cardsData = new ArrayList<NoteData>();
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Map<String, Object> doc = document.getData();
                            String id = document.getId();
                            NoteData cardData = CardDataMapping.toCardData(id, doc);
                            cardsData.add(cardData);
                        }
                        Log.d(TAG, "success " + cardsData.size() + " qnt");
                        cardsSourceResponse.initialized(CardsSourceFirebaseImpl.this);
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                })
                .addOnFailureListener(e -> Log.d(TAG, "get failed with ", e));
        return this;
    }

    @Override
    public NoteData getCardData(int position) {
        return cardsData.get(position);
    }

    @Override
    public int size() {
        if (cardsData == null){
            return 0;
        }
        return cardsData.size();
    }

    @Override
    public void delete(int position) {
        // Удалить документ с определённым идентификатором
        collection.document(cardsData.get(position).getId()).delete();
        cardsData.remove(position);
    }

    @Override
    public void update( NoteData cardData, int position) {
        String id = cardData.getId();
        // Изменить документ по идентификатору
        collection.document(id).set(CardDataMapping.toDocument(cardData));
    }

    @Override
    public void addNote( NoteData cardData) {
        // Добавить документ
        collection.add(CardDataMapping.toDocument(cardData))
                .addOnSuccessListener(documentReference -> cardData.setId(documentReference.getId()));
    }

    @Override
    public void clear() {
        for (NoteData cardData: cardsData) {
            collection.document(cardData.getId()).delete();
        }
        cardsData = new ArrayList<NoteData>();
    }
}

