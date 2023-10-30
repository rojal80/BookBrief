package com.example.bookbrief;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.bookbrief.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class FragmentLibrary extends Fragment {
    ArrayList<ContentModel> arrDetails=new ArrayList<>();
    RecyclerView recyclerView;
    private RecyclerAdapterLibrary adapter;

    public FragmentLibrary(){
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view1= inflater.inflate(R.layout.fragment_library,container,false);
        recyclerView=view1.findViewById(R.id.recyclerContent);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        arrDetails.add(new ContentModel("This is Life","We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us."));
        arrDetails.add(new ContentModel("Love of Life", "We should enjoy every small things that are around us."));
        arrDetails.add(new ContentModel(" My Life", "We should enjoy every small things that are around us."));
        arrDetails.add(new ContentModel("Manifestation", "We should enjoy every small things that are around us."));
//        arrDetails.add(new ContentModel("Surrounding", "We should enjoy every small things that are around us."));
//        arrDetails.add(new ContentModel("Environment", "We should enjoy every small things that are around us."));
//        arrDetails.add(new ContentModel("JavaScript", "We should enjoy every small things that are around us."));
//        arrDetails.add(new ContentModel("Virtual Reality", "We should enjoy every small things that are around us."));
//        arrDetails.add(new ContentModel("Animation", "We should enjoy every small things that are around us."));
//        arrDetails.add(new ContentModel("Broadcast Agency", "We should enjoy every small things that are around us."));
//        arrDetails.add(new ContentModel("Zombie attack", "We should enjoy every small things that are around us."));
        adapter= new RecyclerAdapterLibrary(this,arrDetails);
        recyclerView.setAdapter(adapter);
        return view1;


    }
}