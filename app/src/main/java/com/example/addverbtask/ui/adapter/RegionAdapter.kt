package com.example.addverbtask.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.addverbtask.data.RegionResponse
import com.example.addverbtask.data.RegionResponseList
import com.example.addverbtask.databinding.ViewCountryBinding

class RegionAdapter(val onCountryClicked: (RegionResponseList) -> Unit ): RecyclerView.Adapter<RegionAdapter.ViewHolder>() {

    lateinit var list: List<RegionResponseList>

    inner class ViewHolder(val binding: ViewCountryBinding) : RecyclerView.ViewHolder(binding.root)

    fun setData(list: List<RegionResponseList>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RegionAdapter.ViewHolder, position: Int) {

        with(holder) {
            with(list[position]) {
                binding.countryImg.load(this.flags.svg)
                binding.countryCapital.text = this.capital[0]
                binding.countryName.text = this.name.common
                binding.parentLayout.setOnClickListener { onCountryClicked(this) }
            }
        }
    }

    override fun getItemCount(): Int = list.size



}