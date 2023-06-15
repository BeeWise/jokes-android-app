package dev.beewise.jokes.scenes.jokes

import android.graphics.drawable.Drawable
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import dev.beewise.jokes.extensions.font_span.FontSpan
import dev.beewise.jokes.style.ApplicationConstraints
import dev.beewise.jokes.style.ApplicationStyle
import dev.beewise.jokes.style.withAlpha

public class JokesStyle {
    companion object {
        val instance = JokesStyle()
    }

    var contentViewModel: ContentViewModel = ContentViewModel()
    var recyclerViewModel: RecyclerViewModel = RecyclerViewModel()
    var jokeCellModel: JokeCellModel = JokeCellModel()
    var cellModel: CellModel = CellModel()

    class ContentViewModel {
        fun backgroundColor(): Int { return ApplicationStyle.instance.transparent() }
    }

    class RecyclerViewModel {
        fun backgroundColor(): Int { return ApplicationStyle.instance.backgroundColor() }
        fun activityIndicatorColor(): Int { return ApplicationStyle.instance.primary() }
        fun noMoreItemsSpans(): ArrayList<Any> {
            return arrayListOf(
                ForegroundColorSpan(ApplicationStyle.instance.gray()),
                FontSpan(ApplicationStyle.instance.regular()),
                AbsoluteSizeSpan(14, true)
            )
        }
        fun errorSpans(): ArrayList<Any> {
            return arrayListOf(
                ForegroundColorSpan(ApplicationStyle.instance.secondary()),
                FontSpan(ApplicationStyle.instance.regular()),
                AbsoluteSizeSpan(14, true)
            )
        }
    }

    class CellModel {
        fun backgroundColor(): Int { return ApplicationStyle.instance.transparent() }
    }

    class JokeCellModel {
        val avatarBorderRadius: Float = ApplicationConstraints.constant.x20.value.toFloat()
        fun  avatarActivityColor(): Int { return ApplicationStyle.instance.primary() }
        fun avatarBackgroundColor(): Int { return ApplicationStyle.instance.transparent() }
        fun avatarBorderColor(): Int { return ApplicationStyle.instance.white().withAlpha(0.5F) }
        val avatarBorderWidth: Int = ApplicationConstraints.constant.x1.value
        val avatarMargin: Int = ApplicationConstraints.constant.x0.value
        fun avatarPlaceholder(): Drawable { return ApplicationStyle.instance.userAvatarPlaceholderSmallImage(ApplicationStyle.ImageType.drawable) }

        fun nameSpans(): ArrayList<Any> {
            return arrayListOf(
                ForegroundColorSpan(ApplicationStyle.instance.primary()),
                FontSpan(ApplicationStyle.instance.bold()),
                AbsoluteSizeSpan(17, true)
            )
        }
        fun usernameSpans(): ArrayList<Any> {
            return arrayListOf(
                ForegroundColorSpan(ApplicationStyle.instance.gray()),
                FontSpan(ApplicationStyle.instance.regular()),
                AbsoluteSizeSpan(14, true)
            )
        }
        fun textSpans(): ArrayList<Any> {
            return arrayListOf(
                ForegroundColorSpan(ApplicationStyle.instance.primary()),
                FontSpan(ApplicationStyle.instance.regular()),
                AbsoluteSizeSpan(17, true)
            )
        }
        fun answerSpans(): ArrayList<Any> {
            return arrayListOf(
                ForegroundColorSpan(ApplicationStyle.instance.primary()),
                FontSpan(ApplicationStyle.instance.regular()),
                AbsoluteSizeSpan(17, true)
            )
        }

        fun likeCountActivityColor(): Int { return ApplicationStyle.instance.gray() }
        fun likeCountImage(): Drawable { return ApplicationStyle.instance.likeSmallImage(ApplicationStyle.ImageType.drawable) }
        fun unselectedLikeCountBackgroundColor(): Int { return ApplicationStyle.instance.transparent() }
        fun unselectedLikeCountTintColor(): Int { return ApplicationStyle.instance.gray() }
        fun unselectedLikeCountSpans(): ArrayList<Any> {
            return arrayListOf(
                ForegroundColorSpan(ApplicationStyle.instance.gray()),
                FontSpan(ApplicationStyle.instance.regular()),
                AbsoluteSizeSpan(16, true)
            )
        }

        fun dislikeCountActivityColor(): Int { return ApplicationStyle.instance.primary() }
        fun dislikeCountImage(): Drawable { return ApplicationStyle.instance.dislikeSmallImage(ApplicationStyle.ImageType.drawable) }
        fun unselectedDislikeCountBackgroundColor(): Int { return ApplicationStyle.instance.transparent() }
        fun unselectedDislikeCountTintColor(): Int { return ApplicationStyle.instance.gray() }
        fun unselectedDislikeCountSpans(): ArrayList<Any> {
            return arrayListOf(
                ForegroundColorSpan(ApplicationStyle.instance.gray()),
                FontSpan(ApplicationStyle.instance.regular()),
                AbsoluteSizeSpan(16, true)
            )
        }

        fun timeSpans(): ArrayList<Any> {
            return arrayListOf(
                ForegroundColorSpan(ApplicationStyle.instance.gray()),
                FontSpan(ApplicationStyle.instance.oblique()),
                AbsoluteSizeSpan(13, true)
            )
        }
    }
}
