package com.example.suitamedia.model.entity

import com.google.android.gms.maps.model.LatLng

data class EventData(
    val id: Int,
    val name: String,
    val description: String = "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
    val date: String = "15 januari 2021",
    val time: String = "9.00 AM",
    val latlng:LatLng
)

fun eventDummy():List<EventData>{
    return listOf(
        EventData(
            1,
            "Event 1",
            latlng = LatLng(-6.215804, 106.827579)
        ),
        EventData(
            2,
            "Event 2",
            latlng = LatLng(-6.192940, 106.775116)
        ),
        EventData(
            3,
            "Event 3",
            latlng = LatLng(-6.251746, 106.812049)
        ),
        EventData(
            4,
            "Event 4",
            latlng = LatLng(-6.251437, 106.808712)
        ),
        EventData(
            5,
            "Event 5",
            latlng = LatLng(-6.227501, 106.776378)
        )
    )
}