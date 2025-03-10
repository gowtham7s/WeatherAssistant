package com.example.weatherassistant.Activity

import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weatherassistant.Adapter.FutureAdapter
import com.example.weatherassistant.Adapter.OtherCityAdapter
import com.example.weatherassistant.Model.FutureModel
import com.example.weatherassistant.R
import com.example.weatherassistant.databinding.ActivityFutureBinding

class FutureActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFutureBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityFutureBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.chipNavigator.setItemSelected(R.id.home, isSelected = true)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
        )

        initRecyclerviewFuture()
        setVariable()
    }

    private fun setVariable() {
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun initRecyclerviewFuture() {
        val items: ArrayList<FutureModel> = ArrayList()
        items.add(FutureModel("Sun",  "snowy", "strom", 33, 30))
        items.add(FutureModel("Mon", "cloudy", "cloudy", 22, 12))
        items.add(FutureModel("Tue", "windy", "windy", 25, 19))
        items.add(FutureModel("Wed", "cloudy_sunny", "Cloudy_sunny", 20, 18))
        items.add(FutureModel("Thu", "snowy", "Sunny", 22, 12))
        items.add(FutureModel("Fri",  "windy", "Sunny", 35, 25))
        items.add(FutureModel("Sat", "rainy", "Rainy", 16, 7))

        binding.view3.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.view3.adapter = FutureAdapter(items)
    }
}