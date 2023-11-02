package com.example.bookbrief;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.bookbrief.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentLibrary extends Fragment {
    ArrayList<ContentModel> arrDetails = new ArrayList<>();
    RecyclerView recyclerView;
    private RecyclerAdapterLibrary adapter;
    private RequestQueue requestQueue;

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();


    public FragmentLibrary() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view1 = inflater.inflate(R.layout.fragment_library, container, false);
        recyclerView = view1.findViewById(R.id.recyclerContent);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        requestQueue = Volley.newRequestQueue(requireContext());
        adapter = new RecyclerAdapterLibrary(this, arrDetails , requestQueue);
        recyclerView.setAdapter(adapter);
        // Initialize the requestQueue


        getUserId();
        return view1;
    }


    private void parseJsonResponse(JSONArray response) {
        arrDetails.clear(); // Clear existing data before adding new data

        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject jsonObject = response.getJSONObject(i);
                String title = jsonObject.getString("title");
                String description = jsonObject.getString("description");
                String blogId= jsonObject.getString("_id");
                JSONObject userObject = jsonObject.getJSONObject("user");
                String name = userObject.getString("name");
                String photo = userObject.getString("photo");


                // Create a new ContentModel from JSON data
                ContentModel contentModel = new ContentModel(title, description,blogId,name,photo);
                arrDetails.add(contentModel);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Notify the adapter that the data has changed
        adapter.notifyDataSetChanged();
    }

    // Create a method to make a Volley PUT request to update a resource

    // Create a method to make a Volley DELETE request to delete a resource

    private void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }


    //get user id
    private void getUserId() {


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
                        getPersonalBlog(id);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle errors here (e.g., show an error message)
                        showToast("get user id Error: " + error.getMessage());
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);

    }


    //get all the personal blogs
    private void getPersonalBlog(String id) {
        String email = currentUser.getEmail();
        String url = "https://summary-blog.vercel.app/api/blogs/" + id + "?type=user"; // Replace with your API endpoint

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Handle the JSON response here
                        parseJsonResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle errors here (e.g., show an error message)
                        showToast("get personal blog Error: " + error.getMessage());
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);

    }


    //extract id form the json provided
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

}