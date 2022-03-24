package cs160.dataLayer;

import java.util.Calendar;

public class Goal extends Category {
    private Calendar endDateForThisGoal;
    private Frequency setMoneyAsideFrequency;

    public Goal(String name, Double proposedAmount, Double currentAmount, Calendar endDateForThisGoal) {
        super(name, proposedAmount, currentAmount);
        endDateForThisGoal = endDateForThisGoal;
        setMoneyAsideFrequency = Frequency.MONTHLY;
    }

    public Calendar getEndDateForThisGoal() {
        return endDateForThisGoal;
    }

    public void setEndDateForThisGoal(Calendar endDateForThisGoal) {
        this.endDateForThisGoal = endDateForThisGoal;
    }

    public Frequency getSetMoneyAsideFrequency() {
        return setMoneyAsideFrequency;
    }

    public void setSetMoneyAsideFrequency(Frequency setMoneyAsideFrequency) {
        this.setMoneyAsideFrequency = setMoneyAsideFrequency;
    }
}
