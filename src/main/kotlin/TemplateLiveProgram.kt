import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.extra.color.presets.LIGHT_GRAY
import org.openrndr.extra.olive.oliveProgram
import org.openrndr.math.Vector2
import java.lang.Math.random
import kotlin.math.nextDown
import kotlin.math.nextUp
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

            val circleCentres = mutableListOf<Vector2>()
            circles.forEach { circleCentres.add(Vector2(it.x, it.y)) }
            drawer.stroke = lineColour
            drawer.lineLoop(circleCentres)

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
    val stepChoices = listOf(-1.0, 1.0)

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


    if (circle.x <= 0) {
        circle.x = random().nextUp()
    } else if (circle.x >= screenWidth) {
        circle.x = random().nextDown()
    } else circle.x += circle.xStep * stepChoices[Random.nextInt(stepChoices.size)]

    // update y coordinate
    if (circle.y <= 0) {
        circle.y = random().nextUp()
    } else if (circle.y >= screenHeight) {
        circle.y = random().nextDown()
    } else circle.y += circle.yStep * stepChoices[Random.nextInt(stepChoices.size)]
}


fun createCircles(): List<Circle> {
    return listOf(
        Circle(
            x = Random.nextDouble(screenWidth),
            y = Random.nextDouble(screenHeight)
        ),

        Circle(
            x = Random.nextDouble(screenWidth),
            y = Random.nextDouble(screenHeight),
            radiusMax = 15.0,
            colour = ColorRGBa.WHITE
        ),

        Circle(
            x = Random.nextDouble(screenWidth),
            y = Random.nextDouble(screenHeight),
            radiusMax = 20.0,
            colour = ColorRGBa.PINK,
        ),

        Circle(
            x = Random.nextDouble(screenWidth),
            y = Random.nextDouble(screenHeight),
            colour = ColorRGBa.LIGHT_GRAY,
        ),
    )
}