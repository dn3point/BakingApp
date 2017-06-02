package com.iamzhaoyuan.android.baking.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.v4.app.FragmentManager;

import com.iamzhaoyuan.android.baking.R;
import com.iamzhaoyuan.android.baking.dto.RecipeStep;
import com.iamzhaoyuan.android.baking.ui.RecipeStepDetailsFragment;
import com.stepstone.stepper.Step;
import com.stepstone.stepper.adapter.AbstractFragmentStepAdapter;

import java.util.List;

public class RecipeStepDetailStepperAdapter extends AbstractFragmentStepAdapter {
    private List<RecipeStep> mRecipeSteps;

    public RecipeStepDetailStepperAdapter(FragmentManager fm, Context context, List<RecipeStep> recipeSteps) {
        super(fm, context);
        mRecipeSteps = recipeSteps;
    }

    @Override
    public Step createStep(@IntRange(from = 0L) int position) {
        final RecipeStepDetailsFragment step = new RecipeStepDetailsFragment();
        Bundle b = new Bundle();
        b.putInt(context.getString(R.string.key_recipe_step_position), position);
        b.putParcelable(context.getString(R.string.key_recipe_step_detail), mRecipeSteps.get(position));
        step.setArguments(b);
        return step;
    }

    @Override
    public int getCount() {
        return mRecipeSteps == null ? 0 : mRecipeSteps.size();
    }
}
