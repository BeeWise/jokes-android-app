package dev.beewise.jokes.models.image

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView.ScaleType

class CompoundImage(var url: String? = null,
                    var bitmap: Bitmap? = null,
                    var drawable: Drawable? = null,
                    var scaleType: ScaleType = ScaleType.CENTER_CROP)