package com.example.tvexplorer.com.example.tvexplorer.tools

import android.view.View

fun View?.show(show: Boolean) {
    this?.let {
        visibility = if (show) View.VISIBLE else View.GONE
    }
}