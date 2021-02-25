package com.pureblacksoft.gradinfo.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pureblacksoft.gradinfo.data.Grad
import com.pureblacksoft.gradinfo.databinding.CardGradBinding

class GradAdapter(private val gradList: MutableList<Grad>) : RecyclerView.Adapter<GradAdapter.ViewHolder>()
{
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context

        val layoutInflater = LayoutInflater.from(context)
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
            Glide.with(context).load(grad.image).into(binding.imgGradGC)

            binding.root.setOnClickListener {}
        }
    }
}