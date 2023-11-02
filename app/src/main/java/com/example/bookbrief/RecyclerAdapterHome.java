package com.example.bookbrief;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.orhanobut.dialogplus.DialogPlus;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerAdapterHome extends RecyclerView.Adapter<RecyclerAdapterHome.ViewHolder> implements Filterable {
    FragmentHome context;
    private Fragment fragment;

    ArrayList<ContentModel> arrDetails;
    ArrayList<ContentModel> backup;
    private RequestQueue requestQueue;

    private String userEmail;

    public RecyclerAdapterHome(FragmentHome context, ArrayList<ContentModel> arrDetails, RequestQueue requestQueue ,String email) {
        this.requestQueue=requestQueue;
        this.context = context;
        this.arrDetails = arrDetails;
        this.userEmail=email;
        backup = new ArrayList<>(arrDetails);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context.getContext()).inflate(R.layout.home_content, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {


        holder.txt1.setText(arrDetails.get(position).getTitle());
        holder.userName.setText(arrDetails.get(position).getUserName());
        String imageUrl = arrDetails.get(position).getImage();
        Picasso.get().load(imageUrl).into(holder.photo);


        holder.cardHome.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), HomeDescription.class);

            intent.putExtra("Title", arrDetails.get(position).getTitle());
            intent.putExtra("Description", arrDetails.get(position).getDescription());

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            view.getContext().startActivity(intent);
        });

        holder.saveImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


//                Toast.makeText(view.getContext(), "Post saved!"+position, Toast.LENGTH_SHORT).show();
                getUserId(position);
            }

        });
    }

    private void getUserId(int position) {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    String ee=currentUser.getEmail();
    String TAG="kaljsdlfkajsldf;asjlkfdsfhsadljdsf;jkadjkdajkqewruupepueqw";
        String url = "https://summary-blog.vercel.app/api/users?email=" + userEmail; // Replace with your API endpoint
        Log.i(TAG, "getUserId: "+userEmail);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Handle the JSON response here

                        String id = extractIdFromJson(response.toString());

//                        Log.i(TAG, "onResponse: user id"+id);
//                        Log.i(TAG, "onResponse: blog id"+arrDetails.get(position).getBlogId());

                        JSONObject postData = new JSONObject();
                        try {
                            postData.put("user", id);
                            postData.put("blog", arrDetails.get(position).getBlogId());

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        String postUrl = "https://summary-blog.vercel.app/api/savedBlogs"; // Replace with your POST endpoin
                        saveBlog(postUrl,postData);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle errors here (e.g., show an error message)
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);

    }

    private void saveBlog(String postUrl, JSONObject postObject) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                postUrl,
                postObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the POST request response (e.g., update your UI)
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle errors here (e.g., show an error message)
//                        showToast("Volley POST Request Error: " + error.getMessage());
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


    @Override
    public int getItemCount() {
        return arrDetails.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence keyword) {
            ArrayList<ContentModel> filtereddata = new ArrayList<>();
            if (keyword.toString().isEmpty()) {
                filtereddata.addAll(backup);
            } else {
                for (ContentModel obj : backup) {
                    if (obj.getTitle().toLowerCase().contains(keyword.toString().toLowerCase()))
                        filtereddata.add(obj);
                }
            }
            FilterResults results = new FilterResults();
            results.values = filtereddata;
            return results;
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults results) {
            arrDetails.clear();
            arrDetails.addAll((ArrayList<ContentModel>) results.values);
            notifyDataSetChanged();

        }
    };

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt1, txt2, userName;
        CardView cardHome;
        ImageView saveImage;


        CircleImageView photo;

        public ViewHolder(View itemView) {

            super(itemView);
            txt1 = itemView.findViewById(R.id.txt1);
            txt2 = itemView.findViewById(R.id.txt2);
            cardHome = itemView.findViewById(R.id.cardHome);

            photo = itemView.findViewById(R.id.profileImage);
            userName = itemView.findViewById(R.id.userProfileNameTextView);

            saveImage = itemView.findViewById(R.id.saveImage);
        }

    }
}
