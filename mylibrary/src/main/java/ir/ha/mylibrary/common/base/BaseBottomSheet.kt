package ir.ha.mylibrary.common.base

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.viewbinding.ViewBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ir.ha.mylibrary.common.extensions.hideKeyboard

abstract class BaseBottomSheet<VB : ViewBinding>(
    private val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB
): BottomSheetDialogFragment() {

    val TAG = this::class.java.simpleName

    private var bottomSheetBehavior : BottomSheetBehavior<FrameLayout>? =null

    private var _binding: VB? = null
    protected val binding: VB
        get() = _binding ?: throw IllegalStateException("Fragment view not created yet.")


    val parentActivity by lazy { (requireActivity()) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = bindingInflater.invoke(inflater, container, false)
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        Log.d(TAG, "onCreateDialog: ")
        val bottomSheet = super.onCreateDialog(savedInstanceState)
        bottomSheet.setOnShowListener {
            val frameLayout: FrameLayout? = bottomSheet.findViewById(com.google.android.material.R.id.design_bottom_sheet)
            if (frameLayout != null) {

                val bottomSheetBehavior = BottomSheetBehavior.from(frameLayout)
                bottomSheetBehavior.skipCollapsed = true
                if (bottomSheet is BottomSheetDialog) {
                    bottomSheet.let {
                        it.behavior.state = BottomSheetBehavior.STATE_EXPANDED
                        it.behavior.skipCollapsed = true
                    }
                }

                bottomSheetBehavior.addBottomSheetCallback(object :
                    BottomSheetBehavior.BottomSheetCallback() {
                    override fun onStateChanged(bottomSheet: View, newState: Int) {
                        Log.i(TAG, "onStateChanged: on state change : $newState")
                        if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                            val behevior = BottomSheetBehavior.from(bottomSheet)
                            behevior.state = BottomSheetBehavior.STATE_EXPANDED
                        }

                    }

                    override fun onSlide(bottomSheet: View, slideOffset: Float) {
                        Log.i(TAG, "onSlide: ")
                    }
                })
            }
        }
        return bottomSheet

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

    open fun observers() {
        Log.i(TAG, "observers: ")
    }

    open fun listeners(){
        Log.i(TAG, "listeners: ")
    }


    override fun onDestroyView() {
        Log.i(TAG, "onDestroyView: ")
        super.onDestroyView()
        _binding = null
    }

    open fun onScrollToTop() {}

    open fun onRetrievedTag(retrievedTag: String) {}


}