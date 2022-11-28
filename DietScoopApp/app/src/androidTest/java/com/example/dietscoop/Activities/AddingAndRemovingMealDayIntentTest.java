package com.example.dietscoop.Activities;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
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
public class AddingAndRemovingMealDayIntentTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void addingAndRemovingMealDayIntentTest() {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.login_email_address),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                1),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("marcosmayedo@yahoo.ca"), closeSoftKeyboard());

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

        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.meals), withContentDescription("Meals"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottomNavBar),
                                        0),
                                1),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recycler_for_meal_plans),
                        childAtPosition(
                                withId(R.id.linearLayout),
                                0)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.meal_day_cancel), withText("Cancel"),
                        childAtPosition(
                                allOf(withId(R.id.buttonContainer),
                                        childAtPosition(
                                                withId(R.id.meal_day_fragment_v2),
                                                2)),
                                2),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.add_button),
                        childAtPosition(
                                childAtPosition(
                                        withId(com.firebase.ui.auth.R.id.action_bar),
                                        1),
                                2),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.meal_day_date_enter), withText("Select Date"),
                        childAtPosition(
                                allOf(withId(R.id.meal_day_fragment_v2),
                                        childAtPosition(
                                                withId(R.id.full_fragment_container_view),
                                                0)),
                                4),
                        isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction materialButton4 = onView(
                allOf(withId(android.R.id.button1), withText("OK"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton4.perform(scrollTo(), click());

        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.add_food_item_button), withText("ADD"),
                        childAtPosition(
                                allOf(withId(R.id.buttonContainer),
                                        childAtPosition(
                                                withId(R.id.meal_day_fragment_v2),
                                                2)),
                                0),
                        isDisplayed()));
        materialButton5.perform(click());

        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.recycler_for_food_items),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                0),
                        isDisplayed()));
        appCompatSpinner.perform(click());

        DataInteraction appCompatCheckedTextView = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(1);
        appCompatCheckedTextView.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.insert_quantity_food_item),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("5"), closeSoftKeyboard());

        ViewInteraction appCompatSpinner2 = onView(
                allOf(withId(R.id.select_measurement_unit),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                4),
                        isDisplayed()));
        appCompatSpinner2.perform(click());

        DataInteraction appCompatCheckedTextView2 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(4);
        appCompatCheckedTextView2.perform(click());

        ViewInteraction materialButton6 = onView(
                allOf(withId(android.R.id.button1), withText("Confirm"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton6.perform(scrollTo(), click());

        ViewInteraction materialButton7 = onView(
                allOf(withId(R.id.add_food_item_button), withText("ADD"),
                        childAtPosition(
                                allOf(withId(R.id.buttonContainer),
                                        childAtPosition(
                                                withId(R.id.meal_day_fragment_v2),
                                                2)),
                                0),
                        isDisplayed()));
        materialButton7.perform(click());

        ViewInteraction appCompatSpinner3 = onView(
                allOf(withId(R.id.recycler_for_food_items),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                0),
                        isDisplayed()));
        appCompatSpinner3.perform(click());

        DataInteraction appCompatCheckedTextView3 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(0);
        appCompatCheckedTextView3.perform(click());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.insert_quantity_food_item),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("10"), closeSoftKeyboard());

        ViewInteraction materialButton8 = onView(
                allOf(withId(android.R.id.button1), withText("Confirm"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton8.perform(scrollTo(), click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.meal_in_mealday_description), withText("arroz y leche"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.TableLayout.class))),
                        isDisplayed()));
        textView.check(matches(withText("arroz y leche")));

        ViewInteraction materialButton9 = onView(
                allOf(withId(R.id.add_food_item_button), withText("ADD"),
                        childAtPosition(
                                allOf(withId(R.id.buttonContainer),
                                        childAtPosition(
                                                withId(R.id.meal_day_fragment_v2),
                                                2)),
                                0),
                        isDisplayed()));
        materialButton9.perform(click());

        ViewInteraction materialButton10 = onView(
                allOf(withId(android.R.id.button2), withText("Cancel"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                2)));
        materialButton10.perform(scrollTo(), click());

        ViewInteraction materialButton11 = onView(
                allOf(withId(R.id.meal_day_confirm), withText("Confirm"),
                        childAtPosition(
                                allOf(withId(R.id.buttonContainer),
                                        childAtPosition(
                                                withId(R.id.meal_day_fragment_v2),
                                                2)),
                                1),
                        isDisplayed()));
        materialButton11.perform(click());

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.mealplan_for_date), withText("2022-11-30"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class))),
                        isDisplayed()));
        textView2.check(matches(withText("2022-11-30")));

        ViewInteraction recyclerView2 = onView(
                allOf(withId(R.id.recycler_for_meal_plans),
                        childAtPosition(
                                withId(R.id.linearLayout),
                                0)));
        recyclerView2.perform(actionOnItemAtPosition(1, click()));

        ViewInteraction materialButton12 = onView(
                allOf(withId(R.id.meal_day_cancel), withText("Cancel"),
                        childAtPosition(
                                allOf(withId(R.id.buttonContainer),
                                        childAtPosition(
                                                withId(R.id.meal_day_fragment_v2),
                                                2)),
                                2),
                        isDisplayed()));
        materialButton12.perform(click());

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.mealplan_for_date), withText("2022-11-30"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.view.ViewGroup.class))),
                        isDisplayed()));
        textView3.check(matches(withText("2022-11-30")));

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withId(R.id.logout_button),
                        childAtPosition(
                                childAtPosition(
                                        withId(com.firebase.ui.auth.R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        appCompatImageButton2.perform(click());
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
