package cs160.UILayer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cs160.dataLayer.*;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ExpenseListActivity extends ButtonFragmentActivity {
    @Override
    protected Fragment createButtonFragment() {
        return new ExpenseButtonFragment();
    }

    @Override
    protected Fragment createListFragment() {
        return new ExpenseListFragment();
    }


//    private FirebaseAuth firebaseAuth;
//
//    private Button logoutBtn;
//    private TextView signedInUser;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_dummy);
//
//        //init firebaseAuth
//        firebaseAuth = FirebaseAuth.getInstance();
//        checkUser();
//
//        logoutBtn = findViewById(R.id.logoutBtn);
//        signedInUser = findViewById(R.id.signedInUser);
//
//        logoutBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                firebaseAuth.signOut();
//                checkUser();
//            }
//        });
//
//
//
//
//    }
//
//    private void checkUser() {
//        //get current user
//        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
//        if(firebaseUser == null){
//            // user not logged in
//            startActivity(new Intent(this, SignIn_Activity.class));
//            finish();
//        }
//        else{
//            //user logged in
//            // get user info
//            String email = firebaseUser.getEmail();
//            //set email
////            signedInUser.setText(email);
//        }
//    }
}
