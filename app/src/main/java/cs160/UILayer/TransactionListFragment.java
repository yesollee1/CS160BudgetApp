package cs160.UILayer;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cs160.dataLayer.*;

public class TransactionListFragment extends Fragment {
    private RecyclerView mTransactionRecyclerView;
    private TransactionListFragment.TransactionAdapter mAdapter;
    private int mLastClickedPosition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        mTransactionRecyclerView = (RecyclerView) view.findViewById(R.id.item_recycler_view);
        mTransactionRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
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
//        private ImageView mSolvedImageView;

        private Transaction mTransaction;

        public TransactionHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_transaction, parent, false));
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.merchant_name);
            mAmountTextView = (TextView) itemView.findViewById(R.id.transaction_amount);
            mDateTextView = (TextView) itemView.findViewById(R.id.transaction_date);
        }

        public void bind(Transaction transaction) {
            mTransaction = transaction;
            mTitleTextView.setText(mTransaction.getMerchant());
            mAmountTextView.setText(mTransaction.getAmount().toString());
            DateFormat df = new DateFormat();
            CharSequence formattedDate = df.format("MMM d, yyyy", mTransaction.getDate());
            mDateTextView.setText(formattedDate);
        }

        @Override
        public void onClick(View view) {
//            Toast.makeText(getActivity(), mTransaction.getTitle() + " clicked!", Toast.LENGTH_SHORT).show();
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