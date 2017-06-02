package com.iamzhaoyuan.android.baking.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.iamzhaoyuan.android.baking.R;
import com.iamzhaoyuan.android.baking.adapter.RecipeStepDetailStepperAdapter;
import com.iamzhaoyuan.android.baking.dto.RecipeStep;
import com.stepstone.stepper.StepperLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepDetailsActivity extends AppCompatActivity {
    private static final String LOG_TAG = RecipeStepDetailsActivity.class.getSimpleName();

    @BindView(R.id.stepperLayout)
    StepperLayout mStepperLayout;

    private List<RecipeStep> mRecipeSteps;
    private int mStartPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ButterKnife.bind(this);
        if (getIntent() != null && getIntent().getExtras() != null) {
            mRecipeSteps = getIntent().getExtras().getParcelableArrayList(getString(R.string.intent_key_recipe_steps));
            mStartPosition = getIntent().getIntExtra(getString(R.string.intent_key_recipe_step_position), 0);
        } else {
            Log.d(LOG_TAG, "Recipe step list is null from intent?");
        }
        mStepperLayout.setAdapter(new RecipeStepDetailStepperAdapter(getSupportFragmentManager(), this, mRecipeSteps), mStartPosition);
    }
}
