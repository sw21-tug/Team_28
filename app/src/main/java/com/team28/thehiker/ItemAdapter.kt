package com.team28.thehiker
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
//import com.example.cena.R
import kotlinx.android.synthetic.main.steplist_items.view.*

class ItemAdapter(private val mContext: Context, private val listItem: List<ListItem>): ArrayAdapter<ListItem> (mContext,0,listItem){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val layout = LayoutInflater.from(mContext).inflate(R.layout.steplist_items, parent, false)

        val producto = listItem[position ]
        layout.textViewDate.text=producto.date
        layout.textViewSteps.text=producto.steps

        return layout
    }
}

