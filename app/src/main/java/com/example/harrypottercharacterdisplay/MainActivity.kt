package com.example.harrypottercharacterdisplay

import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONArray
import org.json.JSONException

class MainActivity : AppCompatActivity() {
    var harryPotterURL = ""
    private lateinit var potterList: MutableList<PotterCharacters>
    private lateinit var recyclerViewPotter: RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerViewPotter = findViewById(R.id.potter_recycler_view)
        val manager = LinearLayoutManager(this)
        recyclerViewPotter.layoutManager = manager
        recyclerViewPotter.setHasFixedSize(true)

        potterList = mutableListOf<PotterCharacters>()

        val adapter = PotterAdapter(potterList)
        recyclerViewPotter.adapter = adapter

        getPotterImg()
        Log.d("harryPotterURL", "Harry Potter Image url set")

    }

    private fun getPotterImg() {
        val client = AsyncHttpClient()

        client.get("https://hp-api.onrender.com/api/characters", object : JsonHttpResponseHandler() {

            override fun onSuccess(
                statusCode: Int,
                headers: Headers,
                response: JSON
            ) {
                Log.d("Harry Potter", "response successful")
                harryPotterURL = response.jsonArray.toString()

                try {
                    val jsonArray = JSONArray(harryPotterURL)
                for(i in 0 until jsonArray.length()) {
                    val character = jsonArray.getJSONObject(i)
                    val imageUrl = character.getString("image")
                    val name = character.getString("name")
                    val house = character.getString("house")

                    val potty = PotterCharacters(imageUrl, name, house)
                    potterList.add(potty)
                }
                }
                catch (e: JSONException) {
                    e.printStackTrace()
                }

                val potterAdapter = PotterAdapter(potterList)
                recyclerViewPotter.adapter = potterAdapter
                recyclerViewPotter.layoutManager = LinearLayoutManager(this@MainActivity)
                recyclerViewPotter.addItemDecoration(DividerItemDecoration(this@MainActivity,
                    LinearLayoutManager.VERTICAL))
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                throwable: Throwable
            ) {
                val errorResponse = "Request Failed"
                Log.e("Harry Potter Error", errorResponse)
            }
        })
    }

}

