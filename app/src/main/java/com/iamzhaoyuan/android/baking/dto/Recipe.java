package com.iamzhaoyuan.android.baking.dto;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class Recipe implements Parcelable {
    private int mId;
    private String mName;
    private List<Ingredient> mIngredients;
    private List<RecipeStep> mRecipeSteps;

    public Recipe(int id, String name, List<Ingredient> ingredients, List<RecipeStep> recipeSteps) {
        mId = id;
        mName = name;
        mIngredients = ingredients;
        mRecipeSteps = recipeSteps;
    }

    protected Recipe(Parcel in) {
        mId = in.readInt();
        mName = in.readString();
        mIngredients = new ArrayList<>();
        in.readTypedList(mIngredients, Ingredient.CREATOR);
        mRecipeSteps = new ArrayList<>();
        in.readTypedList(mRecipeSteps, RecipeStep.CREATOR);
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mId);
        parcel.writeString(mName);
        parcel.writeTypedList(mIngredients);
        parcel.writeTypedList(mRecipeSteps);
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public List<Ingredient> getIngredients() {
        return mIngredients;
    }

    public List<RecipeStep> getRecipeSteps() {
        return mRecipeSteps;
    }
}
