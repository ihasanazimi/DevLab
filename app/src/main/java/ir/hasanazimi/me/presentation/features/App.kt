package ir.hasanazimi.me.presentation.features

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class App : Application() {

    private val TAG = Application::class.java.simpleName
    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "onCreate: ")



    }


}