package com.example.android.weather.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.weather.database.CityDatabase
import com.example.android.weather.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSettingsBinding.inflate(inflater)

        val application = requireNotNull(this.activity).application
        val database = CityDatabase.getInstance(application).cityDatabaseDao
        val factory = SettingsViewModelFactory(database)
        val viewModel = ViewModelProvider.create(this, factory)[SettingsViewModel::class.java]

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val adapter = SettingsListAdapter(object : CityActionListener {
            override fun deleteCity(id: Long) {
                viewModel.deleteCity(id)
            }

            override fun changeCity(id: Long) {
                viewModel.changeCity(id)
            }
        })
        binding.allCitiesRecyclerview.layoutManager = LinearLayoutManager(requireContext())
        binding.allCitiesRecyclerview.adapter = adapter
        viewModel.cityList.observe(viewLifecycleOwner, Observer {
            adapter.submitList(viewModel.cityList.value)
        })

        return binding.root
    }

}