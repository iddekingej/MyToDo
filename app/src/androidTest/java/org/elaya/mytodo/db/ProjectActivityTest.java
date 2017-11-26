package org.elaya.mytodo.db;


import android.support.annotation.NonNull;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import org.elaya.mytodo.R;
import org.elaya.mytodo.project.ProjectActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.Matchers.allOf;


@LargeTest
@RunWith(AndroidJUnit4.class)
public class ProjectActivityTest {

    @NonNull
    @Rule
    public ActivityTestRule<ProjectActivity> mActivityTestRule = new ActivityTestRule<>(ProjectActivity.class);

    @Test
    public void projectActivityTest() {

        ViewInteraction actionMenuItemView2 = onView(
                allOf(ViewMatchers.withId(R.id.add_project), withContentDescription("Add project"), isDisplayed()));
        actionMenuItemView2.perform(click());

        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.projectName), isDisplayed()));
        appCompatEditText.perform(replaceText("fsdfsd"), closeSoftKeyboard());

        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.imageButton), withContentDescription("Save project and start adding todo's"),
                        withParent(withContentDescription("Save project and start adding todo's")),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction actionMenuItemView4 = onView(
                allOf(withId(R.id.add_todo), withContentDescription("Add todo"), isDisplayed()));
        actionMenuItemView4.perform(click());

        ViewInteraction appCompatEditText2 = onView(
                withId(R.id.title));
        appCompatEditText2.perform(scrollTo(), replaceText("aaaa"), closeSoftKeyboard());

        ViewInteraction appCompatEditText3 = onView(
                withId(R.id.comment));
        appCompatEditText3.perform(scrollTo(), replaceText("sadasdasdasd"), closeSoftKeyboard());

        ViewInteraction actionMenuItemView5 = onView(
                allOf(withId(R.id.save), withContentDescription("Save todo"), isDisplayed()));
        actionMenuItemView5.perform(click());



    }

}
