package com.example.bookbrief;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class FragmentHome extends Fragment {





    public FragmentHome() {
        // Required empty public constructor
    }

    ArrayList<ContentModel> arrDetails=new ArrayList<>();
    RecyclerView recyclerView;
    private RecyclerAdapterHome adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        View view1=inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView=view1.findViewById(R.id.recyclerContent);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        arrDetails.add(new ContentModel("This is Life","We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us.We should enjoy every small things that are around us."));
        arrDetails.add(new ContentModel("Love of Life", "We should enjoy every small things that are around us."));
        arrDetails.add(new ContentModel(" My Life", "We should enjoy every small things that are around us."));
        arrDetails.add(new ContentModel("Manifestation", "We should enjoy every small things that are around us."));
        arrDetails.add(new ContentModel("Surrounding", "We should enjoy every small things that are around us."));
        arrDetails.add(new ContentModel("Environment", "We should enjoy every small things that are around us."));
        arrDetails.add(new ContentModel("JavaScript", "We should enjoy every small things that are around us."));
        arrDetails.add(new ContentModel("Virtual Reality", "We should enjoy every small things that are around us."));
        arrDetails.add(new ContentModel("Animation", "We should enjoy every small things that are around us."));
        arrDetails.add(new ContentModel("Broadcast Agency", "We should enjoy every small things that are around us."));
        arrDetails.add(new ContentModel("Zombie attack", "We should enjoy every small things that are around us."));
        adapter=new RecyclerAdapterHome(this,arrDetails);
        recyclerView.setAdapter(adapter);
        return view1;



    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu,menu);
        MenuItem item=menu.findItem(R.id.search_bar);
        SearchView searchView=(SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                adapter.getFilter().filter(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
}
