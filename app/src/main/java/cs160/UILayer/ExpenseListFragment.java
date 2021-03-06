package cs160.UILayer;

import static android.content.ContentValues.TAG;

import cs160.dataLayer.*;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.util.List;
import java.util.UUID;

public class ExpenseListFragment extends Fragment {
    private RecyclerView mExpenseRecyclerView;
    private ExpenseAdapter mAdapter;
    private int mLastClickedPosition;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static boolean dataPopulated = false;
    private static boolean incomePopulated = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Expenses");
        String pattern = Balance.getBalance() % 1 == 0 ? "#.##" : "#.00";
        DecimalFormat df = new DecimalFormat(pattern);
//        ExpenseLab expenseLab = ExpenseLab.get(getActivity());j
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Balance: $" + df.format(Balance.getBalance()));
        FirebaseUser current = mAuth.getCurrentUser();
        db.collection("Users").document(current.getUid()).collection("Budget").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful() && !dataPopulated){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Expense expense = new Expense(document.getId(), Frequency.MONTHLY, document.getDouble("proposedAmount"), document.getDouble("currentAmount"));
                        ExpenseLab expenseLab = ExpenseLab.get(getActivity());
                        expenseLab.populateExpense(expense);
                        updateUI();
                        Log.d(TAG, document.getId() + " => " + document.getData());
                    }
                    dataPopulated = true;
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
        /**DocumentReference docRef = db.collection("Users").document(current.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful() && (!incomePopulated)) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
//                        double total = 0.0;
//                        for(double amount:sumOfBudget){
//                            total += amount;
//                        }
                        Balance.addIncome(document.getDouble("Income"));
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        incomePopulated = true;
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });**/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        mExpenseRecyclerView = (RecyclerView) view.findViewById(R.id.item_recycler_view);
        mExpenseRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
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
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        ExpenseLab expenseLab = ExpenseLab.get(getActivity());
        List<Expense> expenses = expenseLab.getExpenses();
        if (mAdapter == null) {
            mAdapter = new ExpenseAdapter(expenses);
            mExpenseRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyItemChanged(mLastClickedPosition);
        }
    }

    private class ExpenseHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mCurrentAmountTextView;
        private TextView mAmountSpentTextView;
        private ProgressBar mProgressBar;

        private Expense mExpense;

        public ExpenseHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_expense, parent, false));
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.expense_title);
            mCurrentAmountTextView = (TextView) itemView.findViewById(R.id.expense_current_amount);
            mAmountSpentTextView = (TextView) itemView.findViewById(R.id.expense_spent_amount);
            mProgressBar = (ProgressBar) itemView.findViewById(R.id.expense_progress_bar);
        }

        public void bind(Expense expense) {
            String pattern = expense.getCurrentAmount() % 1 == 0 ? "#.##" : "#.00";
            DecimalFormat df = new DecimalFormat(pattern);
            mExpense = expense;
            mTitleTextView.setText(mExpense.getTitle());
            mCurrentAmountTextView.setText("$" + df.format(mExpense.getCurrentAmount()) + " left");
            mAmountSpentTextView.setText("$" +
                    Math.round(mExpense.getProposedAmount()-mExpense.getCurrentAmount()) +
                    " spent of $" + Math.round(mExpense.getProposedAmount()));
            mProgressBar.setMax((int) Math.round(mExpense.getProposedAmount()));
            mProgressBar.setProgress((int) Math.round(mExpense.getCurrentAmount()));
        }

        @Override
        public void onClick(View view) {
            Intent intent = ExpenseActivity.newIntent(getActivity(), mExpense.getId());
            mLastClickedPosition = getAdapterPosition();
            startActivity(intent);
        }
    }

    private class ExpenseAdapter extends RecyclerView.Adapter<ExpenseHolder> {
        private List<Expense> mExpenses;

        public ExpenseAdapter(List<Expense> expenses) {
            mExpenses = expenses;
        }

        @NonNull
        @Override
        public ExpenseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ExpenseHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull ExpenseHolder holder, int position) {
            Expense expense = mExpenses.get(position);
            holder.bind(expense);
        }

        @Override
        public int getItemCount() {
            return mExpenses.size();
        }
    }
}
