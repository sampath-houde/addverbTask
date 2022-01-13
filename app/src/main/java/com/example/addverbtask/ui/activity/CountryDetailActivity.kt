package com.example.addverbtask.ui.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.FitCenter
import com.example.addverbtask.R
import com.example.addverbtask.databinding.ActivityCountryDetailBinding
import com.task.krishinetwork.utils.Constants

class CountryDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCountryDetailBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCountryDetailBinding.inflate(layoutInflater)

        val countryName = intent.getStringExtra(Constants.NAME)
        val countryCapital = intent.getStringExtra(Constants.CAPITAL)
        val countryRegion = intent.getStringExtra(Constants.REGION)
        val countrySubRegion = intent.getStringExtra(Constants.SUB_REGION)
        val countryBorders = intent.getStringExtra(Constants.BORDERS)
        val countryPopulation = intent.getIntExtra(Constants.POPULATION, -1)
        val flag = intent.getStringExtra(Constants.FLAG)

        Glide.with(binding.flag)
            .load(flag)
            .placeholder(R.drawable.ic_baseline_broken_image_24)
            .transform(FitCenter())
            .into(binding.flag)

        binding.name.text = "Country: $countryName"
        binding.capital.text = "Capital: $countryCapital"
        binding.borders.text = "Border: $countryBorders"
        binding.region.text = "Region: $countryRegion"
        binding.subRegion.text = "Sub-Region: $countrySubRegion"
        binding.population.text = "Population: $countryPopulation"

        setContentView(binding.root)
    }
}