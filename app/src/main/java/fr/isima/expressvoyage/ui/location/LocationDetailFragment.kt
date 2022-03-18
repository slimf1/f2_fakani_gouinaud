package fr.isima.expressvoyage.ui.location

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.load
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.LinearProgressIndicator
import fr.isima.expressvoyage.R
import fr.isima.expressvoyage.models.CountryData
import fr.isima.expressvoyage.models.CountryLocation
import fr.isima.expressvoyage.ui.home.HomeFragment

class LocationDetailFragment : Fragment() {

    private var titleTextView: TextView? = null
    private var countryFlagImageView: ImageView? = null
    private var countryData: CountryData? = null
    private var goToMapButton: MaterialButton? = null
    private var countryLocation: CountryLocation? = null
    private var progressBar: LinearProgressIndicator? = null
    private var iso2textView: TextView? = null
    private var currencyTextView: TextView? = null
    private var capitalTextView: TextView? = null
    private var addFavoriteButton: MaterialButton? = null

    companion object {
        fun newInstance() = LocationDetailFragment()
    }

    private lateinit var viewModel: LocationDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.location_detail_fragment, container, false)

        countryData = arguments?.get("countryData") as CountryData?

        progressBar = view.findViewById(R.id.prgLocation)

        titleTextView = view.findViewById(R.id.txtLocationDetailTitle)
        titleTextView?.text = countryData?.name

        iso2textView = view.findViewById(R.id.txtIsoCode)
        iso2textView?.text = String.format(
            resources.getString(R.string.iso_code_text), countryData?.iso2)
        currencyTextView = view.findViewById(R.id.txtCurrency)
        capitalTextView = view.findViewById(R.id.txtCapital)

        countryFlagImageView = view.findViewById(R.id.imgLocationDetail)
        val imageLoader = ImageLoader
            .Builder(requireContext())
            .componentRegistry {
                add(SvgDecoder(requireContext()))
            }
            .build()
        countryFlagImageView?.load(countryData?.flagUrl, imageLoader)

        goToMapButton = view.findViewById(R.id.btnMap)
        goToMapButton?.setOnClickListener { _ ->
            // Navigate to the map with the location of the country
            // in order to add a marker and to zoom at it
            val bundle = bundleOf(
                "countryLocation" to countryLocation
            )
            view?.findNavController()?.navigate(R.id.action_details_to_map, bundle)
        }

        addFavoriteButton = view.findViewById(R.id.btnFavorite)
        addFavoriteButton?.setOnClickListener {
            // Add the country to the favorites (saved with shared preferences)

            val sharedPref = requireActivity()
                .getPreferences(MODE_PRIVATE)
            val favCountries = sharedPref
                .getStringSet(HomeFragment.FAVORITE_COUNTRY_KEY, mutableSetOf())

            val setCopy = favCountries?.toMutableSet()
            val toastMessage = if (favCountries?.contains(countryData?.iso3) == true) {
                setCopy?.remove(countryData?.iso3)
                "Country ${countryData?.name} " +
                        "has been removed from your favorites"
            } else {
                setCopy?.add(countryData?.iso3)
                "Country ${countryData?.name} " +
                        "has been added to your favorites"
            }

            val editor = sharedPref.edit()
            editor.putStringSet(HomeFragment.FAVORITE_COUNTRY_KEY, setCopy)
            editor.apply()

            val toast = Toast.makeText(
                requireContext(),
                toastMessage,
                Toast.LENGTH_SHORT
            )
            toast.show()
        }

        return view
    }

    private fun getLocationPosition() {
        if (viewModel.locationPosition.value == null) {
            goToMapButton?.isVisible = false
            progressBar?.isVisible = true
            viewModel.fetchPosition(requireContext(), countryData?.iso2!!)
        }
        viewModel.locationPosition.observe(viewLifecycleOwner) { position ->
            if (position != null) {
                goToMapButton?.isVisible = true
            }
            progressBar?.isVisible = false
            countryLocation = position
        }
    }

    private fun getCurrency() {
        if (viewModel.countryCurrency.value == null) {
            viewModel.fetchCurrency(requireContext(), countryData?.iso2!!)
        }
        viewModel.countryCurrency.observe(viewLifecycleOwner) {
            if (it != null) {
                currencyTextView?.text = String.format(
                    resources.getString(R.string.currency_text), it.currency)
            }
        }
    }

    private fun getCapital() {
        if (viewModel.countryCapital.value == null) {
            viewModel.fetchCapital(requireContext(), countryData?.iso2!!)
        }
        viewModel.countryCapital.observe(viewLifecycleOwner) {
            if (it != null) {
                capitalTextView?.text = String.format(
                    resources.getString(R.string.capital_text), it.capital)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LocationDetailViewModel::class.java)
        getLocationPosition()
        getCurrency()
        getCapital()
    }

}