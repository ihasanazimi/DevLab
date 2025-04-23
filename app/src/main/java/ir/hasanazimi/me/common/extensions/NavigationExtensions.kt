package ir.hasanazimi.me.common.extensions

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.fragment.FragmentNavigator

fun NavController.safeNavigate(direction: NavDirections, navOptions: NavOptions? = null) {
    runCatching {
        currentDestination?.getAction(direction.actionId)?.let {
            navigate(direction.actionId, direction.arguments, navOptions)
        }
    }.onFailure {
        Log.d("", "safeNavigate: ${it.message}")
    }
}

fun NavController.safeNavigateByExtra(
    direction: NavDirections,
    fragmentNavigatorExtras: FragmentNavigator.Extras? = null
) {
    currentDestination?.getAction(direction.actionId)?.let {
        runCatching {
            navigate(direction.actionId, direction.arguments, null, fragmentNavigatorExtras)
        }.onFailure {
            Log.d("", "safeNavigateByExtra: ${it.message}")
        }
    }
}
