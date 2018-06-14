package com.xpanxion.profile

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.xpanxion.architecture.Skill
import kotlinx.android.synthetic.main.skill_grid_item.view.*

class SkillItemRecyclerViewAdapter(
        val skills: Array<Skill>
) : RecyclerView.Adapter<SkillItemRecyclerViewAdapter.SkillViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SkillViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.skill_grid_item, parent, false)
        return SkillViewHolder(view)
    }

    override fun onBindViewHolder(holder: SkillViewHolder, position: Int) {
        val skill = skills[position]
        holder.image_view.setImageResource(skill.image)
        holder.name_view.text = skill.name
    }

    override fun getItemCount(): Int = skills.size

    inner class SkillViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val image_view: ImageView = view.skill_image_view
        val name_view: TextView = view.skill_name_text_view
    }
}
