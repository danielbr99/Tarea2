package com.example.tarea2

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tarea2.databinding.ActivityMainBinding
import com.google.firebase.appdistribution.gradle.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ChampionListActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_id"
        fun getRetrofit(): Retrofit {
            return Retrofit
                .Builder()
                .baseUrl("https://superheroapi.com/api/5ce252f7d948f2d846ea975b31b6c349/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var retrofit: Retrofit
    private lateinit var adapter: ChampionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        retrofit = getRetrofit()
        initUI()
    }

    private fun initUI(){
        binding.searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchByName(query.orEmpty())
                return false  }
            override fun onQueryTextChange(newText: String?) = false
        })
        adapter = ChampionAdapter()
        binding.rvChampion.setHasFixedSize(true)
        binding.rvChampion.layoutManager = LinearLayoutManager(this)
        binding.rvChampion.adapter = adapter
    }

    private fun searchByName(query: String){
        CoroutineScope(Dispatchers.IO).launch {
            val myResponse: Response<ChampionDataResponse> =
                retrofit.create(ApiService::class.java).getChampions(query)
            if (myResponse.isSuccessful) {
                Log.i("Consulta", "Funciona :)")
                val response = myResponse.body()
                if(response != null){
                    Log.i("Cuerpo de la consulta", response.toString())
                    runOnUiThread {
                        adapter.updateList(response.champions)

                    }

                }
            } else {
                Log.i("Consulta", "No funciona :(")
            }

        }
    }
}