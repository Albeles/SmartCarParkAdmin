package com.example.smartcarparkadmin.util
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.smartcarparkadmin.data.Users
import com.example.smartcarparkadmin.databinding.SuspensionBinding
import com.example.smartcarparkadmin.databinding.SuspensionlistBinding

class SuspensionAdapter (


    val fn: (ViewHolders, Users) -> Unit = { _, _ -> }
) : ListAdapter<Users, SuspensionAdapter.ViewHolders>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Users>() {
        override fun areItemsTheSame(a: Users, b: Users) = a.id == b.id
        override fun areContentsTheSame(a: Users, b: Users) = a == b
    }

    class ViewHolders(val binding: SuspensionlistBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolders {
        val binding = SuspensionlistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolders(binding)
    }

    override fun onBindViewHolder(holder: ViewHolders, position: Int) {
        val notificationlist = getItem(position)

        holder.binding.ucarplate.text = notificationlist.carPlate.toString()
        holder.binding.uname.text = notificationlist.name
        holder.binding.usstatus.text = notificationlist.status

        fn(holder, notificationlist)

    }
}