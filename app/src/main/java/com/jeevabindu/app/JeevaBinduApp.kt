package com.jeevabindu.app

import android.app.Application

class JeevaBinduApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // App-level initialization
        // In production: Firebase, Analytics, Crash Reporting, etc.
    }
}
