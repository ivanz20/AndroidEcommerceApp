package com.example.pia_moviles.SQliteDB

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.lang.Exception

class DataDBHelper(var context: Context): SQLiteOpenHelper(context,SetDB.DB_NAME,null,SetDB.DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase?) {
        try {
            val CrearProducto: String = "CREATE TABLE producto (" +
                    "idproducto INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " nombreproducto  VARCHAR(150)," +
                    " descripcion VARCHAR(150)," +
                    " precio FLOAT," +
                    " categoria VARCHAR(50)," +
                    " imagen mediumblob," +
                    " created_at timestamp default current_timestamp," +
                    "iduser INTEGER," +
                    "calificacion INTEGER," +
                    "publicado tinyint" +
                    ")"

            val CrearComenarios: String = "CREATE TABLE comentarios (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "idproducto INTEGER, " +
                    " iduser  INTEGER," +
                    " comentario  varchar(150)," +
                    " username  varchar(150)," +
                    " calificacion  INTEGER" +
                    ")"
            db?.execSQL(CrearProducto)
            db?.execSQL(CrearComenarios)
            Log.e("ENTRO", "CREO TABLAS")

        } catch (e: Exception) {
            Log.e("Execption", e.toString())
        }
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

}