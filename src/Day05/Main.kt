package Day05

import println
import readInput
import kotlin.math.max
import kotlin.math.min

fun main() {
    fun rightOrder(rules: List<Pair<Int, Int>>, updates: List<Int>): Boolean {
        val visited = hashMapOf<Int, Int>()
        var i = 0
        for (update in updates) {
            visited[update] = i++
        }
        return rules.all { (a, b) ->
            (visited[a] == null) || (visited[b] == null) || ((visited[a] ?: 0) <= (visited[b] ?: 0))
        }
    }

    fun sort(rules: List<List<Int>>, updates: List<Int>): List<Int> {
        fun dfs(rules: List<List<Int>>, update: Int, updates: List<Int>, result: MutableList<Int> = mutableListOf()): List<Int> {
            for (next in rules[update]) {
                if (next !in updates || next in result) {
                    continue
                }
                dfs(rules, next, updates, result)
            }
            result.add(update)
            return result
        }

        val result = mutableListOf<Int>()
        for (index in updates.indices) {
            val update = updates[index]
            if (rules.filterIndexed { i, next -> update in next && i in updates }.isNotEmpty()) {
                continue
            } else {
                result.addAll(dfs(rules, updates[index], updates).reversed())
            }
        }
        return result
    }

    fun part1(input: List<String>): Int {
        val rules = input.subList(0, input.indexOfLast { it.isBlank() }).map { it.split('|')[0].toInt() to it.split('|')[1].toInt() }
        val updateLists = input.subList(input.indexOfLast { it.isBlank() } + 1, input.size).map { it.split(',').map { it.toInt() } }
        return updateLists.filter { rightOrder(rules, it) }.sumOf { it[it.size / 2] }
    }

    fun part2(input: List<String>): Int {
        val rules = input.subList(0, input.indexOfLast { it.isBlank() }).map { it.split('|')[0].toInt() to it.split('|')[1].toInt() }
        val graph = List<MutableList<Int>>(100) { mutableListOf() }
        for ((a, b) in rules) {
            graph[a].add(b)
        }
        val updateLists = input.subList(input.indexOfLast { it.isBlank() } + 1, input.size).map { it.split(',').map { it.toInt() } }
        return updateLists.filter { !rightOrder(rules, it) }.map { sort(graph, it) }.sumOf { it[it.size / 2] }

    }

    val testInput = readInput(5, true)
    check(part1(testInput) == 143)
    check(part2(testInput) == 123)

    val input = readInput(5)
    part1(input).println()
    part2(input).println()
}