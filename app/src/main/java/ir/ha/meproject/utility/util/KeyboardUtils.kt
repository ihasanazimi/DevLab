package ir.asadiara.triage.utility

import android.app.Activity
import android.graphics.Rect

class KeyboardUtils(private val activity: Activity) {

    private var keyboardOpen = false
    fun setKeyboardListener(callback: (Boolean) -> Unit) {
        val rootView = activity.window.decorView.rootView
        rootView.viewTreeObserver.addOnGlobalLayoutListener {
            val rect = Rect()
            rootView.getWindowVisibleDisplayFrame(rect)
            val screenHeight = rootView.height
            val keypadHeight = screenHeight - rect.bottom
            if (keypadHeight > screenHeight * 0.15) { // 0.15 ratio is perhaps enough to determine keypad height.
                if (!keyboardOpen) {
                    keyboardOpen = true
                    callback(true) // Keyboard opened
                }
            } else {
                if (keyboardOpen) {
                    keyboardOpen = false
                    callback(false) // Keyboard closed
                }
            }
        }
    }
}