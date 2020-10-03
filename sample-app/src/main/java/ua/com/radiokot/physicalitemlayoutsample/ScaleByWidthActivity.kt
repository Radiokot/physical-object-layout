package ua.com.radiokot.physicalitemlayoutsample

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import io.github.inflationx.viewpump.ViewPumpContextWrapper

class ScaleByWidthActivity : AppCompatActivity(R.layout.activity_scale_by_width) {
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
    }
}