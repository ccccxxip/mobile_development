package array

fun mergeArrays(a1: IntArray, a2: IntArray): IntArray {
    val result = IntArray(a1.size + a2.size)
    var i = 0
    var j = 0
    var k = 0

    while (i < a1.size && j < a2.size) {
        if (a1[i] <= a2[j]) {
            result[k++] = a1[i++]
        } else {
            result[k++] = a2[j++]
        }
    }

    while (i < a1.size) result[k++] = a1[i++]
    while (j < a2.size) result[k++] = a2[j++]

    return result
}

fun check(a1: IntArray, a2: IntArray, expected: IntArray) {
    val actual = mergeArrays(a1, a2)
    val status = if (actual.contentEquals(expected)) "OK  " else "FAIL"
    println("$status ${a1.contentToString()} + ${a2.contentToString()} = ${actual.contentToString()}")
}

fun main() {
    check(intArrayOf(0, 2, 2), intArrayOf(1, 3), intArrayOf(0, 1, 2, 2, 3))
    check(intArrayOf(), intArrayOf(1, 3), intArrayOf(1, 3))
    check(intArrayOf(1, 3), intArrayOf(), intArrayOf(1, 3))
    check(intArrayOf(), intArrayOf(), intArrayOf())
    check(intArrayOf(1, 2, 3), intArrayOf(4, 5), intArrayOf(1, 2, 3, 4, 5))
    check(intArrayOf(4, 5), intArrayOf(1, 2, 3), intArrayOf(1, 2, 3, 4, 5))
    check(intArrayOf(-3, 0, 0, 7), intArrayOf(-5, 0, 8), intArrayOf(-5, -3, 0, 0, 0, 7, 8))
}