package Day18

import bfs
import println
import readInput

fun main() {
    fun part1(input: List<String>, fieldSize: Pair<Int, Int>): Int {
        val fallenBytes = input.map { it.split(',')[0].toInt() to it.split(',')[1].toInt() }
        return bfs(0 to 0, { it == fieldSize }, { it.first in 0 .. fieldSize.first && it.second in 0 .. fieldSize.second &&  it !in fallenBytes}, {
            val (x, y) = it
            listOf(x + 1 to y, x - 1 to y, x to y + 1, x to y - 1)
        })
    }

    fun part2(input: List<String>, fieldSize: Pair<Int, Int>): String {
        var left = 0
        var right = input.size
        while (right - left > 1) {
            val mid = (left + right) / 2
            if (part1(input.subList(0, mid + 1), fieldSize) == -1) {
                right = mid
            } else {
                left = mid
            }
        }
        return input[right]
    }

    val testInput = readInput(18, true)
    check(part1(testInput.subList(0, 12), 6 to 6) == 22)
    check(part2(testInput, 6 to 6) == "6,1")

    val input = readInput(18)
    part1(input.subList(0, 1024), 70 to 70).println()
    part2(input, 70 to 70).println()
}