package Day21

import println
import readInput

fun main() {
    val pad = hashMapOf('A' to (3 to 2), '0' to (3 to 1), '1' to (2 to 0), '2' to (2 to 1), '3' to (2 to 2), '4' to (1 to 0), '5' to (1 to 1), '6' to (1 to 2), '7' to (0 to 0), '8' to (0 to 1), '9' to (0 to 2), '^' to (3 to 1), '<' to (4 to 0), 'v' to (4 to 1), '>' to (4 to 2))
    val memory = hashMapOf<Pair<Pair<Int, Int>, Pair<Int, Int>>, List<String>>()
    val instructionMemory = hashMapOf<Pair<String, Int>, Long>()
    val blacklisted = listOf(3 to 0)

    fun getPermutations(from: Pair<Int, Int>, to: Pair<Int, Int>): List<String> {
        if (memory.contains(from to to)) {
            return memory[from to to]!!
        }
        if (from == to) {
            return listOf("A")
        }
        val result = mutableListOf<String>()
        if (from.first < to.first && from.first + 1 to from.second !in blacklisted) {
            for (x in getPermutations(from.first + 1 to from.second, to)) {
                result.add("v$x")
            }
        }
        if (from.first > to.first && from.first - 1 to from.second !in blacklisted) {
            for (x in getPermutations(from.first - 1 to from.second, to)) {
                result.add("^$x")
            }
        }
        if (from.second < to.second && from.first to from.second + 1 !in blacklisted) {
            for (x in getPermutations(from.first to from.second + 1, to)) {
                result.add(">$x")
            }
        }
        if (from.second > to.second && from.first to from.second - 1 !in blacklisted) {
            for (x in getPermutations(from.first to from.second - 1, to)) {
                result.add("<$x")
            }
        }
        memory[from to to] = result
        return result
    }

    fun getInstructions(fromStr: Char, toStr: Char): List<String> {
        val from = pad[fromStr]!!
        val to = pad[toStr]!!
        return getPermutations(from, to)
    }

    fun getShortestInstruction(instruction: String, fold: Int): Long {
        if (fold == 0) {
            return instruction.length.toLong()
        }
        return "A$instruction".zipWithNext().map { getInstructions(it.first, it.second) }.sumOf { it.minOf {
            if (it to fold - 1 !in instructionMemory) {
                instructionMemory[it to fold - 1] = getShortestInstruction(it, fold - 1)
            }
            instructionMemory[it to fold - 1]!!

        } }
    }

    fun part1(input: List<String>): Long {
        return input.sumOf { it.substring(0, 3).toLong() * getShortestInstruction(it.substring(0, 4), 3) }
    }

    fun part2(input: List<String>): Long {
        return input.sumOf { it.substring(0, 3).toLong() * getShortestInstruction(it.substring(0, 4), 26) }
    }

    val testInput = readInput(21, true)
    check(part1(testInput) == 126384L)
//    check(part2(testInput) == 0L)

    val input = readInput(21)
    part1(input).println()
    part2(input).println()
}