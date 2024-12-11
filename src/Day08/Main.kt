package Day08

import at
import isInsideStr
import println
import readInput

fun main() {
    fun part1(input: List<String>): Int {
        fun getAntinodes(antenna1: Pair<Int, Int>, antenna2: Pair<Int, Int>, field: List<String>): List<Pair<Int, Int>> {
            if (antenna1 == antenna2 || field.at(antenna1) != field.at(antenna2)) {
                return listOf()
            }
            val delta = antenna2.first - antenna1.first to antenna2.second - antenna1.second
            val antinode1 = antenna2.first + delta.first to antenna2.second + delta.second
            val antinode2 = antenna1.first - delta.first to antenna1.second - delta.second
            val result = mutableListOf<Pair<Int, Int>>()
            if (field.isInsideStr(antinode1)) result.add(antinode1)
            if (field.isInsideStr(antinode2)) result.add(antinode2)
            return result
        }

        val antennas = input.flatMapIndexed { i, row -> row.mapIndexed { j, ch -> if (ch == '.') null else i to j } }.filterNotNull()
        val result = mutableSetOf<Pair<Int, Int>>()
        for (antenna1 in antennas) {
            for (antenna2 in antennas) {
                result.addAll(getAntinodes(antenna1, antenna2, input))
            }
        }
        return result.size
    }

    fun part2(input: List<String>): Int {
        fun getAntinodes(antenna1: Pair<Int, Int>, antenna2: Pair<Int, Int>, field: List<String>): List<Pair<Int, Int>> {
            if (antenna1 == antenna2 || field.at(antenna1) != field.at(antenna2)) {
                return listOf()
            }
            val delta = antenna2.first - antenna1.first to antenna2.second - antenna1.second
            val result = mutableListOf<Pair<Int, Int>>()
            var previousSize = -1
            var i = 0
            while (previousSize != result.size) {
                previousSize = result.size
                val antinode1 = antenna2.first + delta.first * i to antenna2.second + delta.second * i
                val antinode2 = antenna1.first - delta.first * i to antenna1.second - delta.second * i
                if (field.isInsideStr(antinode1)) result.add(antinode1)
                if (field.isInsideStr(antinode2)) result.add(antinode2)
                i++
            }

            return result
        }

        val antennas = input.flatMapIndexed { i, row -> row.mapIndexed { j, ch -> if (ch == '.') null else i to j } }.filterNotNull()
        val result = mutableSetOf<Pair<Int, Int>>()
        for (antenna1 in antennas) {
            for (antenna2 in antennas) {
                result.addAll(getAntinodes(antenna1, antenna2, input))
            }
        }
        return result.size
    }

    val testInput = readInput(8, true)
    check(part1(testInput) == 14)
    check(part2(testInput) == 34)

    val input = readInput(8)
    part1(input).println()
    part2(input).println()
}