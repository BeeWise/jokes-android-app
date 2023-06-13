package dev.beewise.jokes.scenes.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import dev.beewise.jokes.R
import dev.beewise.jokes.extensions.fragment.runOnUiThread
import java.lang.ref.WeakReference

interface SplashDisplayLogic {
    fun displaySomething(viewModel: SplashModels.Something.ViewModel)
}

public interface SplashFragmentDelegate {
    fun splashFragmentDismissScene(fragment: SplashFragment)
}

class SplashFragment: Fragment(), SplashDisplayLogic {
    companion object {
        const val TAG: String = "SplashFragmentTag"

        fun fragment(): SplashFragment {
            return SplashFragment()
        }
    }

    var interactor: SplashBusinessLogic? = null

    lateinit var contentView: ConstraintLayout

    var delegate: WeakReference<SplashFragmentDelegate>? = null

    init {
        val presenter = SplashPresenter(this)
        val interactor = SplashInteractor()
        interactor.presenter = presenter
        this.interactor = interactor
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView = inflater.inflate(R.layout.splash_fragment, container, false)
        this.findSubviews(rootView)
        this.setupSubviews()
        this.setupSubviewsConstraints()
        return rootView
    }

    // region Subviews
    private fun findSubviews(view: View) {
        this.contentView = view.findViewById(R.id.contentViewId)
    }

    private fun setupSubviews() {
        
    }
    //endregion

    //region Constraints
    private fun setupSubviewsConstraints() {
        
    }
    // endregion

    //region Display logic
    override fun displaySomething(viewModel: SplashModels.Something.ViewModel) {
        this.runOnUiThread {
            
        }
    }
    //endregion
}
