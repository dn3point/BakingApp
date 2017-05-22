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
import com.iamzhaoyuan.android.baking.dto.RecipeStep;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String LOG_TAG = RecipeStepAdapter.class.getSimpleName();
    public static final int VIEW_TYPE_STEP = 0;

    private Context mContext;
    private List<RecipeStep> mRecipeSteps;

    public RecipeStepAdapter(Context context, List<RecipeStep> recipeSteps) {
        mContext = context;
        mRecipeSteps = recipeSteps;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (VIEW_TYPE_STEP == viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe_step, parent, false);
            return new RecipeStepViewHolder(view);
        } else {
            Log.d(LOG_TAG, "View type is : " + viewType + "?");
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder instanceof RecipeStepViewHolder) {
            RecipeStepViewHolder viewHolder = (RecipeStepViewHolder) holder;
            RecipeStep recipeStep = mRecipeSteps.get(position);
            StringBuilder sb = new StringBuilder(recipeStep.getId() + 1 + "").append(" ").append(recipeStep.getShortDescription());
            Log.d(LOG_TAG, sb.toString());
            viewHolder.recipeStep.setText(sb.toString());
        } else {
            Log.d(LOG_TAG, "ViewHolder type issue: " + holder.getClass().getSimpleName());
        }
    }

    @Override
    public int getItemCount() {
        return mRecipeSteps.size();
    }

    public void addRecipeSteps(List<RecipeStep> recipeSteps) {
        int startPosition = mRecipeSteps == null ? 0 : mRecipeSteps.size();
        recipeSteps.addAll(recipeSteps);
        notifyItemRangeInserted(startPosition, recipeSteps.size() - 1);
    }

    public void clearRecipeSteps() {
        int size = mRecipeSteps.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                mRecipeSteps.remove(0);
            }
            notifyItemRangeRemoved(0, size);
        }
    }

    class RecipeStepViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recipe_step)
        TextView recipeStep;

        public RecipeStepViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
