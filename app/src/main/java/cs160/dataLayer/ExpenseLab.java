package cs160.dataLayer;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

// ExpenseLab is a singleton class
public class ExpenseLab {
    private static ExpenseLab sExpenseLab;
    private List<Expense> mExpenses;
    private Map<UUID, Expense> mExpenseMap; // For more efficient expense lookup

    public static ExpenseLab get(Context context) {
        if (sExpenseLab == null) {
            sExpenseLab = new ExpenseLab(context);
        }
        return sExpenseLab;
    }

    private ExpenseLab(Context context) {
        mExpenses = new ArrayList<>();
        mExpenseMap = new HashMap<>();

//        for (int i = 0; i < 100; i++) {
//            Expense expense = new Expense("Expense #"+i, Frequency.MONTHLY, 0.0, 0.0);
////            expense.setTitle("Expense #" + i);
////            expense.setSolved(i % 2 == 0); // Every other expense will be set to Solved
//            mExpenses.add(expense);
//            mExpenseMap.put(expense.getId(), expense);
//        }
    }

    public void addExpense(Expense expense) {
        mExpenses.add(expense);
        mExpenseMap.put(expense.getId(), expense);
    }

    public List<Expense> getExpenses() {
        return mExpenses;
    }

    public Expense getExpense(UUID id) {
        // Implemented more efficient expense lookup
//        for (Expense expense : mExpenses) {
//            if (expense.getId().equals(id)) {
//                return expense;
//            }
//        }
//        return null;
        return mExpenseMap.get(id);
    }
}
