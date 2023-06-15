import android.text.SpannableString
import android.text.Spanned

public fun String.toSpannableString(spans: ArrayList<Any>): SpannableString {
    val spannableString = SpannableString(this)
    spans.forEach {
        spannableString.setSpan(it, 0, this.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
    return spannableString
}
