import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.extra.color.presets.DARK_GRAY
import org.openrndr.extra.color.presets.LIGHT_GRAY
import org.openrndr.extra.olive.oliveProgram
import kotlin.math.roundToInt
import kotlin.random.Random


/**
 *  All code inside the oliveProgram {} can be changed
 *  while the program is running.
 */

const val screenWidth: Double = 800.0
const val screenHeight: Double = 800.0
const val margin: Double = 40.0
const val sleepTime: Long = 100
val backgroundColour = ColorRGBa.BLACK
val lineColour = ColorRGBa.GRAY


fun main() = application {
    configure {
        width = screenWidth.roundToInt()
        height = screenHeight.roundToInt()
    }
    oliveProgram {
        val circles1 = createCircles(5)
        val circles2 = createCircles(4)

        extend {
            drawer.clear(backgroundColour)

            drawer.stroke = lineColour
            drawer.lineSegments(circles1.getLineSegments())
            drawer.lineSegments(circles2.getLineSegments())

            circles1.circles.forEach() {
                drawer.fill = it.colour
                drawer.circle(it.x, it.y, it.radius)
                updateCircle(it)
            }
            circles2.circles.forEach() {
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
    if (circle.inertiaCounter == 0) {
        circle.inertiaCounter = circle.inertiaMax
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

    circle.inertiaCounter -= 1
}


fun createCircles(numberOfCircles: Int): Circles {
    val circles = mutableListOf<Circle>()
        for (i in 1..numberOfCircles) {
            circles.add(Circle())
        }

    return Circles(circles)
}


    /*return Circles(
        listOf(
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
    )*/



