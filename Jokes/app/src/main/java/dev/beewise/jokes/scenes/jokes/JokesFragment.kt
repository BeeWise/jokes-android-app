package dev.beewise.jokes.scenes.jokes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import dev.beewise.jokes.R
import dev.beewise.jokes.extensions.fragment.runOnUiThread
import java.lang.ref.WeakReference

interface JokesDisplayLogic {
    fun displaySomething(viewModel: JokesModels.Something.ViewModel)
}

public interface JokesFragmentDelegate {
    fun jokesFragmentDismissScene(fragment: JokesFragment)
}

class JokesFragment: Fragment(), JokesDisplayLogic {
    companion object {
        const val TAG: String = "JokesFragmentTag"

        fun fragment(): JokesFragment {
            return JokesFragment()
        }
    }

    var interactor: JokesBusinessLogic? = null

    lateinit var contentView: ConstraintLayout

    var delegate: WeakReference<JokesFragmentDelegate>? = null

    init {
        val presenter = JokesPresenter(this)
        val interactor = JokesInteractor()
        interactor.presenter = presenter
        this.interactor = interactor
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val rootView = inflater.inflate(R.layout.jokes_fragment, container, false)
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
    override fun displaySomething(viewModel: JokesModels.Something.ViewModel) {
        this.runOnUiThread {
            
        }
    }
    //endregion
}
