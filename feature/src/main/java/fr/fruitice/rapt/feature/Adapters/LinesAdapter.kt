package fr.fruitice.rapt.feature.Adapters

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zhukic.sectionedrecyclerview.SectionedRecyclerViewAdapter
import fr.fruitice.rapt.feature.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.lines_recycler_item.view.*
import fr.fruitice.rapt.feature.Objects.Lines

class LinesAdapter(val lines : Lines, val context: Context) : SectionedRecyclerViewAdapter<SubheaderViewHolder, ItemViewHolder>() {
    override fun onPlaceSubheaderBetweenItems(position: Int): Boolean {
        if (position == 0) return true
        if (position == lines.metros.size) return true // then rer
        return false
    }

    override fun onBindSubheaderViewHolder(subheaderHolder: SubheaderViewHolder, nextItemPosition: Int) {
        //if (nextItemPosition == 0) // header metros
        //else // header rers
        //Setup subheader view
        //nextItemPosition - position of the first item in the section to which this subheader belongs
    }

    override fun getItemSize(): Int {
        return lines.metros.size + lines.rers.size
    }

    override fun onCreateItemViewHolder(parent: ViewGroup?, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.lines_recycler_item, parent, false))
    }

    override fun onCreateSubheaderViewHolder(parent: ViewGroup?, viewType: Int): SubheaderViewHolder {
        return SubheaderViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.lines_recycler_header, parent, false))
    }

    override fun onBindItemViewHolder(holder: ItemViewHolder?, itemPosition: Int) {
        //Setup item view
    }
}

class ItemViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    //val tvLineName = view.line_name
}

class SubheaderViewHolder (view: View) : RecyclerView.ViewHolder(view) {

}