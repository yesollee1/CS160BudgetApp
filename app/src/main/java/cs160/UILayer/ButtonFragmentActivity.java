package cs160.UILayer;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

public abstract class ButtonFragmentActivity extends AppCompatActivity {
    protected abstract Fragment createButtonFragment();
    protected abstract Fragment createListFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_fragment);

        FragmentManager fm = getSupportFragmentManager();
        Fragment button_fragment = fm.findFragmentById(R.id.button_fragment_container);
        Fragment body_fragment = fm.findFragmentById(R.id.body_fragment_container);
        if (button_fragment == null) {
            button_fragment = createButtonFragment();
            fm.beginTransaction()
                    .add(R.id.button_fragment_container, button_fragment) // Creates fragment transaction
                    .commit(); // Commits fragment transaction
        }
        if (body_fragment == null) {
            body_fragment = createListFragment();
            fm.beginTransaction()
                    .add(R.id.body_fragment_container, body_fragment) // Creates fragment transaction
                    .commit(); // Commits fragment transaction
        }
    }
}