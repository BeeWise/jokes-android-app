package dev.beewise.jokes.style

import dev.beewise.jokes.extensions.dp
import kotlin.math.roundToInt

class ApplicationConstraints {
    companion object {
        public fun navigationBarHeight(): Int { return 56F.dp.roundToInt() }
        public fun tabBarViewHeight(): Int { return 56F.dp.roundToInt() }
    }

    enum class constant(val value: Int) {
        x1(1F.dp.roundToInt()),
        x2(2F.dp.roundToInt()),
        x4(4F.dp.roundToInt()),
        x6(6F.dp.roundToInt()),
        x8(8F.dp.roundToInt()),
        x10(10F.dp.roundToInt()),
        x12(12F.dp.roundToInt()),
        x16(16F.dp.roundToInt()),
        x20(20F.dp.roundToInt()),
        x24(24F.dp.roundToInt()),
        x32(32F.dp.roundToInt()),
        x40(40F.dp.roundToInt()),
        x50(50F.dp.roundToInt()),
        x64(64F.dp.roundToInt()),
        x72(72F.dp.roundToInt()),
        x80(80F.dp.roundToInt()),
        x88(88F.dp.roundToInt()),
        x96(96F.dp.roundToInt()),
        x100(100F.dp.roundToInt()),
        x125(125F.dp.roundToInt());
    }

    enum class multiplier(val value: Float) {
        x162(1.62F),
        x150(1.5F),
        x125(1.25F),
        x100(1F),
        x75(0.75F),
        x62(0.62F),
        x50(0.5F),
        x45(0.45F),
        x38(0.38F),
        x25(0.25F);
    }
}