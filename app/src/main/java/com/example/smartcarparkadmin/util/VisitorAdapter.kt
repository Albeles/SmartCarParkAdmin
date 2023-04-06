package com.example.smartcarparkadmin.util
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.smartcarparkadmin.data.Users
import com.example.smartcarparkadmin.databinding.VisitorrvBinding

class VisitorAdapter (


    val fn: (ViewHolder, Users) -> Unit = { _, _ -> }
) : ListAdapter<Users, VisitorAdapter.ViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Users>() {
        override fun areItemsTheSame(a: Users, b: Users) = a.id == b.id
        override fun areContentsTheSame(a: Users, b: Users) = a == b
    }

    class ViewHolder(val binding: VisitorrvBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = VisitorrvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val notificationlist = getItem(position)

        holder.binding.ucarplate.text = notificationlist.carPlate.toString()
        holder.binding.uname.text = notificationlist.password
        holder.binding.usstatus.text = notificationlist.compoundCount.toString()

        fn(holder, notificationlist)

    }
}