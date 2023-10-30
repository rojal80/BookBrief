//package com.example.bookbrief.Services;
//
//import android.util.Log;
//
//
//import com.google.firebase.firestore.FirebaseFirestore;
//
//import java.util.HashMap;
//import java.util.Map;
//
//public class PostServices {
//   private FirebaseFirestore db = FirebaseFirestore.getInstance();
//    public boolean addPost(HashMap<String,Object> postDetails){
//// Add a new document with a generated ID
//        db.collection("Post")
//                .add(postDetails)
//                .addOnSuccessListener(documentReference -> Log.d("Postadd", "DocumentSnapshot added with ID: " + documentReference.getId()))
//                .addOnFailureListener(e -> Log.w("Postadd", "Error adding document", e));
//        return false;
//    }
//}
