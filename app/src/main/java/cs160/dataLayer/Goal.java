package cs160.dataLayer;

import java.util.Calendar;

public class Goal extends Category {
    private Calendar endDateForThisGoal;
    private Frequency setMoneyAsideFrequency;

    public Goal(String name, Double savedGoal, Double currentSavedAmount, Calendar endDateForThisGoal) {
        super(name, savedGoal, currentSavedAmount);
        endDateForThisGoal = endDateForThisGoal;
        setMoneyAsideFrequency = Frequency.MONTHLY; // default value frequency is monthly but changeable for users
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
