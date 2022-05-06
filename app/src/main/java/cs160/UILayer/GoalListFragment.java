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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DecimalFormat;
import java.util.List;

public class GoalListFragment extends Fragment {
    private RecyclerView mGoalRecyclerView;
    private GoalAdapter mAdapter;
    private int mLastClickedPosition;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private static boolean dataPopulated = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Goals");
        FirebaseUser current = mAuth.getCurrentUser();
        db.collection("Users").document(current.getUid()).collection("Goals").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful() && !dataPopulated){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Goal goal = new Goal(document.getId(), Frequency.MONTHLY, document.getDouble("amountSpent"), document.getDate("date"));
                        GoalLab goalLab = GoalLab.get(getActivity());
                        goalLab.addGoal(goal);
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
        mGoalRecyclerView = (RecyclerView) view.findViewById(R.id.item_recycler_view);
        mGoalRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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
        GoalLab goalLab = GoalLab.get(getActivity());
        List<Goal> goals = goalLab.getGoals();
        if (mAdapter == null) {
            mAdapter = new GoalAdapter(goals);
            mGoalRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyItemChanged(mLastClickedPosition);
        }
    }

    private class GoalHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mAmountTextView;
        private TextView mDateTextView;
        private Goal mGoal;
        private ProgressBar mProgressBar;

        public GoalHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_goal, parent, false));
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.goal_title);
            mAmountTextView = (TextView) itemView.findViewById(R.id.goal_amount);
            mDateTextView = (TextView) itemView.findViewById(R.id.goal_date);
            mProgressBar = (ProgressBar) itemView.findViewById(R.id.goal_progress_bar);
        }

        public void bind(Goal goal) {
            DecimalFormat numFormat = new DecimalFormat("#.00");
            mGoal = goal;
            mTitleTextView.setText(mGoal.getTitle());
            if (mGoal.getCurrentAmount() % 1 == 0) {
                numFormat = new DecimalFormat("##.##");
            } else {
                numFormat = new DecimalFormat("#.00");
            }
            mAmountTextView.setText("$" + numFormat.format(mGoal.getCurrentAmount()) + " saved of $" + numFormat.format(mGoal.getProposedAmount()));
            DateFormat dateFormat = new DateFormat();
            CharSequence formattedDate = dateFormat.format("MMM d, yyyy", mGoal.getDate());
            mDateTextView.setText(formattedDate);
            mProgressBar.setMax((int) Math.round(mGoal.getProposedAmount()));
            mProgressBar.setProgress((int) Math.round(mGoal.getCurrentAmount()));
        }

        @Override
        public void onClick(View view) {
            Intent intent = GoalActivity.newIntent(getActivity(), mGoal.getId());
            mLastClickedPosition = getAdapterPosition();
            startActivity(intent);
        }
    }

    private class GoalAdapter extends RecyclerView.Adapter<GoalHolder> {
        private List<Goal> mGoals;

        public GoalAdapter(List<Goal> goals) {
            mGoals = goals;
        }

        @NonNull
        @Override
        public GoalHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new GoalHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull GoalHolder holder, int position) {
            Goal goal = mGoals.get(position);
            holder.bind(goal);
        }

        @Override
        public int getItemCount() {
            return mGoals.size();
        }
    }
}
