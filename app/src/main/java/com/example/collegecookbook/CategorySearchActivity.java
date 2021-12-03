package com.example.collegecookbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;

import com.example.collegecookbook.fragments.SearchFragment;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class CategorySearchActivity extends AppCompatActivity {

    public static final String TAG = "CategorySearchActivity";
    private RecyclerView rvCategory;
    protected PostsAdapter adapter;
    protected List<Post> allPosts;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_search);

        Bundle bundle = getIntent().getExtras();
        String category = bundle.getString("category");

        rvCategory = findViewById(R.id.rvCategory);
        allPosts = new ArrayList<>();
        adapter = new PostsAdapter(CategorySearchActivity.this, allPosts);

        rvCategory.setAdapter(adapter);
        rvCategory.setLayoutManager(new LinearLayoutManager(CategorySearchActivity.this));
        queryCategory(category);
    }

    private void queryCategory(String category){
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        ParseUser parseUser = ParseUser.getCurrentUser();
        query.whereEqualTo("category", category);
        query.setLimit(20);
        query.addDescendingOrder(Post.KEY_CREATED_KEY);
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                if(e != null){
                    Log.e(TAG, "Issue with getting posts");
                    return;
                }
                for(Post post: posts){
                    Log.i(TAG, "Post: " + post.getCaption() + ", username: " + post.getUser().getUsername());
                }
                allPosts.addAll(posts);
                adapter.notifyDataSetChanged();
            }
        });

    }
}