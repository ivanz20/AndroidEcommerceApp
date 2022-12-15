package com.example.pia_moviles.Servicios

import com.example.pia_moviles.Modelos.ComentarioModel
import com.example.pia_moviles.Modelos.UsuarioModel
import retrofit2.Call
import retrofit2.http.*

interface ComentariosServicio {
    @Headers("Content-Type: application/json")

    @POST("ComentariosController.php")
    fun AgregarComentario(@Body comentario: ComentarioModel): Call<ComentarioModel>

    @GET("ComentariosController.php")
    fun GetByProductId(@Query("idproducto") idproducto: Int?): Call<List<ComentarioModel>>


}