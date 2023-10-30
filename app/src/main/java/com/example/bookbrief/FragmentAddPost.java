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

import java.util.HashMap;
import java.util.Map;


public class FragmentAddPost extends Fragment {



    public FragmentAddPost() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view1= inflater.inflate(R.layout.fragment_addpost, container, false);
        Button btnPost = view1.findViewById(R.id.btnPost);
        EditText editTitle = view1.findViewById(R.id.editTitle);
        EditText editDescription = view1.findViewById(R.id.editDescription);
        btnPost.setOnClickListener(view -> {
            Log.d("FragmentAddPost", "Post button clicked");
            // Collect the title and description from EditTexts
            String title = editTitle.getText().toString();
            String description = editDescription.getText().toString();
//            Map<String, Object> postDetails = new HashMap<>();
//            postDetails.put("title", title);
//            postDetails.put("description", description);

//            PostServices postServices = new PostServices();
//            boolean success = postServices.addPost((HashMap<String, Object>) postDetails);
//            if (success) {
//                Toast.makeText(getContext(), "Post added to Firestore", Toast.LENGTH_SHORT).show();
//                // Clear the EditText fields after a successful post
//                editTitle.setText("");
//                editDescription.setText("");
//            } else {
//                Toast.makeText(getContext(), "Error adding post to Firestore", Toast.LENGTH_SHORT).show();
//            }



        });
        return view1;
    }


}
