package app.netlify.dev4rju9.geologger.presentation.geofence

import android.annotation.SuppressLint
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import app.netlify.dev4rju9.geologger.R
import app.netlify.dev4rju9.geologger.data.local.entity.GeofenceEntity
import app.netlify.dev4rju9.geologger.utils.PermissionHandler
import com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_ENTER
import com.google.android.gms.location.Geofence.GEOFENCE_TRANSITION_EXIT
import com.google.android.gms.location.GeofencingClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CircleOptions
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolygonOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@AndroidEntryPoint
class GeofenceFragment : Fragment(R.layout.fragment_geofence), OnMapReadyCallback {

    private lateinit var map: GoogleMap

    @Inject
    lateinit var geofencingClient: GeofencingClient

    @Inject
    lateinit var geofenceHelper: GeofenceHelper
    private val geofenceViewModel: GeofenceViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        PermissionHandler.checkPermissions(requireActivity())
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun fetchGeofences () {
        lifecycleScope.launch {
            val geofences = geofenceViewModel.getAllGeofences()
            for (geofence in geofences) {
                val latLng = LatLng(geofence.latitude, geofence.longitude)
                addMarker(latLng, geofence.id)
                val sides = geofence.sides
                if (sides > 2) addPolygon(latLng, geofence.radius, sides)
                else addCircle(latLng, geofence.radius)
                createGeofence(geofence.id, latLng, geofence.radius)
            }
        }
    }

    override fun onMapReady(gMap: GoogleMap) {
        map = gMap
        if (PermissionHandler.checkPermissions(requireActivity())) enableUserLocation()
        fetchGeofences()

        map.setOnMapClickListener { latLng ->
            lifecycleScope.launch {
                val existingGeofences = geofenceViewModel.getAllGeofences()
                val geofenceToRemove = existingGeofences.find {
                    it.latitude == latLng.latitude && it.longitude == latLng.longitude
                }

                if (geofenceToRemove != null) {
                    geofencingClient.removeGeofences(listOf(geofenceToRemove.id))
                    geofenceViewModel.deleteGeofence(geofenceToRemove.id)
                    map.clear()
                    Toast.makeText(requireContext(), "Geofence removed", Toast.LENGTH_SHORT).show()
                } else {
                    showGeofenceDialog(latLng)
                }
            }
        }
    }

    private fun showGeofenceDialog(latLng: LatLng) {
        val view = layoutInflater.inflate(R.layout.dialog_geofence, null)
        val dialog = MaterialAlertDialogBuilder(requireActivity())
            .setTitle("Add Geofence")
            .setView(view)
            .setNegativeButton("Discard") {dialog, _ -> dialog.dismiss() }
            .setPositiveButton("Save") { dialog, _ ->
                val title = view.findViewById<EditText>(R.id.editTextTitle).text.toString()
                val radius = view.findViewById<EditText>(R.id.editTextRadius).text.toString()
                val sides = view.findViewById<EditText>(R.id.editTextSides).text.toString()
                if (title.isNotEmpty() && radius.isNotEmpty()) {
                    addGeofence(title, latLng, radius.toFloat(), sides.toIntOrNull() ?: 0)
                    dialog.dismiss()
                } else {
                    Toast.makeText(
                        requireActivity(),
                        "Invalid inputs, try again..",
                        Toast.LENGTH_SHORT
                    ).show()
                    showGeofenceDialog(latLng)
                }
            }.show()

        dialog.getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(Color.GREEN)
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE)?.setTextColor(Color.RED)

    }

    private fun addGeofence(title: String, latLng: LatLng, radius: Float, sides: Int = 0) {

        addMarker(latLng, title)
        if (sides > 2) addPolygon(latLng, radius, sides)
        else addCircle(latLng, radius)
        createGeofence(title, latLng, radius)

        lifecycleScope.launch {
            geofenceViewModel.insertGeofence(
                GeofenceEntity(id = title, latitude = latLng.latitude, longitude = latLng.longitude, radius = radius, sides = sides)
            )
        }

    }

    @SuppressLint("MissingPermission")
    private fun createGeofence(title: String, latLng: LatLng, radius: Float) {

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16F))

        val requestCode = UUID.randomUUID().hashCode()
        val geofence = geofenceHelper.getGeofence(
            id = title,
            latLng = latLng,
            radius = radius,
            transitionTypes = GEOFENCE_TRANSITION_ENTER or GEOFENCE_TRANSITION_EXIT
        )

        val geofencingRequest = geofenceHelper.getGeofencingRequest(geofence)
        val pendingIntent = geofenceHelper.getPendingIntent(requestCode)

        geofencingClient.addGeofences(geofencingRequest, pendingIntent).run {
            addOnFailureListener { e ->
                val error_message = geofenceHelper.getErrorString(e)
                Toast.makeText(context, error_message, Toast.LENGTH_SHORT).show()
                Log.d("Arjun", "Geofence Error: $error_message")
            }
        }
    }

    private fun addMarker(latLng: LatLng, title: String) {
        val markerOptions = MarkerOptions().position(latLng).title(title)
        map.addMarker(markerOptions)
    }

    private fun addCircle(latLng: LatLng, radius: Float) {
        val circleOptions = CircleOptions()
        circleOptions.center(latLng)
        circleOptions.radius(radius.toDouble())
        circleOptions.strokeColor(Color.BLUE)
        circleOptions.fillColor(Color.CYAN)
        circleOptions.strokeWidth(4F)
        map.addCircle(circleOptions)
    }

    private fun addPolygon(latLng: LatLng, radius: Float, sides: Int = 4) {
        val polygonOptions = PolygonOptions()
            .strokeColor(Color.BLUE)
            .fillColor(Color.CYAN)
            .strokeWidth(4f)

        // Calculating points
        val angleStep = 360.0 / sides
        for (i in 0 until sides) {
            val angle = Math.toRadians(i * angleStep)
            val point = LatLng(
                latLng.latitude + (radius / 111000) * Math.cos(angle),
                latLng.longitude + (radius / 111000) * Math.sin(angle) / Math.cos(Math.toRadians(latLng.latitude))
            )
            polygonOptions.add(point)
        }

        // Closing the polygon by adding the first point again
        polygonOptions.add(polygonOptions.points[0])

        map.addPolygon(polygonOptions)
    }

    @SuppressLint("MissingPermission")
    private fun enableUserLocation() {
        map.isMyLocationEnabled = true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 2) {
            Log.d("Arjun", "Inside onRequestPermissionsResult")

            val allPermissionsGranted =
                grantResults.isNotEmpty() && grantResults.all { it == PERMISSION_GRANTED }

            if (allPermissionsGranted) {
                Log.d("Arjun", "All permissions granted. Enabling user location.")
                enableUserLocation()
            } else {
                Toast.makeText(requireActivity(), "Permission Denied", Toast.LENGTH_SHORT).show()
                Log.d("Arjun", "Permissions Denied")
            }
        }
    }
}