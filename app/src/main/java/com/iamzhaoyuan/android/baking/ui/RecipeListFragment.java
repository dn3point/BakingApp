package com.iamzhaoyuan.android.baking.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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
import com.iamzhaoyuan.android.baking.util.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListFragment extends Fragment implements AsyncResponse {
    private static final String LOG_TAG = RecipeListFragment.class.getSimpleName();

    @BindView(R.id.recipes_recycler_view) RecyclerView mRecyclerView;

    private RecipeAdapter mRecipeAdapter;

    private BroadcastReceiver mNetworkReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            NetworkUtil networkUtil = NetworkUtil.getInstance();
            if (networkUtil.isNetworkConnected(context)) {
                getData();
            }
        }
    };

    private static final String PARAM_NUM_OF_COLUMNS = "num_of_columns";

    private int mParamNumOfColumns;

    private OnRecipeClickListener mListener;

    public RecipeListFragment() {
        // Required empty public constructor
    }

    public static RecipeListFragment newInstance(int paramNumOfColumns) {
        RecipeListFragment fragment = new RecipeListFragment();
        Bundle args = new Bundle();
        args.putInt(PARAM_NUM_OF_COLUMNS, paramNumOfColumns);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (NetworkUtil.getInstance().isNetworkConnected(getActivity())) {
            getData();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParamNumOfColumns = getArguments().getInt(PARAM_NUM_OF_COLUMNS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        ButterKnife.bind(this, rootView);

        mRecipeAdapter = new RecipeAdapter(getActivity(), new ArrayList<Recipe>());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(), 1);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mRecipeAdapter);
        // TODO
        mRecyclerView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onRecipeSelected(0);
            }
        });

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnRecipeClickListener) {
            mListener = (OnRecipeClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnRecipeClickListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void processFinish(List<Recipe> recipes) {
        if (!recipes.isEmpty()) {
            mRecipeAdapter.cleanRecipes();
            mRecipeAdapter.addRecipes(recipes);
        }
    }

    private void getData() {
        new FetchRecipesTask(this).execute();
    }

    public interface OnRecipeClickListener {
        void onRecipeSelected(int position);
    }
}
