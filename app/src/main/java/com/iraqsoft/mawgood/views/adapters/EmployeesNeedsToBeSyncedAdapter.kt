package com.iraqsoft.mawgood.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.iraqsoft.mawgood.R
import com.iraqsoft.mawgood.db.model.EmpNeedsToBeSynced
import java.text.DateFormat

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
            employeeId.text = data[position].time.toString()
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