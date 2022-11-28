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
public class AddingAndDeletingRecipeIntentTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityScenarioRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void addingAndDeletingRecipeIntentTest() {
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

        ViewInteraction bottomNavigationItemView2 = onView(
                allOf(withId(R.id.recipes), withContentDescription("Recipes"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottomNavBar),
                                        0),
                                2),
                        isDisplayed()));
        bottomNavigationItemView2.perform(click());

        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.add_button),
                        childAtPosition(
                                childAtPosition(
                                        withId(com.firebase.ui.auth.R.id.action_bar),
                                        0),
                                2),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.recipe_title),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        0),
                                0)));
        appCompatEditText3.perform(scrollTo(), replaceText("boiled milk"), closeSoftKeyboard());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.recipe_prep_time),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.TableRow")),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText4.perform(replaceText("7"), closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.recipe_no_of_servings),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.TableLayout")),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("2"), closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.recipe_add_ingredient_button), withText("Add"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        4),
                                1)));
        materialButton2.perform(scrollTo(), click());

        ViewInteraction appCompatEditText6 = onView(
                allOf(withId(R.id.editTextDescription),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                3),
                        isDisplayed()));
        appCompatEditText6.perform(replaceText("milk"), closeSoftKeyboard());

        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.categorySpinner),
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
                .atPosition(2);
        appCompatCheckedTextView.perform(click());

        ViewInteraction appCompatEditText7 = onView(
                allOf(withId(R.id.editTextAmount),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                2),
                        isDisplayed()));
        appCompatEditText7.perform(replaceText("250"), closeSoftKeyboard());

        ViewInteraction appCompatSpinner2 = onView(
                allOf(withId(R.id.unitSpinner),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                1),
                        isDisplayed()));
        appCompatSpinner2.perform(click());

        DataInteraction appCompatCheckedTextView2 = onData(anything())
                .inAdapterView(childAtPosition(
                        withClassName(is("android.widget.PopupWindow$PopupBackgroundView")),
                        0))
                .atPosition(0);
        appCompatCheckedTextView2.perform(click());

        ViewInteraction materialButton3 = onView(
                allOf(withId(android.R.id.button1), withText("Confirm"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton3.perform(scrollTo(), click());

        ViewInteraction appCompatEditText8 = onView(
                allOf(withId(R.id.recipe_instructions),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                9)));
        appCompatEditText8.perform(scrollTo(), replaceText("1. Pour milk in pot. 2. Bring to a high degree for several minutes. 3. Serve oncee bubbles form."), closeSoftKeyboard());

        ViewInteraction appCompatEditText9 = onView(
                allOf(withId(R.id.recipe_instructions), withText("1. Pour milk in pot. 2. Bring to a high degree for several minutes. 3. Serve oncee bubbles form."),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                9)));
        appCompatEditText9.perform(scrollTo(), click());

        DataInteraction materialTextView = onData(anything())
                .inAdapterView(allOf(withClassName(is("android.widget.ListView")),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                0)))
                .atPosition(0);
        materialTextView.perform(click());

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.recipe_back_button), withText("Confirm"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                2),
                        isDisplayed()));
        materialButton4.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.list_recipe_title), withText("BOILED MILK"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.TableLayout.class))),
                        isDisplayed()));
        textView.check(matches(withText("BOILED MILK")));

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recipe_list),
                        childAtPosition(
                                withClassName(is("android.widget.LinearLayout")),
                                1)));
        recyclerView.perform(actionOnItemAtPosition(0, click()));

        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.recipe_delete_button), withText("Delete"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.LinearLayout")),
                                        1),
                                1),
                        isDisplayed()));
        materialButton5.perform(click());

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.list_recipe_title), withText("ARROZ Y LECHE"),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.TableLayout.class))),
                        isDisplayed()));
        textView2.check(matches(withText("ARROZ Y LECHE")));

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withId(R.id.logout_button),
                        childAtPosition(
                                childAtPosition(
                                        withId(com.firebase.ui.auth.R.id.action_bar),
                                        0),
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
