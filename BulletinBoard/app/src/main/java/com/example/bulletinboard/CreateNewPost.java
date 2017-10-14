package com.example.bulletinboard;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CreateNewPost extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_post);
    }

    public void createPost(View view) {
        String title = ((EditText) findViewById(R.id.title)).getText().toString().toLowerCase();
        String description = ((EditText) findViewById(R.id.description)).getText().toString().toLowerCase();
        Post thisPost = new Post(title, description);

        TextView t1 = (TextView) findViewById(R.id.postTitle);
        t1.setText(thisPost.getTitle());

        TextView t2 = (TextView) findViewById(R.id.postDescription);
        t2.setText(thisPost.getDescription());

        // send fields to server
    }
}
