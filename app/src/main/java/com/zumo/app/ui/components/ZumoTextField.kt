package com.zumo.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zumo.app.ui.theme.*

@Composable
fun ZumoTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    accent: AccentPalette = ZumoAccents.default,
    keyboardType: KeyboardType = KeyboardType.Text,
    imeAction: ImeAction = ImeAction.Next,
    isPassword: Boolean = false,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    val shape = RoundedCornerShape(12.dp)
    val isFocused = value.isNotEmpty()

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp)
            .clip(shape)
            .background(ZumoDarkSurface.copy(alpha = 0.5f))
            .then(
                if (isFocused) {
                    Modifier.drawBehind {
                        drawRoundRect(
                            color = accent.glow,
                            cornerRadius = CornerRadius(12.dp.toPx()),
                            size = size.copy(
                                width = size.width + 6.dp.toPx(),
                                height = size.height + 6.dp.toPx()
                            ),
                            topLeft = Offset(-3.dp.toPx(), -3.dp.toPx())
                        )
                    }
                } else Modifier
            )
            .border(
                width = 1.dp,
                color = if (isFocused) accent.primary.copy(alpha = 0.3f) else Color.White.copy(alpha = 0.06f),
                shape = shape
            )
            .padding(horizontal = 16.dp)
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier.fillMaxSize(),
            textStyle = TextStyle(
                color = ZumoTextPrimary,
                fontSize = 15.sp
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = imeAction
            ),
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            cursorBrush = SolidColor(accent.primary),
            singleLine = true,
            decorationBox = { innerTextField ->
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = androidx.compose.ui.Alignment.CenterStart
                ) {
                    if (value.isEmpty()) {
                        Text(
                            text = placeholder,
                            color = ZumoTextTertiary,
                            fontSize = 15.sp
                        )
                    }
                    innerTextField()
                }
            }
        )

        if (trailingIcon != null) {
            trailingIcon()
        }
    }
}
