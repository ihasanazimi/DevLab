package ir.hasanazimi.me

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class App : Application(){

    val TAG = this::class.simpleName

    override fun onCreate() {
        super.onCreate()
    }

}