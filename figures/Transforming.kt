package figures
enum class RotateDirection {
    Clockwise, CounterClockwise
}

interface Transforming {
    fun resize(zoom: Int)
    fun rotate(direction: RotateDirection, centerX: Int, centerY: Int)
}