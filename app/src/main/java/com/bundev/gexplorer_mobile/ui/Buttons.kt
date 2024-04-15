package com.bundev.gexplorer_mobile.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CenteredTextButton(
    label: String,
    modifier: Modifier = Modifier,
    subLabel: String = "",
    fontSizeLabel: TextUnit = 20.sp,
    fontSizeSubLabel: TextUnit = (fontSizeLabel.value - 6).sp,
    fontWeightLabel: FontWeight = FontWeight.Normal,
    onClick: () -> Unit,
) {
    val textColor = LocalContentColor.current
    val textStyle = LocalTextStyle.current
    ButtonGenerator(
        label = label,
        subLabel = subLabel,
        modifier = modifier,
        fontSizeLabel = fontSizeLabel,
        fontSizeSubLabel = fontSizeSubLabel,
        textColor = textColor,
        textStyle = textStyle,
        fontWeightLabel = fontWeightLabel,
        shape = ButtonDefaults.textShape,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        onClick()
    }
}

@Composable
fun StackedTextButton(
    label: String,
    modifier: Modifier = Modifier,
    subLabel: String = "",
    textColor: Color = Color.Unspecified,
    enabled: Boolean = true,
    fontSizeLabel: TextUnit = 18.sp,
    fontSizeSubLabel: TextUnit = (fontSizeLabel.value - 6).sp,
    onClick: () -> Unit,
) {
    ButtonGenerator(
        label = label,
        subLabel = subLabel,
        modifier = modifier,
        textColor = textColor,
        enabled = enabled,
        fontSizeLabel = fontSizeLabel,
        fontSizeSubLabel = fontSizeSubLabel
    ) {
        onClick()
    }
}

@Composable
fun IconAndTextButton(
    label: String,
    modifier: Modifier = Modifier,
    subLabel: String = "",
    imageVector: ImageVector,
    imageDescription: String = "",
    fontSizeLabel: TextUnit = 18.sp,
    fontSizeSubLabel: TextUnit = (fontSizeLabel.value - 6).sp,
    onClick: () -> Unit,
) {
    ButtonGenerator(
        label = label,
        subLabel = subLabel,
        modifier = modifier,
        fontSizeLabel = fontSizeLabel,
        fontSizeSubLabel = fontSizeSubLabel,
        imageVector = imageVector,
        imageDescription = imageDescription
    ) {
        onClick()
    }
}

@Composable
private fun ButtonGenerator(
    label: String,
    subLabel: String,
    modifier: Modifier,
    fontSizeLabel: TextUnit,
    fontSizeSubLabel: TextUnit,
    enabled: Boolean = true,
    imageVector: ImageVector? = null,
    imageDescription: String? = null,
    textStyle: TextStyle? = null,
    textColor: Color = Color.Unspecified,
    fontWeightLabel: FontWeight = FontWeight.Normal,
    shape: Shape = RoundedCornerShape(0.dp),
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    onClick: () -> Unit,
) {
    TextButton(
        modifier = modifier,
        shape = shape,
        enabled = enabled,
        onClick = { onClick() }
    ) {
        if (imageVector is ImageVector)
            Icon(
                modifier = Modifier
                    .padding(end = 5.dp)
                    .padding(vertical = 5.dp),
                imageVector = imageVector,
                contentDescription = imageDescription
            )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp),
            horizontalAlignment = horizontalAlignment
        ) {
            Text(
                fontSize = fontSizeLabel,
                fontStyle = textStyle?.fontStyle,
                fontWeight = if (fontWeightLabel != FontWeight.Normal) fontWeightLabel else textStyle?.fontWeight,
                color = textColor,
                text = label
            )
            if (subLabel.isNotEmpty())
                Text(
                    fontSize = fontSizeSubLabel,
                    fontStyle = textStyle?.fontStyle,
                    fontWeight = textStyle?.fontWeight,
                    color = textColor,
                    text = subLabel
                )
        }
    }
}

@Composable
fun ActionButton(imageVector: ImageVector, modifier: Modifier = Modifier, onClick: () -> Unit) {
    SmallFloatingActionButton(
        onClick = { onClick() },
        modifier = modifier
            .width(40.dp)
            .height(40.dp)
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = imageVector,
            contentDescription = null
        )
    }
}