package com.bundev.gexplorer_mobile.pages

import androidx.compose.runtime.Composable
import com.mapbox.geojson.Point
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource

@OptIn(MapboxExperimental::class)
@Composable
fun TripDetailPage(tripId: String?) {
    //TODO Fetch points from backend using tripId
    MapboxMap(
        mapViewportState = MapViewportState().apply {
            setCameraOptions {
                center(Point.fromLngLat(18.6650564007217, 54.29906183330589))
            }
        }
    ) {
        geoJsonSource(id = "Trip") {data("zabawny.URL.thefen.mailto.me", "HIHIHIHA")}
    }
}