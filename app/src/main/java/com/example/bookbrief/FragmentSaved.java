package com.example.bookbrief;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.bookbrief.ContentModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class FragmentSaved extends Fragment {
    ArrayList<ContentModel> arrDetails=new ArrayList<>();
    RecyclerView recyclerView;
    private RecyclerAdapterSaved adapter;
    private RequestQueue requestQueue;

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();


    public FragmentSaved() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view1= inflater.inflate(R.layout.fragment_saved, container, false);
        recyclerView=view1.findViewById(R.id.recyclerContent);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize the requestQueue
        requestQueue = Volley.newRequestQueue(requireContext());
        adapter= new RecyclerAdapterSaved(this,arrDetails);
        recyclerView.setAdapter(adapter);

        // Make a Volley GET request
        getUserId();
        return view1;
    }

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



                        String postUrl = "https://summary-blog.vercel.app/api/blogs"; // Replace with your POST endpoin
                        getSavedBlogs(id);
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


    // Create a method to make a Volley GET request
    private void getSavedBlogs(String id) {
        String url = "https://summary-blog.vercel.app/api/savedBlogs/"+id; // Replace with your API endpoint

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
                        showToast("Volley GET Request Error: " + error.getMessage());
                    }
                }
        );

        // Add the GET request to the queue
        requestQueue.add(jsonArrayRequest);
    }

    private void parseJsonResponse(JSONArray response) {
        arrDetails.clear(); // Clear existing data before adding new data

        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject jsonObject = response.getJSONObject(i);
                JSONObject dataObject=jsonObject.getJSONObject("blog");

                String title = dataObject.getString("title");
                String description = dataObject.getString("description");
                String blogId=dataObject.getString("_id");

                JSONObject imageObject=dataObject.getJSONObject("user");
                String photo = imageObject.getString("photo");

                JSONObject userObject = jsonObject.getJSONObject("user");
                String name = imageObject.getString("name");



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

    private void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}
