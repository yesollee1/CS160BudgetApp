package cs160.dataLayer;

import java.util.Date;

public class Goal extends Category {
    private Date mEndDate;

    public Goal() {
        super(null, Frequency.MONTHLY, 0.0, 0.0);
    }

    public Goal(String title, Frequency frequency, Double proposedAmount, Double currentAmount, Date endDate) {
        super(title, frequency, proposedAmount, currentAmount);
        mEndDate = endDate;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        if(date != null){
            mDate = date;
        }
    }

    public void setProposedAmount(Double proposedAmount) {
        if( proposedAmount == null || proposedAmount < 0 || proposedAmount == Double.MAX_VALUE || proposedAmount == Double.MIN_VALUE){
            return;
        }
        mProposedAmount = proposedAmount;
    }
}
