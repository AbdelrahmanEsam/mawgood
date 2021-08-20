package com.iraqsoft.mawgood.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.iraqsoft.mawgood.R
import com.iraqsoft.mawgood.db.model.Branch
import java.util.ArrayList

class BranchesAdapter(val context:Context, private val listener : OnBranchListener) : RecyclerView.Adapter<BranchesAdapter.ViewHolder>() {
    var data: List<Branch> = ArrayList()
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),View.OnClickListener
    {
        init {
            itemView.setOnClickListener(this)
        }

      var branchName: TextView = itemView.findViewById(R.id.branchNameTextView)
      var background:ConstraintLayout = itemView.findViewById(R.id.background)
      var selectImage: ImageView =   itemView.findViewById(R.id.selectedImageView)

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position!=RecyclerView.NO_POSITION)
             listener.onBranchListener(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.branch_bottom_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
       val dataItem =  data[position]
            branchName.text = dataItem.name

            if (dataItem.selected){
               background.background = ContextCompat.getDrawable(context,R.drawable.branch_selected_shape)
                selectImage.visibility = View.VISIBLE
            }else{
                background.background = ContextCompat.getDrawable(context,R.drawable.branch_shape)
                selectImage.visibility = View.GONE
            }
        }

    }

    override fun getItemCount(): Int {
        return  if(data.isNotEmpty())  data.size else 0
    }

    fun setDataAdapter(data:List<Branch>) {
        this.data = data
        notifyDataSetChanged()
    }

    interface OnBranchListener
    {
        fun onBranchListener(position: Int)
    }
}