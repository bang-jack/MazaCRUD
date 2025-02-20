package com.samapp.mazacrud.adapter

import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.samapp.mazacrud.data.entity.User
import com.samapp.mazacrud.R;


class UserAdapter (var list: List<User>):RecyclerView.Adapter<UserAdapter.ViewHolder>(){
    private lateinit var dialog:Dialog

    fun setDialog(dialog:Dialog){
        this.dialog = dialog
    }

    interface Dialog {
        fun onClick(position: Int)
    }

    inner class ViewHolder(view:View):RecyclerView.ViewHolder(view){
        var fullName: TextView
        var email: TextView
        var phone: TextView

        init {
            fullName = view.findViewById(R.id.full_name)
            email = view.findViewById(R.id.email)
            phone = view.findViewById(R.id.phone)
            view.setOnClickListener{
                dialog.onClick(layoutPosition)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.row_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserAdapter.ViewHolder, position: Int) {
        holder.fullName.text = list[position].fullName
        holder.email.text = list[position].email
        holder.phone.text = list[position].phone
    }

    override fun getItemCount(): Int {
        return list.size
    }

}