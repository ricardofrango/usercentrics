package com.ricardo.usercentrics.composable

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "Dark Mode Portrait",
    device = "spec:parent=pixel_5, orientation=portrait",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(
    name = "Light Mode Portrait",
    device = "spec:parent=pixel_5, orientation=portrait",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "Dark Mode Landscape",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = "spec:parent=pixel_5,orientation=landscape"
)
@Preview(
    name = "Light Mode Landscape",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    device = "spec:parent=pixel_5,orientation=landscape"
)
annotation class UsercentricsPreview