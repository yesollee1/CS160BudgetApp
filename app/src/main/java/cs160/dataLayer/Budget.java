package cs160.dataLayer;

import android.content.Context;
import android.widget.Toast;

public class Budget {
    private static Double mBalance = 0.0;
    private final DatabaseManager databaseManager = new DatabaseManager();

    public static Double getBalance() {
        return mBalance;
    }

    public static void addIncome(Double amount) {
        final DatabaseManager databaseManager = new DatabaseManager();
        mBalance += amount;
        databaseManager.addIncome(mBalance);
    }

    public static void subtractBalance(Double amount) {
        mBalance -= amount;
    }

    public static void resetBudget(Context context) {
        ExpenseLab expenseLab = ExpenseLab.get(context);
        for (Expense expense : expenseLab.getExpenses()) {
            if (expense.getProposedAmount() > mBalance) {
                //notify user not all expenses could be updated
                Toast.makeText(context, "Balance low. Not all expenses could be updated.", Toast.LENGTH_LONG);
            } else {
                expense.setCurrentAmount(expense.getProposedAmount());
            }
        }
        GoalLab goalLab = GoalLab.get(context);
        for (Goal goal : goalLab.getGoals()) {
            if (goal.getMonthlyAmount() > mBalance) {
                //notify user not all expenses could be updated
                Toast.makeText(context, "Balance low. Not all goals could be updated.", Toast.LENGTH_LONG);
            } else {
                goal.saveAmount(goal.getMonthlyAmount());
            }
        }
    }
}
