package com.iraqsoft.mawgood.presentation.fragments.defineEmplyeeFragment

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.card.MaterialCardView
import com.iraqsoft.mawgood.R
import com.iraqsoft.mawgood.data.model.GetResponseItem


class EmployeesAdapter(val context: Context, private val listener : OnEmployeeListener) : RecyclerView.Adapter<EmployeesAdapter.ViewHolder>() {
    private lateinit var data:List<GetResponseItem>
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener
    {
        init {
            itemView.setOnClickListener(this)
        }

        val employeeName: TextView = itemView.findViewById(R.id.nameTextView)
        val employeeImageView: ImageView =   itemView.findViewById(R.id.employeeImageView)
        val background:MaterialCardView = itemView.findViewById(R.id.backgroundCard)
        val selectImage: ImageView =   itemView.findViewById(R.id.selectImageView)


        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position!= RecyclerView.NO_POSITION)
                listener.onEmployeeListener(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.employee_custom_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            employeeName.text = data[position].displayName
            Glide.with(context).load(data[position].image).circleCrop().
            apply(RequestOptions().placeholder(R.drawable.place_holder)).into(holder.employeeImageView)
            if (data[position].selected){
                Glide.with(context).load(R.drawable.ic_select).into(holder.selectImage)
                background.strokeColor = ContextCompat.getColor(context, R.color.blue)

            }else{
                Glide.with(context).load(R.drawable.search_back_white).into(holder.selectImage)
                background.strokeColor = ContextCompat.getColor(context, R.color.white)
            }
        }

    }

    override fun getItemCount(): Int {
        return  if(data.isNotEmpty())  data.size else 0
    }

    fun setDataAdapter(data:List<GetResponseItem>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun setSelectedItemToDefault(position:Int){
        data[position].selected = false
        notifyItemChanged(position)
        Log.d("abdo","tep")
    }

    interface OnEmployeeListener
    {
        fun onEmployeeListener(position: Int)
    }
}