package ir.hasanazimi.me.common.base

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import ir.hasanazimi.me.common.extensions.hideKeyboard

abstract class BaseDialog<VB : ViewBinding>(
    private val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
): DialogFragment() {

    val TAG = this::class.java.simpleName

    private var _binding: VB? = null
    protected val binding: VB
        get() = _binding ?: throw IllegalStateException("Fragment view not created yet.")


    val parentActivity by lazy { (requireActivity()) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = bindingInflater.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializing()
        uiConfig()
        listeners()
        observers()
    }


    open fun initializing(){
        hideKeyboard(view)
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

    override fun onDestroyView() {
        Log.i(TAG, "onDestroyView: ")
        super.onDestroyView()
        _binding = null
    }


    open fun onScrollToTop() {}


    override fun onStart() {
        super.onStart()
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)

        val width = displayMetrics.widthPixels
        val height = displayMetrics.heightPixels

        dialog?.window?.setLayout(width-92, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
}