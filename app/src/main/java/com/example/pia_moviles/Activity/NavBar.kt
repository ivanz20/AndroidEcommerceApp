package com.example.pia_moviles.Activity

import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Xml
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.pia_moviles.R
import com.example.pia_moviles.databinding.ActivityNavBarBinding
import okio.ByteString.Companion.decodeBase64
import java.net.URLDecoder
import java.util.*

class NavBar : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityNavBarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNavBarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarNavBar.toolbar)

        val nombre =

        binding.appBarNavBar.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_nav_bar)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_Perfil, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onStart() {
        super.onStart()
        loadData();
    }

    private fun loadData() {
        var pref: SharedPreferences? = null
        pref = getSharedPreferences("usuario",AppCompatActivity.MODE_PRIVATE)

        var iduser = pref?.getInt("Id",0)
        var img = pref?.getString("Image","")
        var nameuser = pref?.getString("Nombre","")
        var lastnameuser = pref?.getString("Apellido","")
        var nombrecompleto = nameuser + " " + lastnameuser
        var emailuser = pref?.getString("Email","")
        var vista = binding.navView.getHeaderView(0)
        var tvfoto = vista.findViewById<ImageView>(R.id.imgperfil)
        var tvname = vista.findViewById<TextView>(R.id.NombreUser)
        var tvcorreo = vista.findViewById<TextView>(R.id.CorreoUser)
        tvname.setText(nombrecompleto.toString())
        tvcorreo.setText(emailuser.toString())


        if(!img!!.isEmpty()) {
            val img2 = img.decodeBase64()?.toByteArray()
            val imageBitmap: Bitmap? = img2?.let { BitmapFactory.decodeByteArray(img2, 0, it.size) }
            tvfoto.setImageBitmap(imageBitmap)
        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.nav_bar, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_nav_bar)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}