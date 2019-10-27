package com.jmcarcedo.internationbusinessmen.core.ui

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE

fun View.showOrGone(show: Boolean) {
    visibility = if (show) VISIBLE else GONE
}