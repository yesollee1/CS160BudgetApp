package cs160.dataLayer;

public abstract class Category {
    private String mName;
    private Double mProposedAmount;
    private Double mCurrentAmount;

    public Category(String name, Double proposedAmount, Double currentAmount) {
        mName = name;
        mProposedAmount = proposedAmount;
        mCurrentAmount = currentAmount;
    }

    public Double getPercent() {
        return mCurrentAmount / mProposedAmount;
    }

    public void edit() { }

    public void delete() { }
}
