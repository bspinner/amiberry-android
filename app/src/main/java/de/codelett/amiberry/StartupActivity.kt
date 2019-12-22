package de.codelett.amiberry;

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_startup.*
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream

class StartupActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        // As of now, this is just knocked together code for getting permissions.
        // As soon as the JNI part stops crashing at startup, this will be remade

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startup)
        setSupportActionBar(toolbar)
        val intent = Intent(this, org.libsdl.app.SDLActivity::class.java)

        // WRITE permission also implies READ
        if (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                        2)
            }
        } else {
            // Permission has already been granted

        }

        fab.setOnClickListener { _ ->
            startActivity(intent)
        }
    }
}