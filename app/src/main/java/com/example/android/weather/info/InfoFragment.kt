package com.example.android.weather.info

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.android.weather.R
import com.example.android.weather.database.CityDatabase
import com.example.android.weather.database.CityInfoEntity
import com.example.android.weather.databinding.FragmentInfoBinding
import com.google.android.material.snackbar.Snackbar

class InfoFragment : Fragment(), AdapterView.OnItemSelectedListener {
    private lateinit var viewModel: InfoViewModel
    private lateinit var binding: FragmentInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentInfoBinding.inflate(inflater)
        setHasOptionsMenu(true)

        val application = requireNotNull(this.activity).application
        val database = CityDatabase.getInstance(application).cityDatabaseDao
        val factory = InfoViewModelFactory(database)
        viewModel = ViewModelProvider.create(this, factory)[InfoViewModel::class]

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        var ad: ArrayAdapter<*>
        viewModel.cityList.observe(viewLifecycleOwner, Observer {
            it.let {
                ad = ArrayAdapter(application, android.R.layout.simple_spinner_item, it)
                ad.setDropDownViewResource(
                    android.R.layout.simple_spinner_dropdown_item
                )
                binding.spinner.adapter = ad
                binding.spinner.onItemSelectedListener = this
            }
        })

        binding.searchButton.setOnClickListener {
            viewModel.searchButton()
        }

        viewModel.seasonTemperature.observe(viewLifecycleOwner, Observer {
            Snackbar.make(
                binding.root, resources.getString(R.string.temperature_is)
                        + " " + viewModel.seasonTemperature.value.toString()
                        + viewModel.temperatureCalc.getMeasurementUnits(), Snackbar.LENGTH_LONG
            ).show()
        })

        return binding.root
    }

    @Deprecated("Deprecated in Java")
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
    }

    @Deprecated("Deprecated in Java")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(
            item,
            requireView().findNavController()
        ) || super.onOptionsItemSelected(item)
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        viewModel.getSelectedItem((parent?.selectedItem as CityInfoEntity))
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

}

