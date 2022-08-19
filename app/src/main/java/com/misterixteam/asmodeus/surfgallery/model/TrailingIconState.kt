package com.misterixteam.asmodeus.surfgallery.model

import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

enum class TrailingIconState(
    val isMaskActive: Boolean,
    val mask: VisualTransformation
) {
    SHOWED_ACTIVE(
        true,
        PasswordVisualTransformation()
    ),
    SHOWED_DISABLE(
        false,
        VisualTransformation.None
    ),
    HIDDEN(
        false,
        VisualTransformation.None
    )
}