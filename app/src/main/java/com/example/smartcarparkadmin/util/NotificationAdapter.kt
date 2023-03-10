package com.example.smartcarparkadmin.util
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.smartcarparkadmin.data.Notification
import com.example.smartcarparkadmin.databinding.NotificationlistBinding

class NotificationAdapter (
    val fn: (ViewHolders, Notification) -> Unit = { _, _ -> }
    ) : ListAdapter<Notification, NotificationAdapter.ViewHolders>(DiffCallback) {

        companion object DiffCallback : DiffUtil.ItemCallback<Notification>() {
            override fun areItemsTheSame(a: Notification, b: Notification) = a.id == b.id
            override fun areContentsTheSame(a: Notification, b: Notification) = a == b
        }

        class ViewHolders(val binding: NotificationlistBinding) : RecyclerView.ViewHolder(binding.root)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolders {
            val binding = NotificationlistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ViewHolders(binding)
        }

    override fun onBindViewHolder(holder: ViewHolders, position: Int) {
            val notificationlist = getItem(position)

            holder.binding.nodate.text = notificationlist.Date.toString()
            holder.binding.NoTitle.text = notificationlist.Title
            holder.binding.NoDesc.text = notificationlist.Description

            fn(holder, notificationlist)
        }
}
