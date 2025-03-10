package com.example.weatherassistant.Activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.Toast
//import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.weatherassistant.Adapter.HourlyAdapter
import com.example.weatherassistant.Adapter.OtherCityAdapter
import com.example.weatherassistant.Model.CityModel
import com.example.weatherassistant.Model.CityWeather
import com.example.weatherassistant.Model.HourlyModel
import com.example.weatherassistant.R
import com.example.weatherassistant.data.ApiCall
import com.example.weatherassistant.data.QuotesApi
import com.example.weatherassistant.data.RetrofitHelper
import com.example.weatherassistant.databinding.ActivityMainBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import android.view.View
import android.view.ViewGroup

class MainActivity : AppCompatActivity() {

    //private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.chipNavigator.setItemSelected(R.id.home, isSelected = true)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        )

        //binding.chipNavigator.setItemSelected(R.id.explorer, isSelected = true)

        binding.chipNavigator.setOnItemSelectedListener { itemId ->
            when (itemId) {
                2131230946 -> {
                    // Do something for the first chip
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    Log.d("MainActivity", "First chip clicked")
                }
                2131230916 -> {
                    // Start the SecondActivity
                    val intent = Intent(this, FutureActivity::class.java)
                    startActivity(intent)
                    Log.d("MainActivity", "Second chip clicked")
                }
            }
        }

        initRecyclerviewHourly()
        initRecyclerOtherCity()
        setNavigation()
        setSearch()
        fetchWeatherData()
    }

    private fun setSearch() {
        binding.searchButton.setOnClickListener {
            Log.d("MainActivity", "Search button clicked!")
            hideKeyboard()
            // Trigger the API call when the searchButton is clicked
            val city = binding.editText.text.toString()
            fetchWeatherData(city)
        }
    }

    private fun fetchWeatherData(city: String = "Bangalore") {
        // Get the name of the city from the user
        showLoader()
        val apiCall = ApiCall()
        apiCall.getWeatherData(this, city) { cityWeather ->
            // Handle the successful API response here (update UI, etc.)
            if(cityWeather.current != null) {
                runOnUiThread {
                    updateUiWithWeatherData(cityWeather)
                    hideLoader()
                }
            } else {
                binding.editText.setText("")
                val myToast = Toast.makeText(this, "City Not found, Try with valid city name", Toast.LENGTH_LONG)
                myToast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 0)
                myToast.show()
                hideLoader()
            }
        }
//        val quotesApi = RetrofitHelper.getInstance().create(QuotesApi::class.java)
//        GlobalScope.launch {
//            val result = quotesApi.getQuotes()
//            if (result != null)
//            // Checking the results
//                Log.d("ayush: ", result.body().toString())
//        }
    }

    private fun hideLoader() {
        (binding.loaderLayout.root as ViewGroup).visibility = View.GONE
    }

    private fun showLoader() {
        (binding.loaderLayout.root as ViewGroup).visibility = View.VISIBLE
    }

    private fun hideKeyboard() {
        // Get the InputMethodManager
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        // Check if a view has focus
        val currentFocus = currentFocus

        // If a view has focus, hide the keyboard
        if (currentFocus != null) {
            imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
        }
    }

    private fun updateUiWithWeatherData(cityWeather: CityWeather) {
        // Update the UI with the weather data
        //Log.d("MainActivity", "City name: ${cityWeather.location.name}")
        Log.d("MainActivity", "temperature: ${cityWeather.current.temperature}")
        // Example of updating TextViews
        binding.cityname.text = "${cityWeather.location.name}, ${cityWeather.location.region}, ${cityWeather.location.country}"
        binding.temperature.text = "${cityWeather.current.temperature}°C"
        binding.windTxt.text = "${cityWeather.current.wind_speed} km/h"
        binding.humidityMainTxt.text = "${cityWeather.current.humidity} %"
        binding.rainTxt.text = "${cityWeather.current.precip} %"

        val imageUrl = cityWeather.current.weather_icons[0]
        // Load the image using Glide
        Glide.with(this)
            .load(imageUrl)
            .into(binding.currentPic)
    }

    private fun setNavigation() {
        binding.nextBtn.setOnClickListener {
            val intent = Intent(this, FutureActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initRecyclerOtherCity() {
        val items: ArrayList<CityModel> = ArrayList()
        items.add(CityModel("Paris", 28, "cloudy", 12, 20, 30))
        items.add(CityModel("Berlin", 29, "sunny", 5, 22, 12))
        items.add(CityModel("Rome", 30, "windy", 30, 25, 50))
        items.add(CityModel("London", 31, "cloudy_2", 20, 20, 35))
        items.add(CityModel("NewYork", 32, "snowy", 8, 8, 7))

        binding.view2.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.view2.adapter = OtherCityAdapter(items)
    }

    private fun initRecyclerviewHourly() {
        val items: ArrayList<HourlyModel> = ArrayList()
        items.add(HourlyModel(hour = "9 pm", temp = 28, picPath = "cloudy"))
        items.add(HourlyModel(hour = "10 pm", temp = 28, picPath = "sunny"))
        items.add(HourlyModel(hour = "11 pm", temp = 28, picPath = "windy"))
        items.add(HourlyModel(hour = "12 pm", temp = 28, picPath = "cloudy_2"))
        items.add(HourlyModel(hour = "1 am", temp = 10, picPath = "snowy"))

        binding.view1.setLayoutManager(
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        )

        binding.view1.adapter = HourlyAdapter(items)
    }
}