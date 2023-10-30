package com.example.bookbrief;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import com.example.bookbrief.ContentModel;


public class FragmentSaved extends Fragment {
    ArrayList<ContentModel> arrDetails=new ArrayList<>();
    RecyclerView recyclerView;
    private RecyclerAdapterSaved adapter;



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
        arrDetails.add(new ContentModel("This is Life","We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us."));
        arrDetails.add(new ContentModel("Love of Life", "We should enjoy every small things that are around us."));
        arrDetails.add(new ContentModel(" My Life", "We should enjoy every small things that are around us."));
        arrDetails.add(new ContentModel("Manifestation", "We should enjoy every small things that are around us."));
        adapter= new RecyclerAdapterSaved(this,arrDetails);
        recyclerView.setAdapter(adapter);
        return view1;
    }
}