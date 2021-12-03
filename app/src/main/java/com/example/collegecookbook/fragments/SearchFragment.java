package com.example.collegecookbook.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.collegecookbook.Categories;
import com.example.collegecookbook.CategorySearchActivity;
import com.example.collegecookbook.Post;
import com.example.collegecookbook.PostsAdapter;
import com.example.collegecookbook.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    public static final String TAG = "SearchFragment";
    private RecyclerView rvCategories;
    private RecyclerView rvCategory;
    protected PostsAdapter adapter;
    //protected List<Categories> allCategories;
    protected List<Post> allPosts;
    private Button btnHealthy;
    private Button btnBreakfast;
    private Button btnLunch;
    private Button btnDinner;
    private Button btnSnack;
    private Button btnComfortFood;
    public String currentCategory;

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

        //rvCategory = view.findViewById(R.id.rvCategory);
        btnBreakfast = view.findViewById(R.id.btnBreakfast);
        btnBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentCategory = "Breakfast";
                goCategorySearchActivity(currentCategory);
            }
        });
        btnLunch = view.findViewById(R.id.btnLunch);
        btnLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentCategory = "Lunch";
                goCategorySearchActivity(currentCategory);
            }
        });
        btnDinner = view.findViewById(R.id.btnDinner);
        btnDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentCategory = "Dinner";
                goCategorySearchActivity(currentCategory);
            }
        });
        btnHealthy = view.findViewById(R.id.btnHealthy);
        btnHealthy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentCategory = "Healthy";
                goCategorySearchActivity(currentCategory);
            }
        });
        btnSnack= view.findViewById(R.id.btnSnack);
        btnSnack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentCategory = "Snack";
                goCategorySearchActivity(currentCategory);
            }
        });
        btnComfortFood = view.findViewById(R.id.btnComfortFood);
        btnComfortFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentCategory = "Comfort Food";
                goCategorySearchActivity(currentCategory);
            }
        });
    }

    public void goCategorySearchActivity(String category) {
            Intent i = new Intent(getActivity(), CategorySearchActivity.class);
            i.putExtra("category", category);
            startActivity(i);
    }


    public String getCategory(){
        return currentCategory;
    }





}