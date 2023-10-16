import org.openrndr.color.ColorRGBa

data class Circle(
    var x: Double,
    var y: Double,
    val xStep: Double = 0.5,
    val yStep: Double = xStep,
    val radiusMin: Double = 2.0,
    val radiusMax: Double = 10.0,
    val radiusStep: Double = 0.5,
    var radius: Double = radiusMin,
    val colour: ColorRGBa = ColorRGBa.GRAY,
    var isRadiusGrowing: Boolean = true,
    var isXGrowing: Boolean = true,
    var isYGrowing: Boolean = true,
    val inertiaMax: Int = 10,
    var inertia: Int = inertiaMax,

)