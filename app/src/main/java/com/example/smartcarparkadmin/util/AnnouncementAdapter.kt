package com.example.smartcarparkadmin.util
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.smartcarparkadmin.data.uNotification
import com.example.smartcarparkadmin.databinding.AnnouncementlistrvBinding
import java.text.SimpleDateFormat

class AnnouncementAdapter  (
    val fn: (ViewHolder, uNotification) -> Unit = { _, _ -> }
) : ListAdapter<uNotification, AnnouncementAdapter.ViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<uNotification>() {
        override fun areItemsTheSame(a: uNotification, b: uNotification) = a.id == b.id
        override fun areContentsTheSame(a: uNotification, b: uNotification) = a == b
    }

    class ViewHolder(val binding: AnnouncementlistrvBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AnnouncementlistrvBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val unotificationlist = getItem(position)
        @SuppressLint("SimpleDateFormat")
        val dateFormat = SimpleDateFormat("dd/MM/yyyy hh:mm:ss")
        val date = unotificationlist.date

        val formattedDate = dateFormat.format(date)
        holder.binding.nodates.text = formattedDate
        holder.binding.NoTitles.text = unotificationlist.title
        holder.binding.NoDescs.text = unotificationlist.desc

        fn(holder, unotificationlist)
    }
}
