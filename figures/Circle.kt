package figures
import kotlin.math.PI

class Circle(
    var x: Int,
    var y: Int,
    var radius: Int,
    id: Int = 0
) : Figure(id), Movable, Transforming {

    override fun move(dx: Int, dy: Int) {
        x += dx
        y += dy
    }

    override fun area(): Float {
        return (PI * radius * radius).toFloat()
    }

    override fun resize(zoom: Int) {
        radius *= zoom
    }

    override fun rotate(direction: RotateDirection, centerX: Int, centerY: Int) {
        val relX = x - centerX
        val relY = y - centerY

        when (direction) {
            RotateDirection.Clockwise -> {
                x = centerX + relY
                y = centerY - relX
            }
            RotateDirection.CounterClockwise -> {
                x = centerX - relY
                y = centerY + relX
            }
        }
    }

    override fun toString(): String {
        return "Circle(id=$id, x=$x, y=$y, r=$radius, area=${area()})"
    }
}