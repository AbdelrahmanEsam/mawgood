package com.iraqsoft.mawgood.views.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.card.MaterialCardView
import com.iraqsoft.mawgood.R
import com.iraqsoft.mawgood.db.model.Branch
import com.iraqsoft.mawgood.db.model.EmpNeedsToBeSynced
import com.iraqsoft.mawgood.db.model.GetResponse
import com.iraqsoft.mawgood.db.model.GetResponseItem
import java.util.ArrayList







class EmployeesNeedsToBeSyncedAdapter(val context: Context) : RecyclerView.Adapter<EmployeesNeedsToBeSyncedAdapter.ViewHolder>() {
    private lateinit var data:List<EmpNeedsToBeSynced>
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {

        val employeeName: TextView = itemView.findViewById(R.id.nameTextView)
        val employeeId: TextView = itemView.findViewById(R.id.idTextView)




    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.need_to_be_synced_custom_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            employeeName.text = data[position].displayName
            employeeId.text = data[position]._id
        }

    }

    override fun getItemCount(): Int {
        return  if(data.isNotEmpty())  data.size else 0
    }

    fun setDataAdapter(data:List<EmpNeedsToBeSynced>) {
        this.data = data
        notifyDataSetChanged()
    }




}