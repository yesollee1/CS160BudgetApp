package cs160.dataLayer;

import java.util.ArrayList;
import java.util.UUID;

public class Expense extends Category {

    private ArrayList<String> mMerchants;

    public Expense() {
        super(null, Frequency.MONTHLY, 0.0, 0.0);
    }

    public Expense(String title, Frequency frequency, Double proposedAmount, Double currentAmount) {
        super(title, frequency, proposedAmount, currentAmount);
    }

    public ArrayList<String> getMerchants() {
        return mMerchants;
    }

    // this may be the autocategorize feature
    public void addMerchant(String merchant) {
        mMerchants.add(merchant);
    }

    public boolean containsMerchant(String merchant) {
        return mMerchants.contains(merchant);
    }
}
