package com.example.securityapp.presentation.security.encryption.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.securityapp.commons.interfaces.ListItemOnClickListener
import com.example.securityapp.databinding.ViewFileItemBinding
import com.example.securityapp.model.file.data.FileDto

class EncryptedFilesAdapter(private val fileOnClickListener: ListItemOnClickListener<FileDto>) :
    RecyclerView.Adapter<EncryptedFilesAdapter.VH>() {
    val files = mutableListOf<FileDto>()

    override fun getItemCount() = files.size

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(files[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VH(ViewFileItemBinding.inflate(LayoutInflater.from(parent.context), parent, false), fileOnClickListener)


    class VH(
        private val binding: ViewFileItemBinding,
        private val fileOnClickListener: ListItemOnClickListener<FileDto>
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(file: FileDto) {
            binding.name.text = file.name

            binding.root.setOnClickListener {
                fileOnClickListener.onClicked(file, adapterPosition)
            }
        }
    }
}