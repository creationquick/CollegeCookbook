package com.example.collegecookbook.fragments;

import android.content.Intent;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collegecookbook.LoginActivity;
import com.example.collegecookbook.Post;
import com.example.collegecookbook.PostsAdapter;
import com.example.collegecookbook.R;
import com.example.collegecookbook.User;
import com.example.collegecookbook.UserPostsAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class ProfileFragment extends Fragment {

    public static final String TAG = "ProfileFragment";
    private Button btnLogout;
    LoginActivity loginActivity;
    private RecyclerView rvUserPosts;
    protected UserPostsAdapter adapter;
    protected List<Post> allPosts;
    private ImageView ivProfilePicture;
    private EditText etBio;
    private Button btnEditProfile;
    private File photoFile;
    private TextView tvProfileUsername;

    public ProfileFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    // This event is triggered soon after onCreateView().
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        btnLogout = view.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i(TAG, "onClick logout Button");
                logoutUser();
            }
        });

        rvUserPosts = view.findViewById(R.id.rvUserPosts);
        allPosts = new ArrayList<>();
        adapter = new UserPostsAdapter(getContext(), allPosts);

        rvUserPosts.setAdapter(adapter);
        rvUserPosts.setLayoutManager(new GridLayoutManager(getContext(), 3));
        queryPosts();

        ParseUser currentUser = ParseUser.getCurrentUser();
        ivProfilePicture = view.findViewById(R.id.ivProfilePicture);
        //ivProfilePicture.setImageResource();

        btnEditProfile = view.findViewById(R.id.btnSaveProfile);
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bio = etBio.getText().toString();
                //String photofile =
                saveUserProfile(currentUser, bio, photoFile);
            }
        });

        tvProfileUsername = view.findViewById(R.id.tvProfileUsername);
        tvProfileUsername.setText(currentUser.getUsername());
    }

    private void logoutUser() {
        Log.i(TAG, "Attempting to logout");
        ParseUser parseUser = ParseUser.getCurrentUser();
        parseUser.logOut();
        goLoginActivity();
    }

    private void goLoginActivity() {
        Intent i = new Intent(getActivity(), LoginActivity.class);
        startActivity(i);
        //finish();
    }

    private void queryPosts() {
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        query.include(Post.KEY_USER);
        ParseUser parseUser = ParseUser.getCurrentUser();
        query.whereEqualTo("user", parseUser);
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

    private void saveUserProfile(ParseUser currentUser,  String bio, File photoFile) {
        User user = new User();
        user.setUser(currentUser);
        user.setProfilePic(new ParseFile(photoFile));
        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e != null){
                    Log.e(TAG, "Error while saving", e);
                    Toast.makeText(getContext(), "error while saving!", Toast.LENGTH_SHORT).show();
                }else{
                    Log.i(TAG, "Profile save was successful!");
//                    ivConfirmRecipePhoto.setImageResource(0);
                }
            }
        });
    }


}