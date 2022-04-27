package cs160.UILayer;

import cs160.dataLayer.*;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
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

import java.util.List;
import java.util.UUID;

public class ExpenseListFragment extends Fragment {
    private RecyclerView mExpenseRecyclerView;
    private ExpenseAdapter mAdapter;
    private int mLastClickedPosition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Expenses");
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
            mExpense = expense;
            mTitleTextView.setText(mExpense.getTitle());
            mCurrentAmountTextView.setText("$" + mExpense.getCurrentAmount().toString() + " left");
            mAmountSpentTextView.setText("$" + (mExpense.getProposedAmount()-mExpense.getCurrentAmount()) + " spent of $" + mExpense.getProposedAmount());
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
