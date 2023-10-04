package com.example.bookbrief;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;


public class FragmentProfile extends Fragment {
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
        View v=inflater.inflate(R.layout.fragment_profile, container, false);
        logoutbtn=v.findViewById(R.id.logoutbtn);
        gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN).requestEmail().build();
        gsc= GoogleSignIn.getClient(requireContext(),gso);
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });

        return v;

    }
    private void signOut() {
        gsc.signOut()
                .addOnCompleteListener(requireActivity(), task -> {

                    navigateToSignInActivity();
                });
    }

    private void navigateToSignInActivity() {
        Intent intent = new Intent(requireContext(), SignIn.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}