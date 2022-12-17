package com.example.pia_moviles.Servicios

import com.example.pia_moviles.Modelos.ProductoModel
import com.example.pia_moviles.Modelos.UsuarioModel
import retrofit2.Call
import retrofit2.http.*

interface ProductosServicio {
    @Headers("Content-Type: application/json")

    @POST("RegistrarProducto.php")
    fun AgregarProducto(@Body producto: ProductoModel): Call<ProductoModel>

    @GET("GetByID.php")
    fun GetProductById(@Query("idproducto") idproducto: Int?): Call<ProductoModel>

    @GET("Busqueda.php")
    fun SearchByName(@Query("name") name: String?): Call<List<ProductoModel>>

    @GET("GetProductByUser.php")
    fun GetProductsByUser(@Query("iduser")iduser:Int?): Call<List<ProductoModel>>

    @GET("EliminarProducto.php")
    fun EliminarById(@Query("idproducto") idproducto: Int?): Call<Int>

    @POST("EditarProducto.php")
    fun EditById(@Body producto: ProductoModel): Call<ProductoModel>

    @GET("GetAllById.php")
    fun GetAllById():Call<List<ProductoModel>>

    @GET("GetAllByRate.php")
    fun GetAllByRate():Call<List<ProductoModel>>

}