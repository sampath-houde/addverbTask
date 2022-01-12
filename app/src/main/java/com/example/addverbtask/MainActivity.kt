package com.example.addverbtask

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.addverbtask.api.RegionApi
import com.example.addverbtask.data.RegionOnlineRepo
import com.example.addverbtask.data.RegionResponse
import com.example.addverbtask.data.RegionResponseList
import com.example.addverbtask.database.RegionDatabase
import com.example.addverbtask.database.entities.RegionOffline
import com.example.addverbtask.database.repo.RegionOfflineRepo
import com.example.addverbtask.databinding.ActivityMainBinding
import com.example.addverbtask.ui.activity.CountryDetailActivity
import com.example.addverbtask.ui.adapter.RegionAdapter
import com.example.addverbtask.ui.viewmodel.RegionViewModel
import com.example.addverbtask.utils.*
import com.task.krishinetwork.utils.Constants

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: RegionViewModel
    private lateinit var adapter: RegionAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        initViewModel()
        initAdapter()

        setContentView(binding.root)
    }

    private fun initAdapter() {
        adapter = RegionAdapter(::onCountryClicked)
        binding.recyclerView.setHasFixedSize(true)
    }

    override fun onStart() {
        super.onStart()

        viewModel.readAllCountries.observe(this, {
            if (it == null) {
                if(isInternetAvailable(this)) observeCountriesOnline()
                else toast(this,"Connection Error")
            } else {
                adapter.setData(it.reponse)
            }
        })
    }

    private fun observeCountriesOnline() {
        binding.progress.visibility = View.VISIBLE
        viewModel.getCountriesFromRegionApi()
        viewModel.countriesListOnline.observe(this, {response->
            binding.progress.visibility = View.GONE
            when(response) {
                is ApiResponseHandler.Success -> {
                    adapter.setData(response.value)
                    saveDataToDatabase(response.value)
                }

                is ApiResponseHandler.Failure -> {
                    toast(this, "Error")
                }
            }
        })
    }

    private fun saveDataToDatabase(value: RegionResponse) {
        viewModel.addCountries(RegionOffline(0, value))
    }

    private fun onCountryClicked(value: RegionResponseList) {
        val intent = Intent(this, CountryDetailActivity::class.java)
        intent.putExtra(Constants.NAME, value.name.common)
        intent.putExtra(Constants.CAPITAL, value.capital[0])
        intent.putExtra(Constants.FLAG, value.flags.svg)
        intent.putExtra(Constants.BORDERS, value.borders[0])
        intent.putExtra(Constants.REGION, value.region)
        intent.putExtra(Constants.SUB_REGION, value.subregion)
        intent.putExtra(Constants.POPULATION, value.population)
        startActivity(intent)
    }

    private fun initViewModel() {
        val api = RetrofitInstance.buildApi(RegionApi::class.java)
        val onlineRepo = RegionOnlineRepo(api)
        val dao = RegionDatabase.getDatabase(application).regionDao()
        val offlineRepo = RegionOfflineRepo(dao)
        val factory = ViewModelFactory(onlineRepo, offlineRepo)
        viewModel = ViewModelProvider(this, factory).get(RegionViewModel::class.java)
    }
}