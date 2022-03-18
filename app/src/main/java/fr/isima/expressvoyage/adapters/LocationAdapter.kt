package fr.isima.expressvoyage.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.load
import com.google.android.material.card.MaterialCardView
import fr.isima.expressvoyage.R
import fr.isima.expressvoyage.models.CountryData
import fr.isima.expressvoyage.utils.CardClickListener


class LocationAdapter(
    dataSet: MutableList<CountryData>,
    private val cardClickListener: CardClickListener? = null) :
    RecyclerView.Adapter<LocationAdapter.ViewHolder>(), Filterable {

    var countriesList: MutableList<CountryData> = dataSet
    var countriesListFiltered: MutableList<CountryData> = dataSet

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val titleView: TextView = view.findViewById(R.id.txtLocationTitle)
        val flagImageView: ImageView = view.findViewById(R.id.imgLocation)
        val cardView: MaterialCardView = view.findViewById(R.id.cardLocation)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_list_location, viewGroup, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.cardView.setOnClickListener {
            cardClickListener?.onCardClick(countriesListFiltered[position])
        }
        viewHolder.titleView.text = countriesListFiltered[position].name
        val imageLoader = ImageLoader
            .Builder(viewHolder.flagImageView.context)
            .componentRegistry {
                add(SvgDecoder(viewHolder.flagImageView.context))
            }
            .build()
        viewHolder.flagImageView.load(countriesListFiltered[position].flagUrl, imageLoader)
    }

    override fun getItemCount() = countriesListFiltered.size

    @SuppressLint("NotifyDataSetChanged")
    fun addData(list: MutableList<CountryData>) {
        countriesList = list
        countriesListFiltered = countriesList
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString()?.lowercase() ?: ""

                // Filtering based on the name of the country
                countriesListFiltered = if (charString.isEmpty()) countriesList else {
                    val filteredList = ArrayList<CountryData>()
                    countriesList.filter {
                        it.name.lowercase().startsWith(charString)
                    }.forEach { filteredList.add(it) }
                    filteredList
                }
                return FilterResults().apply { values = countriesListFiltered }
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                countriesListFiltered = if (results?.values == null)
                    ArrayList()
                else
                    results.values as ArrayList<CountryData>
                notifyDataSetChanged()
            }
        }
    }
}
