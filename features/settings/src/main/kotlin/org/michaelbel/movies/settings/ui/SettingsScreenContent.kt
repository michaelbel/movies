package org.michaelbel.movies.settings.ui

import android.app.Activity
import android.content.Context
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.michaelbel.movies.core.review.rememberReviewManager
import org.michaelbel.movies.core.review.rememberReviewTask
import org.michaelbel.movies.settings.SettingsViewModel
import org.michaelbel.movies.ui.SystemTheme

@Composable
fun SettingsScreenContent(
    navController: NavController
) {
    val viewModel: SettingsViewModel = hiltViewModel()
    val currentTheme: SystemTheme = viewModel.currentTheme
        .collectAsState(SystemTheme.FollowSystem).value
    val dynamicColors: Boolean = viewModel.dynamicColors.collectAsState(false).value
    var backHandlingEnabled: Boolean by remember { mutableStateOf(false) }
    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmStateChange = { value: ModalBottomSheetValue ->
            if (value == ModalBottomSheetValue.Hidden) {
                backHandlingEnabled = false
            }
            true
        }
    )
    val context: Context = LocalContext.current
    val scope: CoroutineScope = rememberCoroutineScope()
    val reviewManager: ReviewManager = rememberReviewManager()
    val reviewInfo: ReviewInfo? = rememberReviewTask(reviewManager)

    fun showThemeModalBottomSheet() = scope.launch {
        modalBottomSheetState.show()
        backHandlingEnabled = true
    }

    fun hideThemeModalBottomSheet() = scope.launch {
        modalBottomSheetState.hide()
        backHandlingEnabled = false
    }

    fun onLaunchReviewFlow() {
        reviewInfo?.let {
            reviewManager.launchReviewFlow(context as Activity, reviewInfo)
        }
    }

    BackHandler(backHandlingEnabled) {
        if (modalBottomSheetState.isVisible) {
            hideThemeModalBottomSheet()
        }
    }

    Scaffold(
        topBar = {
            SettingsToolbar(
                modifier = Modifier
                    .statusBarsPadding(),
                onNavigationIconClick = {
                    navController.popBackStack()
                }
            )
        }
    ) { paddingValues: PaddingValues ->
        ModalBottomSheetLayout(
            sheetContent = {
                SettingsThemeModalContent(
                    modifier = Modifier
                        .padding(
                            vertical = 8.dp
                        ),
                    themes = viewModel.themes,
                    currentTheme = currentTheme,
                    onThemeSelected = { systemTheme ->
                        viewModel.selectTheme(systemTheme)
                        hideThemeModalBottomSheet()
                    }
                )
            },
            sheetState = modalBottomSheetState,
            sheetShape = RoundedCornerShape(8.dp),
            sheetBackgroundColor = MaterialTheme.colorScheme.surface
        ) {
            Content(
                modifier = Modifier
                    .padding(paddingValues),
                currentTheme = currentTheme,
                isDynamicColorsEnabled = dynamicColors,
                onDynamicColorsCheckedChange = viewModel::setDynamicColors,
                onShowThemeBottomSheet = ::showThemeModalBottomSheet,
                onReviewClick = { onLaunchReviewFlow() }
            )
        }
    }
}

@Composable
private fun Content(
    modifier: Modifier,
    currentTheme: SystemTheme,
    isDynamicColorsEnabled: Boolean,
    onShowThemeBottomSheet: () -> Unit,
    onDynamicColorsCheckedChange: (Boolean) -> Unit,
    onReviewClick: () -> Unit
) {
    Column(
        modifier = modifier
    ) {
        SettingsThemeBox(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clickable {
                    onShowThemeBottomSheet()
                },
            currentTheme = currentTheme
        )

        if (Build.VERSION.SDK_INT >= 31) {
            SettingsDynamicColorsBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clickable {
                        onDynamicColorsCheckedChange(!isDynamicColorsEnabled)
                    },
                isDynamicColorsEnabled = isDynamicColorsEnabled
            )
        }

        SettingsReviewBox(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clickable {
                    onReviewClick()
                }
        )
    }
}