package cs160.UILayer;

import cs160.dataLayer.*;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ExpenseListFragment extends Fragment {
    private RecyclerView mExpenseRecyclerView;
    private ExpenseAdapter mAdapter;
    private int mLastClickedPosition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
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
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_list, menu);
//        MenuItem subtitleItem = menu.findItem(R.id.show_subtitle);
//        if (mSubtitleVisible) {
//            subtitleItem.setTitle(R.string.hide_subtitle);
//        } else {
//            subtitleItem.setTitle(R.string.show_subtitle);
//        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.expense_list_activity:
//                Expense crime = new Expense();
//                ExpenseLab.get(getActivity()).addExpense(crime);
//                Intent intent = ExpenseActivity.newIntent(getActivity(), crime.getId());
                intent = new Intent(getActivity(), ExpenseListActivity.class);
                startActivity(intent);
                return true;
            case R.id.goal_list_activity:
//                Expense crime = new Expense();
//                ExpenseLab.get(getActivity()).addExpense(crime);
//                Intent intent = ExpenseActivity.newIntent(getActivity(), crime.getId());
                intent = new Intent(getActivity(), GoalListActivity.class);
                startActivity(intent);
                return true;
            case R.id.transaction_list_activity:
//                Expense crime = new Expense();
//                ExpenseLab.get(getActivity()).addExpense(crime);
//                Intent intent = ExpenseActivity.newIntent(getActivity(), crime.getId());
                intent = new Intent(getActivity(), TransactionListActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

//    private void updateSubtitle() {
//        CrimeLab crimeLab = CrimeLab.get(getActivity());
//        int crimeCount = crimeLab.getCrimes().size();
//        String subtitle = getResources().getQuantityString(R.plurals.subtitle_plural, crimeCount, crimeCount);
//        if (!mSubtitleVisible) {
//            subtitle = null;
//        }
//        AppCompatActivity activity = (AppCompatActivity) getActivity();
//        activity.getSupportActionBar().setSubtitle(subtitle);
//    }

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
        private TextView mAmountTextView;
//        private TextView mDateTextView;
//        private ImageView mSolvedImageView;

        private Expense mExpense;

        public ExpenseHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_expense, parent, false));
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.expense_title);
            mAmountTextView = (TextView) itemView.findViewById(R.id.expense_amount);
//            mDateTextView = (TextView) itemView.findViewById(R.id.expense_date);
//            mSolvedImageView = (ImageView) itemView.findViewById(R.id.expense_solved);
        }

        public void bind(Expense expense) {
            mExpense = expense;
            mTitleTextView.setText(mExpense.getTitle());
            mAmountTextView.setText(mExpense.getProposedAmount().toString());
//            mDateTextView.setText(mExpense.getDate().toString());
//            DateFormat df = new DateFormat();
//            CharSequence formattedDate = df.format("E, MMM d, yyyy", mExpense.getDate());
//            mDateTextView.setText(formattedDate);
//            mSolvedImageView.setVisibility(expense.isSolved() ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View view) {
//            Toast.makeText(getActivity(), mExpense.getTitle() + " clicked!", Toast.LENGTH_SHORT).show();
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
