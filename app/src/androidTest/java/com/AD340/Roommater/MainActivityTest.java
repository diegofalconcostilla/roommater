

package com.AD340.Roommater;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.doesNotExist;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.widget.DatePicker;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> mainActivityRule = new ActivityScenarioRule<>(MainActivity.class);


/*
    @Test
    public void checkingSubmitButton(){
        onView(withId(R.id.login_email)).perform(replaceText("zaya@yahoo.com"));
        onView(withId(R.id.login_password)).perform(replaceText("123456"));

        onView(withId(R.id.date_picker_button)).perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName()))).perform(PickerActions.setDate(2000,1,2));
        onView(withId(android.R.id.button1)).perform(click());
        onView(withId(R.id.submitButton)).perform(click());
    }

    @Test
    public void checkingMustHaveValidEmail() {
        onView(withId(R.id.login_email)).perform(replaceText("zaya@yahoo.com"));
        onView(withId(R.id.login_password)).perform(replaceText("123456"));
        onView(withId(R.id.submitButton)).perform(click());
        onView(withText("zaya@yahoo.com")).check(doesNotExist());
    }
    */
}
