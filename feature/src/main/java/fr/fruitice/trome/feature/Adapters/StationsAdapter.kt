package fr.fruitice.trome.features

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.fruitice.trome.feature.Objects.ratp.Station
import fr.fruitice.trome.feature.R
import kotlinx.android.synthetic.main.stations_recycler_item.view.*

class StationsAdapter(val stations: Array<Station>) : RecyclerView.Adapter<ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClicked(station: Station)
    }

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.stations_recycler_item, parent, false))
    }

    override fun getItemCount(): Int {
        return stations.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val station = stations.get(position)
        holder.tvStationName.text = station.name

        holder.itemView.setOnClickListener { _ -> onItemClickListener?.onItemClicked(station) }
    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val tvStationName = view.station_name
}