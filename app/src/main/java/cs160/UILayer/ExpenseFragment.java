package cs160.UILayer;

import cs160.dataLayer.*;

import static android.widget.CompoundButton.*;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import java.util.Date;
import java.util.UUID;

public class ExpenseFragment extends Fragment {
    private static final String ARG_EXPENSE_ID = "expense_id";

    private Expense mExpense;
    private EditText mTitleField;
    private Button mDateButton;
    private CheckBox mSolvedCheckBox;

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
//        mExpense = new Expense();
//        UUID expenseId = (UUID) getActivity().getIntent()
//                .getSerializableExtra(ExpenseActivity.EXTRA_EXPENSE_ID);
        UUID expenseId = (UUID) getArguments().getSerializable(ARG_EXPENSE_ID);
        mExpense = ExpenseLab.get(getActivity()).getExpense(expenseId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // The third argument of the method below tells whether to add the inflated view to the view's parent
        // Here, we pass in false because we will add the view in the activity's code
        View v = inflater.inflate(R.layout.fragment_expense, container, false);

        mTitleField = (EditText) v.findViewById(R.id.expense_title);
        mTitleField.setText(mExpense.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // This space intentionally left blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after) {
                mExpense.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // This space intentionally left blank
            }
        });

        mDateButton = (Button) v.findViewById(R.id.expense_date);
        mDateButton.setText(mExpense.getDate().toString());
        DateFormat df = new DateFormat();
        CharSequence formattedDate = df.format("E, MMM d, yyyy", mExpense.getDate());
        mDateButton.setText(formattedDate);
        mDateButton.setEnabled(false);

//        mSolvedCheckBox = (CheckBox) v.findViewById(R.id.expense_solved);
//        mSolvedCheckBox.setChecked(mExpense.isSolved());
//        mSolvedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                mExpense.setSolved(isChecked);
//            }
//        });

        return v;
    }
}
