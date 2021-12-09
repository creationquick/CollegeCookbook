package com.example.collegecookbook.fragments;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collegecookbook.Post;
import com.example.collegecookbook.R;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.util.ArrayList;

public class ComposeFragment extends Fragment {
    private Button btnRecipePhoto;


    public static final String TAG = "ComposeFragment";
    public static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 42;
    private EditText etAddCaption;
    private EditText etAddCategory;
    private EditText etAddRecipeName;
    private Button btnAddRecipePhoto;
    private ImageView ivConfirmRecipePhoto;
    private Button btnSubmitPost;
    private File photoFile;
    private ListView lvIngredients;
    private ListView lvSteps;
    private EditText etAddIngredient;
    private EditText etAddStep;
    private Button btnAddIngredient;
    private Button btnAddStep;
    private String photoFileName = "photo.jpg";

    public ComposeFragment() {
        // Required empty public constructor
    }


    // The onCreateView method is called when Fragment should create its View object hierarchy,
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_compose, container, false);
    }

    // This event is triggered soon after onCreateView().
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        etAddCaption = view.findViewById(R.id.etAddCaption);
        etAddCategory = view.findViewById(R.id.etAddCategory);
        etAddRecipeName = view.findViewById(R.id.etAddRecipeName);
        btnAddRecipePhoto = view.findViewById(R.id.btnAddRecipePhoto);
        ivConfirmRecipePhoto = view.findViewById(R.id.ivConfirmRecipePhoto);
        btnSubmitPost = view.findViewById(R.id.btnSubmitPost);


        lvIngredients = view.findViewById(R.id.lvIngredients);
        ArrayList<String> ingredientsList = new ArrayList<>();
        ingredientsList.add("Ingredient 1");
        ingredientsList.add("Ingredient 2");

        ArrayAdapter<String> ingredientAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, ingredientsList);
        lvIngredients.setAdapter(ingredientAdapter);
        lvIngredients.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                String clickedItem = (String) lvIngredients.getItemAtPosition(position);
                //Delete the item from the model
                ingredientsList.remove(position);
                //Notify the adapter
                ingredientAdapter.notifyDataSetChanged();
                //ingredientAdapter.notifyItemRemoved(position);
                //Toast.makeText(getApplicationContext(),"Item was removed", Toast.LENGTH_SHORT).show();
                //saveItems();
                return true;
            }
        });
        lvIngredients.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem=(String) lvIngredients.getItemAtPosition(position);
                Toast.makeText(getContext(),clickedItem,Toast.LENGTH_LONG).show();
            }
        });

        lvSteps = view.findViewById(R.id.lvSteps);
        ArrayList<String> stepsList = new ArrayList<>();
        stepsList.add("Step 1");
        stepsList.add("Step 2");

        ArrayAdapter<String> stepsAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1, stepsList);
        lvSteps.setAdapter(stepsAdapter);
        lvSteps.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                String clickedItem = (String) lvSteps.getItemAtPosition(position);
                //Delete the item from the model
                stepsList.remove(position);
                //Notify the adapter
                stepsAdapter.notifyDataSetChanged();
                //ingredientAdapter.notifyItemRemoved(position);
                //Toast.makeText(getApplicationContext(),"Item was removed", Toast.LENGTH_SHORT).show();
                //saveItems();
                return true;
            }
        });
        lvSteps.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String clickedItem=(String) lvSteps.getItemAtPosition(position);
                Toast.makeText(getContext(),clickedItem,Toast.LENGTH_LONG).show();
            }
        });

        etAddIngredient = view.findViewById(R.id.etAddIngredients);
        btnAddIngredient = view.findViewById(R.id.btnAddIngredient);
        btnAddIngredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newIngredient = etAddIngredient.getText().toString();
                ingredientsList.add(newIngredient);
                stepsAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(),newIngredient,Toast.LENGTH_LONG).show();
                etAddIngredient.setText("");
            }
        });

        etAddStep = view.findViewById(R.id.etAddSteps);
        btnAddStep = view.findViewById(R.id.btnAddStep);
        btnAddStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newStep = etAddStep.getText().toString();
                stepsList.add(newStep);
                ingredientAdapter.notifyDataSetChanged();
                Toast.makeText(getContext(),newStep,Toast.LENGTH_LONG).show();
                etAddStep.setText("");
            }
        });

        btnAddRecipePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchCamera();
            }
        });

        //queryPosts();
        btnSubmitPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String caption = etAddCaption.getText().toString();
                String recipeName = etAddRecipeName.getText().toString();
                String category = etAddCategory.getText().toString();
                String recipeTitle = etAddRecipeName.getText().toString();
                if(caption.isEmpty() || recipeName.isEmpty() || category.isEmpty()){
                    Toast.makeText(getContext(), "All Fields must be filled", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (photoFile == null || ivConfirmRecipePhoto.getDrawable() == null){
                    Toast.makeText(getContext(), "There is no image!", Toast.LENGTH_SHORT).show();
                    return;
                }
                ParseUser currentUser = ParseUser.getCurrentUser();
                savePost(caption, category, currentUser, photoFile, recipeTitle, ingredientsList, stepsList);
            }
        });
    }
    private void launchCamera() {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference for future access
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(getContext(), "com.codepath.fileproviderr", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getContext().getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // by this point we have the camera photo on disk
                Bitmap takenImage = BitmapFactory.decodeFile(photoFile.getAbsolutePath());
                // RESIZE BITMAP, see section below
                // Load the taken image into a preview

                ivConfirmRecipePhoto.setImageBitmap(takenImage);
            } else { // Result was a failure
                Toast.makeText(getContext(), "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        return new File(mediaStorageDir.getPath() + File.separator + fileName);
    }

    private void savePost(String caption, String category, ParseUser currentUser, File photoFile, String recipeTitle, ArrayList<String> ingredientsList, ArrayList<String> stepsList) {
        Post post = new Post();
        post.setCaption(caption);
        post.setCategory(category);
        post.setImage(new ParseFile(photoFile));
        post.setUser(currentUser);
        post.setRecipeTitle(recipeTitle);
        post.setIngredients(ingredientsList);
        post.setSteps(stepsList);
        post.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e != null){
                    Log.e(TAG, "Error while saving", e);
                    Toast.makeText(getContext(), "error while saving!", Toast.LENGTH_SHORT).show();
                }else{
                    Log.i(TAG, "Post save was successful!");
                    etAddCaption.setText("");
                    etAddRecipeName.setText("");
                    etAddCategory.setText("");
                    ivConfirmRecipePhoto.setImageResource(0);
                }
            }
        });
    }
}