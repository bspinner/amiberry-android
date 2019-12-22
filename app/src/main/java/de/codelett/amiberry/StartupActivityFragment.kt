package de.codelett.amiberry;

import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import de.codelett.amiberry.R

/**
 * A placeholder fragment containing a simple view.
 */
class StartupActivityFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_startup, container, false)
    }
}
