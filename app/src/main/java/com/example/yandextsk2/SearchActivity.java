package com.example.yandextsk2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

public class SearchActivity extends AppCompatActivity {

    Toolbar searchToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchToolbar = findViewById(R.id.searchToolbar);
        setSupportActionBar(searchToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

//        SearchView searchView = (SearchView) menu.findItem(R.id.search_bar).getActionView();
//        searchView.setIconified(false);

        MenuItem searchItem = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) searchItem.getActionView();

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if(null!=searchManager ) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }
        searchView.setIconifiedByDefault(false);
        return super.onCreateOptionsMenu(menu);
    }
}