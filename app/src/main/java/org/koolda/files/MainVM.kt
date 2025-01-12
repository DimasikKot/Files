package org.koolda.files

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import org.koolda.files.expect.Database
import org.koolda.files.serializable.CatalogItem
import org.koolda.files.serializable.Link
import org.koolda.files.serializable.Note
import org.koolda.files.serializable.Server
import org.koolda.files.server.createApi

class MainVM(private val datastore: Database, val navController: NavHostController) : ViewModel() {
    private var _serverIP by mutableStateOf(datastore.getString("server_ip", "192.168.0.2:8888"))
    private var _connectStatus by mutableStateOf(datastore.getString("connectStatus", "true"))
    private var _debugIsView by mutableStateOf(datastore.getBoolean("debugIsView", false))
    private var _debugTransparent by mutableFloatStateOf(datastore.getFloat("debugTransparent", 1f))
    private var _api = createApi(_serverIP)
    private var _listCatalogItems by mutableStateOf(emptyList<CatalogItem>())
    private var _listNotes by mutableStateOf(emptyList<Note>())
    private var _diskForRequest by mutableStateOf("")
    private var _pathForRequest by mutableStateOf("")

    var currentFile by mutableStateOf("")

    private fun listCatalogItemsUpdate() {
        _listCatalogItems = emptyList()
        currentFile = ""
    }

    var debugIsView: Boolean
        get() = _debugIsView
        set(value) {
            viewModelScope.launch {
                _debugIsView = value
                datastore.setBoolean("debugIsView", value)
            }
        }
    var debugTransparent: Float
        get() = _debugTransparent
        set(value) {
            viewModelScope.launch {
                _debugTransparent = value
                datastore.setFloat("debugTransparent", value)
            }
        }
    var serverIP: String
        get() {
            return _serverIP
        }
        set(value) {
            viewModelScope.launch {
                _serverIP = value
                _api = createApi(_serverIP)
                connectStatus
                datastore.setString("server_ip", value)
            }
        }
    val connectStatus: String
        get() {
            viewModelScope.launch {
                try {
                    val answer = _api.getConnectStatus()
                    _connectStatus = answer
                } catch (e: Exception) {
                    _connectStatus = "[ERROR] $e"
                }
            }
            return _connectStatus
        }
    val listCatalogItems: List<CatalogItem>
        get() {
            viewModelScope.launch {
                if (_diskForRequest.isEmpty()) {
                    try {
                        val answer = _api.getDisks()
                        _listCatalogItems = answer
                    } catch (_: Exception) {
                        _listCatalogItems = emptyList()
                        connectStatus
                    }
                } else {
                    if (_listCatalogItems.isEmpty()) {
                        try {
                            val answer = _api.getCatalogFromPath(
                                disk = _diskForRequest, path = _pathForRequest
                            )
                            _listCatalogItems = answer
                        } catch (_: Exception) {
                            _listCatalogItems = emptyList()
                            connectStatus
                        }
                    }
                }
            }
            return _listCatalogItems
        }
    val listNotes: List<Note>
        get() {
            viewModelScope.launch {
                if (_diskForRequest == "") {
                    _listNotes = listOf(
                        Note(
                            link = Link(
                                disk = "C",
                                path = "Users:kozhu:AndroidStudioProjects:Files:app:build:outputs:apk:debug",
                                file = "app-debug.apk"
                            ), server = Server(
                                ip = "192.168.0.4:8888"
                            )
                        ), Note(
                            link = Link(
                                disk = "C",
                                path = "Users:kozhu:AndroidStudioProjects:Files:app:build:outputs:apk:debug",
                                file = "app-debug.apk"
                            ), server = Server(
                                ip = "192.168.0.16:8888"
                            )
                        ), Note(
                            link = Link(
                                disk = "C",
                                path = "Users:murka:AndroidStudioProjects:Files:app:build:outputs:apk:debug",
                                file = "app-debug.apk"
                            ), server = Server(
                                ip = "192.168.0.24:8888"
                            )
                        )
                    ).filter { it.server.ip == _serverIP }
                } else {
                    _listNotes = emptyList()
                }
            }
            return _listNotes
        }
    var diskForRequest: String
        get() = _diskForRequest
        set(value) {
            _diskForRequest = value
            listCatalogItemsUpdate()
        }
    var pathForRequest: String
        get() {
            return _pathForRequest
        }
        set(value) {
            if (value == ":") {
                val splited = _pathForRequest.split(":")
                if (splited.size > 1) {
                    _pathForRequest = splited.dropLast(1).joinToString(":")
                } else {
                    if (_pathForRequest != "") {
                        _pathForRequest = ""
                    } else {
                        _diskForRequest = ""
                    }
                }
            } else {
                if (_pathForRequest == "") {
                    _pathForRequest = value
                } else {
                    _pathForRequest += ":$value"
                }
            }
            listCatalogItemsUpdate()
        }
}

//@Composable
//fun SharedTransitionLayoutExample() {
//    var isDragging by remember { mutableStateOf(false) }
//    val offsetX = remember { Animatable(0f) }
//    val offsetY = remember { Animatable(0f) }
//    var duration by remember { mutableIntStateOf(500) }
//
//    val scope = rememberCoroutineScope()
//    Box(modifier = Modifier
//        .fillMaxSize()
//        .pointerInput(Unit) {
//            detectDragGestures(onDragStart = { isDragging = true },
//                onDragEnd = { isDragging = false }) { change, dragAmount ->
//                change.consume()
//                if (isDragging) {
//                    // Обновляем значение смещения и масштаба
//                    scope.launch {
//                        offsetY.snapTo(offsetY.value + dragAmount.y)
//                        offsetX.snapTo(offsetX.value + dragAmount.x)
//                    }
//                }
//            }
//        }
//        .graphicsLayer(
//            translationX = offsetX.value, translationY = offsetY.value
//        )) {
//        // Ваш контент здесь, например:
//        Button(onClick = { duration += 500 }) {
//            Text("Duration Millis: $duration")
//        }
//    }
//    // Анимация возврата при отпускании пальца
//    LaunchedEffect(isDragging) {
//        if (!isDragging) {
//            // Анимируем возврат к исходному состоянию
//            offsetX.animateTo(0f, animationSpec = tween(durationMillis = duration/2))
//            offsetY.animateTo(0f, animationSpec = tween(durationMillis = duration/2))
//        }
//    }
//}

//@OptIn(ExperimentalSharedTransitionApi::class)
//@Composable
//fun UiMainContent(
//    modifier: Modifier = Modifier,
//    sharedTransitionScope: SharedTransitionScope,
//    animatedVisibilityScope: AnimatedVisibilityScope,
//) {
//    with(sharedTransitionScope) {
//        Row {
//            Spacer(Modifier.size(100.dp))
//            Text(
//                ":D",
//                Modifier.sharedElement(rememberSharedContentState("txt"), animatedVisibilityScope)
//            )
//            Spacer(Modifier.size(200.dp))
//            Image(
//                painter = painterResource(Res.drawable.xlsx),
//                contentDescription = "Cupcake",
//                modifier = Modifier.sharedElement(
//                    rememberSharedContentState(key = "image"),
//                    animatedVisibilityScope = animatedVisibilityScope
//                ).clip(CircleShape),
//                contentScale = ContentScale.Crop
//            )
//        }
//    }
//}
//
//@OptIn(ExperimentalSharedTransitionApi::class)
//@Composable
//fun UiDetailsContent(
//    modifier: Modifier = Modifier,
//    sharedTransitionScope: SharedTransitionScope,
//    animatedVisibilityScope: AnimatedVisibilityScope,
//) {
//    with(sharedTransitionScope) {
//        Column {
//            Spacer(Modifier.size(100.dp))
//            Image(
//                painter = painterResource(Res.drawable.xlsx),
//                contentDescription = "Cupcake",
//                modifier = Modifier.sharedElement(
//                    rememberSharedContentState(key = "image"),
//                    animatedVisibilityScope = animatedVisibilityScope
//                ).size(100.dp).clip(CircleShape),
//                contentScale = ContentScale.Crop
//            )
//            Spacer(Modifier.size(100.dp))
//            Text(
//                ";D",
//                Modifier.sharedElement(rememberSharedContentState("txt"), animatedVisibilityScope)
//            )
//        }
//    }
//}