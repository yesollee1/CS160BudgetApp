package cs160.dataLayer;

import java.util.Date;

public class Goal extends Category {
    private Date mEndDate;

    public Goal(String title, Frequency frequency, Double proposedAmount, Double currentAmount, Date endDate) {
        super(title, frequency, proposedAmount, currentAmount);
        mEndDate = endDate;
    }
}
