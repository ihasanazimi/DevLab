package ir.hasanazimi.androidlab.common.helpers

//import androidx.annotation.RawRes
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.graphics.toArgb
//import com.airbnb.lottie.LottieProperty
//import com.airbnb.lottie.SimpleColorFilter
//import com.airbnb.lottie.compose.LottieAnimation
//import com.airbnb.lottie.compose.LottieCompositionSpec
//import com.airbnb.lottie.compose.LottieConstants
//import com.airbnb.lottie.compose.animateLottieCompositionAsState
//import com.airbnb.lottie.compose.rememberLottieComposition
//import com.airbnb.lottie.compose.rememberLottieDynamicProperties
//import com.airbnb.lottie.compose.rememberLottieDynamicProperty
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.request.ImageResult
import coil.transition.CrossfadeTransition
import coil.transition.Transition
import coil.transition.TransitionTarget
import coil.util.DebugLogger
import ir.hasanazimi.android_compose_lab.R
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/*

@Composable
fun LottieAnimationLoader(
    modifier: Modifier = Modifier,
    @RawRes resId: Int,
    repeat: Boolean = true,
    autoStart: Boolean = true,
    color: Color = Color.Unspecified
) {


    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(resId))
    val animationState = animateLottieCompositionAsState(
        composition = composition,
        iterations = if (repeat) LottieConstants.IterateForever else 1,
        isPlaying = autoStart
    )

    val dynamicProperties = rememberLottieDynamicProperties(
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR_FILTER,
            value = SimpleColorFilter(color.toArgb()),
            keyPath = arrayOf("**")
        )
    )

    LottieAnimation(
        modifier = modifier,
        composition = composition,
        progress = { animationState.progress },
        dynamicProperties = dynamicProperties
    )
}
*/



fun getCustomImageLoader(context: Context): ImageLoader {
    return ImageLoader.Builder(context)
        .okHttpClient {
            OkHttpClient.Builder()
                .connectTimeout(8, TimeUnit.SECONDS)
                .readTimeout(8, TimeUnit.SECONDS)
                .build()
        }
        .logger(DebugLogger())
        .build()
}




@Composable
fun ImageLoader(
    modifier: Modifier = Modifier,
    url: Any?,
    contentScale: ContentScale = ContentScale.Crop,
    placeHolderResId: Int = R.drawable.cover_loading,
    errorCover: Int = R.drawable.cover_warning,
    onSuccessListener: () -> Unit = {}
) {
    val context = LocalContext.current

    val finalUrl = when {
        url == null -> null
        url is String && url.isBlank() -> null
        else -> url
    }

    AsyncImage(
        modifier = modifier,
        imageLoader = remember { getCustomImageLoader(context) },
        model = ImageRequest.Builder(context)
            .data(finalUrl)
            .crossfade(256)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .allowHardware(false)
            .transitionFactory(AlwaysCrossfadeTransitionFactory)
            .placeholder(placeHolderResId)
            .error(if (errorCover != -1) errorCover else placeHolderResId)
            .fallback(if (errorCover != -1) errorCover else placeHolderResId)
            .diskCacheKey(finalUrl?.toString())
            .memoryCacheKey(finalUrl?.toString())
            .listener(
                onSuccess = { _, _ ->
                    onSuccessListener()
                    // TODO
                },
                onError = { _, _ ->
                    // TODO
                }
            )
            .build(),
        contentDescription = null,
        contentScale = if (finalUrl == null) ContentScale.Inside else contentScale
    )
}



object AlwaysCrossfadeTransitionFactory : Transition.Factory {
    override fun create(target: TransitionTarget, result: ImageResult): Transition {
        return CrossfadeTransition(
            target = target,
            result = result,
            durationMillis = 300
        )
    }
}