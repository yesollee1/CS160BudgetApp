package cs160.UILayer;

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

/*
Fix method order is needed to specify the order the tests are run in. Now We can prepend the
test methods with A_nameOfTest, B_nameOfTest, etc and they'll run in ascending order. Without it
the tests are ran in random order which leads to problems such as trying to edit an expense
that doesn't exist yet.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class UITest {

    String title;

    @Rule
    public ActivityTestRule <ExpenseListActivity> ExpenseRule =
            new ActivityTestRule<>(ExpenseListActivity.class, false, true);

    @Rule
    public ActivityTestRule<GoalListActivity> GoalRule =
            new ActivityTestRule<>(GoalListActivity.class, false, true);

    @Rule
    public ActivityTestRule<TransactionListActivity> TransactionRule =
            new ActivityTestRule<>(TransactionListActivity.class, false, true);

    @Before
    public void init() {
        title = "Expense Title";
    }

    @Test
    public void A_addExpenseTest() {
        onView(ViewMatchers.withId(R.id.add_new_button)).perform(click());
        onView(ViewMatchers.withId(R.id.expense_title)).perform(typeText("Expense1"))
        .check(matches(withText("Expense1")));

        onView(ViewMatchers.withId(R.id.expense_amount)).perform(typeText("100"))
                .check(matches(withText("100")));

        closeSoftKeyboard();
        onView(ViewMatchers.withId(R.id.confirm_button)).perform(click());

        onView(ViewMatchers.withId(R.id.item_recycler_view)).check(matches(hasChildCount(1)));

    }

    @Test
    public void B_addGoalTest() {
        // Open the menu and click on "Goals" to get to the correct screen
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText("Goals")).perform(click());

        onView(ViewMatchers.withId(R.id.add_new_button)).perform(click());

        onView(ViewMatchers.withId(R.id.goal_title)).perform(typeText("Goal1"))
                .check(matches(withText("Goal1")));

        onView(ViewMatchers.withId(R.id.goal_amount)).perform(typeText("100"))
                .check(matches(withText("100")));

        // If you don't close the keyboard, sometimes the test fails because it can't "see" the
        // submit button.
        closeSoftKeyboard();
        onView(ViewMatchers.withId(R.id.confirm_button)).perform(click());

        // TODO: Update this check to use the count of items in the recycler view instead of a hard-coded value.
        onView(ViewMatchers.withId(R.id.item_recycler_view)).check(matches(hasChildCount(1)));
    }

    @Test
    public void C_addTransactionTest() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText("Transactions")).perform(click());

        onView(ViewMatchers.withId(R.id.add_new_button)).perform(click());

        onView(ViewMatchers.withId(R.id.merchant_name)).perform(typeText("Merchant1"))
                .check(matches(withText("Merchant1")));

        onView(ViewMatchers.withId(R.id.transaction_amount)).perform(typeText("100"))
                .check(matches(withText("100")));

        onView(ViewMatchers.withId(R.id.transaction_notes)).perform(typeText("Notes"))
                .check(matches(withText("Notes")));

        closeSoftKeyboard();
        onView(ViewMatchers.withId(R.id.confirm_button)).perform(click());

        onView(ViewMatchers.withId(R.id.item_recycler_view)).check(matches(hasChildCount(1)));
    }

    @Test
    public void D_editExpenseTitleTest() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText("Expenses")).perform(click());

        onView(ViewMatchers.withId(R.id.expense_title)).perform(click());
        onView(ViewMatchers.withId(R.id.expense_title)).perform(replaceText("Expense2"))
                .check(matches(withText("Expense2")));
    }

    @Test
    public void E_editGoalTitleTest() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText("Goals")).perform(click());

        onView(ViewMatchers.withId(R.id.goal_title)).perform(click());
        onView(ViewMatchers.withId(R.id.goal_title)).perform(replaceText("Goal2"))
                .check(matches(withText("Goal2")));
    }

    @Test
    public void F_editTransactionTitleTest() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText("Transactions")).perform(click());

        onView(ViewMatchers.withId(R.id.merchant_name)).perform(click());
        onView(ViewMatchers.withId(R.id.merchant_name)).perform(replaceText("Merchant2"))
                .check(matches(withText("Merchant2")));
    }

    @Test
    public void G_editExpenseAmountTest() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText("Expenses")).perform(click());

        onView(ViewMatchers.withId(R.id.expense_title)).perform(click());
        onView(ViewMatchers.withId(R.id.expense_amount)).perform(replaceText("200"))
                .check(matches(withText("200")));
    }

    @Test
    public void H_editGoalAmountTest() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText("Goals")).perform(click());

        onView(ViewMatchers.withId(R.id.goal_amount)).perform(click());
        onView(ViewMatchers.withId(R.id.goal_amount)).perform(replaceText("200"))
                .check(matches(withText("200")));
    }

    @Test
    public void I_editTransactionAmountTest() {
        openActionBarOverflowOrOptionsMenu(InstrumentationRegistry.getInstrumentation().getTargetContext());
        onView(withText("Transactions")).perform(click());

        onView(ViewMatchers.withId(R.id.transaction_amount)).perform(click());
        onView(ViewMatchers.withId(R.id.transaction_amount)).perform(replaceText("200"))
                .check(matches(withText("200")));
    }

    @Test
    public void J_datePickerTest() {
        TestHelper.setDate(R.id.transaction_date, 2022, 1, 1);
        onView(ViewMatchers.withId(R.id.confirm_button)).perform(click());
        onView(ViewMatchers.withId(R.id.transaction_date)).check(matches(withText("Jan 1, 2022")));

    }
}

