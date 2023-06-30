package com.example.bookbrief;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class FragmentHome extends Fragment {





    public FragmentHome() {
        // Required empty public constructor
    }

    ArrayList<ContentModel> arrDetails=new ArrayList<>();
RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view1=inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView=view1.findViewById(R.id.recyclerContent);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        arrDetails.add(new ContentModel(R.drawable.ic_launcher_background,"This is Life","We should enjoy every small things that are around us."));
        arrDetails.add(new ContentModel(R.drawable.ic_launcher_background,"This is Life","We should enjoy every small things that are around us."));
        arrDetails.add(new ContentModel(R.drawable.ic_launcher_background,"This is Life","We should enjoy every small things that are around us."));
        arrDetails.add(new ContentModel(R.drawable.ic_launcher_background,"This is Life","We should enjoy every small things that are around us."));
        arrDetails.add(new ContentModel(R.drawable.ic_launcher_background,"This is Life","We should enjoy every small things that are around us."));
        arrDetails.add(new ContentModel(R.drawable.ic_launcher_background,"This is Life","We should enjoy every small things that are around us."));
        arrDetails.add(new ContentModel(R.drawable.ic_launcher_background,"This is Life","We should enjoy every small things that are around us."));
        arrDetails.add(new ContentModel(R.drawable.ic_launcher_background,"This is Life","We should enjoy every small things that are around us."));
        arrDetails.add(new ContentModel(R.drawable.ic_launcher_background,"This is Life","We should enjoy every small things that are around us."));
        arrDetails.add(new ContentModel(R.drawable.ic_launcher_background,"This is Life","We should enjoy every small things that are around us."));
        arrDetails.add(new ContentModel(R.drawable.ic_launcher_background,"This is Life","We should enjoy every small things that are around us."));
        RecyclerAdapterHome adapter=new RecyclerAdapterHome(this,arrDetails);
        recyclerView.setAdapter(adapter);
        return view1;
    }

}