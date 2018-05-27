package fr.fruitice.trome.feature.Adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zhukic.sectionedrecyclerview.SectionedRecyclerViewAdapter
import fr.fruitice.trome.feature.Objects.ratp.Line
import fr.fruitice.trome.feature.R
import kotlinx.android.synthetic.main.lines_recycler_item.view.*
import fr.fruitice.trome.feature.Objects.ratp.Lines
import kotlinx.android.synthetic.main.lines_recycler_header.view.*
import android.support.v4.content.ContextCompat



class LinesAdapter(val lines : Lines, val context: Context) : SectionedRecyclerViewAdapter<SubheaderViewHolder, ItemViewHolder>() {

    interface OnItemClickListener {
        fun onItemClicked(type: Lines.Type?, line: Line?)
        fun onSubheaderClicked(position: Int)
    }

    var onItemClickListener: OnItemClickListener? = null

    override fun onPlaceSubheaderBetweenItems(position: Int): Boolean {
        if (position == (lines.getLastPos(Lines.Type.METROS) - 1)) return true // then rer
        if (position == (lines.getLastPos(Lines.Type.RERS) - 1)) return true // then rer
        if (position == (lines.getLastPos(Lines.Type.TRAMWAYS) - 1)) return true // then rer
        //if (position == (lines.getLastPos(Lines.Type.BUS) - 1)) return true // then rer
        return false
    }

    override fun onBindSubheaderViewHolder(subheaderHolder: SubheaderViewHolder, nextItemPosition: Int) {
        if (nextItemPosition == 0) subheaderHolder.tvCategoryName.text = "Subway"
        else if (nextItemPosition == lines.getLastPos(Lines.Type.METROS)) subheaderHolder.tvCategoryName.text = "RER"
        else if (nextItemPosition == lines.getLastPos(Lines.Type.RERS)) subheaderHolder.tvCategoryName.text = "Tramway"
        else if (nextItemPosition == lines.getLastPos(Lines.Type.TRAMWAYS)) subheaderHolder.tvCategoryName.text = "Bus"

        val isSectionExpanded = isSectionExpanded(getSectionIndex(subheaderHolder.adapterPosition))

        if (isSectionExpanded) {
            subheaderHolder.itemView.arrow.setImageDrawable(ContextCompat.getDrawable(subheaderHolder.itemView.context, R.drawable.ic_keyboard_arrow_up_black_24dp))
        } else {
            subheaderHolder.itemView.arrow.setImageDrawable(ContextCompat.getDrawable(subheaderHolder.itemView.context, R.drawable.ic_keyboard_arrow_down_black_24dp))
        }


        subheaderHolder.itemView.setOnClickListener { v -> onItemClickListener?.onSubheaderClicked(subheaderHolder.adapterPosition) }
    }

    override fun getItemSize(): Int {
        return lines.getSize()
    }

    override fun onCreateItemViewHolder(parent: ViewGroup?, viewType: Int): ItemViewHolder {
        return ItemViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.lines_recycler_item, parent, false))
    }

    override fun onCreateSubheaderViewHolder(parent: ViewGroup?, viewType: Int): SubheaderViewHolder {
        return SubheaderViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.lines_recycler_header, parent, false))
    }

    override fun onBindItemViewHolder(holder: ItemViewHolder?, itemPosition: Int) {
        //Setup item view
        val line = lines.getItem(itemPosition)
        holder!!.tvLineName.text = line?.getComputedName()
        holder!!.tvLineDest.text = line?.name

        holder.itemView.setOnClickListener { v -> onItemClickListener?.onItemClicked(lines.getType(itemPosition), line) }
    }
}

class ItemViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val tvLineName = view.line_name
    val tvLineDest = view.line_dest
}

class SubheaderViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    val tvCategoryName = view.line_category
}