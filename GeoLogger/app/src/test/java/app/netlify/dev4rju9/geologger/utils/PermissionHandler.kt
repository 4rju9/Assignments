package app.netlify.dev4rju9.geologger.utils

import android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.POST_NOTIFICATIONS
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_DENIED
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Build
import android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionHandler {

    companion object {

        fun checkPermissions(activity: Activity): Boolean {
            val permissions = mutableListOf<String>()

            if (ContextCompat.checkSelfPermission(
                    activity,
                    ACCESS_FINE_LOCATION
                ) == PERMISSION_DENIED
            ) {
                permissions.add(ACCESS_FINE_LOCATION)
            }
            if (ContextCompat.checkSelfPermission(
                    activity,
                    ACCESS_COARSE_LOCATION
                ) == PERMISSION_DENIED
            ) {
                permissions.add(ACCESS_COARSE_LOCATION)
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ActivityCompat.checkSelfPermission(
                        activity, POST_NOTIFICATIONS
                    ) != PERMISSION_GRANTED
                ) {
                    permissions.add(POST_NOTIFICATIONS)
                }
            }

            if (permissions.isNotEmpty()) {
                ActivityCompat.requestPermissions(activity, permissions.toTypedArray(), 2)
                return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    checkBackgroundLocationPermission(activity)
                } else {
                    false
                }
            }

            return true
        }

        private fun checkBackgroundLocationPermission(activity: Activity): Boolean {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                if (ContextCompat.checkSelfPermission(
                        activity,
                        ACCESS_BACKGROUND_LOCATION
                    ) == PERMISSION_DENIED
                ) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) { // API 34+ (Android 14)
                        openLocationSettings(activity)
                    } else {
                        ActivityCompat.requestPermissions(
                            activity,
                            arrayOf(ACCESS_BACKGROUND_LOCATION),
                            2
                        )
                    }
                    false
                } else {
                    true
                }
            } else {
                true
            }
        }

        private fun openLocationSettings(activity: Activity) {
            val intent = Intent(ACTION_LOCATION_SOURCE_SETTINGS)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            activity.startActivity(intent)
            Toast.makeText(
                activity,
                "Enable background location in settings.",
                Toast.LENGTH_LONG
            ).show()
        }

    }

}