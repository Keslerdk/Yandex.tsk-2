package com.example.yandextsk2.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.widget.SearchView;

import com.example.yandextsk2.R;
import com.example.yandextsk2.data.db.entity.StockSymbol;
import com.example.yandextsk2.data.network.ApiCall;
import com.example.yandextsk2.ui.recyclerViews.SearchRecyclerViewAdapter;
import com.example.yandextsk2.ui.recyclerViews.StockItem;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    Toolbar searchToolbar;
    SearchViewModel mViewModel;

    SearchRecyclerViewAdapter searchAdapter;
    RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    List<StockItem> searchList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);

        searchToolbar = findViewById(R.id.searchToolbar);

        setSupportActionBar(searchToolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        new ApiCall(mViewModel).firstApiCall();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.search_bar).getActionView();
        searchView.setIconified(false);

//        MenuItem searchItem = menu.findItem(R.id.search_bar);
//        SearchView searchView = (SearchView) searchItem.getActionView();

//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        if(null!=searchManager ) {
//            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//        }
//        searchView.setIconifiedByDefault(false);

        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        if(query != null){
            searchDatabase(query);
        }
        return true;
    }

    private void searchDatabase(String query) {
        mViewModel.searchDatabase(query).observe(this, new Observer<List<StockSymbol>>() {
            @Override
            public void onChanged(List<StockSymbol> stockSymbols) {
                searchList = new ArrayList<>();
                for (StockSymbol stockSymbol : stockSymbols) {
                    searchList.add(new StockItem(R.drawable.ic_group ,stockSymbol.getSymbol(),
                            stockSymbol.getDescription(), "80977", "0",
                            false));
                }

                recyclerView = findViewById(R.id.searchRecyclerView);
                recyclerView.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(getBaseContext());
                searchAdapter = new SearchRecyclerViewAdapter(searchList);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(searchAdapter);
            }
        });
    }
}