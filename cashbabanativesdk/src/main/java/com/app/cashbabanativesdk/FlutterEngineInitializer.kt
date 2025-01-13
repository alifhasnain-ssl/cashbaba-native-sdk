package com.app.cashbabanativesdk

import android.content.Context
import androidx.startup.Initializer
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.dart.DartExecutor

class FlutterEngineInitializer: Initializer<Boolean> {
    override fun create(context: Context): Boolean {
        val flutterEngine: FlutterEngine = FlutterEngine(context)
        // Start executing Dart code
        flutterEngine.dartExecutor.executeDartEntrypoint(
            DartExecutor.DartEntrypoint.createDefault()
        )
        return true
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf()
    }

}