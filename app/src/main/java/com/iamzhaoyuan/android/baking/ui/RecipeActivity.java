package com.iamzhaoyuan.android.baking.ui;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.iamzhaoyuan.android.baking.R;
import com.iamzhaoyuan.android.baking.dto.Recipe;

public class RecipeActivity extends AppCompatActivity {

    private static final String LOG_TAG = RecipeActivity.class.getSimpleName();

    private Recipe mRecipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getIntent() != null && getIntent().getExtras() != null) {
            mRecipe = getIntent().getExtras().getParcelable(getString(R.string.intent_key_recipe));
        }
        if (mRecipe != null) {
            getSupportActionBar().setTitle(mRecipe.getName());
        }
    }
}
