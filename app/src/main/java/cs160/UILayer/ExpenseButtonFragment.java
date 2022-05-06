package cs160.UILayer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cs160.dataLayer.Expense;
import cs160.dataLayer.ExpenseLab;
import cs160.dataLayer.Frequency;

public class ExpenseButtonFragment extends Fragment {
    private Button mAddNewButton;
    private int mLastClickedPosition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_button, container, false);
        mAddNewButton = (Button) view.findViewById(R.id.add_new_button);
        mAddNewButton.setText("Add Expense");
        mAddNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = ExpenseActivity.newIntent(getActivity(), null);
                startActivity(intent);
            }
        });
        return view;
    }
}