package cs160.UILayer;

import static android.app.PendingIntent.getActivity;
import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import androidx.test.espresso.ViewAssertion;
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


public class RegisterLoginTest {

    @Rule
    public ActivityTestRule <RegisterActivity> RegisterRule =
            new ActivityTestRule<>(RegisterActivity.class, false, true);

    @Test
    public void A_RegisterTest() {
        onView(ViewMatchers.withId(R.id.etRegEmail)).perform(typeText("EspressoTest@gmail.com"))
                .check(matches(withText("EspressoTest@gmail.com")));

        closeSoftKeyboard();
        onView(ViewMatchers.withId(R.id.etRegPass)).perform(typeText("EspressoPass"))
                .check(matches(withText("EspressoPass")));

        closeSoftKeyboard();
        onView(ViewMatchers.withId(R.id.btnRegister)).perform(click());
    }

    @Test
    public void B_LoginTest() {
        onView(ViewMatchers.withId(R.id.tvLoginHere)).perform(click());
        onView(ViewMatchers.withId(R.id.etLoginEmail)).perform(typeText("EspressoTest@gmail.com"))
                .check(matches(withText("EspressoTest@gmail.com")));

        closeSoftKeyboard();
        onView(ViewMatchers.withId(R.id.etLoginPass)).perform(typeText("EspressoPass"))
                .check(matches(withText("EspressoPass")));


        closeSoftKeyboard();
        onView(ViewMatchers.withId(R.id.btnLogin)).perform(click());

    }

    @Test
    public void C_LogoutTest() {
        onView(ViewMatchers.withId(R.id.tvLoginHere)).perform(click());
        onView(ViewMatchers.withId(R.id.etLoginEmail)).perform(typeText("EspressoTest@gmail.com"))
                .check(matches(withText("EspressoTest@gmail.com")));

        closeSoftKeyboard();
        onView(ViewMatchers.withId(R.id.etLoginPass)).perform(typeText("EspressoPass"))
                .check(matches(withText("EspressoPass")));

        closeSoftKeyboard();
        onView(ViewMatchers.withId(R.id.btnLogin)).perform(click());

        // Espresso moves too fast, so we have to force a 1-second wait for the new view to be
        // visible before we try to click the button.
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(ViewMatchers.withId(R.id.logoutBtn)).perform(click());
        onView(ViewMatchers.withId(R.id.tvRegisterHere)).check(matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void D_GoalListTest() {
        onView(ViewMatchers.withId(R.id.tvLoginHere)).perform(click());
        onView(ViewMatchers.withId(R.id.etLoginEmail)).perform(typeText("EspressoTest@gmail.com"))
                .check(matches(withText("EspressoTest@gmail.com")));

        closeSoftKeyboard();
        onView(ViewMatchers.withId(R.id.etLoginPass)).perform(typeText("EspressoPass"))
                .check(matches(withText("EspressoPass")));

        closeSoftKeyboard();
        onView(ViewMatchers.withId(R.id.btnLogin)).perform(click());

        // Espresso moves too fast, so we have to force a 1-second wait for the new view to be
        // visible before we try to click the button.
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(ViewMatchers.withId(R.id.linkToGoal)).perform(click());

        onView(ViewMatchers.withId(R.id.add_new_button)).check(matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void E_TransactionListTest() {
        onView(ViewMatchers.withId(R.id.tvLoginHere)).perform(click());
        onView(ViewMatchers.withId(R.id.etLoginEmail)).perform(typeText("EspressoTest@gmail.com"))
                .check(matches(withText("EspressoTest@gmail.com")));

        closeSoftKeyboard();
        onView(ViewMatchers.withId(R.id.etLoginPass)).perform(typeText("EspressoPass"))
                .check(matches(withText("EspressoPass")));

        closeSoftKeyboard();
        onView(ViewMatchers.withId(R.id.btnLogin)).perform(click());

        // Espresso moves too fast, so we have to force a 1-second wait for the new view to be
        // visible before we try to click the button.
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(ViewMatchers.withId(R.id.linkToTransaction)).perform(click());

        onView(ViewMatchers.withId(R.id.add_new_button)).check(matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void F_ExpenseListTest() {
        onView(ViewMatchers.withId(R.id.tvLoginHere)).perform(click());
        onView(ViewMatchers.withId(R.id.etLoginEmail)).perform(typeText("EspressoTest@gmail.com"))
                .check(matches(withText("EspressoTest@gmail.com")));

        closeSoftKeyboard();
        onView(ViewMatchers.withId(R.id.etLoginPass)).perform(typeText("EspressoPass"))
                .check(matches(withText("EspressoPass")));

        closeSoftKeyboard();
        onView(ViewMatchers.withId(R.id.btnLogin)).perform(click());

        // Espresso moves too fast, so we have to force a 1-second wait for the new view to be
        // visible before we try to click the button.
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(ViewMatchers.withId(R.id.linkToExpense)).perform(click());

        onView(ViewMatchers.withId(R.id.add_new_button)).check(matches(ViewMatchers.isDisplayed()));
    }
}
