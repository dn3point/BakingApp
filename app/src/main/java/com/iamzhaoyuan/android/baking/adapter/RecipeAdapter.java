package com.iamzhaoyuan.android.baking.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iamzhaoyuan.android.baking.R;
import com.iamzhaoyuan.android.baking.dto.Recipe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int VIEW_TYPE_RECIPE = 0;
    private static final String LOG_TAG = RecipeAdapter.class.getSimpleName();
    private Context mContext;
    private List<Recipe> mRecipes;

    public RecipeAdapter(Context context, List<Recipe> recipes) {
        mContext = context;
        mRecipes = recipes;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder instanceof RecipeViewHolder) {
            RecipeViewHolder recipeViewHolder = (RecipeViewHolder) holder;
            Recipe recipe = mRecipes.get(position);
            recipeViewHolder.recipeName.setText(recipe.getName());
            recipeViewHolder.numOfIngredients.setText(recipe.getIngredients().size() + "");
            recipeViewHolder.numOfSteps.setText(recipe.getRecipeSteps().size() + "");
        } else {
            Log.d(LOG_TAG, "ViewHolder type issue: " + holder.getClass().getSimpleName());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (VIEW_TYPE_RECIPE == viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe_card, parent, false);
            return new RecipeViewHolder(view);
        } else {
            Log.d(LOG_TAG, "View type is : " + viewType + "?");
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return mRecipes == null ? 0 : mRecipes.size();
    }

    public void cleanRecipes() {
        int size = mRecipes.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                mRecipes.remove(0);
            }
            notifyItemRangeRemoved(0, size);
        }
    }

    public void addRecipes(List<Recipe> recipes) {
        int startPosition = mRecipes == null ? 0 : mRecipes.size();
        mRecipes.addAll(recipes);
        notifyItemRangeInserted(startPosition, recipes.size() - 1);
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recipe_name)
        TextView recipeName;
        @BindView(R.id.recipe_num_of_ingredients)
        TextView numOfIngredients;
        @BindView(R.id.recipe_num_of_steps)
        TextView numOfSteps;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
