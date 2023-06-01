package com.nacarseven.series.presentation

import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.ComponentActivity
import androidx.annotation.IdRes
import androidx.annotation.MainThread
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding
import java.lang.reflect.Method
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

inline fun <reified T : ViewBinding> Fragment.viewBinding(): ReadOnlyProperty<Fragment, T> =
    FragmentBindingProperty { fragment -> fragment.requireView().bind(T::class.java) }

class FragmentBindingProperty<T : ViewBinding>(
    private val viewBindingCreator: (Fragment) -> T,
) : ReadOnlyProperty<Fragment, T> {

    private var viewBinding: T? = null
    private val lifecycleObserver = BindingLifecycleObserver()

    @MainThread
    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        checkMainThread { "The viewBinding property must be used on main thread." }
        thisRef.viewLifecycleOwner.lifecycle.addObserver(lifecycleObserver)
        return viewBinding ?: viewBindingCreator(thisRef)
            .also { viewBinding = it }
    }

    private inner class BindingLifecycleObserver : DefaultLifecycleObserver {

        @MainThread
        override fun onDestroy(owner: LifecycleOwner) {
            owner.lifecycle.removeObserver(this)
            // Fragment.viewLifecycleOwner call LifecycleObserver.onDestroy() before Fragment.onDestroyView().
            // That's why we need to postpone reset of the viewBinding
            val mainHandler = Handler(Looper.getMainLooper())
            mainHandler.post {
                viewBinding = null
            }
        }
    }
}

inline fun <reified T : ViewBinding> ComponentActivity.viewBinding(
    @IdRes viewBindingRootId: Int
): ReadOnlyProperty<ComponentActivity, T> = ActivityBindingProperty { activity ->
    ActivityCompat.requireViewById<View>(activity, viewBindingRootId).bind(T::class.java)
}

class ActivityBindingProperty<T : ViewBinding>(
    private val viewBindingCreator: (ComponentActivity) -> T,
) : ReadOnlyProperty<ComponentActivity, T> {

    private var viewBinding: T? = null

    @MainThread
    override fun getValue(thisRef: ComponentActivity, property: KProperty<*>): T {
        checkMainThread { "The viewBinding property must be used on main thread." }
        return viewBinding ?: viewBindingCreator(thisRef)
            .also { viewBinding = it }
    }
}

internal fun isMainThread(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        Looper.getMainLooper().isCurrentThread
    } else {
        Looper.myLooper() == Looper.getMainLooper()
    }
}

internal fun checkMainThread(lazyMessage: (() -> Any)? = null) {
    if (!isMainThread()) {
        val message = lazyMessage?.invoke() ?: "This call must be executed on main thread."
        throw IllegalStateException(message.toString())
    }
}

private const val BIND_METHOD_NAME = "bind"

@Suppress("UNCHECKED_CAST")
fun <T : ViewBinding> View.bind(viewBindingClass: Class<T>): T {
    val bindMethod: Method = viewBindingClass.getMethod(BIND_METHOD_NAME, View::class.java)
    return bindMethod.invoke(null, this) as T
}

inline fun <reified T : ViewBinding> View.bind(): T = bind(T::class.java)