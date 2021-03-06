package com.example.bulletinboard;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.bulletinboard.network.GetJSONObjectRequest;
import com.example.bulletinboard.network.PostJSONObjectRequest;
import com.example.bulletinboard.network.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Class: Posts
 * Public Methods: getInstance() - gets the posts object (which is made up of Array
 * List of Post
 * addPost() - adds a post to the array
 * getPost(index) - returns the post at an index
 * Description: Class to hold an ArrayList of posts for use on the show post page
 * Created by Logan on 2017-10-14.
 */

public class Posts {
    private static Posts posts = new Posts();
    private ArrayList<Post> postList;

    public Posts() {
        postList = new ArrayList<>();
    }

    public static Posts getInstance() {
        return posts;
    }

    public static void addPost(Post post, Context context){
        PostJSONObjectRequest request = PostJSONObjectRequest.post(new VolleyCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                Log.d("POST POSTED","HeY");
                CharSequence text = "Your post has been added";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
                Intent showPost = new Intent(context, ShowPost.class);
                context.startActivity(showPost);
            }

            @Override
            public void onFailure() {
                Log.d("failed", "Failure");
                CharSequence text = "Post creation failed";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
        }, post);

        request.send();
    }


    public void addPosts(JSONObject json) {

        try {
            if (json.getString("success").equals("true")) {
                Log.d("Add Posts", json.toString());
                JSONArray data = json.getJSONArray("posts");

                postList = new ArrayList<>();

                for (int i = 0; i < data.length(); i++) {
                    Post p;
                    JSONObject post = data.getJSONObject(i);
                    p = new Post(post.getString("title"),
                            post.getString("description"),
                            post.getString("_id"),
                            post.getString("user_id"),
                            post.getBoolean("showPhone"),
                            post.getBoolean("showEmail"),
                            post.getString("date"));
                    postList.add(p);

                }

            }
        } catch (org.json.JSONException e) {
            e.printStackTrace();
        }
    }

    public Post getPost(int index) {
        if (index < postList.size()) {
            return postList.get(index);
        } else {
            return new Post("Network Error", "Go back and try again");
        }
    }

    public ArrayList<Post> getPosts() {

        GetJSONObjectRequest request = GetJSONObjectRequest.getAllPosts(new VolleyCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {
                addPosts(response);
            }

            @Override
            public void onFailure() {
                Log.d("failed", "Failure");
            }
        });

        request.send();

        //while (request.getStatus() != Status.SUCCESS || request.getStatus() != Status.ERROR);
        return postList;
    }

}
