package com.AD340.Roommater;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;

import androidx.test.espresso.contrib.PickerActions;
import android.widget.DatePicker;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;


import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SignInTest {
    @Rule
    public ActivityScenarioRule<SignupActivity> welcomeScreenActivity =
            new ActivityScenarioRule<>(SignupActivity.class);

    @Test
    public void canGoThroughForm() {
        onView(withId(R.id.names)).perform(replaceText("Kyle Bastien"));
        onView(withId(R.id.email)).perform(replaceText("foo20@bar.com"));
        onView(withId(R.id.age)).perform(replaceText("25"));
        onView(withId(R.id.zip)).perform(replaceText("98004"));

        onView(withId(R.id.date_picker_button)).perform(click());

        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2000, 2 + 1, 5));

        onView(withId(android.R.id.button1)).perform(click());

        onView(withId(R.id.submitButton)).perform(click());

        onView(withId(R.id.password))
                .check(matches(withText("")));
    }
}
