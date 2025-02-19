package ir.ha.mylibrary.common.extensions

import android.Manifest
import android.app.Activity
import android.location.Location
import android.os.Looper
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import ir.ha.mylibrary.common.security_and_permissions.requestPermission

/** add -> implementation("com.google.android.gms:play-services-location:21.2.0") */
fun Activity.getCurrentLocation(callback: (Location?) -> Unit) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    val locationRequest = LocationRequest.create().apply {
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        interval = 10000 // Update interval in milliseconds
        fastestInterval = 5000 // Fastest update interval in milliseconds
    }
    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(lr: LocationResult) {
            lr ?: return
            for (location in lr.locations) {
                callback(location)
                return
            }
        }
    }

    if (ir.ha.mylibrary.common.security_and_permissions.checkPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    ) {
        requestPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
    }
    /** add permission ->     <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" /> */
    fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
}