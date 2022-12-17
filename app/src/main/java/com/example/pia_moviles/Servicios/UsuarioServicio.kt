package com.example.pia_moviles.Servicios

import com.example.pia_moviles.Modelos.UsuarioModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.*

interface UsuarioServicio {
    @Headers("Content-Type: application/json")

    @POST("RegistrarUsuario.php")
    fun AgregarUsuario(@Body user: UsuarioModel): Call<UsuarioModel>

    @POST("Auth.php")
    fun InicioSesion(@Body user: UsuarioModel): Call<UsuarioModel>

    @POST("EditarUsuario.php")
    fun EditarUsuario(@Body user: UsuarioModel): Call<UsuarioModel>

    @GET("GetUserById.php")
    fun GetUserById(@Query ("iduser")iduser:Int?):Call<UsuarioModel>

}