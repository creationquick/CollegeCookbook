package com.example.collegecookbook;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //Register your parse models
        ParseObject.registerSubclass(Post.class);
        ParseObject.registerSubclass(User.class);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("TBhmBvSof9zleS8QD8xsCmgvnavG7ZmACucLlPFb")
                .clientKey("4OleK6iClG27giZXb6jaVQIdzaacRo9kijohK92W")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
