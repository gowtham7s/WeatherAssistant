package com.example.weatherassistant.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherassistant.Model.FutureModel
import com.example.weatherassistant.databinding.ViewholderFutureBinding

class FutureAdapter(private val items: ArrayList<FutureModel>):
        RecyclerView.Adapter<FutureAdapter.Viewholder>() {
            private lateinit var context: Context

    class Viewholder (val binding: ViewholderFutureBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewholder {
        context = parent.context
        val binding = ViewholderFutureBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {
        val item = items[position]
        holder.binding.apply {
            dayTxt.text = item.day
            statusTxt.text = "${item.status}"
            lowTxt.text = "${item.lowTemp}°"
            highTxt.text = "${item.highTemp}°"

            val drawableResourceId = holder.itemView.context.resources.getIdentifier(
                item.picPath,"drawable",holder.itemView.context.packageName)

            Glide.with(context)
                .load(drawableResourceId)
                .into(pic)

        }
    }

    override fun getItemCount(): Int = items.size

}