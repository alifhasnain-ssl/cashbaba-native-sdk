package com.example.testaar

import android.app.Application
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.dart.DartExecutor

class MyApplication : Application() {

    // Declare a FlutterEngine
    var flutterEngine: FlutterEngine? = null

    override fun onCreate() {
        super.onCreate()

        // Initialize the Flutter engine
        flutterEngine = FlutterEngine(this)

        // Start executing Dart code
        flutterEngine?.dartExecutor?.executeDartEntrypoint(
            DartExecutor.DartEntrypoint.createDefault()
        )
    }
}
