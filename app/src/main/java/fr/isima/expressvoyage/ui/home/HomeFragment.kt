package fr.isima.expressvoyage.ui.home

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.LinearProgressIndicator
import fr.isima.expressvoyage.R
import fr.isima.expressvoyage.adapters.LocationAdapter
import fr.isima.expressvoyage.databinding.FragmentHomeBinding
import fr.isima.expressvoyage.models.CountryData
import fr.isima.expressvoyage.utils.CardClickListener

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null
    private var recyclerView: RecyclerView? = null
    private var autoCompleteTextView: AutoCompleteTextView? = null
    private var progressBar: LinearProgressIndicator? = null
    private var retryButton: MaterialButton? = null
    private val binding get() = _binding!!
    private lateinit var locationAdapter: LocationAdapter

    companion object {
        const val FAVORITE_COUNTRY_KEY = "favoriteCountries"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        locationAdapter = LocationAdapter(
            homeViewModel.countries.value as MutableList<CountryData>? ?: ArrayList(),
            object : CardClickListener {
            override fun onCardClick(countryData: CountryData) {
                // Handler when the user clicks on the country card

                val bundle = bundleOf(
                    "countryData" to countryData
                )
                view?.findNavController()?.navigate(R.id.action_home_to_details, bundle)
            }
        })

        recyclerView = root.findViewById(R.id.recyclerViewLocation)

        // We use 4 columns on landscape, 2 otherwise
        val orientation = resources.configuration.orientation
        val spanCount = if (orientation == Configuration.ORIENTATION_LANDSCAPE) 4 else 2
        recyclerView?.layoutManager = GridLayoutManager(activity, spanCount)
        recyclerView?.adapter = locationAdapter

        autoCompleteTextView = root.findViewById(R.id.txtCountrySearch)
        autoCompleteTextView?.addTextChangedListener {
            locationAdapter.filter.filter(it.toString())
        }

        progressBar = root.findViewById(R.id.prgList)

        retryButton = root.findViewById(R.id.btnRetry)
        retryButton?.setOnClickListener {
            getLocations()
        }

        getLocations()

        return root
    }

    private fun getLocations() {

        if (homeViewModel.countries.value == null || homeViewModel.countries.value!!.isEmpty()) {
            homeViewModel.fetchLocations(requireContext())
            progressBar?.isVisible = true
            retryButton?.isVisible = false
        }

        homeViewModel.countries.observe(viewLifecycleOwner) { countries ->
            progressBar?.isVisible = false
            recyclerView?.isVisible = countries.isNotEmpty()
            retryButton?.isVisible = countries.isEmpty()

            if (autoCompleteTextView?.adapter == null) {
                // Adapter for the auto completion
                val autoCompleteAdapter = ArrayAdapter(
                    requireView().context,
                    android.R.layout.simple_dropdown_item_1line,
                    countries.map { location -> location.name }
                )
                autoCompleteTextView?.setAdapter(autoCompleteAdapter)
            }

            locationAdapter.addData(countries as MutableList<CountryData>)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}