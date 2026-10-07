package figures
class Rect(
    var x: Int,
    var y: Int,
    var width: Int,
    var height: Int,
    id: Int = 0
) : Figure(id), Movable, Transforming {

    var color: Int = -1
    lateinit var name: String

    constructor(rect: Rect) : this(rect.x, rect.y, rect.width, rect.height, rect.id)

    override fun move(dx: Int, dy: Int) {
        x += dx
        y += dy
    }

    override fun area(): Float {
        return (width * height).toFloat()
    }

    override fun resize(zoom: Int) {
        width *= zoom
        height *= zoom
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
        val temp = width
        width = height
        height = temp
    }

    override fun toString(): String {
        return "Rect(id=$id, x=$x, y=$y, w=$width, h=$height, area=${area()})"
    }
}