import org.openrndr.color.ColorRGBa
import org.openrndr.math.Vector2
import kotlin.random.Random

data class Circle(
    var x: Double = Random.nextDouble(margin, screenWidth - margin),
    var y: Double = Random.nextDouble(margin, screenHeight - margin),
    val xStep: Double = 0.5,
    val yStep: Double = xStep,
    val radiusMin: Double = 1.0,
    val radiusMax: Double = 5.0,
    val radiusStep: Double = 0.1,
    var radius: Double = radiusMin,
    val colour: ColorRGBa = ColorRGBa.GRAY,
    var isRadiusGrowing: Boolean = true,
    var isXGrowing: Boolean = true,
    var isYGrowing: Boolean = true,
    val inertiaMax: Int = 30,
    var inertiaCounter: Int = inertiaMax,
) {
    fun update() {
        // update growing/shrinking behaviour
        if (this.radius >= this.radiusMax) {
            this.isRadiusGrowing = false
        }
        if (this.radius <= this.radiusMin) {
            this.isRadiusGrowing = true
        }

        // update radius
        if (this.isRadiusGrowing) {
            this.radius += this.radiusStep
        } else {
            this.radius -= this.radiusStep
        }

        // update x, y coordinates
        if (this.inertiaCounter == 0) {
            this.inertiaCounter = this.inertiaMax
            this.isXGrowing = Random.nextBoolean()
            this.isYGrowing = Random.nextBoolean()
        }

        if (this.x !in margin..screenWidth - margin) {
            this.isXGrowing = !this.isXGrowing
        }
        if (this.isXGrowing) {
            this.x += this.xStep
        } else {
            this.x -= this.xStep
        }

        if (this.y !in margin..screenHeight - margin) {
            this.isYGrowing = !this.isYGrowing
        }
        if (this.isYGrowing) {
            this.y += this.yStep
        } else {
            this.y -= this.yStep
        }

        this.inertiaCounter -= 1
    }
}


fun Collection<Circle>.getLineSegments(): MutableList<Vector2> {
    val circleCentres = mutableListOf<Vector2>()
    this.forEach { circleCentres.add(Vector2(it.x, it.y)) }

    val lineSegments = mutableListOf<Vector2>()
    circleCentres.forEachIndexed { index, it ->
        for (i in 0 until index) {
            lineSegments.add(Vector2(circleCentres[i].x, circleCentres[i].y))
            lineSegments.add(Vector2(it.x, it.y))
        }
    }
    return lineSegments
}
