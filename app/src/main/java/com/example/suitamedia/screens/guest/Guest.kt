package com.example.suitamedia.screens.guest

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.SubcomposeAsyncImage
import com.example.suitamedia.R
import com.example.suitamedia.helper.items
import com.example.suitamedia.model.entity.Data
import com.example.suitamedia.screens.screenTwo.ScreenTwoViewModel
import com.example.suitamedia.ui.theme.dimen


@ExperimentalFoundationApi
@Composable
fun Guest(modifier: Modifier = Modifier, viewModel: ScreenTwoViewModel, onBack:()->Unit, onGuestClicked:(String)->Unit){
    val guest: LazyPagingItems<Data> = viewModel.guest.collectAsLazyPagingItems()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.guest))
                },
                navigationIcon = {
                    IconButton(onClick = { onBack() }) {
                        Icon(imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(
                                R.string.back))
                    }
                }
            )
        }
    ){
        LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.padding(it)){
            items(guest){g->
                if (g != null) GuestItem(data = g, onGuestClicked = onGuestClicked)
            }
        }
    }


}

@Composable
fun GuestItem(modifier : Modifier = Modifier, data: Data, onGuestClicked: (String) -> Unit){
    Column(modifier = modifier
        .padding(MaterialTheme.dimen.small)
        .clickable { onGuestClicked(data.first_name) }, horizontalAlignment = Alignment.CenterHorizontally) {
        SubcomposeAsyncImage(
            model = data.avatar,
            contentDescription = stringResource(id = R.string.avatar),
            loading = {
                CircularProgressIndicator()
            },
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(90.dp)
                .clip(CircleShape)
        )

        Text(text = data.first_name)
    }
}