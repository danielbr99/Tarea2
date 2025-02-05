package com.example.tarea2

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tarea2.databinding.ActivityMainBinding
import com.google.firebase.appdistribution.gradle.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response as OkHttpResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException


class ChampionListActivity : AppCompatActivity() {

    companion object {

        class AuthInterceptor(private val apiKey: String) : Interceptor {
            override fun intercept(chain: Interceptor.Chain): OkHttpResponse { // Correct Response type
                val request = chain.request().newBuilder()
                    .addHeader("X-Riot-Token", apiKey)
                    .build()
                return chain.proceed(request)
            }
        }

        fun client(): OkHttpClient {
            return OkHttpClient
                .Builder()
                .addInterceptor(AuthInterceptor("RGAPI-2402bdfd-4608-4a34-b199-c90dc2b3e5fe")) //   AÑADIR AQUI LA NUEVA API KEY
                .build()
        }

        fun getRetrofit(): Retrofit {
            return Retrofit
                .Builder()
                .baseUrl("https://ddragon.leagueoflegends.com")
                .client(client())
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

    private fun initUI() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchByName(query.orEmpty())
                return false
            }

            override fun onQueryTextChange(newText: String?) = false
        })
        adapter = ChampionAdapter(Champion("","",ChampionImageResponse("", "", "", 0, 0, 0, 0), emptyList(), "", emptyList()))
        binding.rvChampion.setHasFixedSize(true)
        binding.rvChampion.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.rvChampion.adapter = adapter
    }

    private fun searchByName(query: String) {
        CoroutineScope(Dispatchers.IO).launch {

            val query2 = query.substring(0,1).uppercase() + query.substring(1)

            try {
                val myResponse: Response<ChampionItemResponse> =
                    retrofit.create(com.example.tarea2.ApiService::class.java).getChampions(query2)
                val url = myResponse.raw().request().url().toString() //Get the actual url

                val response = myResponse.body()
                if (myResponse.isSuccessful) {

                    if (response != null) {
                        Log.i("Cuerpo de la consulta", response.toString())
                        println("http://ddragon.leagueoflegends.com/cdn/img/champion/splash/"+response.championDataList.getValue(query2).name + "_" + response.championDataList.getValue(query2).skins[1].num + ".jpg")
                        runOnUiThread {
                            adapter.updateList(response.championDataList.getValue(query2))
                        }
                    }
                } else {
                    // Handle error
                    val errorMessage = when (myResponse.code()) {
                        400 -> "Bad Request"
                        401 -> "Unauthorized"
                        403 -> "Forbidden"
                        404 -> "Not Found"
                        500 -> "Internal Server Error"
                        else -> "Unknown Error: ${myResponse.code()}"
                    }
                    println("Error: $errorMessage") // Or log it, or throw an exception
                }


            } catch (e: IOException) {
                println("Network Error: ${e.message}") // Handle network errors
                null // Return null or throw an exception
            } catch (e: Exception) { // Catch any other type of exceptions
                println("An unexpected error occurred: ${e.message}")
                null // Return null or throw an exception
            }

        }
    }


}


