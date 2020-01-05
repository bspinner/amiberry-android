package de.codelett.amiberry

import android.os.Bundle
import android.widget.FrameLayout
import de.codelett.amiberry.ui.ControllerOverlay
import org.libsdl.app.SDLActivity

class AmiberryActivity : SDLActivity() {

    private lateinit var controllerOverlay: ControllerOverlay

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        controllerOverlay = ControllerOverlay(this)

        val params = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT)
        addContentView(controllerOverlay, params)
    }
}