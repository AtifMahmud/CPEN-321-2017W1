package com.example.bulletinboard;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.bulletinboard.network.GetJSONObjectRequest;
import com.example.bulletinboard.network.VolleyCallback;
import com.example.bulletinboard.User;

import org.json.JSONObject;

import static com.example.bulletinboard.User.getUserById;
import static com.example.bulletinboard.User.updateRating;

public class PostDisplayActivity extends AppCompatActivity {

    private static Context context;

    private Post post;
    private TextView description;
    private Toolbar toolbar;
    private RatingBar ratingBar;

    private static PostDisplayActivity me = new PostDisplayActivity();

    public PostDisplayActivity getInstance() {
        return me;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        context = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_display);

        Bundle b = getIntent().getExtras();
        String id = ""; // or other values
        if (b != null)
            id = b.getString("id");

        this.description = (TextView) findViewById(R.id.postDisplayDescr);
        toolbar = (Toolbar) findViewById(R.id.toolbar);


        GetJSONObjectRequest request = GetJSONObjectRequest.getPostById(id, new VolleyCallback<JSONObject>() {
            @Override
            public void onSuccess(JSONObject response) {

                Log.d("RESPONSE", response.toString());

                try {

                    JSONObject data = response.getJSONObject("post");

                    post = new Post(data.getString("title"),
                            data.getString("description"),
                            data.getString("_id"),
                            data.getString("user_id"),
                            data.getBoolean("showPhone"),
                            data.getBoolean("showEmail"),
                            data.getString("date"));

                    displayText(post, description);
                    displayTitle(post, toolbar);
                    // Show user rating in the rating bar
                    ratingBar = (RatingBar) findViewById(R.id.ratingBar);
                    ratingBar.setRating((float) getUserById(post.getUserId()).getRating());

                } catch (org.json.JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure() {
                Log.d("failed", "Failure");
            }
        });

        request.send();
    }

    public static void displayText(Post p, TextView tv) {
        tv.setText(p.getDescription());
        User u = getUserById(p.getUserId());
        tv.append("\n Created By "+u.getFirstName()+" "+u.getLastName());
    }

    public static void displayTitle(Post p, Toolbar tb) {
        tb.setTitle(p.getTitle());
    }

    public static void addToFavs() {

    }

    private void updateUsersRating(){
        Log.d("RATING UPDATED", "rating has been updated");
        updateRating(ratingBar.getRating(), post.getUserId(), context);
    }
}
