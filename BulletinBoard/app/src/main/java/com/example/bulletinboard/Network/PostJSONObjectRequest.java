package com.example.bulletinboard.Network;

import com.android.volley.toolbox.JsonObjectRequest;
import com.example.bulletinboard.Post;

import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by alexandre on 18/10/17.
 */

public class PostJSONObjectRequest implements Request {

    private Status status = Status.UNINITIALISED;
    private Connection connection;
    private final VolleyCallback<JSONObject> callback;
    private final String url;
    private final JSONObject params;

    private PostJSONObjectRequest(String url, VolleyCallback<JSONObject> callback, JSONObject params){
        connection = Connection.get();
        this.url = url;
        this.callback = callback;
        this.status = Status.INITIALISED;
        this.params = params;
    }

    @Override
    public void send() {
        JsonObjectRequest req = new JsonObjectRequest(url, params,
                response -> {
                    status = Status.SUCCESS;
                    callback.onSuccess(response);
                },
                e -> {
                    status = Status.ERROR;
                    callback.onFailure();
                });

        connection.getRequestQueue().add(req);
        status = Status.SENT;
    }

    @Override
    public Status getStatus() {
        return null;
    }

    public static PostJSONObjectRequest post(VolleyCallback<JSONObject> callback, Post p){
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("user_id","temp user");
        params.put("title", p.getTitle());
        params.put("description", p.getDescription());

        return new PostJSONObjectRequest("http://104.197.33.114:8000/api/posts/",callback,new JSONObject(params));
    }
}
