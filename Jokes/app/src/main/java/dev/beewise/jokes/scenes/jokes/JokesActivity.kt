package dev.beewise.jokes.scenes.jokes

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import dev.beewise.jokes.R
import dev.beewise.jokes.extensions.activity.setupFragment
import java.lang.ref.WeakReference

class JokesActivity: AppCompatActivity(), JokesFragmentDelegate {
    private var fragment: WeakReference<JokesFragment>? = null
    var router: JokesRoutingLogic? = null

    var frameLayout: FrameLayout? = null

    companion object {
        fun intent(activity: Activity): Intent {
            return Intent(activity, JokesActivity::class.java)
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
        val router = JokesRouter()
        router.activity = WeakReference(this)
        this.router = router
    }

    private fun setupContentView() {
        this.setContentView(R.layout.activity_jokes)
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
        val fragment = this.setupFragment(JokesFragment.fragment(), JokesFragment.TAG, R.id.frameLayoutId) as? JokesFragment
        fragment?.delegate = WeakReference(this)
        this.fragment = WeakReference(fragment)
    }

    override fun jokesFragmentDismissScene(fragment: JokesFragment) {
        this.router?.dismissActivity()
    }
}
