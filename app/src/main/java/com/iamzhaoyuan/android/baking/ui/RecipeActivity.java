package com.iamzhaoyuan.android.baking.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.iamzhaoyuan.android.baking.R;
import com.iamzhaoyuan.android.baking.adapter.RecipeStepAdapter;
import com.iamzhaoyuan.android.baking.adapter.RecipeStepDetailStepperAdapter;
import com.iamzhaoyuan.android.baking.dto.Recipe;
import com.iamzhaoyuan.android.baking.dto.RecipeStep;

import java.util.ArrayList;
import java.util.List;

public class RecipeActivity extends AppCompatActivity implements RecipeStepAdapter.Callback {

    private static final String LOG_TAG = RecipeActivity.class.getSimpleName();

    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            if (getIntent() != null && getIntent().getExtras() != null) {
                mRecipe = getIntent().getExtras().getParcelable(getString(R.string.intent_key_recipe));
            }
            if (mRecipe != null) {
                getSupportActionBar().setTitle(mRecipe.getName());
            }
        }
    }

    @Override
    public void onItemSelected(List<RecipeStep> recipeSteps, int position) {
        Intent intent = new Intent(this, RecipeStepDetailsActivity.class);
        intent.putParcelableArrayListExtra(getString(R.string.intent_key_recipe_steps), (ArrayList<RecipeStep>) recipeSteps);
        intent.putExtra(getString(R.string.intent_key_recipe_step_position), position);
        startActivity(intent);
    }
}
