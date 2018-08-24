package com.example.dell.bakingapp;

import android.app.Fragment;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.espresso.contrib.RecyclerViewActions;

import com.example.dell.bakingapp.fragments.MainActivityFragment;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class RecipeDetailActivityTest  {


    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void clickRecyclerViewItem_Step0_OpensStepDetailActivity() {
        onView(withId(R.id.recipe_step_list)).perform(RecyclerViewActions.actionOnItemAtPosition(0, scrollTo()));
        onView(withId(R.id.step_card_item)).perform(click());
    }

}