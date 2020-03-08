package de.codelett.amiberry.ui

import android.view.KeyEvent
import android.view.MotionEvent
import android.widget.FrameLayout
import de.codelett.amiberry.R
import kotlinx.android.synthetic.main.amiberry_controlleroverlay.view.*
import org.libsdl.app.SDLActivity

internal class ControllerOverlay(context: SDLActivity) : FrameLayout(context)
{
    init {
        inflate(context, R.layout.amiberry_controlleroverlay, this)

        // Directional Buttons
        button_w.setOnTouchListener { view, motionEvent ->
            handleTouch(motionEvent, KeyEvent.KEYCODE_DPAD_UP)
        }
        button_a.setOnTouchListener { view, motionEvent ->
            handleTouch(motionEvent, KeyEvent.KEYCODE_DPAD_LEFT)
        }
        button_s.setOnTouchListener { view, motionEvent ->
            handleTouch(motionEvent, KeyEvent.KEYCODE_DPAD_DOWN)
        }
        button_d.setOnTouchListener { view, motionEvent ->
            handleTouch(motionEvent, KeyEvent.KEYCODE_DPAD_RIGHT)
        }

        // Fire buttons
        button_x.setOnTouchListener { view, motionEvent ->
            handleTouch(motionEvent, KeyEvent.KEYCODE_ALT_LEFT)
        }

        // Meta buttons for opening keyboard etc.
        button_openkeyboard.setOnTouchListener { view, motionEvent ->
            handleTouch(motionEvent, KeyEvent.KEYCODE_F12)
        }
    }

    private fun handleTouch(motionEvent: MotionEvent, keyEvent: Int) : Boolean
    {
        when(motionEvent.action)
        {
            MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> SDLActivity.onNativeKeyUp(keyEvent)
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> SDLActivity.onNativeKeyDown(keyEvent)
        }
        return false // never return true here, so the Buttons can react on touches, too (for ripple animation)
    }
}