package fr.isima.expressvoyage.ui.map

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.data.geojson.GeoJsonLayer
import fr.isima.expressvoyage.R
import fr.isima.expressvoyage.models.CountryLocation
import fr.isima.expressvoyage.ui.home.HomeFragment

class MapsFragment : Fragment() {

    private val callback = OnMapReadyCallback { googleMap ->
        val sharedPref = requireActivity()
            .getPreferences(Context.MODE_PRIVATE)
        val favoriteCountries = sharedPref
            .getStringSet(HomeFragment.FAVORITE_COUNTRY_KEY, mutableSetOf())

        // Country geometry data
        val layer = GeoJsonLayer(googleMap, R.raw.countries_geometry, context)
        val style = layer.defaultPolygonStyle
        style.fillColor = Color.parseColor(resources.getString(R.color.favorite_color as Int))
        style.strokeWidth = 2f

        // We don't show the layers of non favorite countries
        val favoriteCountriesFeatures = layer.features.filter {
            !favoriteCountries?.contains(it.id)!!
        }
        for(feature in favoriteCountriesFeatures) {
            layer.removeFeature(feature)
        }
        layer.addLayerToMap()

        if (arguments != null && requireArguments().containsKey("countryLocation")) {
            // Get the location from the arguments then zoom to it and add a marker on the map
            val location = requireArguments().get("countryLocation") as CountryLocation
            val countryLocationLatLng = LatLng(location.lat, location.long)
            googleMap.addMarker(
                MarkerOptions().position(countryLocationLatLng).title("Marker in ${location.name}")
            )
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(countryLocationLatLng))
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(countryLocationLatLng, 3f))
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}