package cs160.UILayer;

import static android.content.ContentValues.TAG;

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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.util.List;

import cs160.dataLayer.*;

public class TransactionListFragment extends Fragment {
    private RecyclerView mTransactionRecyclerView;
    private TransactionListFragment.TransactionAdapter mAdapter;
    private int mLastClickedPosition;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static boolean dataPopulated = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Transactions");
        FirebaseUser current = mAuth.getCurrentUser();
        db.collection("Users").document(current.getUid()).collection("Transactions").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful() && !dataPopulated){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Transaction transaction = new Transaction(document.getString("title"), document.getDouble("amount"));
                        TransactionLab transactionLab = TransactionLab.get(getActivity());
                        transactionLab.addTransaction(transaction);
                        updateUI();
                        Log.d(TAG, document.getId() + " => " + document.getData());
                    }
                    dataPopulated = true;
                } else {
                    Log.d(TAG, "Error getting documents: ", task.getException());
                }
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        mTransactionRecyclerView = (RecyclerView) view.findViewById(R.id.item_recycler_view);
        mTransactionRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
        TransactionLab transactionLab = TransactionLab.get(getActivity());
        List<Transaction> transactions = transactionLab.getTransactions();
        if (mAdapter == null) {
            mAdapter = new TransactionListFragment.TransactionAdapter(transactions);
            mTransactionRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyItemChanged(mLastClickedPosition);
        }
    }

    private class TransactionHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mAmountTextView;
        private TextView mDateTextView;

        private Transaction mTransaction;

        public TransactionHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_transaction, parent, false));
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.merchant_name);
            mAmountTextView = (TextView) itemView.findViewById(R.id.transaction_amount);
            mDateTextView = (TextView) itemView.findViewById(R.id.transaction_date);
        }

        public void bind(Transaction transaction) {
            DecimalFormat numFormat = new DecimalFormat("#.00");
            mTransaction = transaction;
            mTitleTextView.setText(mTransaction.getMerchant());
            mAmountTextView.setText("$" + numFormat.format(mTransaction.getAmount()));
            DateFormat dateFormat = new DateFormat();
            CharSequence formattedDate = dateFormat.format("MMM d, yyyy", mTransaction.getDate());
            mDateTextView.setText(formattedDate);
        }

        @Override
        public void onClick(View view) {
            Intent intent = TransactionActivity.newIntent(getActivity(), mTransaction.getId());
            mLastClickedPosition = getAdapterPosition();
            startActivity(intent);
        }
    }

    private class TransactionAdapter extends RecyclerView.Adapter<TransactionListFragment.TransactionHolder> {
        private List<Transaction> mTransactions;

        public TransactionAdapter(List<Transaction> transactions) {
            mTransactions = transactions;
        }

        @NonNull
        @Override
        public TransactionListFragment.TransactionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new TransactionListFragment.TransactionHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull TransactionListFragment.TransactionHolder holder, int position) {
            Transaction transaction = mTransactions.get(position);
            holder.bind(transaction);
        }

        @Override
        public int getItemCount() {
            return mTransactions.size();
        }
    }
}