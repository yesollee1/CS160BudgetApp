package cs160.dataLayer;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

public class DatabaseManager {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public void addDataToDatabase(Category category){
//        db.collection("Users3").add(category).addOnSuccessListener(documentReference ->
//                Log.d(TAG, "Data added successfully")).addOnFailureListener(e ->
//                Log.d(TAG, "Error adding data"));
        db.collection("User").document(String.valueOf(category.mId)).set(category, SetOptions.merge());
    }
}