package com.example.suitamedia.screens.screenTwo

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.suitamedia.R
import com.example.suitamedia.screens.event.Event
import com.example.suitamedia.screens.guest.Guest
import com.example.suitamedia.screens.map.MapScreen
import com.example.suitamedia.ui.theme.dimen
import com.example.suitamedia.ui.theme.gray_200

sealed class ScreenTwoGroup{
    object Parent:ScreenTwoGroup()
    object Event: ScreenTwoGroup()
    object Guest: ScreenTwoGroup()
}

@ExperimentalFoundationApi
@Composable
fun ScreenTwo(modifier: Modifier = Modifier, viewmodel: ScreenTwoViewModel = hiltViewModel()) {
    val currentScreen by viewmodel.currentScreen.collectAsState()
    var eventBtnText by rememberSaveable{ mutableStateOf<String?>(null) }
    var guesBtnText by rememberSaveable{ mutableStateOf<String?>(null)}

    when(currentScreen){
        is ScreenTwoGroup.Parent -> {
            ScreenTwoView(
                modifier = modifier,
                viewmodel = viewmodel,
                onChooseEvent = {
                     viewmodel.changeCurrentScreent(ScreenTwoGroup.Event)
                },
                onEventBtnChangedText = eventBtnText,
                onChooseGuest = {
                    viewmodel.changeCurrentScreent(ScreenTwoGroup.Guest)
                },
                onGuestBtnChangedText = guesBtnText
            )
        }

        is ScreenTwoGroup.Event -> {
            Event(
                onBack = {
                    viewmodel.changeCurrentScreent(ScreenTwoGroup.Parent)
                },
                onEventSelected = {
                    eventBtnText = it
                    viewmodel.changeCurrentScreent(ScreenTwoGroup.Parent)
                }
            )
        }

        is ScreenTwoGroup.Guest -> {
            Guest(viewModel = viewmodel, onBack = {
                viewmodel.changeCurrentScreent(ScreenTwoGroup.Parent)
            }, onGuestClicked = {
                guesBtnText = it
                viewmodel.changeCurrentScreent(ScreenTwoGroup.Parent)
            })
        }
    }
}

@Composable
fun ScreenTwoView(
    modifier: Modifier = Modifier,
    viewmodel: ScreenTwoViewModel,
    onEventBtnChangedText: String?,
    onGuestBtnChangedText: String?,
    onChooseEvent: () -> Unit,
    onChooseGuest: () -> Unit,
) {
    val name by viewmodel.name.collectAsState()
    Box(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier
            .fillMaxWidth()
            .height(221.dp)
            .background(gray_200))
        Column(modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center) {
            Spacer(modifier = Modifier
                .height(2.dp)
                .width(200.dp)
                .background(MaterialTheme.colors.primary))
            Spacer(modifier = Modifier.height(MaterialTheme.dimen.small))
            Spacer(modifier = Modifier
                .height(2.dp)
                .width(216.dp)
                .background(MaterialTheme.colors.primary))
            Spacer(modifier = Modifier.height(MaterialTheme.dimen.small))
            Spacer(modifier = Modifier
                .height(2.dp)
                .width(200.dp)
                .background(MaterialTheme.colors.primary))
        }
        Column {
            Column(modifier = modifier) {
                Text(text = "Hallo,", style = MaterialTheme.typography.h4)
                Text(text = name,
                    style = MaterialTheme.typography.h4,
                    color = MaterialTheme.colors.primary)
            }
            Column(modifier = modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Button(onClick = {
                    onChooseEvent()
                }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = onEventBtnChangedText ?: stringResource(R.string.choose_event))
                }
                Spacer(modifier = Modifier.height(MaterialTheme.dimen.small))
                Button(onClick = { onChooseGuest() }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = onGuestBtnChangedText ?: stringResource(R.string.choose_guest))
                }
                Spacer(modifier = Modifier.height(MaterialTheme.dimen.medium))
                Image(painter = painterResource(id = R.drawable.img_suitmedia),
                    contentDescription = stringResource(id = R.string.app_name),
                    modifier = Modifier.size(244.dp), contentScale = ContentScale.Fit)
            }
        }

    }
}
