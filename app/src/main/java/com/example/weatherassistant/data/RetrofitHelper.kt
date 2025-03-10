package com.example.weatherassistant.data

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.weatherassistant.Model.CityWeather
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    val baseUrl = "http://api.weatherstack.com/current?access_key=0e5e622167a2eab01d8c10557f7273c9&query=Chittoor"

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            // we need to add converter factory to
            // convert JSON object to Java object
            .build()
    }
}

class ApiCall {
    // This function takes a Context and callback function
    // as a parameter, which will be called
    // when the API response is received.
    fun getWeatherData(context: Context, city: String, callback: (CityWeather) -> Unit) {

        // Create a Retrofit instance with the base URL and
        // a GsonConverterFactory for parsing the response.
        val retrofit: Retrofit = Retrofit.Builder().baseUrl("https://api.weatherstack.com/").addConverterFactory(
            GsonConverterFactory.create()).build()

        // Create an ApiService instance from the Retrofit instance.
        val service: ApiService = retrofit.create<ApiService>(ApiService::class.java)

        // Call the getWeather() method of the ApiService
        // to make an API request.
        val call: Call<CityWeather> = service.getWeather(city)

        // Use the enqueue() method of the Call object to
        // make an asynchronous API request.
        call.enqueue(object : Callback<CityWeather> {
            override fun onResponse(call: Call<CityWeather>, response: Response<CityWeather>) {
                if (response.isSuccessful) {
                    val cityWeather: CityWeather? = response.body()
                    if (cityWeather != null) {
                        callback(cityWeather)
                        Log.d("ApiCall", "API call successful: $cityWeather")
                    } else {
                        Log.e("ApiCall", "Response body is null")
                        Toast.makeText(context, "Response body is null", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("ApiCall", "API call failed: ${response.code()} - $errorBody")
                    Toast.makeText(context, "API call failed: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<CityWeather>, t: Throwable) {
                Log.e("ApiCall", "API call failed with exception", t)
                Toast.makeText(context, "Request Fail", Toast.LENGTH_SHORT).show()
            }
        })
    }
}