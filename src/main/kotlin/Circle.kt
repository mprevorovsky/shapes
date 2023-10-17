import org.openrndr.color.ColorRGBa
import org.openrndr.math.Vector2
import kotlin.random.Random

data class Circle(
    var x: Double = Random.nextDouble(margin, screenWidth - margin),
    var y: Double = Random.nextDouble(margin, screenHeight - margin),
    val xStep: Double = 0.5,
    val yStep: Double = xStep,
    val radiusMin: Double = 2.0,
    val radiusMax: Double = 5.0,
    val radiusStep: Double = 0.1,
    var radius: Double = radiusMin,
    val colour: ColorRGBa = ColorRGBa.GRAY,
    var isRadiusGrowing: Boolean = true,
    var isXGrowing: Boolean = true,
    var isYGrowing: Boolean = true,
    val inertiaMax: Int = 30,
    var inertiaCounter: Int = inertiaMax,
    )


data class Circles(
    val circles: Collection<Circle>,
) {
    fun getLineSegments(): MutableList<Vector2> {
        val circleCentres = mutableListOf<Vector2>()
        this.circles.forEach { circleCentres.add(Vector2(it.x, it.y)) }

        val lineSegments = mutableListOf<Vector2>()
        circleCentres.forEachIndexed { index, it ->
            for (i in 0 until index) {
                lineSegments.add(Vector2(circleCentres[i].x, circleCentres[i].y))
                lineSegments.add(Vector2(it.x, it.y))
            }
        }
        return lineSegments
    }
}