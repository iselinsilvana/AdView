package com.example.adview


import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.adview.adapters.AdAdapter
import com.example.adview.domain.Ad
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private lateinit var recyclerViewAdapter: AdAdapter
    private lateinit var viewModel: AdViewModel
    private lateinit var viewManager: GridLayoutManager
    private lateinit var recyclerView: RecyclerView


    private var showingAllAds = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //to change title of activity
        val actionBar = supportActionBar
        actionBar!!.title = "New Title"


        val viewModelFactory = AdViewModelFactory(application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AdViewModel::class.java)
        viewManager = GridLayoutManager(this, 2)
        recyclerViewAdapter = AdAdapter(viewModel)

        initRecyclerView(emptyList())

        viewModel.allAds.observe(this, Observer {
        })

        viewModel.favouriteAdsList.observe(this, Observer { adListToDisplay ->
            if (!showingAllAds) (recyclerView.adapter as AdAdapter).loadAds(adListToDisplay)

        })

        viewModel.currentAdList.observe(this, Observer { adListToDisplay ->
            Log.i("MainActivity", "this is observer of curentAdList, will now call initRecyclerView")
            (recyclerView.adapter as AdAdapter).loadAds(adListToDisplay)
        })

        swiperefresh.setOnRefreshListener { refresh() }

    }

    private fun initRecyclerView(listOfAds: List<Ad>?) {
        recyclerView = findViewById<RecyclerView>(R.id.recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = recyclerViewAdapter
            (adapter as AdAdapter).loadAds(listOfAds)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.app_menu, menu)
        val menuItemShowAllAds = menu?.findItem(R.id.menu_show_all_ads)
        menuItemShowAllAds?.isVisible = false
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {

        val actionBar = supportActionBar
        val menuItemShowAllAds = menu?.findItem(R.id.menu_show_all_ads)
        val menuItemShowFavs = menu?.findItem(R.id.menu_show_only_favourites)
        if (showingAllAds) actionBar!!.title = "Showing all ads"
        else actionBar!!.title = "Showing favourite ads"
        menuItemShowAllAds?.isVisible = !showingAllAds
        menuItemShowFavs?.isVisible = showingAllAds
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when (item?.itemId) {
            R.id.menu_show_only_favourites -> {
                showingAllAds = false
                viewModel.changeAdList(showingAllAds)
            }
            R.id.menu_show_all_ads -> {
                showingAllAds = true
                viewModel.changeAdList(showingAllAds)
            }
            R.id.menu_refresh -> refresh()
        }
        invalidateOptionsMenu()
        return true
    }

    private fun refresh() {
        if (showingAllAds) viewModel.refresh()
        viewModel.changeAdList(showingAllAds)
        swiperefresh.isRefreshing = false
    }
}
