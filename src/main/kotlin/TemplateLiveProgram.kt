import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.extra.color.presets.DARK_GRAY
import org.openrndr.extra.color.presets.LIGHT_GRAY
import org.openrndr.extra.olive.oliveProgram
import org.openrndr.math.Vector2
import kotlin.math.roundToInt
import kotlin.random.Random


/**
 *  This is a template for a live program.
 *
 *  It uses oliveProgram {} instead of program {}. All code inside the
 *  oliveProgram {} can be changed while the program is running.
 */

const val screenWidth: Double = 400.0
const val screenHeight: Double = 400.0
const val margin: Double = 40.0


fun main() = application {
    configure {
        width = screenWidth.roundToInt()
        height = screenHeight.roundToInt()
    }
    oliveProgram {
        val sleepTime: Long = 100
        val backgroundColour = ColorRGBa.BLACK
        val lineColour = ColorRGBa.GRAY

        val circles = createCircles()

        extend {
            drawer.clear(backgroundColour)

            drawer.stroke = lineColour
            drawer.lineSegments(getLineSegments(circles))

            circles.forEach {
                drawer.fill = it.colour
                drawer.circle(it.x, it.y, it.radius)
                updateCircle(it)
            }

            Thread.sleep(sleepTime)
        }
    }
}


fun updateCircle(circle: Circle) {
    // update growing/shrinking behaviour
    if (circle.radius >= circle.radiusMax) {
        circle.isRadiusGrowing = false
    }
    if (circle.radius <= circle.radiusMin) {
        circle.isRadiusGrowing = true
    }

    // update radius
    if (circle.isRadiusGrowing) {
        circle.radius += circle.radiusStep
    } else {
        circle.radius -= circle.radiusStep
    }

    // update x, y coordinates
    if (circle.inertia == 0) {
        circle.inertia = circle.inertiaMax
        circle.isXGrowing = Random.nextBoolean()
        circle.isYGrowing = Random.nextBoolean()
    }

    if (circle.x !in margin..screenWidth - margin) {
        circle.isXGrowing = !circle.isXGrowing
    }
    if (circle.isXGrowing) {
        circle.x += circle.xStep
    } else {
        circle.x -= circle.xStep
    }


    if (circle.y !in margin..screenHeight - margin) {
        circle.isYGrowing = !circle.isYGrowing
    }
    if (circle.isYGrowing) {
        circle.y += circle.yStep
    } else {
        circle.y -= circle.yStep
    }


    circle.inertia -= 1
}


fun createCircles(): List<Circle> {
    return listOf(
        Circle(
            x = Random.nextDouble(margin, screenWidth - margin),
            y = Random.nextDouble(margin, screenHeight - margin)
        ),

        Circle(
            x = Random.nextDouble(margin, screenWidth - margin),
            y = Random.nextDouble(margin, screenHeight - margin),
            radiusMax = 10.0,
            colour = ColorRGBa.WHITE
        ),

        Circle(
            x = Random.nextDouble(margin, screenWidth - margin),
            y = Random.nextDouble(margin, screenHeight - margin),
            radiusMax = 8.0,
            colour = ColorRGBa.PINK,
        ),

        Circle(
            x = Random.nextDouble(margin, screenWidth - margin),
            y = Random.nextDouble(margin, screenHeight - margin),
            colour = ColorRGBa.LIGHT_GRAY,
        ),
        Circle(
            x = Random.nextDouble(margin, screenWidth - margin),
            y = Random.nextDouble(margin, screenHeight - margin),
            colour = ColorRGBa.DARK_GRAY,
        ),
    )
}

fun getLineSegments(circles: Collection<Circle>): MutableList<Vector2> {
    val circleCentres = mutableListOf<Vector2>()
    circles.forEach { circleCentres.add(Vector2(it.x, it.y)) }

    val lineLoopSegments = mutableListOf<Vector2>()
    circleCentres.forEachIndexed { index, it ->
        for (i in 0 until index) {
            lineLoopSegments.add(Vector2(circleCentres[i].x, circleCentres[i].y))
            lineLoopSegments.add(Vector2(it.x, it.y))
        }
    }
    return lineLoopSegments
}