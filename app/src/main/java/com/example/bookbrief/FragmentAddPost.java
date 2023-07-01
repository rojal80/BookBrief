package com.example.bookbrief;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import androidx.fragment.app.Fragment;

import com.example.bookbrief.R;

public class FragmentAddPost extends Fragment {



    public FragmentAddPost() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_addpost, container, false);
    }


}
