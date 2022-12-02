package com.example.pia_moviles.Servicios

import com.example.pia_moviles.Modelos.CarritoModel
import com.example.pia_moviles.Modelos.ProductoModel
import retrofit2.Call
import retrofit2.http.*

interface CarritoServicio {

    @Headers("Content-Type: application/json")

    @POST("CarritoController.php")
    fun AgregarAlCarrito(@Body carrito: CarritoModel): Call<CarritoModel>

    @PUT("CarritoController.php")
    fun EliminarDelCarrito(@Query("id") id: Int?): Call<CarritoModel>

    @POST("GetCart.php")
    fun GetCart(@Query("userid") id: Int?): Call<List<CarritoModel>>

    @GET("BuyCart.php")
    fun BuyCart(@Query("userid") id: Int?): Call<CarritoModel>

}