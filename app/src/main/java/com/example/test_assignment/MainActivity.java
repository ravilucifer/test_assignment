package com.example.test_assignment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.test_assignment.Fragment.CategoryListFragment;
import com.example.test_assignment.Fragment.FeaturedImagesFragment;
import com.example.test_assignment.Fragment.RandomArticleFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    CategoryListFragment categoryListFragment = new CategoryListFragment();
    FeaturedImagesFragment featuredImagesFragment = new FeaturedImagesFragment();
    RandomArticleFragment randomArticleFragment = new RandomArticleFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUI();

    }

    private void initUI() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.articles);

    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.categoryList:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, categoryListFragment)
                        .commit();
                return true;

            case R.id.articles:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, randomArticleFragment)
                        .commit();
                return true;

            case R.id.images:
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flFragment, featuredImagesFragment)
                        .commit();
                return true;
        }
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);

    }
}