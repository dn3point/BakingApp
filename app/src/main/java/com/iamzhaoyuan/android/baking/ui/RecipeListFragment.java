package com.iamzhaoyuan.android.baking.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.iamzhaoyuan.android.baking.R;
import com.iamzhaoyuan.android.baking.adapter.RecipeAdapter;
import com.iamzhaoyuan.android.baking.dto.Recipe;
import com.iamzhaoyuan.android.baking.task.FetchRecipesTask;
import com.iamzhaoyuan.android.baking.task.FetchRecipesTask.AsyncResponse;
import com.iamzhaoyuan.android.baking.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListFragment extends Fragment implements AsyncResponse {
    private static final String LOG_TAG = RecipeListFragment.class.getSimpleName();

    @BindView(R.id.recipes_recycler_view)
    RecyclerView mRecyclerView;

    private RecipeAdapter mRecipeAdapter;

    private BroadcastReceiver mNetworkReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            NetworkUtils networkUtils = NetworkUtils.getInstance();
            if (networkUtils.isNetworkConnected(context)) {
                getData();
            }
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        getActivity().registerReceiver(mNetworkReceiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(mNetworkReceiver);
    }

    public RecipeListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        if (NetworkUtils.getInstance().isNetworkConnected(getActivity())) {
            getData();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        ButterKnife.bind(this, rootView);

        mRecipeAdapter = new RecipeAdapter(getActivity(), new ArrayList<Recipe>());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), getResources().getInteger(R.integer.col_num_of_grid));
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mRecipeAdapter);

        return rootView;
    }

    @Override
    public void processFinish(List<Recipe> recipes) {
        if (!recipes.isEmpty()) {
            mRecipeAdapter.clearRecipes();
            mRecipeAdapter.addRecipes(recipes);
        }
    }

    private void getData() {
        new FetchRecipesTask(this).execute();
    }
}
