package com.example.smartcarparkadmin.util
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.smartcarparkadmin.data.User
import com.example.smartcarparkadmin.databinding.SuspensionBinding
import com.example.smartcarparkadmin.databinding.SuspensionlistBinding

class SuspensionAdapter (
    val fn: (ViewHolders, User) -> Unit = { _, _ -> }
) : ListAdapter<User, SuspensionAdapter.ViewHolders>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(a: User, b: User) = a.id == b.id
        override fun areContentsTheSame(a: User, b: User) = a == b
    }

    class ViewHolders(val binding: SuspensionlistBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolders {
        val binding = SuspensionlistBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolders(binding)
    }

    override fun onBindViewHolder(holder: ViewHolders, position: Int) {
        val notificationlist = getItem(position)

        holder.binding.Tcarplate.text = notificationlist.carPlate.toString()
        holder.binding.Tname.text = notificationlist.name
        holder.binding.Tsstatus.text = notificationlist.status

        fn(holder, notificationlist)

    }
}