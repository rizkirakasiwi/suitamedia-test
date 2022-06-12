package com.example.suitamedia.screens.screenOne

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.suitamedia.R
import com.example.suitamedia.helper.Result
import com.example.suitamedia.helper.WindowSize
import com.example.suitamedia.helper.WindowType
import com.example.suitamedia.helper.toUri
import com.example.suitamedia.model.entity.MyData
import com.example.suitamedia.navigation.Destination
import com.example.suitamedia.ui.theme.dimen
import com.google.accompanist.permissions.*

@ExperimentalPermissionsApi
@Composable
fun ScreenOne(windowSize: WindowSize, navController: NavController ) {
    when {
        windowSize.widthWindowSizeClass == WindowType.COMPACT
                || windowSize.widthWindowSizeClass == WindowType.MEDIUM
                || windowSize.heightWindowSizeClass == WindowType.COMPACT -> {
            ScreenOneCompact(windowSize = windowSize, navController = navController)
        }
        else -> ScreenOneMedium(windowSize = windowSize, navController = navController)

    }
}

@ExperimentalPermissionsApi
@Composable
private fun ScreenOneCompact(
    modifier: Modifier = Modifier,
    windowSize: WindowSize,
    navController: NavController
) {
    val parentModifier = if (windowSize.heightWindowSizeClass == WindowType.COMPACT) modifier
        .verticalScroll(rememberScrollState())
        .fillMaxWidth() else modifier.fillMaxWidth()
    Box(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier
            .height(221.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary))
        Column(
            modifier = parentModifier.padding(MaterialTheme.dimen.extraLarge),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ScreenOneHeaderText()
            Spacer(modifier = Modifier.height(MaterialTheme.dimen.large))
            ScreenOneCard(windowSize,navController)
            Spacer(modifier = Modifier.weight(1f))
            ScreenOneFooter()
        }
    }
}

@ExperimentalPermissionsApi
@Composable
private fun ScreenOneMedium(
    modifier: Modifier = Modifier,
    windowSize: WindowSize,
    navController: NavController,
) {
    Row(modifier = Modifier.fillMaxSize()) {
        Column(modifier = modifier
            .weight(1f)
            .fillMaxHeight()
            .background(MaterialTheme.colors.primary)) {
            ScreenOneHeaderText()
        }
        Column(modifier = modifier
            .padding(MaterialTheme.dimen.extraLarge)
            .weight(2f)
            .fillMaxHeight()) {
            ScreenOneCard(windowSize,navController)
            Spacer(modifier = Modifier.weight(1f))
            ScreenOneFooter()
        }
    }
}


@Composable
private fun ScreenOneHeaderText() {
    Text(stringResource(R.string.welcome),
        fontSize = 18.sp,
        color = MaterialTheme.colors.onPrimary,
        fontWeight = FontWeight.Bold
    )
    Text(stringResource(R.string.desc_app_for),
        color = MaterialTheme.colors.onPrimary,
        textAlign = TextAlign.Center)
}

@ExperimentalPermissionsApi
@Composable
private fun ScreenOneCard(
    windowSize: WindowSize,
    navController: NavController,
    viewmodel: ScreenOneViewModel = hiltViewModel(),
) {
    //textfield data
    var name by rememberSaveable { mutableStateOf("") }
    var palindrom by rememberSaveable { mutableStateOf("") }

    val context = LocalContext.current

    //modifier for different screen sizes
    val parentModifier = if (windowSize.heightWindowSizeClass == WindowType.COMPACT
        || windowSize.widthWindowSizeClass == WindowType.MEDIUM
    ) Modifier
        .width(windowSize.widthWindowDpSize / 2)
    else Modifier.wrapContentSize()

    //image file here
    var imageUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    //image bitmap
    var imageBitmap by rememberSaveable { mutableStateOf<Bitmap?>(null) }


    //launcher to pick image from gallery
    val pickLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) {
            //uri file from user picked image
                uri ->

            //save image uri to [imageUri]
            imageUri = uri
        }

    //launcher to open camera
    val openCameraLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.TakePicturePreview()) {
            //bitmap file from camera
                bitmap ->

            //save image bitmap to [imageBitmap]
            /**
             * we can convert this to uri,
             * but it will consume more cost in memory because user behavior
             * in this feature
             * */
            imageBitmap = bitmap
        }

    //declare permission state
    val readPermission =
        rememberPermissionState(permission = Manifest.permission.READ_EXTERNAL_STORAGE)
    val writePermission =
        rememberPermissionState(permission = Manifest.permission.WRITE_EXTERNAL_STORAGE)

    //setup modifier for image feature
    val imageModifier = Modifier
        .size(90.dp)
        //change image shape to circle image
        .clip(CircleShape)
        //make image clickable
        .clickable {
            //check read external storage permission
            if (!readPermission.status.isGranted) {
                readPermission.launchPermissionRequest()
            } else {
                openCameraOrPickImage(
                    context = context,
                    onPick = {
                        pickLauncher.launch("image/*")
                    },
                    onOpenCamera = {
                        openCameraLauncher.launch()
                    }).show()
            }
        }

    //layout
    Card(elevation = MaterialTheme.dimen.small) {
        Column(modifier = parentModifier
            .padding(MaterialTheme.dimen.medium),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {

            when {
                imageUri != null -> {
                    Image(
                        painter = rememberAsyncImagePainter(imageUri),
                        contentDescription = stringResource(R.string.avatar),
                        contentScale = ContentScale.Crop,
                        modifier = imageModifier
                    )
                }

                imageBitmap != null -> {
                    Image(
                        imageBitmap!!.asImageBitmap(),
                        contentDescription = stringResource(R.string.avatar),
                        contentScale = ContentScale.Crop,
                        modifier = imageModifier
                    )
                }

                else -> {
                    Image(
                        painter = painterResource(id = R.drawable.img_avatar),
                        contentDescription = stringResource(R.string.avatar),
                        contentScale = ContentScale.Crop,
                        modifier = imageModifier
                    )
                }
            }


            Spacer(modifier = Modifier.height(MaterialTheme.dimen.large))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = name,
                onValueChange = { name = it },
                shape = MaterialTheme.shapes.small.copy(CornerSize(12.dp)),
                placeholder = {
                    Text(text = stringResource(R.string.type_the_name_here))
                }
            )

            Spacer(modifier = Modifier.height(MaterialTheme.dimen.medium))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = palindrom,
                onValueChange = { palindrom = it },
                shape = MaterialTheme.shapes.small.copy(CornerSize(12.dp)),
                placeholder = {
                    Text(text = stringResource(R.string.type_text_palindrome))
                }
            )

            Spacer(modifier = Modifier.height(MaterialTheme.dimen.medium))

            Row {
                Button(
                    modifier = Modifier.weight(2f),
                    onClick = {
                        //validate image not null
                        if (imageUri != null || imageBitmap != null) {
                            //check write permission
                            if(writePermissionCheck(context,writePermission)){
                                onNext(
                                    viewmodel = viewmodel,
                                    navController = navController,
                                    data = MyData(
                                        avatar = imageUri?.toString() ?: imageBitmap?.toUri(
                                            context)
                                            .toString(),
                                        name = name
                                    ),
                                    context = context
                                )
                            }
                        } else {
                            Toast.makeText(context,
                                context.getString(R.string.please_pick_or_take_picture),
                                Toast.LENGTH_SHORT).show()
                        }
                    }
                ) {
                    Text(stringResource(R.string.next))
                }
                Spacer(modifier = Modifier.width(MaterialTheme.dimen.small))
                Button(
                    modifier = Modifier.weight(1f),
                    onClick = {
                        if (palindrom != "") {
                            viewmodel.checkIsSintancePalindrome(palindrom) { isPalindrom ->
                                if (isPalindrom) {
                                    Toast.makeText(context,
                                        context.getString(R.string.s_is_palindrom, palindrom),
                                        Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(context,
                                        context.getString(R.string.s_is_not_palindrom, palindrom),
                                        Toast.LENGTH_SHORT).show()
                                }
                            }
                        } else {
                            Toast.makeText(context,
                                context.getString(R.string.no_text_typed),
                                Toast.LENGTH_SHORT).show()
                        }

                    }
                ) {
                    Text(stringResource(R.string.check))
                }
            }

        }
    }
}

@Composable
private fun ScreenOneFooter() {
    Text(stringResource(R.string.copyright), style = MaterialTheme.typography.caption)
}

private fun openCameraOrPickImage(
    context: Context,
    onPick: () -> Unit,
    onOpenCamera: () -> Unit,
): AlertDialog {
    val features = arrayOf("Pick Image", "Open Camera")

    return AlertDialog.Builder(context)
        .setTitle(context.getString(R.string.change_image))
        .setItems(features) { _, index ->
            if (index == 0) {
                onPick()
            } else {
                onOpenCamera()
            }
        }
        .setNegativeButton(context.getString(R.string.cancel)) { _, _ -> }
        .create()
}


private fun onNext(
    viewmodel: ScreenOneViewModel,
    navController: NavController,
    data: MyData,
    context: Context,
) {
    viewmodel.onNext(data) {

        when (it) {
            is Result.SUCCESS -> {
                navController.navigate(Destination.SCREEN_TWO)
            }
            is Result.ERROR -> {
                Toast.makeText(context,
                    "error ${it.message}",
                    Toast.LENGTH_SHORT).show()
            }
            is Result.EXCEPTION -> {
                Toast.makeText(context,
                    "exception ${it.error.message}",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }
}

@ExperimentalPermissionsApi
private fun writePermissionCheck(context: Context, writePermission: PermissionState): Boolean {
    //permission in >= R have different way to request
    //WRITE_EXTERNAL_STORAGE permission totally rejected by OS in >= R
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        val isExternalStorageAccessAvailable =
            Environment.isExternalStorageManager()
        if (!isExternalStorageAccessAvailable) {
            Intent()
                .setAction(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                .also {
                    it.data =
                        Uri.fromParts("package", context.packageName, null)
                    context.startActivity(it)
                }
            false
        } else {
            true
        }
        //if android sdk < R
    } else {
        if (!writePermission.status.isGranted) {
            writePermission.launchPermissionRequest()
            false
        } else {
            true
        }
    }
}

