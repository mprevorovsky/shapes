import org.openrndr.application
import org.openrndr.color.ColorRGBa
import org.openrndr.extra.noise.random
import org.openrndr.extra.olive.oliveProgram
import kotlin.math.roundToInt


const val screenWidth: Double = 800.0
const val screenHeight: Double = 800.0
const val margin: Double = 40.0
const val sleepTime: Long = 100
val backgroundColour = ColorRGBa.BLACK

/**
 *  All code inside the oliveProgram {} can be changed
 *  while the program is running.
 */

fun main() = application {
    configure {
        width =  screenWidth.roundToInt()
        height = screenHeight.roundToInt()
    }
    oliveProgram {
        val circleGroups = listOf(
            MutableList(5) { Circle(colour = ColorRGBa.GRAY, radiusMax = random(1.0, 5.0)) },
            MutableList(5) { Circle(colour = ColorRGBa.CYAN, radiusMax = random(1.0, 5.0)) },
            MutableList(6) { Circle(colour = ColorRGBa.PINK, radiusMax = random(1.0, 5.0)) },
        )

        extend {
            drawer.clear(backgroundColour)

            // draw lines
            val lineColours = listOf(ColorRGBa.GRAY, ColorRGBa.CYAN, ColorRGBa.PINK)
            circleGroups.forEachIndexed { index, it ->
                drawer.stroke = lineColours[index]
                drawer.lineSegments(it.getLineSegments())
            }

            // draw circles and update their state for next round
            circleGroups.forEach { group ->
                group.forEach {circle ->
                    drawer.stroke = circle.colour
                    drawer.fill = circle.colour
                    drawer.circle(circle.x, circle.y, circle.radius)
                    circle.update()
                }
            }

            Thread.sleep(sleepTime)
        }
    }
}


fun createCircles(numberOfCircles: Int): MutableList<Circle> {
    val circles = mutableListOf<Circle>()
        for (i in 1..numberOfCircles) {
            circles.add(Circle())
        }

    return circles
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



