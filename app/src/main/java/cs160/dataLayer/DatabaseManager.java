package cs160.dataLayer;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class DatabaseManager {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public void addToGoals(Category category) {
        FirebaseUser current = mAuth.getCurrentUser();
        if (current == null) {
            db.collection("Users").document("Null User Alert").
                    set(category, SetOptions.merge());
            Log.d("error", "login not workin");
        }
       else {
            db.collection("Users").document(current.getUid()).collection("Goals").
                    document(category.getTitle()).set(category,SetOptions.merge());
        }
    }

    public static void addNewUser(String uid, String name) {
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> data = new HashMap<>();
        data.put("Name", name);
        db.collection("Users").document(uid).set(data,SetOptions.merge());
    }

}
