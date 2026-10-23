package robot

enum class Direction {
    UP, DOWN, RIGHT, LEFT
}

class Robot(
    var x: Int,
    var y: Int,
    var direction: Direction
) {
    fun turnLeft() {
        direction = when (direction) {
            Direction.UP -> Direction.LEFT
            Direction.LEFT -> Direction.DOWN
            Direction.DOWN -> Direction.RIGHT
            Direction.RIGHT -> Direction.UP
        }
    }

    fun turnRight() {
        direction = when (direction) {
            Direction.UP -> Direction.RIGHT
            Direction.RIGHT -> Direction.DOWN
            Direction.DOWN -> Direction.LEFT
            Direction.LEFT -> Direction.UP
        }
    }

    fun stepForward() {
        when (direction) {
            Direction.UP -> y++
            Direction.DOWN -> y--
            Direction.LEFT -> x--
            Direction.RIGHT -> x++
        }
    }

    override fun toString(): String {
        return "x: $x, y: $y, dir: $direction"
    }
}

fun rotateTo(r: Robot, target: Direction) {
    while (r.direction != target) {
        r.turnRight()
    }
}

fun moveRobot(r: Robot, toX: Int, toY: Int) {
    val startX = r.x
    val startY = r.y

    if (startX < toX) {
        rotateTo(r, Direction.RIGHT)
        repeat(toX - startX) { r.stepForward() }
    } else if (startX > toX) {
        rotateTo(r, Direction.LEFT)
        repeat(startX - toX) { r.stepForward() }
    }

    if (startY < toY) {
        rotateTo(r, Direction.UP)
        repeat(toY - startY) { r.stepForward() }
    } else if (startY > toY) {
        rotateTo(r, Direction.DOWN)
        repeat(startY - toY) { r.stepForward() }
    }
}

fun main() {

    println("Перемещение в (3, 7)")
    val robot1 = Robot(0, 1, Direction.UP)
    println("Старт:  $robot1")
    moveRobot(robot1, 3, 7)
    println("Финиш: $robot1\n")

    println("Перемещение в (-4, -5)")
    val robot2 = Robot(2, 2, Direction.DOWN)
    println("Старт:  $robot2")
    moveRobot(robot2, -4, -5)
    println("Финиш: $robot2\n")

}