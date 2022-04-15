package cs160.UILayer;

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
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;

import java.util.Date;
import java.util.UUID;

import cs160.dataLayer.*;

public class TransactionFragment extends Fragment {
    private static final String ARG_TRANSACTION_ID = "transaction_id";
    private static final String DIALOG_DATE = "DialogDate";

    private Transaction mTransaction;
    private EditText mTitleField;
    private EditText mAmountField;
    ////TODO Add notes functionality
    private EditText mNotesField;
    private Button mDateButton;
    private Button mConfirmBtn;

    private Date mDate; // this is for saving a date before the transaction has been saved

    public static TransactionFragment newInstance(UUID transactionId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TRANSACTION_ID, transactionId);
        TransactionFragment fragment = new TransactionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UUID transactionId = (UUID) getArguments().getSerializable(ARG_TRANSACTION_ID);
        if (transactionId != null) {
            mTransaction = TransactionLab.get(getActivity()).getTransaction(transactionId);
        }
        if (mTransaction != null) {
            mDate = mTransaction.getDate();
        }
        if (mDate == null) {
            mDate = new Date();
        }
        setHasOptionsMenu(true);
        if (mTransaction != null) {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Transaction: " + mTransaction.getMerchant());
        } else {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("New Transaction");
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
        View v = inflater.inflate(R.layout.fragment_transaction, container, false);

        mTitleField = (EditText) v.findViewById(R.id.merchant_name);
        if (mTransaction != null) {
            mTitleField.setText(mTransaction.getMerchant());
        }
//        mTitleField.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                // This space intentionally left blank
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int count, int after) {
//                mTransaction.categorize(s.toString());
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                // This space intentionally left blank
//            }
//        });

        // Change amount for transaction in onPause() method
        mAmountField = (EditText) v.findViewById(R.id.transaction_amount);
        if (mTransaction != null) {
            Double amount = mTransaction.getAmount();
            if (amount != 0) {
                mAmountField.setText(amount.toString());
            }
        }
//        mAmountField.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                // This space intentionally left blank
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int count, int after) {
//                try {
//                    Double amount = Double.parseDouble(s.toString());
//                    mTransaction.setAmount(amount);
//                } catch (NumberFormatException e) {
//                    // on empty string (when user backspaces) or non-number input, amount should be 0
//                    mTransaction.setAmount(0.0);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                // This space intentionally left blank
//            }
//        });

        mDateButton = (Button) v.findViewById(R.id.transaction_date);
        updateDate();
//        mDateButton.setEnabled(false); // Disables button

        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getChildFragmentManager();
                DatePickerFragment dialog;
                if (mTransaction != null) {
                    dialog = DatePickerFragment.newInstance(mTransaction.getDate());
                } else {
                    dialog = DatePickerFragment.newInstance(new Date());
                }
                manager.setFragmentResultListener(DatePickerFragment.ARG_DATE, getViewLifecycleOwner(), new FragmentResultListener() {
                    @Override
                    public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                        mDate = (Date) result.getSerializable(DatePickerFragment.EXTRA_DATE);
                        updateDate();
                    }
                });
                dialog.show(manager, DIALOG_DATE);
            }
        });

        mConfirmBtn = v.findViewById(R.id.confirm_button);
        mConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTransaction == null) {
                    Transaction transaction = new Transaction();
                    TransactionLab transactionLab = TransactionLab.get(getActivity());
                    transactionLab.addTransaction(transaction);
                    mTransaction = transaction;
                }
                String merchantName = mTitleField.getText().toString();
                if (merchantName.isEmpty()) {
                    mTitleField.setError("Merchant name must not be empty");
                } else {
                    mTransaction.categorize(merchantName);
                    try {
                        Double amount = Double.parseDouble(mAmountField.getText().toString());
                        mTransaction.setAmount(amount);

                        mTransaction.setDate(mDate);

                        Intent intent = new Intent(getActivity(), TransactionListActivity.class);
                        startActivity(intent);
                    } catch (NumberFormatException e) {
                        mAmountField.setError("Amount must be a valid number");
                    }
                }
            }
        });

        return v;
    }

//    @Override
//    public void onPause() {
//        super.onPause();
//        double amount;
//        try {
//            amount = Double.parseDouble(mAmountField.getText().toString());
//        } catch (Exception e) {
//            amount = 0.0;
//        }
//        mTransaction.setAmount(amount);
//    }

    private void updateDate() {
        DateFormat df = new DateFormat();
        CharSequence formattedDate = df.format("E, MMM d, yyyy", mDate);
        mDateButton.setText(formattedDate);
    }
}