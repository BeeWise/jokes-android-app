import android.graphics.Color
import android.view.View
import android.widget.Space
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import dev.beewise.jokes.R

class SpaceCell(itemView: View) : RecyclerView.ViewHolder(itemView) {
    class Model(var height: Int)

    var contentView: ConstraintLayout? = null
    var space: Space? = null

    init {
        this.setupSubviews()
        this.setupSubviewsConstraints()
    }

    private fun setupSubviews() {
        this.setupItemView()
        this.setupContentView()
        this.setupSpace()
    }

    private fun setupItemView() {
        this.itemView.setBackgroundColor(Color.TRANSPARENT)
    }

    private fun setupContentView() {
        this.contentView = this.itemView.findViewById(R.id.contentViewId)
        this.contentView?.setBackgroundColor(Color.TRANSPARENT)
    }

    private fun setupSpace() {
        val space = Space(this.itemView.context)
        space.id = View.generateViewId()
        space.layoutParams = ConstraintLayout.LayoutParams(0, 0)
        this.contentView?.addView(space)
        this.space = space
    }

    private fun setupSubviewsConstraints() {
        this.setupSpaceConstraints()
    }

    private fun setupSpaceConstraints() {
        this.space?.updateLayoutParams<ConstraintLayout.LayoutParams> {
            this.topToTop = ConstraintLayout.LayoutParams.PARENT_ID
            this.bottomToBottom = ConstraintLayout.LayoutParams.PARENT_ID
            this.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
            this.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
        }
    }

    fun setModel(model: Model) {
        this.space?.updateLayoutParams<ConstraintLayout.LayoutParams> {
            this.height = model.height
        }
    }
}