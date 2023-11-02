package com.example.bookbrief;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.orhanobut.dialogplus.DialogPlus;
import com.squareup.picasso.Picasso;


import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerAdapterLibrary extends RecyclerView.Adapter<RecyclerAdapterLibrary.ViewHolder> implements Filterable {
    FragmentLibrary context;

    ArrayList<ContentModel> arrDetails;
    ArrayList<ContentModel> backup;

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();


    private RequestQueue requestQueue;
    String TAG = "lasdjflasjdflasdjflaksdjf";

    public RecyclerAdapterLibrary(FragmentLibrary context, ArrayList<ContentModel> arrDetails, RequestQueue requestQueue) {
        this.context = context;
        this.requestQueue = requestQueue;
//        Log.i(TAG, "RecyclerAdapterLibrary: " + requestQueue);

        this.arrDetails = arrDetails;
        backup = new ArrayList<>(arrDetails);
        if (context == null) {
            Log.e("Adapter", "Context is null!");
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context.getContext()).inflate(R.layout.librarycontent, parent, false);

        if (view.getContext() == null) {
            Log.e("onCreateViewHolder", "Context in onCreateViewHolder is null!");
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        //sets title username and image in library activity
        holder.txt1.setText(arrDetails.get(position).getTitle());
        holder.userName.setText(arrDetails.get(position).getUserName());

        String imageUrl = arrDetails.get(position).getImage();
        Picasso.get().load(imageUrl).into(holder.photo);



        holder.cardHome.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), HomeDescription.class);

            intent.putExtra("Title", arrDetails.get(position).getTitle());
            intent.putExtra("Description", arrDetails.get(position).getDescription());
            intent.putExtra("BlogId", arrDetails.get(position).getBlogId());

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            view.getContext().startActivity(intent);
        });

        holder.editpost.setOnClickListener(view -> {
            if (holder.editpost != null) {


                final DialogPlus dialogplus = DialogPlus.newDialog(holder.txt1.getContext())
                        .setContentHolder(new com.orhanobut.dialogplus.ViewHolder(R.layout.update_popup))
                        .setExpanded(true, 1200).create();

                EditText editTitle = (EditText) dialogplus.findViewById(R.id.editTitle);
                EditText editDescription = (EditText) dialogplus.findViewById(R.id.editDescription);
                ImageView editpost = (ImageView) dialogplus.findViewById(R.id.editpost);
                Button btnUpdate=(Button) dialogplus.findViewById(R.id.btnUpdate);
                // Get the position of the clicked item
                int itemPosition = holder.getAdapterPosition();
                ContentModel contentModel = arrDetails.get(itemPosition);

                // Populate the EditText fields with existing data
                editTitle.setText(contentModel.getTitle());
                editDescription.setText(contentModel.getDescription());


                    btnUpdate.setOnClickListener(e->{
                String blogId=arrDetails.get(position).getBlogId();
                String title = editTitle.getText().toString();
                String description=editDescription.getText().toString();

                        JSONObject updateData = new JSONObject();
                        try {
                            updateData.put("title", title);
                            updateData.put("description", description);

                        } catch (JSONException ex) {
                            ex.printStackTrace();
                        }

                        updateBlog(blogId,updateData);


                    });





                // Handle the editpost ImageView click here to save the edited data
//            editpost.setOnClickListener(view1 -> {
//
//            });

                dialogplus.show();
            }
        });

        holder.deletepost.setOnClickListener(view -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(holder.txt1.getContext());
            builder.setTitle("Are you sure you want to delete?");
            builder.setMessage("Deleted data can't be undo.");
            builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //delete http request
                    deleteBlog(arrDetails.get(position).getBlogId());

                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Toast.makeText(holder.txt1.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                }
            });
            builder.show();


        });
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
        TextView txt1, txt2,userName;

        CardView cardHome;
        ImageView editpost, deletepost, saveimage;

        CircleImageView photo;


        public ViewHolder(View itemView) {

            super(itemView);

            txt1 = itemView.findViewById(R.id.txt1);
            userName=itemView.findViewById(R.id.userProfileNameTextView);

            cardHome = itemView.findViewById(R.id.cardHome);
            editpost = itemView.findViewById(R.id.editpost);
            deletepost = itemView.findViewById(R.id.deletepost);
            saveimage = itemView.findViewById(R.id.saveImage);

            photo=itemView.findViewById(R.id.profileImage);

        }

    }


    //http request to delete blog
    private void deleteBlog(String resourceId) {
        String url = "https://summary-blog.vercel.app/api/blogs/" + resourceId; // Replace with your API endpoint
        String TAG = "checkasdjfalsdjfalsdfjlasfklasjdlkfajsdklfajsd;faksdjfasdk;fja;sdlfkjasdfkladsjf;lakfj;alsfjasl;djf;l";
        Log.i(TAG, "deleteBlog: " + resourceId);

        StringRequest stringRequest = new StringRequest(
                Request.Method.DELETE,
                url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the DELETE request response (e.g., update your UI)
                        Log.i(TAG, "delete response: " + response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle errors here (e.g., show an error message)
                        Log.i(TAG, "delete error: " + error);
                    }
                }
        );

        // Add the DELETE request to the request queue
        requestQueue.add(stringRequest);


        // Add the DELETE request to the queue
//        requestQueue.add(stringRequest);
    }


    //for updating the post
    private void updateBlog(String resourceId, JSONObject updatedData) {
        String url = "https://summary-blog.vercel.app/api/blogs/" + resourceId; // Replace with your API endpoint

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                updatedData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Handle the PUT request response (e.g., update your UI)
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle errors here (e.g., show an error message)
                    }
                }
        );

        // Add the PUT request to the queue
        requestQueue.add(jsonObjectRequest);
    }

}
