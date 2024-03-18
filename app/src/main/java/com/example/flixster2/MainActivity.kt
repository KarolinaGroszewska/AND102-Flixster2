package com.example.flixster2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import okhttp3.Headers
import kotlin.coroutines.resumeWithException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL ,false)
        requestShows()
    }

    private suspend fun fetchShows(): ArrayList<TVShow>? = suspendCancellableCoroutine { continuation ->
        try {
            val client = AsyncHttpClient()
            val params = RequestParams()
            val TVItems = arrayListOf<TVShow>()
            client.get(
                "https://api.themoviedb.org/3/trending/tv/day?language=en-US&api_key=$apiKey",
                params, object : JsonHttpResponseHandler() {
                    override fun onSuccess(
                        statusCode: Int,
                        headers: Headers,
                        json: JSON
                    ) {
                        val jsonArray = json.jsonObject.getJSONArray("results")
                        for (i in 0 until jsonArray.length()){
                            val item = jsonArray.getJSONObject(i)
                            Log.e("item", item.toString())
                            val tv = TVShow(
                                item.getString("poster_path"),
                                item.getString("original_name"),
                                item.getString("overview"),
                                item.getString("first_air_date"),
                                item.getString("original_language"),
                                item.getString("vote_average")
                            )
                            TVItems.add(tv)
                            Log.e("TV", TVItems.toString())
                        }
                        continuation.resume(TVItems, null)
                    }

                    override fun onFailure(
                        statusCode: Int,
                        headers: Headers?,
                        errorResponse: String,
                        t: Throwable?
                    ) {
                        continuation.resume(null, null)
                    }
                })
        } catch (e: Exception) {
            continuation.resumeWithException(e)
        }
    }

    private fun updateUI(result: ArrayList<TVShow>){
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = AdapterClass(result)
    }

    private fun requestShows() {
        GlobalScope.launch(Dispatchers.Main) {
            val TVItems = fetchShows()
            if (TVItems != null) {
                updateUI(TVItems)
            }
        }
    }
}




//class MainActivity : AppCompatActivity() {
//
//    private val apiKey: String = "a07e22bc18f5cb106bfe4cc1f83ad8ed"
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var dataList: ArrayList<TVShow>
//    private lateinit var myAdapter: AdapterClass
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        recyclerView = findViewById(R.id.recyclerView)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.setHasFixedSize(true)
//
//        dataList = arrayListOf<TVShow>()
//        getData()
//
//        recyclerView.adapter = myAdapter
//
//        myAdapter.onItemClick = {
//            val intent = Intent(this, DetailActivity::class.java)
//            startActivity(intent)
//        }
//        searchView.clearFocus()
//        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                searchView.clearFocus()
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                searchList.clear()
//                val searchText = newText!!.lowercase()
//                if (searchText.isNotEmpty()){
//                    dataList.forEach{
//                        if (it.dataTitle.lowercase().contains(searchText)) {
//                            searchList.add(it)
//                        }
//                    }
//                    recyclerView.adapter!!.notifyDataSetChanged()
//                } else {
//                    searchList.clear()
//                    searchList.addAll(dataList)
//                    recyclerView.adapter!!.notifyDataSetChanged()
//                }
//                return false
//            }
//
//        })
//
//    }
//
//    private suspend fun fetchShows(): MutableList<TVShow>? = suspendCancellableCoroutine { continuation ->
//        try {
//            val client = AsyncHttpClient()
//            val params = RequestParams()
//            val tvShows = mutableListOf<TVShow>()
//            client.get(
//                "https://api.themoviedb.org/3/trending/tv/day?language=en-US&api_key=$apiKey",
//                params, object : JsonHttpResponseHandler() {
//                    override fun onSuccess(
//                        statusCode: Int,
//                        headers: Headers,
//                        json: JSON
//                    ) {
//                        val jsonArray = json.jsonObject.getJSONArray("results")
//                        for (i in 0 until jsonArray.length()){
//                            val item = jsonArray.getJSONObject(i)
//                            val show = TVShow(
//                                item.getString("poster_path"),
//                                item.getString("original_name"),
//                                item.getString("overview"),
//                                item.getString("first_air_date"),
//                                item.getString("original_language"),
//                                item.getString("origin_country")
//                            )
//                            Log.e("show", show.toString())
//                            tvShows.add(show)
//                        }
//                        continuation.resume(tvShows, null)
//                    }
//
//                    override fun onFailure(
//                        statusCode: Int,
//                        headers: Headers?,
//                        errorResponse: String,
//                        t: Throwable?
//                    ) {
//                        continuation.resume(null, null)
//                    }
//                })
//        } catch (e: Exception) {
//            continuation.resumeWithException(e)
//        }
//    }
//
//
//    private fun getData(){
//        GlobalScope.launch(Dispatchers.Main) {
//            val tvShows = fetchShows()
//            if (tvShows != null) {
//                searchList.addAll(tvShows)
//                recyclerView.adapter = AdapterClass(searchList)
//            }
//        }
//    }
//}
