package cs160.dataLayer;

import java.util.Date;

public class Goal extends Category {
    private Date mEndDate;

    public Goal() {
        super(null, Frequency.MONTHLY, 0.0, 0.0);
    }

    public Goal(String title, Frequency frequency, Double proposedAmount, Date endDate) {
        super(title, frequency, proposedAmount, 0.0);
        mEndDate = endDate;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        mDate = date;
    }

    public void setProposedAmount(Double proposedAmount) {
        if(proposedAmount < 0){
            return;
        }
        mProposedAmount = proposedAmount;
    }
}
