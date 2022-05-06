package cs160.UILayer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.UUID;

import cs160.dataLayer.Balance;
import cs160.dataLayer.Expense;
import cs160.dataLayer.ExpenseLab;
import cs160.dataLayer.Frequency;

public class IncomeFragment extends Fragment {
    private EditText mAmountField;
    private Button mConfirmBtn;

//    public static IncomeFragment newInstance() {
//        IncomeFragment fragment = new IncomeFragment();
//        return fragment;
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Add Income");
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.expense_list_activity:
                intent = new Intent(getActivity(), ExpenseListActivity.class);
                startActivity(intent);
                return true;
            case R.id.goal_list_activity:
                intent = new Intent(getActivity(), GoalListActivity.class);
                startActivity(intent);
                return true;
            case R.id.transaction_list_activity:
                intent = new Intent(getActivity(), TransactionListActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // The third argument of the method below tells whether to add the inflated view to the view's parent
        // Here, we pass in false because we will add the view in the activity's code
        View v = inflater.inflate(R.layout.fragment_income, container, false);

        mAmountField = (EditText) v.findViewById(R.id.income_amount);

        mConfirmBtn = v.findViewById(R.id.confirm_button);
        mConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Double amount = Double.parseDouble(mAmountField.getText().toString());
//                    ExpenseLab expenseLab = ExpenseLab.get(getActivity());
                    Balance.addIncome(amount);
                    Intent intent = new Intent(IncomeFragment.this.getActivity(), DashboardActivity.class);
                    startActivity(intent);
                } catch (NumberFormatException e) {
                    mAmountField.setError("Amount must be a valid number");
                }
            }
        });

        return v;
    }
}
