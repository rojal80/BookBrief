package com.example.bookbrief;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class FragmentSaved extends Fragment {
    ArrayList<ContentModel> arrDetails=new ArrayList<>();
    RecyclerView recyclerView;
    private RecyclerAdapterSaved adapter;
    private RequestQueue requestQueue;



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
//        arrDetails.add(new ContentModel("This is Life","We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us."));
//        arrDetails.add(new ContentModel("Love of Life", "We should enjoy every small things that are around us."));
//        arrDetails.add(new ContentModel(" My Life", "We should enjoy every small things that are around us."));
//        arrDetails.add(new ContentModel("Manifestation", "We should enjoy every small things that are around us."));
        adapter= new RecyclerAdapterSaved(this,arrDetails);
        recyclerView.setAdapter(adapter);
        // Initialize the requestQueue
        requestQueue = Volley.newRequestQueue(requireContext());

        // Make a Volley GET request
        makeVolleyGetRequest();
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

    private void showToast(String message) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
    }
}
