package cs160.dataLayer;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Goal extends Category {

    private Date mEndDate;
    private ArrayList<String> listOfGoals;
    private Double mMonthlyAmount;

    public Goal() {
        super(null, Frequency.MONTHLY, 0.0, 0.0);
    }

    public Goal(String title, Frequency frequency, Double proposedAmount, Date date) {
        super(title, frequency, proposedAmount, 0.0);
        mDate = date;

        Calendar m_calendar=Calendar.getInstance();
        m_calendar.setTime(new Date());
        int nMonth1=12*m_calendar.get(Calendar.YEAR)+m_calendar.get(Calendar.MONTH);
        m_calendar.setTime(date);
        int nMonth2=12*m_calendar.get(Calendar.YEAR)+m_calendar.get(Calendar.MONTH);
        int monthsBetween = java.lang.Math.abs(nMonth2-nMonth1);
        mMonthlyAmount = proposedAmount / monthsBetween;

//        if (android.os.Build.VERSION.SDK_INT >= 26){
//            long monthsBetween = ChronoUnit.MONTHS.between(
//                    LocalDate.parse("2016-08-31").withDayOfMonth(1),
//                    LocalDate.parse("2016-11-30").withDayOfMonth(1));
//            monthlyAmount = proposedAmount / monthsBetween;
//        } else{
//            // do something for phones running an SDK before lollipop
//        }
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
        if (proposedAmount == null || proposedAmount < 0 || proposedAmount == Double.MAX_VALUE || proposedAmount == Double.MIN_VALUE){
            return;
        }
        mProposedAmount = proposedAmount;
    }

    public void saveAmount(Double amount) {
        mCurrentAmount += amount;
    }

    public Double getMonthlyAmount() {
        return mMonthlyAmount;
    }
}
