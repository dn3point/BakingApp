package com.iamzhaoyuan.android.baking.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.iamzhaoyuan.android.baking.R;
import com.iamzhaoyuan.android.baking.adapter.RecipeAdapter;
import com.iamzhaoyuan.android.baking.dto.Recipe;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.Callback {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onItemSelected(Recipe recipe) {
        Intent intent = new Intent(this, RecipeActivity.class);
        intent.putExtra(getString(R.string.intent_key_recipe), recipe);
        startActivity(intent);
    }
}
