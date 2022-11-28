package com.example.dietscoop.Activities;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.example.dietscoop.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ActivityViewTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void activityViewTest() throws InterruptedException {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.login_email_address),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                1),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("marcosmayedo@yahoo.ca"), closeSoftKeyboard());

        Thread.sleep(2000);

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.login_password),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                2),
                        isDisplayed()));
        appCompatEditText2.perform(replaceText("loggin2435"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.login_button), withText("Login"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                3),
                        isDisplayed()));
        materialButton.perform(click());

        Thread.sleep(2000);

        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.meals), withContentDescription("Meals"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottomNavBar),
                                        0),
                                1),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        Thread.sleep(2000);

        ViewInteraction bottomNavigationItemView2 = onView(
                allOf(withId(R.id.recipes), withContentDescription("Recipes"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottomNavBar),
                                        0),
                                2),
                        isDisplayed()));
        bottomNavigationItemView2.perform(click());

        Thread.sleep(2000);

        ViewInteraction bottomNavigationItemView3 = onView(
                allOf(withId(R.id.meals), withContentDescription("Meals"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottomNavBar),
                                        0),
                                1),
                        isDisplayed()));
        bottomNavigationItemView3.perform(click());

        Thread.sleep(2000);

        ViewInteraction textView = onView(
                allOf(withId(R.id.mealplan_for_date), withText("2022-11-30"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class))),
                        isDisplayed()));
        textView.check(matches(withText("2022-11-30")));

        ViewInteraction bottomNavigationItemView4 = onView(
                allOf(withId(R.id.recipes), withContentDescription("Recipes"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottomNavBar),
                                        0),
                                2),
                        isDisplayed()));
        bottomNavigationItemView4.perform(click());

        Thread.sleep(2000);

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.list_recipe_title), withText("ARROZ Y LECHE"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.TableLayout.class))),
                        isDisplayed()));
        textView2.check(matches(withText("ARROZ Y LECHE")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.list_prep_time), withText("10 mins"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.TableLayout.class))),
                        isDisplayed()));
        textView3.check(matches(withText("10 mins")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.list_recipe_category), withText("Dessert"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.TableLayout.class))),
                        isDisplayed()));
        textView4.check(matches(withText("Dessert")));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.list_num_servings), withText("4 servings"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.TableLayout.class))),
                        isDisplayed()));
        textView5.check(matches(withText("4 servings")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
