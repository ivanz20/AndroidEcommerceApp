package com.example.pia_moviles.Adaptadores

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.pia_moviles.Modelos.ComentarioModel
import com.example.pia_moviles.Modelos.ProductoModel
import com.example.pia_moviles.R
import okio.ByteString.Companion.decodeBase64
import kotlin.properties.Delegates

class ComentariosAdapter(private val ComentarioList : ArrayList<ComentarioModel>):RecyclerView.Adapter<ComentariosAdapter.ComentarioViewholder>() {


    var prueba by Delegates.notNull<Int>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ComentarioViewholder {

        val layoutView:View = LayoutInflater.from(parent.context).
            inflate(R.layout.coments, parent,false)
        return ComentarioViewholder(layoutView)

    }

    override fun onBindViewHolder(holder: ComentarioViewholder, position: Int) {
        val comentario : ComentarioModel = ComentarioList[position]

        holder.TheComment.text = comentario.comentario.toString();
        holder.TheUser.text = comentario.usuario.toString();
        holder.TheRate.text = comentario.calificacion.toString() + " de 5 estrellas";


    }

    override fun getItemCount(): Int {

        return ComentarioList.size
    }

    class ComentarioViewholder(view: View):RecyclerView.ViewHolder(view){

            var TheComment: TextView = view.findViewById(R.id.coment_card)
            var TheUser: TextView = view.findViewById(R.id.usercoment)
            var TheRate: TextView = view.findViewById(R.id.rate)

    }
}