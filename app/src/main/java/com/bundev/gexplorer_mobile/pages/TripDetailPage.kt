package com.bundev.gexplorer_mobile.pages

import android.graphics.Color
import android.util.Log
import androidx.compose.runtime.Composable
import com.mapbox.geojson.Point
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.Style
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.MapViewportState
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.extension.style.layers.generated.fillLayer
import com.mapbox.maps.extension.style.layers.generated.lineLayer
import com.mapbox.maps.extension.style.sources.generated.geoJsonSource
import com.mapbox.maps.extension.style.style

@OptIn(MapboxExperimental::class)
@Composable
fun TripDetailPage(tripId: String?) {
    //TODO Fetch points from backend using tripId
    val mapView = MapView
    val style = style(Style.LIGHT) {

        +geoJsonSource(id = "Trip") { data("asset://gdansk_4326.geojson") }
        +lineLayer("line-layer", "Trip") {
            lineColor(Color.RED)
            lineWidth(3.0)
        }
        +fillLayer("fill-layer", "Trip") {
            fillOpacity(0.4)
            fillColor("#0000ff")
        }
    }

    val mapViewportState = rememberMapViewportState {
        setCameraOptions {
            zoom(10.0)
            center(Point.fromLngLat(18.6570989, 54.3542712))
            pitch(0.0)
            bearing(0.0)
        }
    }

    Log.wtf("sdfkjksdgj", "please ffs")

    MapboxMap(
        mapViewportState = mapViewportState,
        mapInitOptionsFactory = {
            MapInitOptions(it)
        }
    ) {
        MapEffect(Unit) { mapView ->
            run {
                mapView.mapboxMap.loadStyle(style)
                Log.wtf("aawawaw", "sdfkjnsfkgdnd")
            }
        }
    }
}