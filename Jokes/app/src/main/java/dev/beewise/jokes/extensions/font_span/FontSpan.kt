package dev.beewise.jokes.extensions.font_span

import android.graphics.Typeface
import android.text.TextPaint
import android.text.style.MetricAffectingSpan

class FontSpan(var typeface: Typeface): MetricAffectingSpan() {
    override fun updateMeasureState(p0: TextPaint) {
        p0.typeface = this.typeface
    }

    override fun updateDrawState(p0: TextPaint?) {
        p0?.typeface = this.typeface
    }
}
