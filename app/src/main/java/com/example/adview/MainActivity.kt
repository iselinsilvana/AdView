package com.example.adview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.example.adview.adapters.AdAdapter
import com.example.adview.model.Ad
import com.example.adview.network.RetrofitFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import android.support.v7.widget.LinearLayoutManager



class MainActivity : AppCompatActivity() {
    private lateinit var viewManager: GridLayoutManager
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView(emptyList())

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
                Log.e("REQUEST", "$e")
            }
        }
    }

        private fun initRecyclerView( listOfAds: List<Ad>) {
            viewManager = GridLayoutManager(this, 2)
            viewAdapter = AdAdapter(listOfAds)

            recyclerView = findViewById<RecyclerView>(R.id.recycler_view).apply {
                setHasFixedSize(true)
                layoutManager = viewManager
                adapter = viewAdapter

        }
    }
}
