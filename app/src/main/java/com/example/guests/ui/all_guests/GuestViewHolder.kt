package com.example.guests.ui.all_guests

import android.app.AlertDialog
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.guests.R
import com.example.guests.service.model.GuestModel
import com.example.guests.ui.all_guests.listener.GuestListener

class GuestViewHolder(itemView: View, private val listener: GuestListener) : RecyclerView.ViewHolder(itemView) {

    fun bind(guest: GuestModel) {
        val textName = itemView.findViewById<TextView>(R.id.item_name_guest)
        textName.text = guest.name

        //evento de click para ver situação do convidado
        textName.setOnClickListener {
            listener.onClick(guest.id)
        }

        //cria mensagem de confirmação de remoção convidado
        textName.setOnLongClickListener {

            AlertDialog.Builder(itemView.context)
                    .setTitle(R.string.remove_guest)
                    .setMessage(R.string.want_to_remove)
                    .setPositiveButton(R.string.remove) { dialog, which ->
                        listener.onDelete(guest.id)
                    }
                    .setNeutralButton(R.string.cancel, null)
                    .show()

            true
        }
    }
}