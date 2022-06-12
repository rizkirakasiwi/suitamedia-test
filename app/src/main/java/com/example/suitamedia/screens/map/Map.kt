package com.example.suitamedia.screens.map

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.example.suitamedia.model.entity.EventData
import com.example.suitamedia.model.entity.eventDummy
import com.example.suitamedia.screens.event.EventItem
import com.example.suitamedia.ui.theme.dimen
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
fun MapScreen(){
    var detail by rememberSaveable{ mutableStateOf<EventData?>(null) }
    val currentCamera = if(detail != null) detail!!.latlng else LatLng(-6.237236, 106.817831)
    val cameraPositionState: CameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentCamera, 12f)
    }


    Box(Modifier.fillMaxSize()) {
        GoogleMap(cameraPositionState = cameraPositionState){
            eventDummy().forEach {
                Marker(
                    state = MarkerState(position = it.latlng),
                    title = it.name,
                    snippet = it.description,
                    onClick = {m->
                        detail = it
                        false
                    }
                )

            }
        }
        if(detail != null){
            Column(modifier = Modifier.padding(MaterialTheme.dimen.medium)) {
                EventItem(data = detail!!, onEventSelected = {})
            }

        }
    }
}