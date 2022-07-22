package com.example.tvexplorer.com.example.tvexplorer.tools

import android.content.Context
import android.util.Log
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.example.tvexplorer.BuildConfig

@GlideModule
class TvExplorerGlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        if (BuildConfig.DEBUG) builder.setLogLevel(Log.INFO)
    }
}