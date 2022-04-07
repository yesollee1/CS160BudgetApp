package cs160.UILayer;

import cs160.dataLayer.*;

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

public class GoalListFragment extends Fragment {
    private RecyclerView mGoalRecyclerView;
    private GoalAdapter mAdapter;
    private int mLastClickedPosition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        mGoalRecyclerView = (RecyclerView) view.findViewById(R.id.item_recycler_view);
        mGoalRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return view;
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
//        private ImageView mSolvedImageView;

        private Goal mGoal;

        public GoalHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_goal, parent, false));
            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.goal_title);
            mAmountTextView = (TextView) itemView.findViewById(R.id.goal_amount);
            mDateTextView = (TextView) itemView.findViewById(R.id.goal_date);
        }

        public void bind(Goal goal) {
            mGoal = goal;
            mTitleTextView.setText(mGoal.getTitle());
            mAmountTextView.setText(mGoal.getProposedAmount().toString());
            DateFormat df = new DateFormat();
            CharSequence formattedDate = df.format("MMM d, yyyy", mGoal.getDate());
            mDateTextView.setText(formattedDate);
        }

        @Override
        public void onClick(View view) {
//            Toast.makeText(getActivity(), mGoal.getTitle() + " clicked!", Toast.LENGTH_SHORT).show();
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
