package com.example.myapplication

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var tvTitle: TextView
    private lateinit var tvCount: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: Adapter
    private var listQuakes = mutableListOf<Feature>()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tvTitle = findViewById(R.id.textViewTitle)
        tvCount = findViewById(R.id.textViewCount)
        recyclerView = findViewById(R.id.recyclerQuakes)

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = Adapter(listQuakes)
        recyclerView.adapter = adapter


    }


    private fun getQuakes(){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(ServiceApi::class.java).getQuakes("significant_month.geojson")
            val response = call.body()
            runOnUiThread {
                if (call.isSuccessful) {
                    val quakes = response?.features
                    quakes?.forEach {
                        listQuakes.add(it)
                    }
                    val title = response?.metadata?.title
                    val count = response?.metadata?.count
                    tvTitle.text = title
                    tvCount.text = "Mostrando $count terremotos"
                }
            }
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /*companion object {
        const val BASE_URL = "https://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/"
        const val ALL_QUAKES_LAST_DAY = "all_day.geojson"
        const val SIGNIFICANT_QUAKES_MONTH = "significant_month.geojson"
    }*/
}