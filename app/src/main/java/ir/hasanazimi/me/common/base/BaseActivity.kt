package ir.ha.meproject.common.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import ir.ha.meproject.R
import ir.ha.meproject.common.more.ENGLISH_LANGUAGE_CODE
import ir.ha.meproject.common.more.PERSIAN_LANGUAGE_CODE
import ir.ha.meproject.common.more.localizedContext
import ir.ha.meproject.common.more.SnackBarHelper
import java.util.Locale

abstract class BaseActivity<VB : ViewBinding>(
    private val bindingInflater: (LayoutInflater) -> VB
) : AppCompatActivity() {

    private val TAG = this::class.java.simpleName

    private lateinit var _binding: VB
    protected val binding: VB get() = _binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "onCreate: ")
        _binding = bindingInflater.invoke(layoutInflater)
        setContentView(binding.root)
        initializing()
        uiConfig()
        listeners()
        observers()
    }


    open fun initializing(){
        Log.i(TAG, "initializing: ")
    }

    open fun uiConfig(){
        Log.i(TAG, "uiConfig: ")
    }

    open fun listeners(){
        Log.i(TAG, "listeners: ")
    }

    open fun observers(){
        Log.i(TAG, "observers: ")
    }

    override fun attachBaseContext(context: Context) {
        super.attachBaseContext(localizedContext(context))
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "onStart: ")
        localizedContext(this, Locale(ENGLISH_LANGUAGE_CODE))
    }

    fun showErrorMessage(message: String) {
        Log.e(TAG, "showErrorMessage - $message")
        SnackBarHelper.showSnackBar(
            this, message, R.drawable.baseline_error_outline_24
        )
    }

    fun showMessage(message: String, icon: Int = R.drawable.baseline_done_24) {
        Log.e(TAG, "showMessage : $message")
        SnackBarHelper.showSnackBar(
            this, message, icon
        )
    }
}