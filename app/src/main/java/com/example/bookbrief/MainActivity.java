package com.example.bookbrief;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
BottomNavigationView bnView;
FloatingActionButton btnOpenDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bnView=findViewById(R.id.bnView);


        bnView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();
                if(id==R.id.nav_home){
                    loadFrag(new FragmentHome(),false);

                }else if(id==R.id.nav_search){
                    loadFrag(new FragmentSearch(),false);

                }else if(id==R.id.nav_library){
                    loadFrag(new FragmentLibrary(),false);

                }else{
                    loadFrag(new FragmentProfile(),true);

                }
                return true;
            }
        });
        bnView.setSelectedItemId(R.id.nav_profile);
    }
    public void loadFrag(Fragment fragment,Boolean flag){
        FragmentManager fm=getSupportFragmentManager();
        FragmentTransaction ft=fm.beginTransaction();
        if(flag)
        ft.add(R.id.container,fragment);
        else
            ft.replace(R.id.container,fragment);
        ft.commit();

    }
}