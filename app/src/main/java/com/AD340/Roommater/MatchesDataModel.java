package com.AD340.Roommater;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class MatchesDataModel {
    private FirebaseFirestore db;
    private List<ListenerRegistration> listeners;

    public MatchesDataModel() {
        db = FirebaseFirestore.getInstance();
        listeners = new ArrayList<>();
    }

    public void getMatches(Consumer<QuerySnapshot> dataChangedCallback, Consumer<FirebaseFirestoreException> dataErrorCallback) {
        ListenerRegistration listener = db.collection("users")
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
                        dataErrorCallback.accept(e);
                    }

                    dataChangedCallback.accept(queryDocumentSnapshots);
                });
        listeners.add(listener);
    }

    public void updateMatchById(Matches match) {
        DocumentReference todoItemRef = db.collection("users").document(match.getUid());
        Map<String, Object> data = new HashMap<>();
        data.put("age", match.getAge());
        data.put("email", match.getEmail());
        data.put("liked", match.isLiked());
        data.put("name", match.getName());
        data.put("uid", match.getUid());
        todoItemRef.update(data);
    }

    public void clear() {
        // Clear all the listeners onPause
        listeners.forEach(ListenerRegistration::remove);
    }
}
