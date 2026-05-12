package com.coding.higamerapp.feature_terms

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.coding.higamerapp.R
import com.coding.higamerapp.common.util.Screen
import com.coding.higamerapp.ui.theme.BabyBlue
import com.coding.higamerapp.ui.theme.DarkGray
import com.coding.higamerapp.ui.theme.RedOrange
import com.coding.higamerapp.ui.theme.Typography
import kotlinx.coroutines.launch

@Composable
fun TermsScreen(
    viewModel: TermsViewModel = hiltViewModel(),
    navController: NavController
) {
    val checkTerms by remember { (viewModel.checkTerms) }
    val scope = rememberCoroutineScope()

    Dialog(onDismissRequest = {
    }) {
            Column(
                Modifier
                    .fillMaxHeight(0.8f).padding(5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.terms_conditions_title),
                    style = Typography.h3,
                    color = RedOrange
                )
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .weight(1f)
                        .border(BorderStroke(1.dp, RedOrange), RoundedCornerShape(10.dp))
                ) {
                    Column(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .padding(10.dp)
                    ) {
                        Text(
                            text = stringResource(id = R.string.terms_conditions),
                            modifier = Modifier.padding(5.dp)
                        )
                        AnnotatedClickableText(
                            text = "Google Play Services",
                            stringResource(id = R.string.link1)
                        )
                        AnnotatedClickableText(
                            text = "AdMob",
                            resourceId = stringResource(id = R.string.link2)
                        )
                        AnnotatedClickableText(
                            text = "Google Analytics for Firebase",
                            resourceId = stringResource(id = R.string.link3)
                        )
                        AnnotatedClickableText(
                            text = "Firebase Crashlytics",
                            resourceId = stringResource(id = R.string.link4)
                        )
                        AnnotatedClickableText(
                            text = "Facebook",
                            resourceId = stringResource(id = R.string.link5)
                        )
                        Text(
                            text = stringResource(id = R.string.terms_conditions_2),
                            modifier = Modifier.padding(5.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.changes_terms),
                            modifier = Modifier.padding(5.dp),
                            color = RedOrange
                        )
                        Text(
                            text = stringResource(id = R.string.changes),
                            modifier = Modifier.padding(5.dp),
                        )
                        Text(
                            text = stringResource(id = R.string.contacts_terms),
                            modifier = Modifier.padding(5.dp),
                            color = RedOrange
                        )
                        Text(
                            text = stringResource(id = R.string.contacts),
                            modifier = Modifier.padding(5.dp),
                        )
                    }
                }

                Spacer(modifier = Modifier.padding(3.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                )
                {
                    Text(
                        text = stringResource(id = R.string.agree_terms),
                        modifier = Modifier.padding(5.dp, 0.dp)
                    )
                    Checkbox(
                        checked = checkTerms,
                        onCheckedChange = { viewModel.setCheckTerms(checkTerms) },
                        colors = CheckboxDefaults.colors(
                            checkedColor = RedOrange,
                            uncheckedColor = Color.White,
                            checkmarkColor = DarkGray
                        )
                    )
                }
                Spacer(modifier = Modifier.padding(10.dp))
                Button(
                    onClick = {
                        if (checkTerms) {
                            scope.launch {
                                viewModel.saveTermsDataStore()
                                navController.navigate(Screen.LoginScreen.route)
                                navController.popBackStack()
                            }
                        }
                    },
                    colors = ButtonDefaults.outlinedButtonColors(
                        backgroundColor = RedOrange,
                        contentColor = Color.Black
                    )
                ) {
                    Text(
                        text = stringResource(id = R.string.accept),
                        style = Typography.button
                    )
                }
            }
    }
}

@Composable
fun AnnotatedClickableText(text: String, resourceId: String) {
    val uriHandler = LocalUriHandler.current
    val annotatedText = buildAnnotatedString {
        // We attach this *URL* annotation to the following content
        // until `pop()` is called
        pushStringAnnotation(
            tag = "URL",
            annotation = resourceId
        )
        withStyle(
            style = SpanStyle(
                color = BabyBlue,
                fontWeight = FontWeight.Bold
            )
        ) {
            append(text)
        }
        pop()
    }
    ClickableText(
        modifier = Modifier.padding(5.dp),
        text = annotatedText,
        onClick = { offset ->
            // We check if there is an *URL* annotation attached to the text
            // at the clicked position
            annotatedText.getStringAnnotations(
                tag = "URL", start = offset,
                end = offset
            )
                .firstOrNull()?.let { annotation ->
                    // If yes, we log its value
                    uriHandler.openUri(annotation.item)

                }
        }
    )
}