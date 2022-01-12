package com.example.addverbtask.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.addverbtask.R
import com.example.addverbtask.databinding.ActivityCountryDetailBinding

class CountryDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCountryDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCountryDetailBinding.inflate(layoutInflater)

        setContentView(binding.root)
    }
}