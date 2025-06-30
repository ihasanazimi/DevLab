package ir.hasanazimi.devlab.common.base

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.hasanazimi.devlab.presentation.theme.AvandTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScreen(
    modifier: Modifier = Modifier,
    title: @Composable (() -> Unit)? = null,
    navigationIcon: @Composable (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            title?.let {
                TopAppBar(
                    title = it,
                    navigationIcon = navigationIcon ?: {},
                    actions = actions,
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                        actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        },
        content = { paddingValues ->
            content(paddingValues)
        }
    )
}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "FA")
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "FA")
@Composable
fun PreviewBaseScreen() {
    AvandTheme {
        BaseScreen(
            title = {
                Text(
                    text = "صفحه اصلی",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Start
                )
            },
            navigationIcon = {
                IconButton(onClick = { /* بازگشت */ }) {
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "بازگشت"
                    )
                }
            },
            actions = {
                IconButton(onClick = { /* تنظیمات */ }) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "تنظیمات"
                    )
                }

                IconButton(onClick = { /* جستجو */ }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "جستجو"
                    )
                }

            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Text(
                    text = "محتوای صفحه اصلی",
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    }
}