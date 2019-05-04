package chugh.puneet.com.repos;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import chugh.puneet.com.repos.views.SearchActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;

@RunWith(AndroidJUnit4.class)
public class ReposIntegrationTesting {

    private String orgToBeSearched = "microsoft";

    @Rule
    public ActivityTestRule<SearchActivity> searchActivity
            = new ActivityTestRule<>(SearchActivity.class);

    @Test
    public void checkInitialText(){
        onView(withId(R.id.editText)).check(matches(withText("")));
    }

    @Test
    public void checkSearchForEmptyString(){
        onView(withId(R.id.button)).perform(click());
        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText(R.string.enter_org_name)))
                .check(matches(isDisplayed()));
    }

    @Test
    public void checkWithValidOrgName(){
        onView(withId(R.id.editText)).perform(typeText(orgToBeSearched), closeSoftKeyboard());
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.layout.activity_main)).check(doesNotExist());
    }

}
