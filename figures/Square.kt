package figures
class Square(
    var x: Int,
    var y: Int,
    var side: Int,
    id: Int = 0
) : Figure(id), Movable, Transforming {

    override fun move(dx: Int, dy: Int) {
        x += dx
        y += dy
    }

    override fun area(): Float {
        return (side * side).toFloat()
    }

    override fun resize(zoom: Int) {
        side *= zoom
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
        return "Square(id=$id, x=$x, y=$y, side=$side, area=${area()})"
    }
}