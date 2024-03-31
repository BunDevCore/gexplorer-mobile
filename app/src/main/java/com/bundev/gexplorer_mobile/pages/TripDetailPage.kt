package com.bundev.gexplorer_mobile.pages

import android.graphics.Color
import androidx.compose.runtime.Composable
import com.mapbox.geojson.Point
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.extension.style.layers.generated.lineLayer
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource

@OptIn(MapboxExperimental::class)
@Composable
fun TripDetailPage(tripId: String?) {
    //TODO Fetch points from backend using tripId
    val mapViewportState = MapViewportState
    val mapView = MapView
    MapboxMap(
        mapViewportState = MapViewportState().apply {
            setCameraOptions {
                center(Point.fromLngLat(18.6650564007217, 54.29906183330589))
                pitch(0.0)
                bearing(0.0)
                zoom(16.0)
            }
        }
    ) {
        geoJsonSource(id = "Trip") { data("asset://gdansk_4326.geojson") }
        lineLayer("line-layer", "Trip") {
            lineColor(Color.RED)
            lineWidth(1.0)
        }
    }
}