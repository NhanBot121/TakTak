package com.mck.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*

import androidx.compose.material3.OutlinedTextFieldDefaults

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

import com.mck.theme.OverlayWhiteColor
import com.mck.theme.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentSearchBar(
    onClickNav: () -> Unit,
    onClickSearch: () -> Unit,
    placeholder: String = "",
    readOnly: Boolean = true,
    marginHorizontal: Dp = 16.dp,
    marginTop: Dp = 8.dp
) {
    val textFieldColors = OutlinedTextFieldDefaults.colors(
        unfocusedTextColor = Color.White,
        focusedTextColor = Color.White,
        disabledTextColor = Color.Gray,
        errorTextColor = Color.Red,
        unfocusedContainerColor = Color.Transparent,
        focusedContainerColor = Color.Transparent,
        disabledContainerColor = Color.Gray.copy(alpha = 0.2f),
        errorContainerColor = Color.Red.copy(alpha = 0.1f),
        cursorColor = Color.White,
        errorCursorColor = Color.Red,
        focusedBorderColor = Color.White,
        unfocusedBorderColor = OverlayWhiteColor,
        disabledBorderColor = Color.Gray,
        errorBorderColor = Color.Red,
        focusedLeadingIconColor = Color.White,
        unfocusedLeadingIconColor = Color.White,
        disabledLeadingIconColor = Color.Gray,
        errorLeadingIconColor = Color.Red,
        focusedTrailingIconColor = Color.White,
        unfocusedTrailingIconColor = Color.White,
        disabledTrailingIconColor = Color.Gray,
        errorTrailingIconColor = Color.Red,
        focusedLabelColor = Color.White,
        unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
        disabledLabelColor = Color.Gray,
        errorLabelColor = Color.Red,
        focusedPlaceholderColor = Color.White.copy(alpha = 0.7f),
        unfocusedPlaceholderColor = Color.White.copy(alpha = 0.7f),
        disabledPlaceholderColor = Color.Gray,
        errorPlaceholderColor = Color.Red,
        focusedSupportingTextColor = Color.White,
        unfocusedSupportingTextColor = Color.White,
        disabledSupportingTextColor = Color.Gray,
        errorSupportingTextColor = Color.Red,
        focusedPrefixColor = Color.White,
        unfocusedPrefixColor = Color.White,
        disabledPrefixColor = Color.Gray,
        errorPrefixColor = Color.Red,
        focusedSuffixColor = Color.White,
        unfocusedSuffixColor = Color.White,
        disabledSuffixColor = Color.Gray,
        errorSuffixColor = Color.Red
    )

    val shape = RoundedCornerShape(8.dp)
    val interactionSource = remember { MutableInteractionSource() }

    Row(
        modifier = Modifier
            .padding(horizontal = marginHorizontal)
            .padding(top = marginTop),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_back),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(18.dp).clickable {
                onClickNav()
            }
        )

        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(38.dp)
                .clickable { onClickSearch() },
            value = "",
            onValueChange = {},
            singleLine = true,
            readOnly = readOnly,
            decorationBox = { innerTextField ->
                OutlinedTextFieldDefaults.DecorationBox(
                    value = "",
                    innerTextField = innerTextField,
                    enabled = true,
                    leadingIcon = {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_search),
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(14.dp)
                        )
                    },
                    colors = textFieldColors,
//                    shape = shape,
                    trailingIcon = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Divider(
                                modifier = Modifier
                                    .width(1.dp)
                                    .height(16.dp),
                                thickness = 16.dp,
                                color = OverlayWhiteColor
                            )
                            Text(
                                text = stringResource(id = R.string.search),
                                style = MaterialTheme.typography.labelLarge,
                                color = Color.White
                            )
                        }
                    },
                    placeholder = {
                        Text(
                            text = placeholder,
                            style = MaterialTheme.typography.labelLarge,
                            //color = textFieldColors.unfocusedPlaceholderColor
                        )
                    },
                    singleLine = true,
                    visualTransformation = VisualTransformation.None,
                    interactionSource = interactionSource,
                    contentPadding = PaddingValues(4.dp),
//                    container = {
//                        OutlinedTextFieldDefaults.Container(
//                            interactionSource = interactionSource,
//                            colors = textFieldColors,
//                            shape = shape,
//                            focusedBorderThickness = 1.dp,
//                            unfocusedBorderThickness = 1.dp,
//                            enabled = true,
//                            isError = false
//                        )
//                    }
                )
            }
        )
    }
}
