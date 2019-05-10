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


class MainActivity : AppCompatActivity() {
    private lateinit var recyclerViewAdapter: AdAdapter
    private lateinit var viewModel: AdViewModel
    private lateinit var viewManager: GridLayoutManager
    private lateinit var recyclerView: RecyclerView
    // Er det best å ha denne her, eller i onCreate?
    private var showingAllAds = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //to change title of activity
        val actionBar = supportActionBar
        actionBar!!.title = "New Title"


        // val dataSource = AdsRepository(application)
        //get vm to fetch the datasource
        //observe vievmodel
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
            Log.i("TEST", "this is observer, will now call initRecyclerView")
            (recyclerView.adapter as AdAdapter).loadAds(adListToDisplay)
        })

    }

    private fun initRecyclerView(listOfAds: List<Ad>?) {
        Log.i("TEST!", "initRecyclerView called")
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
        //val menuItemShowAllAds = menu?.findItem(R.id.show_all_ads)
        val menuItemShowFavs = menu?.findItem(R.id.show_only_favourites)
        //menuItemShowAllAds?.isVisible = !showingAllAds
        menuItemShowFavs?.isVisible
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {

        val actionBar = supportActionBar
        val menuItemShowAllAds = menu?.findItem(R.id.show_all_ads)
        val menuItemShowFavs = menu?.findItem(R.id.show_only_favourites)
        if (showingAllAds) actionBar!!.title = "Showing all ads"
        else actionBar!!.title = "Showing favourite ads"
        menuItemShowAllAds?.isVisible = !showingAllAds
        menuItemShowFavs?.isVisible = showingAllAds
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        updateMenuDisplay()
        viewModel.changeAdList(showingAllAds)
        Log.i("TEST main", "selscted to view other list. the new list has size ${viewModel.currentAdList.value?.size}")
        initRecyclerView(viewModel.currentAdList.value)
        return true
    }

    private fun updateMenuDisplay() {
        showingAllAds = !showingAllAds
        invalidateOptionsMenu()
    }
/*
        val recyclerViewAdapter = AdAdapter(viewModel)
        recycler_view.layoutManager = GridLayoutManager(this, 2)
*/

      //  initRecyclerView(emptyList())

      //  viewModel.allAds
/*
        val service = RetrofitFactory.makeRetrofitService()
        CoroutineScope(Dispatchers.IO).launch {
            val request = service.getAds()
            try {
                val response = request.await()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        response.body()?.let { initRecyclerView(it.adList) }
                    } else {
                        // toast("Error network operation failed with ${response.code()}") <-- finn ut koffor toast ikkje blei godkjent
                        Log.e("REQUEST","Error network operation failed with ${response.code()}" )
                    }
                }
            } catch (e: HttpException) {
                Log.e("REQUEST", "Exception ${e.message}")
            } catch (e: Throwable) {
                Log.e("REQUEST", "Exception ${e.message}")
            }
        }
        */

/*
        private fun initRecyclerView( listOfAds: List<Ad>) {
            viewManager = GridLayoutManager(this, 2)
            viewAdapter = AdAdapter(listOfAds)

            recyclerView = findViewById<RecyclerView>(R.id.recycler_view).apply {
                setHasFixedSize(true)
                layoutManager = viewManager
                recyclerViewAdapter = viewAdapter

        }
    }*/

}
