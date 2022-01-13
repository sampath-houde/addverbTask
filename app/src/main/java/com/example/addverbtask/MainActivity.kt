package com.example.addverbtask

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.task.krishinetwork.utils.Constants

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: RegionViewModel
    private lateinit var adapter: RegionAdapter
    private lateinit var regionOffline: RegionOffline

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)


        initViewModel()
        initAdapter()
        setUpToolBar()

        binding.swipeRefresh.setOnRefreshListener { observeCountriesOnline() }

        setContentView(binding.root)
    }

    private fun setUpToolBar() {
        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.delete -> {
                    showDeleteDialog()
                    true
                }
                else -> false
            }
        }
    }

    private fun showDeleteDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Delete")
            .setMessage("Do you want to delete data?")
            .setPositiveButton("No") {dialog, _ ->
                dialog.dismiss()
            }
            .setNegativeButton("Yes") { _ , _->
                viewModel.deleteCountries(regionOffline)
            }
            .setCancelable(false)
            .show()

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
                regionOffline = it
                binding.recyclerView.adapter = adapter
                adapter.setData(it.reponse)
            }
        })
    }

    private fun observeCountriesOnline() {
        binding.swipeRefresh.isRefreshing = true
        viewModel.getCountriesFromRegionApi()
        viewModel.countriesListOnline.observe(this, {response->
            binding.swipeRefresh.isRefreshing = false
            when(response) {
                is ApiResponseHandler.Success -> {
                    binding.recyclerView.adapter = adapter
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
        intent.putExtra(Constants.NAME, value.name?.common)
        intent.putExtra(Constants.CAPITAL, value.capital?.let{it[0]})
        intent.putExtra(Constants.FLAG, value.flags?.png)
        intent.putExtra(Constants.BORDERS, value.borders?.let{it[0]})
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