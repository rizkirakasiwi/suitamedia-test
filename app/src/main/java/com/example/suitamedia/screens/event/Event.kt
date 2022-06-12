package com.example.suitamedia.screens.event

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.suitamedia.R
import com.example.suitamedia.model.entity.EventData
import com.example.suitamedia.model.entity.eventDummy
import com.example.suitamedia.screens.map.MapScreen
import com.example.suitamedia.ui.theme.dimen

@Composable
fun Event(onBack: () -> Unit, onEventSelected: (String) -> Unit) {
    var onMap by rememberSaveable{mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.event))
                },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(
                                R.string.back))
                    }
                },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Search,
                            contentDescription = stringResource(
                                R.string.search))
                    }
                    IconButton(onClick = { onMap = !onMap }) {
                        Icon(imageVector = if(onMap) Icons.Default.List else Icons.Default.Map,
                            contentDescription = stringResource(R.string.map))
                    }
                }
            )
        }
    ) {
        if(onMap){
            MapScreen()
        }else{
            LazyColumn(modifier = Modifier.padding(MaterialTheme.dimen.medium),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.dimen.small)) {
                items(eventDummy()) { data ->
                    EventItem(data = data, onEventSelected = onEventSelected)
                }
            }
        }
    }
}

@Composable
fun EventItem(data: EventData, onEventSelected: (String) -> Unit) {
    Card(elevation = MaterialTheme.dimen.small,
        modifier = Modifier
            .padding(MaterialTheme.dimen.extraSmall)
            .clickable { onEventSelected(data.name) }) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.dimen.medium)) {
            Image(painter = painterResource(id = R.drawable.kopi),
                contentDescription = stringResource(R.string.event_pictures),
                modifier = Modifier
                    .size(90.dp)
                    .clip(MaterialTheme.shapes.small.copy(CornerSize(MaterialTheme.dimen.small))),
                contentScale = ContentScale.Crop)
            Spacer(modifier = Modifier.width(MaterialTheme.dimen.medium))
            Column(modifier = Modifier.weight(1f)) {
                Text(text = data.name,
                    style = MaterialTheme.typography.h5,
                    fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(MaterialTheme.dimen.small))
                Text(text = data.description)
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(text = data.date)
                    Spacer(modifier = Modifier.weight(1f))
                    Text(text = data.time)
                }
            }
        }
    }
}