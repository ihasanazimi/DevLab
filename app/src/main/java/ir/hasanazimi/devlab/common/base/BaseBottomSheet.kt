package ir.hasanazimi.devlab.common.base

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseBottomSheet(
    onDismissRequest: () -> Unit,
    sheetState: SheetState = rememberModalBottomSheetState(),
    content: @Composable () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        content = {
            content()
        }
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true , uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showBackground = true , uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun BaseBottomSheetPreview() {
    Surface {
        BaseBottomSheet(
            onDismissRequest = { /* بستن Bottom Sheet */ }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = "انتخاب شهر",
                    style = MaterialTheme.typography.titleLarge,
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "لطفاً یک شهر را انتخاب کنید.",
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    }
}