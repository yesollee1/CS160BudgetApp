package cs160.UILayer;

import cs160.dataLayer.*;

import static android.widget.CompoundButton.*;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;

import java.util.Date;
import java.util.UUID;

public class ExpenseFragment extends Fragment {
    private static final String ARG_EXPENSE_ID = "expense_id";

    private Expense mExpense;
    private EditText mTitleField;
    private EditText mAmountField;
    private Button mConfirmBtn;

    public static ExpenseFragment newInstance(UUID expenseId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_EXPENSE_ID, expenseId);
        ExpenseFragment fragment = new ExpenseFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID expenseId = (UUID) getArguments().getSerializable(ARG_EXPENSE_ID);
        if (expenseId != null) {
            mExpense = ExpenseLab.get(getActivity()).getExpense(expenseId);
        }
        setHasOptionsMenu(true);
        if (mExpense != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Expense: " + mExpense.getTitle());
        } else {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("New Expense");
        }
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
        View v = inflater.inflate(R.layout.fragment_expense, container, false);

        mTitleField = (EditText) v.findViewById(R.id.expense_title);
        if (mExpense != null) {
            mTitleField.setText(mExpense.getTitle());
        }

        mAmountField = (EditText) v.findViewById(R.id.expense_amount);
        if (mExpense != null) {
            Double proposedAmount = mExpense.getProposedAmount();
            if (proposedAmount != 0) {
                mAmountField.setText(proposedAmount.toString());
            }
        }

        mConfirmBtn = v.findViewById(R.id.confirm_button);
        mConfirmBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = mTitleField.getText().toString();
                if (title.isEmpty()) {
                    mTitleField.setError("Title must not be empty");
                } else {
                    boolean expenseAdded = true;
                    try {
                        Double amount = Double.parseDouble(mAmountField.getText().toString());
                        if (mExpense == null) {
                            Expense expense = new Expense(title, Frequency.MONTHLY, amount);
                            ExpenseLab expenseLab = ExpenseLab.get(getActivity());
                            expenseAdded = expenseLab.addExpense(expense);
                            if (!expenseAdded) {
//                                Toast.makeText("You only have $" + expenseLab.getBalance() + " to use.", Toast.LENGTH_LONG).show();
                                mAmountField.setError("You only have $" + Balance.getBalance() + " to use.");
                            } else {
                                mExpense = expense;
                            }
                        } else {
                            mExpense.setTitle(title);
                            mExpense.setProposedAmount(amount);
                            mExpense.setCurrentAmount(mExpense.getProposedAmount() - mExpense.getAmountSpent());
                        }

                        if (expenseAdded) {
                            Intent intent = new Intent(ExpenseFragment.this.getActivity(), ExpenseListActivity.class);
                            startActivity(intent);
                        }
                    } catch (NumberFormatException e) {
                        mAmountField.setError("Amount must be a valid number");
                    }
                }
            }
        });

        return v;
    }
}
