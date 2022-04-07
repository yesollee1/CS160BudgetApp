package cs160.UILayer;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cs160.dataLayer.Expense;
import cs160.dataLayer.ExpenseLab;
import cs160.dataLayer.Frequency;

public class ExpenseButtonFragment extends Fragment {
    private Button mAddNewButton;
    private int mLastClickedPosition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_button, container, false);
        mAddNewButton = (Button) view.findViewById(R.id.add_new_button);
        mAddNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Expense expense = new Expense();
                ExpenseLab expenseLab = ExpenseLab.get(getActivity());
                expenseLab.addExpense(expense);
                Intent intent = ExpenseActivity.newIntent(getActivity(), expense.getId());
                startActivity(intent);
            }
        });
//        updateUI();
        return view;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        updateUI();
//    }
//
//    private void updateUI() {
//        ExpenseLab expenseLab = ExpenseLab.get(getActivity());
//        List<Expense> expenses = expenseLab.getExpenses();
//        if (mAdapter == null) {
//            mAdapter = new ExpenseListFragment.ExpenseAdapter(expenses);
//            mExpenseRecyclerView.setAdapter(mAdapter);
//        } else {
//            mAdapter.notifyItemChanged(mLastClickedPosition);
//        }
//    }
//
//    private class ExpenseHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        private TextView mTitleTextView;
//        private TextView mAmountTextView;
//        private TextView mDateTextView;
////        private ImageView mSolvedImageView;
//
//        private Expense mExpense;
//
//        public ExpenseHolder(LayoutInflater inflater, ViewGroup parent) {
//            super(inflater.inflate(R.layout.list_item_expense, parent, false));
//            itemView.setOnClickListener(this);
//            mTitleTextView = (TextView) itemView.findViewById(R.id.expense_title);
//            mAmountTextView = (TextView) itemView.findViewById(R.id.expense_amount);
//            mDateTextView = (TextView) itemView.findViewById(R.id.expense_date);
////            mSolvedImageView = (ImageView) itemView.findViewById(R.id.expense_solved);
//        }
//
//        public void bind(Expense expense) {
//            mExpense = expense;
//            mTitleTextView.setText(mExpense.getTitle());
//            mAmountTextView.setText(mExpense.getProposedAmount().toString());
////            mDateTextView.setText(mExpense.getDate().toString());
////            DateFormat df = new DateFormat();
////            CharSequence formattedDate = df.format("E, MMM d, yyyy", mExpense.getDate());
////            mDateTextView.setText(formattedDate);
////            mSolvedImageView.setVisibility(expense.isSolved() ? View.VISIBLE : View.GONE);
//        }
//
//        @Override
//        public void onClick(View view) {
////            Toast.makeText(getActivity(), mExpense.getTitle() + " clicked!", Toast.LENGTH_SHORT).show();
//            Intent intent = ExpenseActivity.newIntent(getActivity(), mExpense.getId());
//            mLastClickedPosition = getAdapterPosition();
//            startActivity(intent);
//        }
//    }
//
//    private class ExpenseAdapter extends RecyclerView.Adapter<ExpenseListFragment.ExpenseHolder> {
//        private List<Expense> mExpenses;
//
//        public ExpenseAdapter(List<Expense> expenses) {
//            mExpenses = expenses;
//        }
//
//        @NonNull
//        @Override
//        public ExpenseListFragment.ExpenseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
//            return new ExpenseListFragment.ExpenseHolder(layoutInflater, parent);
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull ExpenseListFragment.ExpenseHolder holder, int position) {
//            Expense expense = mExpenses.get(position);
//            holder.bind(expense);
//        }
//
//        @Override
//        public int getItemCount() {
//            return mExpenses.size();
//        }
//    }
}