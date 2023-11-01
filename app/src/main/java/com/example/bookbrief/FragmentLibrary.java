package com.example.bookbrief;
import android.os.Bundle;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentLibrary extends Fragment {
    ArrayList<ContentModel> arrDetails=new ArrayList<>();
    RecyclerView recyclerView;
    private RecyclerAdapterLibrary adapter;
    private RequestQueue requestQueue;

    public FragmentLibrary(){
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view1= inflater.inflate(R.layout.fragment_library,container,false);
        recyclerView=view1.findViewById(R.id.recyclerContent);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        arrDetails.add(new ContentModel("This is Life","We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us."));
//        arrDetails.add(new ContentModel("Love of Life", "We should enjoy every small things that are around us."));
//        arrDetails.add(new ContentModel(" My Life", "We should enjoy every small things that are around us."));
//        arrDetails.add(new ContentModel("Manifestation", "We should enjoy every small things that are around us."));
////        arrDetails.add(new ContentModel("Surrounding", "We should enjoy every small things that are around us."));
////        arrDetails.add(new ContentModel("Environment", "We should enjoy every small things that are around us."));
////        arrDetails.add(new ContentModel("JavaScript", "We should enjoy every small things that are around us."));
////        arrDetails.add(new ContentModel("Virtual Reality", "We should enjoy every small things that are around us."));
////        arrDetails.add(new ContentModel("Animation", "We should enjoy every small things that are around us."));
////        arrDetails.add(new ContentModel("Broadcast Agency", "We should enjoy every small things that are around us."));
////        arrDetails.add(new ContentModel("Zombie attack", "We should enjoy every small things that are around us."));
        adapter= new RecyclerAdapterLibrary(this,arrDetails);
        recyclerView.setAdapter(adapter);
        // Initialize the requestQueue
        requestQueue = Volley.newRequestQueue(requireContext());

        // Make a Volley GET request
        makeVolleyGetRequest();
// make a volley put request
        makeVolleyPutRequest("resource_id", createUpdateData());

        //  making a DELETE request
        makeVolleyDeleteRequest("resource_id");

        return view1;


    }
    // Create a method to make a Volley GET request
    private void makeVolleyGetRequest() {
        String url = "https://api.example.com/data"; // Replace with your API endpoint

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
                        showToast("Volley Request Error: " + error.getMessage());
                    }
                }
        );

        // Add the request to the queue
        requestQueue.add(jsonArrayRequest);
    }

    private void parseJsonResponse(JSONArray response) {
        arrDetails.clear(); // Clear existing data before adding new data

        for (int i = 0; i < response.length(); i++) {
            try {
                JSONObject jsonObject = response.getJSONObject(i);
                String title = jsonObject.getString("title");
                String description = jsonObject.getString("description");

                // Create a new ContentModel from JSON data
                ContentModel contentModel = new ContentModel(title, description);
                arrDetails.add(contentModel);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        // Notify the adapter that the data has changed
        adapter.notifyDataSetChanged();
    }

    // Create a method to make a Volley PUT request to update a resource
    private void makeVolleyPutRequest(String resourceId, JSONObject updatedData) {
        String url = "https://api.example.com/resource/" + resourceId; // Replace with your API endpoint

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                updatedData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the PUT request response (e.g., update your UI)
                        showToast("Volley PUT Request Response: " + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle errors here (e.g., show an error message)
                        showToast("Volley PUT Request Error: " + error.getMessage());
                    }
                }
        );

        // Add the PUT request to the queue
        requestQueue.add(jsonObjectRequest);
    }


    // Create a method to make a Volley DELETE request to delete a resource
    private void makeVolleyDeleteRequest(String resourceId) {
        String url = "https://api.example.com/resource/" + resourceId; // Replace with your API endpoint

        StringRequest stringRequest = new StringRequest(
                Request.Method.DELETE,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the DELETE request response (e.g., update your UI)
                        showToast("Volley DELETE Request Response: " + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle errors here (e.g., show an error message)
                        showToast("Volley DELETE Request Error: " + error.getMessage());
                    }
                }
        );

        // Add the DELETE request to the queue
        requestQueue.add(stringRequest);
    }

    private void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
    private JSONObject createUpdateData() {
        try {
            // Create a JSON object with the data you want to update
            JSONObject updatedData = new JSONObject();
            updatedData.put("key1", "new_value1");
            updatedData.put("key2", "new_value2");

            return updatedData;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

}