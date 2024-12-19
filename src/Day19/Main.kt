package Day19

import println
import readInput

fun main() {
    fun assemble(pattern: String, towels: List<String>, memory: HashMap<String, Long>): Long {
        if (pattern.isEmpty()) {
            return 1
        }
        if (pattern in memory) {
            return memory[pattern]!!
        }
        var result = 0L
        for (towel in towels) {
            if (pattern.startsWith(towel)) {
                result += assemble(pattern.substring(towel.length), towels, memory)
            }
        }
        return result.also { memory[pattern] = it }
    }

    fun part1(input: List<String>): Int {
        val towels = input[0].split(", ").toList()
        val memory = hashMapOf<String, Long>()
        return input.subList(2, input.size).count { assemble(it, towels, memory) > 0L }
    }

    fun part2(input: List<String>): Long {
        val towels = input[0].split(", ").toList()
        val memory = hashMapOf<String, Long>()
        return input.subList(2, input.size).sumOf { assemble(it, towels, memory) }
    }

    val testInput = readInput(19, true)
    check(part1(testInput) == 6)
    check(part2(testInput) == 16L)

    val input = readInput(19)
    part1(input).println()
    part2(input).println()
}