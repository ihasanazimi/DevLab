package ir.hasanazimi.me.common.more

import android.animation.ObjectAnimator
import android.app.Activity
import android.graphics.Rect
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver
import androidx.lifecycle.Lifecycle

private val TAG = "KEYBOARD_EXT_TAG"

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


fun keyboardListener(
    activity: Activity,
    view: View,
    lifecycleOwner: Lifecycle,
    callBack: (keyboardIsOpen: Boolean) -> Unit
) {
    var isKeyboardOpenLastState = false
    var isKeyboardOpen = false
    Log.i(TAG, "handleKeyboardAndAnimation: ")
    view.viewTreeObserver.addOnGlobalLayoutListener(ViewTreeObserver.OnGlobalLayoutListener {
        Log.i(
            TAG,
            "onGlobalLayout: ======================================================================================================"
        )
        val layoutRect = Rect()
        var translationYAnimator: ObjectAnimator? = null
        //r will be populated with the coordinates of your view that area still visible.
        view.getWindowVisibleDisplayFrame(layoutRect)
        val statusBarHeight = layoutRect.top
        Log.i(TAG, "onGlobalLayout: statusBarHeight => $statusBarHeight")
//        isKeyboardOpenLastState = isKeyboardOpen

        ///////////////////////////// get focus view ////////////////////////////////////////
        val focusedView: View?
        val focusedViewRect = Rect()
        var focusedViewHeight = 0
        var focusedViewY = 0
        var heightDiff = 0
        heightDiff = view.rootView.height - (layoutRect.bottom - layoutRect.top)
        focusedView = activity.currentFocus
        if (focusedView != null) {
            Log.i(TAG, "onGlobalLayout: focusedView is not null => " + focusedView.id)
            focusedView.getWindowVisibleDisplayFrame(focusedViewRect)
            focusedViewHeight = focusedView.height
            Log.i(TAG, "onGlobalLayout: focusedViewHeight => $focusedViewHeight")
            val locationWin = IntArray(2)
            focusedView.getLocationInWindow(locationWin)
            focusedViewY = locationWin[1]
            Log.i(TAG, "onGlobalLayout: focusedViewY => $focusedViewY")
        }
        ////////////////////////////////////////////////////////////////////////////////////////////
//        if (isKeyboardOpenLastState==isKeyboardOpen)
//            return@OnGlobalLayoutListener

        if (heightDiff > 0.25 * view.rootView.height) { // if more than 25% of the screen, its probably a keyboard...
            Log.i(TAG, "onGlobalLayout: keyboard opened")
            isKeyboardOpen = true
        } else {
            Log.i(TAG, "onGlobalLayout: keyboard closed")
            isKeyboardOpen = false
        }

        if (isKeyboardOpen == isKeyboardOpenLastState)
            return@OnGlobalLayoutListener


        if (lifecycleOwner.currentState.isAtLeast(Lifecycle.State.STARTED))
            callBack.invoke(isKeyboardOpen)

        isKeyboardOpenLastState = isKeyboardOpen
    })
}

