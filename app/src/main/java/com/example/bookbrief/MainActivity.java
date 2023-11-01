package com.example.bookbrief;

import static androidx.core.content.ContentProviderCompat.requireContext;
import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bnView;
    FloatingActionButton btnOpenDialog;
    private RequestQueue requestQueue;
    private RecyclerAdapterHome adapter;
    ArrayList<ContentModel> arrDetails = new ArrayList<>();


    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bnView = findViewById(R.id.bnView);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new FragmentHome())
                    .commit();
        }





        bnView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    loadFrag(new FragmentHome(), false);

                } else if (id == R.id.nav_addpost) {
                    loadFrag(new FragmentAddPost(), false);

                } else if (id == R.id.nav_library) {
                    loadFrag(new FragmentLibrary(), false);

                } else if (id == R.id.nav_savepost) {
                    loadFrag(new FragmentSaved(), false);

                } else {
                    loadFrag(new FragmentProfile(), true);

                }
                return true;
            }
        });
        bnView.setSelectedItemId(R.id.nav_home);
    }

    public void loadFrag(Fragment fragment, Boolean flag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (flag)
            ft.replace(R.id.container, fragment, "HomeFragmentTag");
        else
            ft.replace(R.id.container, fragment);
        ft.commit();

    }

    // Create a method to make a Volley GET request






}