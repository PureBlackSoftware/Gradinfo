package com.pureblacksoft.gradinfo.adapter

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pureblacksoft.gradinfo.data.Grad
import com.pureblacksoft.gradinfo.databinding.CardGradBinding

class GradAdapter(private val gradList: MutableList<Grad>) : RecyclerView.Adapter<GradAdapter.ViewHolder>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CardGradBinding.inflate(layoutInflater, parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val grad = gradList[position]
        holder.bind(grad)
    }

    override fun getItemCount(): Int = gradList.size

    inner class ViewHolder(private val binding: CardGradBinding) : RecyclerView.ViewHolder(binding.root)
    {
        fun bind(grad: Grad) {
            binding.txtNumberGC.text = grad.number.toString()
            binding.txtNameGC.text = grad.name
            binding.txtDegreeGC.text = grad.degree
            binding.txtYearGC.text = grad.year.toString()

            val imageBitmap = BitmapFactory.decodeByteArray(grad.image, 0, grad.image.size)
            binding.imgGradGC.setImageBitmap(Bitmap.createScaledBitmap(imageBitmap, imageBitmap.width, imageBitmap.height, false))

            binding.root.setOnClickListener {}
        }
    }
}