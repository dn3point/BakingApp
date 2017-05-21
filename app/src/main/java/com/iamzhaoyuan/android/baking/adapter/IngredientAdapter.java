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
import com.iamzhaoyuan.android.baking.dto.Ingredient;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String LOG_TAG = IngredientAdapter.class.getSimpleName();
    public static final int VIEW_TYPE_INGREDIENT = 0;

    private Context mContext;
    private List<Ingredient> mIngredients;

    public IngredientAdapter(Context context, List<Ingredient> ingredients) {
        mContext = context;
        mIngredients = ingredients;
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.recipe_ingredient)
        TextView ingredient;

        public IngredientViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (VIEW_TYPE_INGREDIENT == viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe_ingredient, parent, false);
            return new IngredientViewHolder(view);
        } else {
            Log.d(LOG_TAG, "View type is : " + viewType + "?");
        }
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder instanceof IngredientViewHolder) {
            IngredientViewHolder ingredientHolder = (IngredientViewHolder) holder;
            Ingredient ingredient = mIngredients.get(position);
            StringBuilder sb = new StringBuilder(ingredient.getQuantity()).append(" ").append(ingredient.getMeasure().toLowerCase());
            if (ingredient.getQuantity() > 1) {
                sb.append("s");
            }
            sb.append(" ").append(ingredient.getIngredient());
            ingredientHolder.ingredient.setText(sb.toString());
        } else {
            Log.d(LOG_TAG, "ViewHolder type issue: " + holder.getClass().getSimpleName());
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
