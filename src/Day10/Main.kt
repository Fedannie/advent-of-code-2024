package Day10

import at
import isInside
import next
import println
import readInput

fun main() {
    fun getHikesDfs(field: List<List<Int>>, current: Pair<Int, Int>): Int {
        var result = 0
        if (field.at(current) == 9) {
            return 1
        }
        for (neighbor in next(current)) {
            if (field.isInside(neighbor) && field.at(neighbor) - field.at(current) == 1) {
                result += getHikesDfs(field, neighbor)
            }
        }
        return result
    }

    fun getHikesBfs(field: List<List<Int>>, start: Pair<Int, Int>): Int {
        if (field.at(start) != 0) {
            return 0
        }
        val queue = mutableListOf(start)
        val visited = mutableSetOf(start)
        var result = 0

        while (queue.isNotEmpty()) {
            val size = queue.size
            for (i in 0 ..< size) {
                val current = queue.removeFirst()

                if (field.at(current) == 9) {
                    result++
                    break
                }
                for (neighbor in next(current)) {
                    if (field.isInside(neighbor) && neighbor !in visited && field.at(neighbor) - field.at(current) == 1) {
                        visited.add(neighbor)
                        queue.add(neighbor)
                    }
                }
            }
        }

        return result
    }

    fun part1(input: List<String>): Int {
        val field = input.map { it.map { if (it == '.') -1 else it.digitToInt() } }
        var result = 0
        for (i in field.indices) {
            for (j in field[0].indices) {
                if (field.at(i to j) == 0) {
                    result += getHikesBfs(field, i to j)
                }
            }
        }
        return result
    }

    fun part2(input: List<String>): Int {
        val field = input.map { it.map { if (it == '.') -1 else it.digitToInt() } }
        var result = 0
        for (i in field.indices) {
            for (j in field[0].indices) {
                if (field.at(i to j) == 0) {
                    result += getHikesDfs(field, i to j)
                }
            }
        }
        return result    }

    val testInput = readInput(10, true)
    check(part1(testInput) == 36)
    check(part2(testInput) == 81)

    val input = readInput(10)
    part1(input).println()
    part2(input).println()
}