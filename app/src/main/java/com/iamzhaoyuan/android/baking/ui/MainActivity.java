package com.iamzhaoyuan.android.baking.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.iamzhaoyuan.android.baking.R;

public class MainActivity extends AppCompatActivity implements RecipeListFragment.OnRecipeClickListener {

    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onRecipeSelected(int position) {

    }
}
