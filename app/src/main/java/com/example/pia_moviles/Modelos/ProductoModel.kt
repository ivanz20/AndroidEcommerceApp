package com.example.pia_moviles.Modelos

data class ProductoModel (
        var id:Int? = null,
        var nombreproducto:String? = null,
        var descripcion:String? = null,
        var precio:Float?=null,
        var categoria:String?=null,
        var imagen:String?=null,
        var created_at:String?=null

        )