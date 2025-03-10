package com.example.weatherassistant.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherassistant.Model.HourlyModel
import com.example.weatherassistant.databinding.ViewholderHourlyBinding

class HourlyAdapter(private val items:ArrayList<HourlyModel>)
    :RecyclerView.Adapter<HourlyAdapter.ViewHolder>() {
        private lateinit var context: Context

    class ViewHolder (val binding: ViewholderHourlyBinding)
        :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyAdapter.ViewHolder {
        context = parent.context
        val binding = ViewholderHourlyBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HourlyAdapter.ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.apply {
            hourTxt.text = item.hour
            tempTxt.text = "${item.temp}°"

            val drawableResourceId = holder.itemView.context.resources.getIdentifier(
                item.picPath,"drawable",holder.itemView.context.packageName)

            Glide.with(context)
                .load(drawableResourceId)
                .into(pic)
        }
    }

    override fun getItemCount(): Int = items.size
}