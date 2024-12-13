package Day13

import println
import readInput

fun main() {
    fun solveGame(input: List<String>, addition: Double): Long {
        val regexButtons = """Button ([^ ]+): X\+([^ ]+), Y\+([^ ]+)""".toRegex()
        val (_, aStr, cStr) = regexButtons.matchEntire(input[0])?.destructured!!
        val (_, bStr, dStr) = regexButtons.matchEntire(input[1])?.destructured!!
        val regexResult = """Prize: X=([^ ]+), Y=([^ ]+)""".toRegex()
        val (eStr, fStr) = regexResult.matchEntire(input[2])?.destructured!!

        val (a, b, c, d) = listOf(aStr, bStr, cStr, dStr).map { it.toDouble() }
        val (e, f) = listOf(eStr, fStr).map { it.toDouble() + addition }

        val det = a * d - b * c
        if (det == 0.0) {
            return 0
        }
        val x = (d * e - b * f) / det
        val y = (a * f - c * e) / det
        if ((addition == 0.0 && (x > 100 || y > 100)) || x < 0 || y < 0 || x % 1 != 0.0 || y % 1 != 0.0) {
            return 0
        }
        return (x * 3 + y).toLong()
    }

    fun part1(input: List<String>): Long {
        return input.mapIndexed { index, s -> index / 4 to s }.groupBy { it.first }.values.sumOf { solveGame(it.map { it.second }, 0.0) }
    }

    fun part2(input: List<String>): Long {
        return input.mapIndexed { index, s -> index / 4 to s }.groupBy { it.first }.values.sumOf { solveGame(it.map { it.second }, 10000000000000.0) }
    }

    val testInput = readInput(13, true)
    check(part1(testInput) == 480L)
//    check(part2(testInput) == 0)

    val input = readInput(13)
    part1(input).println()
    part2(input).println()
}