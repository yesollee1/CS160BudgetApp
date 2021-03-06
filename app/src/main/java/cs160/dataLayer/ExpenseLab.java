package cs160.dataLayer;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

// ExpenseLab is a singleton class
public class ExpenseLab {
    private static ExpenseLab sExpenseLab;
    private List<Expense> mExpenses;
    private Map<UUID, Expense> mExpenseMap; // For more efficient expense lookup
    private final DatabaseManager databaseManager = new DatabaseManager();

    public static ExpenseLab get(Context context) {
        if (sExpenseLab == null) {
            sExpenseLab = new ExpenseLab(context);
        }
        return sExpenseLab;
    }

    private ExpenseLab(Context context) {
        mExpenses = new ArrayList<>();
        mExpenseMap = new HashMap<>();
    }

    public void addExpense(Expense expense) {
//        if (expense.getProposedAmount() > Balance.getBalance()) {
//            return false;
//        } else {
            mExpenses.add(expense);
            mExpenseMap.put(expense.getId(), expense);
            databaseManager.addToExpenses(expense);
//            Balance.subtractBalance(expense.getProposedAmount());
//            return true;
//        }
    }

    public void deleteExpense(UUID id){
        Expense expense = mExpenseMap.get(id);
        Balance.addIncome(expense.getCurrentAmount());
        databaseManager.deleteExpense(Objects.requireNonNull(mExpenseMap.get(id)));
        mExpenses.remove(expense);
        mExpenseMap.remove(id);
    }

    public void populateExpense(Expense expense) {
        mExpenses.add(expense);
        mExpenseMap.put(expense.getId(), expense);
    }

    public List<Expense> getExpenses() {
        return mExpenses;
    }

    public Expense getExpense(UUID id) {
        return mExpenseMap.get(id);
    }

    //TODO: Implement autocategorize for transactions
    public void autocategorize(String merchant) {
        // search expenses merchant lists for merchant name each time a transaction is added
    }

//    public String[] getExpenseNames() {
//        String[] expenseNames = new String[mExpenses.size()];
//        for (int i = 0; i < mExpenses.size(); i++) {
//            expenseNames[i] = mExpenses.get(i).getTitle();
//        }
//        return expenseNames;
//    }

    public ArrayList<String> getExpenseNames() {
        ArrayList<String> expenseNames = new ArrayList<>();
        for (Expense expense : mExpenses) {
            expenseNames.add(expense.getTitle());
        }
//        for (int i = 0; i < mExpenses.size(); i++) {
//            expenseNames[i] = mExpenses.get(i).getTitle();
//        }
        return expenseNames;
    }

    public Expense getExpenseByName(String expenseName) {
        for (Expense expense : mExpenses) {
            if (expense.getTitle().equals(expenseName)) {
                return expense;
            }
        }
        return null;
    }

}
