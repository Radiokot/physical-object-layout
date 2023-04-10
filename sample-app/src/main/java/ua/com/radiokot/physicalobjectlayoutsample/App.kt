package ua.com.radiokot.physicalobjectlayoutsample

import android.app.Application
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        initCalligraphy()
    }

    private fun initCalligraphy() {
        ViewPump.init(ViewPump.builder()
                .addInterceptor(
                        CalligraphyInterceptor(CalligraphyConfig.Builder()
                                .setFontAttrId(io.github.inflationx.calligraphy3.R.attr.fontPath)
                                .build())
                )
                .build())
    }
}