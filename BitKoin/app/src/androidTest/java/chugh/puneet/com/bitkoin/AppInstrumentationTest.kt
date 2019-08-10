package chugh.puneet.com.bitkoin

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import androidx.test.filters.LargeTest
import chugh.puneet.com.bitkoin.model.data.network.AppModule
import chugh.puneet.com.bitkoin.ui.MainActivity
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class AppInstrumentationTest {
    @get:Rule
    public val activityTestRule : ActivityTestRule<MainActivity> =
            ActivityTestRule(MainActivity::class.java, false, false)
    val fetchingIdlingResource : FetchingIdlingResource = FetchingIdlingResource()

    @Before
    fun setup() {
        activityTestRule.launchActivity(null)
        //fetchingIdlingResource = activityTestRule.activity.fetchingIdlingResource
        activityTestRule.activity.setIdlingResource(fetchingIdlingResource)
        IdlingRegistry.getInstance().register(fetchingIdlingResource)

    }

    @Test
    fun onActivityOpenInternetAvailable(){
        if(AppModule.hasNetwork(activityTestRule.activity.application)!!){
            onView(withId(R.id.id_bitkoin_recyclerview_list)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
            onView(withId(R.id.id_status_message)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
        }
    }

    @Test
    fun onActivityOpenInternetUnavailable() {
        //Thread.sleep(1000)
        if (!AppModule.hasNetwork(activityTestRule.activity.application)!!) {
            onView(withId(R.id.id_bitkoin_recyclerview_list)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
            onView(withId(R.id.id_status_message)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
            onView(withId(R.id.id_status_message)).check(matches(withText(R.string.no_data)))
            onView(withText(R.string.no_network)).check(matches(isDisplayed()))
        }
    }

    @Test
    fun testSnackBar(){
        activityTestRule.activity.showSnackbarMessage(activityTestRule.activity.resources.getString(R.string.just_testing))
        onView(allOf(withId(android.support.design.R.id.snackbar_text), withText(R.string.just_testing))).check(matches(withEffectiveVisibility(
                           ViewMatchers.Visibility.VISIBLE)))
    }

    @After
    fun unregisterIdlingResource() {
        if (fetchingIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(fetchingIdlingResource)
        }
    }
}