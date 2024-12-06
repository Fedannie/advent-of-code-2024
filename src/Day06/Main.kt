package Day06

import println
import readInput

fun main() {
    val directions = listOf(Pair(-1, 0), Pair(0, 1), Pair(1, 0), Pair(0, -1))
    fun parseMap(input: List<String>): Pair<Pair<Int, Int>, List<MutableList<Char>>> {
        val graph = input.map { it.toMutableList() }
        var current = Pair(0, 0)
        for (i in graph.indices) {
            for (j in graph[i].indices) {
                if (graph[i][j] == '^') {
                    graph[i][j] = '.'
                    current = i to j
                }
            }
        }
        return current to graph
    }

    fun travel(start: Pair<Int, Int>, graph: List<List<Char>>): Pair<Pair<Int, Int>, Set<Pair<Int, Int>>> {
        var current = start
        var direction = 0
        val visited = mutableSetOf<Pair<Pair<Int, Int>, Pair<Int, Int>>>()
        while (current.first in graph.indices && current.second in graph[0].indices) {
            if (current to directions[direction] in visited) {
                break
            }
            visited.add(current to directions[direction])
            if (current.first + directions[direction].first in graph.indices && current.second + directions[direction].second in graph[0].indices && graph[current.first + directions[direction].first][current.second + directions[direction].second] == '#') {
                direction = (direction + 1) % 4
            } else {
                current = current.first + directions[direction].first to current.second + directions[direction].second
            }
        }

        return current to visited.map { it.first }.toSet()
    }

    fun part1(input: List<String>): Int {
        val (current, graph) = parseMap(input)
        return travel(current, graph).second.size
    }

    fun part2(input: List<String>): Int {
        val (current, graph) = parseMap(input)
        val pois = travel(current, graph).second
        var result = 0
        for (poi in pois) {
            if (poi == current || graph[poi.first][poi.second] == '#') {
                continue
            }
            graph[poi.first][poi.second] = '#'
            val finish = travel(current, graph).first
            if (finish.first in graph.indices && finish.second in graph[0].indices) {
                result++
            }
            graph[poi.first][poi.second] = '.'
        }
        return result
    }

    val testInput = readInput(6, true)
    check(part1(testInput) == 41)
    check(part2(testInput) == 6)

    val input = readInput(6)
    part1(input).println()
    part2(input).println()
}