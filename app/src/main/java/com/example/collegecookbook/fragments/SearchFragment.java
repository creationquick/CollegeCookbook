package com.example.collegecookbook.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.collegecookbook.Categories;
import com.example.collegecookbook.CategoriesAdapter;
import com.example.collegecookbook.Post;
import com.example.collegecookbook.PostsAdapter;
import com.example.collegecookbook.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    public static final String TAG = "SearchFragment";
    private RecyclerView rvCategories;
    protected CategoriesAdapter adapter;
    protected List<Categories> allCategories;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        rvCategories = view.findViewById(R.id.rvCategories);
        allCategories = new ArrayList<>();
        adapter = new CategoriesAdapter(getContext(), allCategories);

        rvCategories.setAdapter(adapter);
        rvCategories.setLayoutManager(new LinearLayoutManager(getContext()));
        //queryCategories();
    }

    private void queryCategories() {
        ParseQuery<Categories> query = ParseQuery.getQuery(Categories.class);
        query.include(Post.KEY_USER);
        query.include(Categories.KEY_CATEGORYNAME);
        //query.setLimit(20);
        //query.addDescendingOrder(Post.KEY_CREATED_KEY);
        query.findInBackground(new FindCallback<Categories>() {
            @Override
            public void done(List<Categories> categories, ParseException e) {
                if(e != null){
                    Log.e(TAG, "Issue with getting categories");
                    return;
                }
                for(Categories category: categories){
                    //Log.i(TAG, "received Category: " + category.getCategoryName());
                    Log.i(TAG, "received Category: ");

                }
                allCategories.addAll(categories);
                //adapter.notifyDataSetChanged();
            }
        });
    }

}