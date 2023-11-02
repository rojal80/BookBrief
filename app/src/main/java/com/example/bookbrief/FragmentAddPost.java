package com.example.bookbrief;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

//import com.example.bookbrief.Services.PostServices;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class FragmentAddPost extends Fragment {


    private RequestQueue requestQueue;
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

    public FragmentAddPost() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view1 = inflater.inflate(R.layout.fragment_addpost, container, false);
        Button btnPost = view1.findViewById(R.id.btnPost);
        EditText editTitle = view1.findViewById(R.id.editTitle);
        EditText editDescription = view1.findViewById(R.id.editDescription);


        // Initialize the requestQueue
        requestQueue = Volley.newRequestQueue(requireContext());



        //post button click listener
        btnPost.setOnClickListener(view -> {
            Log.d("FragmentAddPost", "Post button clicked");
            String title = editTitle.getText().toString();
            String description = editDescription.getText().toString();

            //this function gets the user id
            getUserId(title, description);

        });
        return view1;
    }


    //gets user id through email and get request

    private void getUserId(String title, String description) {
        String email = currentUser.getEmail();
        String url = "https://summary-blog.vercel.app/api/users?email=" + email; // Replace with your API endpoint

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Handle the JSON response here

                        String id = extractIdFromJson(response.toString());

                        JSONObject postData = new JSONObject();
                        try {
                            postData.put("title", title);
                            postData.put("description", description);
                            postData.put("user", id);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        String postUrl = "https://summary-blog.vercel.app/api/blogs"; // Replace with your POST endpoin
                        addNewBlog(postUrl, postData);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle errors here (e.g., show an error message)
                        showToast("Volley Request Error: " + error.getMessage());
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);

    }


    // Create a method to make a Volley POST request
    private void addNewBlog(String postUrl, JSONObject postObject) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                postUrl,
                postObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the POST request response (e.g., update your UI)
                        showToast("blog posted successfully");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle errors here (e.g., show an error message)
                        showToast("Volley POST Request Error: " + error.getMessage());
                    }
                }
        );

        // Add the POST request to the queue
        requestQueue.add(jsonObjectRequest);
    }

    public String extractIdFromJson(String jsonResponse) {
        try {
            // Parse the JSON array
            JSONArray jsonArray = new JSONArray(jsonResponse);

            if (jsonArray.length() > 0) {
                // Get the first object from the array
                JSONObject jsonObject = jsonArray.getJSONObject(0);

                // Extract the "_id" field from the object
                return jsonObject.getString("_id");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            // Handle JSON parsing errors here
        }

        return null; // Return null if the ID couldn't be extracted
    }

    private void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }

}
