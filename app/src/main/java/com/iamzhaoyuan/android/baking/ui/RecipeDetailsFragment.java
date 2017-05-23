package com.iamzhaoyuan.android.baking.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iamzhaoyuan.android.baking.R;
import com.iamzhaoyuan.android.baking.adapter.IngredientAdapter;
import com.iamzhaoyuan.android.baking.adapter.RecipeStepAdapter;
import com.iamzhaoyuan.android.baking.dto.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeDetailsFragment extends Fragment {

    private static final String LOG_TAG = RecipeDetailsFragment.class.getSimpleName();

    private Recipe mRecipe;
    private IngredientAdapter mIngredientAdapter;
    private RecipeStepAdapter mRecipeStepAdapter;

    @BindView(R.id.ingredients_recycler_view)
    RecyclerView mIngredientsRecyclerView;
    @BindView(R.id.steps_recycler_view)
    RecyclerView mStepsRecyclerView;

    public RecipeDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_recipe_details, container, false);
        if (getActivity().getIntent() != null && getActivity().getIntent().getExtras() != null) {
            mRecipe = getActivity().getIntent().getExtras().getParcelable(getString(R.string.intent_key_recipe));
        } else if (getArguments() != null) {
            mRecipe = getArguments().getParcelable(getString(R.string.intent_key_recipe));
        } else {
            Log.d(LOG_TAG, "Intent from MainActivity is null?");
        }

        if (mRecipe != null) {
            ButterKnife.bind(this, rootView);
            mIngredientAdapter = new IngredientAdapter(getActivity(), mRecipe.getIngredients());
            RecyclerView.LayoutManager ingredientManager = new GridLayoutManager(getActivity(), 1);
            mIngredientsRecyclerView.setLayoutManager(ingredientManager);
            mIngredientsRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mIngredientsRecyclerView.setAdapter(mIngredientAdapter);

            mRecipeStepAdapter = new RecipeStepAdapter(getActivity(), mRecipe.getRecipeSteps());
            RecyclerView.LayoutManager stepManager = new GridLayoutManager(getActivity(), 1);
            mStepsRecyclerView.setLayoutManager(stepManager);
            mStepsRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mStepsRecyclerView.setAdapter(mRecipeStepAdapter);
        } else {
            Log.d(LOG_TAG, "Recipe obj passed from MainActivity is null?");
        }
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getArguments() == null) {
            Bundle bundle = new Bundle();
            bundle.putParcelable(getString(R.string.intent_key_recipe), mRecipe);
            setArguments(bundle);
        }
    }
}
