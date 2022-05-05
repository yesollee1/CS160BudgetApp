package cs160.UILayer;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentResultListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import cs160.dataLayer.*;

public class TransactionFragment extends Fragment {
    private static final String ARG_TRANSACTION_ID = "transaction_id";
    private static final String DIALOG_DATE = "DialogDate";

    private Transaction mTransaction;
    private EditText mTitleField;
    private EditText mAmountField;
    //TODO: Add notes functionality
    private EditText mNotesField;
    //TODO: Add categorize functionality
    private Spinner mExpenseList;
    private Button mDateButton;
    private TextView mSpendFromText;
    private Button mCategorizeButton;
    private Button mConfirmBtn;

    private Date mDate; // this is for saving a date before the transaction has been saved
    private String mExpenseName;

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
        mAmountField = (EditText) v.findViewById(R.id.transaction_amount);
        mNotesField = (EditText) v.findViewById(R.id.transaction_notes);
        mDateButton = (Button) v.findViewById(R.id.transaction_date);
//        mExpenseList = (Spinner) v.findViewById(R.id.expense_list);
        mSpendFromText = (TextView) v.findViewById(R.id.spend_from_text);
        mCategorizeButton = (Button) v.findViewById(R.id.categorize_button);

        if (mTransaction != null) {
            mTitleField.setText(mTransaction.getMerchant());
            Double amount = mTransaction.getAmount();
            if (amount != 0) {
                mAmountField.setText(amount.toString());
            }
            mNotesField.setText(mTransaction.getNotes());
        }

        updateCategorizeUI();
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
//        String catBtnLabel;
//        if (mExpenseName != null) {
//            catBtnLabel = String.format(getResources().getString(R.string.categorize_button_label), mExpenseName);
//        } else {
//            catBtnLabel = "Categorize Transaction";
//        }

        updateCategorizeUI();
        mCategorizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_spinner, null);
                builder.setTitle("Choose an expense to spend from");
                Spinner mSpinner = (Spinner) dialogView.findViewById(R.id.expense_spinner);
                ArrayList<String> expenseNames = ExpenseLab.get(getActivity()).getExpenseNames();
                expenseNames.add(0, getResources().getString(R.string.expense_list_prompt));
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                        android.R.layout.simple_list_item_1, expenseNames);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner.setAdapter(adapter);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (!mSpinner.getSelectedItem().toString().equalsIgnoreCase(getResources().getString(R.string.expense_list_prompt))) {
                            mExpenseName = mSpinner.getSelectedItem().toString();
                            Toast.makeText(getActivity(),"Spending from " + mExpenseName, Toast.LENGTH_LONG).show();
                            updateCategorizeUI();
                            dialogInterface.dismiss();
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.setView(dialogView);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

////        String[] expenseNames = ExpenseLab.get(getActivity()).getExpenseNames();
//        ArrayList<String> expenseNames = ExpenseLab.get(getActivity()).getExpenseNames();
////        expenseNames.add(0, "Choose Expense:");
////        mExpenseList.setPromptId(expenseNames.indexOf(mTransaction.getExpenseName()));
////        mExpenseList.setPrompt(mTransaction.getExpenseName());
//        mExpenseList.setAdapter(new ArrayAdapter<String>(getActivity(),
//                android.R.layout.simple_list_item_1, expenseNames));
//        mExpenseList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
////                if (position > 0) {
//                    mExpenseName = expenseNames.get(position);
////                }
////                Log.d("Transaction Spinner", "Position selected: " + position);
////                Expense expense = ExpenseLab.get(getActivity()).getExpenseByName(expenseNames[position]);
////                expense.addTransaction(mTransaction);
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//                Log.d("Spinner Transaction", "Nothing selected");
//            }
//        });

        mConfirmBtn = v.findViewById(R.id.confirm_button);
        mConfirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String merchantName = mTitleField.getText().toString();
                if (merchantName.isEmpty()) {
                    mTitleField.setError("Merchant name must not be empty");
                } else {
                    try {
                        Double amount = Double.parseDouble(mAmountField.getText().toString());
                        if (mTransaction == null) {
                            Transaction transaction = new Transaction();
                            TransactionLab transactionLab = TransactionLab.get(getActivity());
                            transactionLab.addTransaction(transaction);
                            mTransaction = transaction;
                        }

                        mTransaction.setMerchant(merchantName);
                        mTransaction.setAmount(amount);
                        mTransaction.setNotes(mNotesField.getText().toString());

                        mTransaction.setDate(mDate);

                        if (mExpenseName != null) {
                            mTransaction.spendFrom(getActivity(), mExpenseName);
                            databaseManager.updateExpenses(getActivity(), mExpenseName);
                        }

                        databaseManager.addToTransactions(mTransaction);

                        //// use the following code instead of the line above when Plaid is integrated
//                    if (!mTransaction.spendFrom(getActivity(), mExpenseName)) {
//                        Toast.makeText(getActivity(),
//                                "Could not spend from " + merchantName + ".\nInsufficient funds.",
//                                Toast.LENGTH_LONG);
//                    } else {
//                        Toast.makeText(getActivity(), mAmountField.toString() + " spent from " + merchantName, Toast.LENGTH_LONG);
//                    }

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

//// We should probably use the onPause() method to store info when the date fragment pops up
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

    private void updateCategorizeUI() {
        if (mExpenseName != null) {
            mCategorizeButton.setText(mExpenseName);
        } else {
            mCategorizeButton.setText("None");
        }
    }
    private final DatabaseManager databaseManager = new DatabaseManager();
}