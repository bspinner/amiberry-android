package de.codelett.amiberry.ui.startup;

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import de.codelett.amiberry.AmiberryActivity
import de.codelett.amiberry.R
import kotlinx.android.synthetic.main.fragment_startup.*

class StartupActivityFragment : Fragment() {

    private val permissionsRequestCode = 30433;

    private lateinit var model: StartupViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.activity?.let {
            model = it.run {
                ViewModelProviders.of(it)[StartupViewModel::class.java]
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_startup, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        proceed.setOnClickListener { _ ->
            requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), permissionsRequestCode)
        }

        if (savedInstanceState != null) {
            // don't force restart the emulation on rotation etc.
            return
        }

        this.activity?.let {
            // WRITE permission also implies READ
            if (ContextCompat.checkSelfPermission(it, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                // Permission has already been granted
                setupAndStartEmulation()
            }
        } ?: throw Exception("Invalid Activity")
    }

    private fun setupAndStartEmulation()
    {
        this.activity?.let {
            val intent = Intent(it, AmiberryActivity::class.java)
            model.extractGameContent()
            startActivity(intent)
        } ?: throw Exception("Activity went missing")
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode != permissionsRequestCode)
        {
            return
        }

        if (permissions.isEmpty()) {
            // Request was likely cancelled
            return
        }

        val perms = HashMap<String, Int>()
        for (i in permissions.indices) {
            perms[permissions[i]] = grantResults[i]
        }

        if(perms[Manifest.permission.WRITE_EXTERNAL_STORAGE] == PackageManager.PERMISSION_GRANTED)
        {
            setupAndStartEmulation()
        }
    }
}