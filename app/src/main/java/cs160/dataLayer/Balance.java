package cs160.dataLayer;

public class Balance {
    private static Double mBalance = 0.0;

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
}
