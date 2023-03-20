package com.example.smartcarparkadmin.util
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.smartcarparkadmin.data.Compounds
import com.example.smartcarparkadmin.databinding.CompoundlistshowBinding
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.time.Duration.Companion.days

class CompoundAdapter (

    val fn: (ViewHolder, Compounds) -> Unit = { _, _ -> }
    ) : ListAdapter<Compounds, CompoundAdapter.ViewHolder>(DiffCallback) {


        companion object DiffCallback : DiffUtil.ItemCallback<Compounds>() {
            override fun areItemsTheSame(a: Compounds, b: Compounds) = a.id == b.id
            override fun areContentsTheSame(a: Compounds, b: Compounds) = a == b
        }

        class ViewHolder(val binding: CompoundlistshowBinding) : RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = CompoundlistshowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val compoundlists = getItem(position)
            @SuppressLint("SimpleDateFormat")
            val dateFormat = SimpleDateFormat("dd/MM/yyyy")
            val date = compoundlists.date

            val formattedDate = dateFormat.format(date)
            holder.binding.Comdate.text = formattedDate
            holder.binding.carplateNum.text = compoundlists.carplate
            holder.binding.status.text = compoundlists.status

            fn(holder, compoundlists)
        }
    }