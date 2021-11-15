package com.example.collegecookbook;

import android.service.controls.templates.StatelessTemplate;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.lang.reflect.Array;
import java.util.List;

@ParseClassName("Post")
public class Post extends ParseObject {

    public static final String KEY_CAPTION = "caption";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_USER = "user";
    public static final String KEY_CATEGORY = "category";
    public static final String KEY_LIKESCOUNT = "likesCount";
    public static final String KEY_INGREDIENTS = "ingredients";
    public static final String KEY_STEPS = "steps";
    public static final String KEY_RECIPETITLE = "recipeTitle";
    public static final String KEY_CREATED_KEY = "createdAt";


    public  String getCaption() {
        return getString(KEY_CAPTION);
    }

    public void setCaption(String caption) {
        put(KEY_CAPTION, caption);
    }

    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    public void setImage(ParseFile parseFile){
        put(KEY_IMAGE, parseFile);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user){
        put(KEY_USER, user);
    }

    public  String getCategory() {
        return getString(KEY_CATEGORY);
    }

    public void setCategory(String category) {
        put(KEY_CATEGORY, category);
    }

    public int getLikesCount() {
        return getInt(KEY_LIKESCOUNT);
    }

    public void setLikesCount(int likesCount) {
        put(KEY_LIKESCOUNT, likesCount);
    }
    public  String getRecipeTitle() {
        return getString(KEY_RECIPETITLE);
    }

    public void setRecipetitle(String recipetitle) {
        put(KEY_RECIPETITLE, recipetitle);
    }

    public List<String> getSteps() {
        return getList(KEY_STEPS);
    }

    public void setSteps(List<String> steps) {
        put(KEY_STEPS, steps);
    }

//    public void addStep(String step, List steps){
//        put(KEY_STEPS, );
//    }

    public List<String> getIngredients() {
        return getList(KEY_INGREDIENTS);
    }

    public void setIngredients(List<String> ingredients) {
        put(KEY_INGREDIENTS, ingredients);
    }

}

