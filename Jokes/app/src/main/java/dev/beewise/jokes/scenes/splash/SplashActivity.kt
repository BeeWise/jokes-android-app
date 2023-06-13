package dev.beewise.jokes.scenes.splash

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import dev.beewise.jokes.R
import dev.beewise.jokes.extensions.activity.setupFragment
import java.lang.ref.WeakReference

@SuppressLint("CustomSplashScreen")
class SplashActivity: AppCompatActivity(), SplashFragmentDelegate {
    private var fragment: WeakReference<SplashFragment>? = null
    var router: SplashRoutingLogic? = null

    var frameLayout: FrameLayout? = null

    companion object {
        fun intent(activity: Activity): Intent {
            return Intent(activity, SplashActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.setup()
        this.setupContentView()
        this.setupSubviews()
        this.setupFragment()
    }

    private fun setup() {
        val router = SplashRouter()
        router.activity = WeakReference(this)
        this.router = router
    }

    private fun setupContentView() {
        this.setContentView(R.layout.activity_splash)
    }

    private fun setupSubviews() {
        this.setupActionBar()
        this.setupFrameLayout()
    }

    private fun setupActionBar() {
        this.supportActionBar?.hide()
    }

    private fun setupFrameLayout() {
        this.frameLayout = this.findViewById(R.id.frameLayoutId)
    }

    private fun setupFragment() {
        val fragment = this.setupFragment(SplashFragment.fragment(), SplashFragment.TAG, R.id.frameLayoutId) as? SplashFragment
        fragment?.delegate = WeakReference(this)
        this.fragment = WeakReference(fragment)
    }

    override fun splashFragmentDismissScene(fragment: SplashFragment) {
        this.router?.dismissActivity()
    }
}
