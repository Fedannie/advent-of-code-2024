package Day04

import println
import readInput

fun main() {
    val moves = listOf(
        Pair(0, 1),
        Pair(0, -1),
        Pair(1, 0),
        Pair(-1, 0),
        Pair(1, 1),
        Pair(1, -1),
        Pair(-1, 1),
        Pair(-1, -1)
    )

    fun isWord(field: List<String>, origin: Pair<Int, Int>, direction: Pair<Int, Int>, word: String): Boolean {
        if (origin.first in field.indices && origin.second in field[0].indices && origin.first + direction.first * (word.length - 1) in field.indices && origin.second + direction.second * (word.length - 1) in field[0].indices) {
            for (i in word.indices) {
                if (field[origin.first + direction.first * i][origin.second + direction.second * i] != word[i]) {
                    return false
                }
            }
            return true
        }
        return false
    }

    fun part1(input: List<String>): Int {
        var result = 0
        for (i in input.indices) {
            for (j in input[0].indices) {
                result += moves.count { isWord(input, i to j, it, "XMAS") }
            }
        }
        return result
    }

    fun part2(input: List<String>): Int {
        fun isX(i: Int, j: Int): Boolean {
            return (isWord(input, i - 1 to j - 1, 1 to 1, "MAS") || isWord(input, i - 1 to j - 1, 1 to 1, "SAM")) &&
                (isWord(input, i - 1 to j + 1, 1 to -1, "MAS") || isWord(input, i - 1 to j + 1, 1 to -1, "SAM"))
        }

        var result = 0
        for (i in input.indices) {
            for (j in input[0].indices) {
                if (isX(i, j)) result++
            }
        }
        return result
    }

    val testInput = readInput(4, true)
    check(part1(testInput) == 18)
    check(part2(testInput) == 9)

    val input = readInput(4)
    part1(input).println()
    part2(input).println()
}