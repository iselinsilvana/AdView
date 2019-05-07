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
    private lateinit var viewModel: AdViewModel
    private lateinit var viewManager: GridLayoutManager
    private lateinit var recyclerView: RecyclerView
    // Er det best å ha denne her, eller i onCreate?
    private var showingAllAds = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // val dataSource = AdsRepository(application)
        //get vm to fetch the datasource
        //observe vievmodel
        val viewModelFactory = AdViewModelFactory(application)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AdViewModel::class.java)
        viewManager = GridLayoutManager(this, 2)

        viewModel.allAds.observe(this, Observer { adList ->
            Log.i("TEST", "Init new ads layout")
            initRecyclerView(adList) })

/*
        getViewModel().getAdListResponse().observe(this, adListResponse -> {
                Timber.i(“Response received ->” + adListResponse.size);
                this.adListResponse = adListResponse;
                initializeView();//initialise all the views
            });
*/

    }

    fun initRecyclerView(listOfAds: List<Ad>?) {
        recyclerView = findViewById<RecyclerView>(R.id.recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            val adapter = AdAdapter(viewModel)
            recycler_view.adapter = adapter
            adapter.loadAds(listOfAds)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.app_menu, menu)
        val menuItemShowAllAds = menu?.findItem(R.id.show_all_ads)
        val menuItemShowFavs = menu?.findItem(R.id.show_only_favourites)
        menuItemShowAllAds?.setVisible(!showingAllAds)
        menuItemShowFavs?.setVisible(showingAllAds)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {

        val menuItemShowAllAds = menu?.findItem(R.id.show_all_ads)
        val menuItemShowFavs = menu?.findItem(R.id.show_only_favourites)
        if (showingAllAds) actionBar?.title = "Showing all ads"
        else actionBar?.title = "Showing favourite ads"
        menuItemShowAllAds?.setVisible(!showingAllAds)
        menuItemShowFavs?.setVisible(showingAllAds)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        updateMenuDisplay()
        return true
    }

    private fun updateMenuDisplay() {
        showingAllAds = !showingAllAds
        invalidateOptionsMenu()
    }
/*
        val adapter = AdAdapter(viewModel)
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
                adapter = viewAdapter

        }
    }*/

}
