package ir.hasanazimi.androidlab

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class App : Application(){

    private val TAG = this::class.simpleName

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "onCreate: ")
    }

}