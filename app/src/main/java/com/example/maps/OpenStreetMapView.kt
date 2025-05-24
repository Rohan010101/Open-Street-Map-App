package com.example.maps

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.BoundingBox
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@Composable
fun OpenStreetMapView(){

    Scaffold {
        AndroidView(
            modifier = Modifier.fillMaxSize().padding(it),
            factory = { context ->
                MapView(context).apply {
                    setTileSource(TileSourceFactory.MAPNIK)
                    setMultiTouchControls(true)

                    val mapController = controller
                    val startPoint = GeoPoint(28.6139, 77.2090)     // New Delhi
                    mapController.setZoom(10.0)
                    mapController.setCenter(startPoint)

                    // Set Min & Max Zoom to avoid extreme zooming
                    minZoomLevel = 4.0          // PERFECT
                    maxZoomLevel = 15.0

//                // Set Bounding Box to prevent scrolling beyond one map
//                val southWest = GeoPoint(-85.0, -180.0)     // Bottom-Left Corner
//                val northEast = GeoPoint(85.0, 180.0)       // Top-Right Corner
//                setScrollableAreaLimitLatitude(northEast.latitude, southWest.latitude,0)


                    // Set a Bounding Box to restrict vertical movement but allow horizontal scrolling
                    val boundingBox = BoundingBox(
                        85.0,       // North Latitude (max)
                        180.0,      // East Longitude (max)  ->  Allows Horizontal Scrolling
                        -85.0,      // South Latitude (min)
                        -180.0      // West Longitude (min)  ->  Allows Horizontal Scrolling
                    )

                    // Enable horizontal scrolling but restrict vertical movement
                    setScrollableAreaLimitDouble(
                        boundingBox
                    )

                    // Add a Marker
                    val marker = Marker(this)
                    marker.position = startPoint
                    marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
                    marker.title = "New Delhi"
                    overlays.add(marker)
                }
            }
        )
    }
}