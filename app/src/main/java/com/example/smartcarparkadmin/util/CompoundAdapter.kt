package com.example.smartcarparkadmin.util
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.smartcarparkadmin.data.CompoundViewModel
import com.example.smartcarparkadmin.data.Compounds
import com.example.smartcarparkadmin.databinding.CompoundlistBinding
import kotlin.time.Duration.Companion.days

class CompoundAdapter (
    val fn: (RecyclerView.ViewHolder, Compounds) -> Unit = { _, _ -> }
    ) : ListAdapter<Compounds, CompoundAdapter.ViewHolder>(DiffCallback) {

        companion object DiffCallback : DiffUtil.ItemCallback<Compounds>() {
            override fun areItemsTheSame(a: Compounds, b: Compounds) = a.id == b.id
            override fun areContentsTheSame(a: Compounds, b: Compounds) = a == b
        }

        class ViewHolder(val binding: CompoundlistBinding) : RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val binding = CompoundlistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val compoundlist = getItem(position)

            holder.binding.Comdate.text = compoundlist.date.time.days.toString()
            holder.binding.carplateNum.text = compoundlist.carplate
            holder.binding.status.text = compoundlist.status

            fn(holder, compoundlist)
        }
    }