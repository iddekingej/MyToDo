package org.elaya.mytodo;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ProjectActivityTest {

    @Rule
    public ActivityTestRule<ProjectActivity> mActivityTestRule = new ActivityTestRule<>(ProjectActivity.class);

    @Test
    public void projectActivityTest() {

        ViewInteraction actionMenuItemView2 = onView(
                allOf(withId(R.id.add_project), withContentDescription("Add project"), isDisplayed()));
        actionMenuItemView2.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.projectName), isDisplayed()));
        appCompatEditText.perform(replaceText("fsdfsd"), closeSoftKeyboard());

        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.imageButton), withContentDescription("Save project and start adding todo's"),
                        withParent(withContentDescription("Save project and start adding todo's")),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction actionMenuItemView3 = onView(
                allOf(withId(R.id.back), withContentDescription("Back"), isDisplayed()));
        actionMenuItemView3.perform(click());

        ViewInteraction actionMenuItemView4 = onView(
                allOf(withId(R.id.add_todo), withContentDescription("Add project"), isDisplayed()));
        actionMenuItemView4.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                withId(R.id.title));
        appCompatEditText2.perform(scrollTo(), replaceText("aaaa"), closeSoftKeyboard());

        ViewInteraction appCompatSpinner = onView(
                withId(R.id.status));
        appCompatSpinner.perform(scrollTo(), click());

        ViewInteraction linearLayout = onView(
                allOf(withClassName(is("android.widget.LinearLayout")), isDisplayed()));
        linearLayout.perform(click());

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withId(R.id.imageButton3), withContentDescription("Start date:")));
        appCompatImageButton2.perform(scrollTo(), click());

        ViewInteraction appCompatButton = onView(
                allOf(withId(android.R.id.button1), withText("OK")));
        appCompatButton.perform(scrollTo(), click());

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withId(R.id.imageButton2), withContentDescription("End date")));
        appCompatImageButton3.perform(scrollTo(), click());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(android.R.id.button1), withText("OK")));
        appCompatButton2.perform(scrollTo(), click());

        ViewInteraction appCompatEditText3 = onView(
                withId(R.id.comment));
        appCompatEditText3.perform(scrollTo(), replaceText("sadasdasdasd"), closeSoftKeyboard());

        ViewInteraction actionMenuItemView5 = onView(
                allOf(withId(R.id.save), withContentDescription("Save todo"), isDisplayed()));
        actionMenuItemView5.perform(click());

        ViewInteraction linearLayout2 = onView(
                allOf(childAtPosition(
                        withId(R.id.todoList),
                        0),
                        isDisplayed()));
        linearLayout2.perform(click());

        ViewInteraction linearLayout3 = onView(
                allOf(childAtPosition(
                        withId(R.id.todoList),
                        0),
                        isDisplayed()));
        linearLayout3.perform(click());

        ViewInteraction actionMenuItemView6 = onView(
                allOf(withId(R.id.edit), withContentDescription("Edit todo"), isDisplayed()));
        actionMenuItemView6.perform(click());

        ViewInteraction appCompatSpinner2 = onView(
                withId(R.id.status));
        appCompatSpinner2.perform(scrollTo(), click());

        ViewInteraction linearLayout4 = onView(
                allOf(withClassName(is("android.widget.LinearLayout")), isDisplayed()));
        linearLayout4.perform(click());

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.title), withText("aaaa")));
        appCompatEditText4.perform(scrollTo(), click());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.title), withText("aaaa")));
        appCompatEditText5.perform(scrollTo(), replaceText("aaaad"), closeSoftKeyboard());

        ViewInteraction actionMenuItemView7 = onView(
                allOf(withId(R.id.save), withContentDescription("Save todo"), isDisplayed()));
        actionMenuItemView7.perform(click());

        ViewInteraction actionMenuItemView8 = onView(
                allOf(withId(R.id.edit), withContentDescription("Edit todo"), isDisplayed()));
        actionMenuItemView8.perform(click());

        ViewInteraction actionMenuItemView9 = onView(
                allOf(withId(R.id.delete), withContentDescription("Delete todo item"), isDisplayed()));
        actionMenuItemView9.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(android.R.id.button1), withText("Delete")));
        appCompatButton3.perform(scrollTo(), click());

        ViewInteraction actionMenuItemView10 = onView(
                allOf(withId(R.id.back), withContentDescription("Back"), isDisplayed()));
        actionMenuItemView10.perform(click());

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
