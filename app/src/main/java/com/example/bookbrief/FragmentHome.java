package com.example.bookbrief;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.PixelCopy;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentHome extends Fragment {


    public FragmentHome() {
        // Required empty public constructor
    }

    ArrayList<ContentModel> arrDetails = new ArrayList<>();
    RecyclerView recyclerView;
    private RecyclerAdapterHome adapter;
    private RequestQueue requestQueue;



    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        View view1 = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view1.findViewById(R.id.recyclerContent);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new RecyclerAdapterHome(this, arrDetails);
        recyclerView.setAdapter(adapter);
        requestQueue = Volley.newRequestQueue(requireContext());

        // Make a Volley GET request
        makeVolleyGetRequest();
        checkIfUserExists();
        return view1;
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }


    //check if user exists
    private void checkIfUserExists() {
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

                        if (response.length() == 0) {
                            JSONObject postData = new JSONObject();
                            try {
                                postData.put("name",currentUser.getDisplayName() );
                                postData.put("email",currentUser.getEmail() );
                                postData.put("photo",currentUser.getPhotoUrl() );

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            String postUrl = "https://summary-blog.vercel.app/api/users"; // Replace with your POST endpoin
                            insertUserToDatabase(postUrl, postData);
                        }

//                        parseJsonResponse(response);
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


    //inserts new user to database
    private void insertUserToDatabase(String postUrl, JSONObject postObject) {


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                postUrl,
                postObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
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


    // Create a method to make a Volley GET request
    private void makeVolleyGetRequest() {
        String url = "https://summary-blog.vercel.app/api/blogs"; // Replace with your API endpoint

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


    public void addContent(ContentModel newContent) {
        arrDetails.add(newContent);
        adapter.notifyDataSetChanged();
        showToast("Successfully Posted");
    }

    private void showToast(String message) {
        if (getContext() != null) {
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
        }
    }


}

