package com.example.bookbrief;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class FragmentProfile extends Fragment {


    Uri photoUrl;

    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    TextView userName, emailid;
    CircleImageView pic;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    Button logoutbtn;


    public FragmentProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        logoutbtn = v.findViewById(R.id.logoutbtn);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(requireContext(), gso);
        logoutbtn.setOnClickListener(view -> signOut());


        emailid = v.findViewById(R.id.emailid);
        userName = v.findViewById(R.id.userName);
        pic = v.findViewById(R.id.pic);
        //logging user details

        String email = currentUser.getEmail();
        String displayName = currentUser.getDisplayName();
        photoUrl = currentUser.getPhotoUrl();


// Load the user's profile picture using Picasso
//        if (photoUrl != null) {
//            Picasso.get().load(photoUrl).into(pic);
//        }

        emailid.setText(email);
        userName.setText(displayName);
        pic.setImageURI(photoUrl);


        return v;

    }


    private void signOut() {
        FirebaseAuth.getInstance().signOut();
        navigateToSignInActivity();
//        gsc.signOut()
//                .addOnCompleteListener(requireActivity(), task -> navigateToSignInActivity());
    }

    private void navigateToSignInActivity() {
        Intent intent = new Intent(requireContext(), SignIn.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
//        requireActivity().finish();
    }

    @Override
    public void onResume() {
        super.onResume();

        // Load the user's profile picture using Picasso
        if (photoUrl != null) {
            Picasso.get().load(photoUrl).into(pic);
        }
    }
}