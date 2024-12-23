package Day23

import println
import readInput

fun main() {
    fun parseInput(input: List<String>, strToNodes: HashMap<String, Int>, nodesToStr: MutableList<String>): MutableList<MutableList<Int>> {
        val graph = mutableListOf<MutableList<Int>>()
        input.forEach { line ->
            val (a, b) = line.split("-")
            val aInt = strToNodes.getOrPut(a) { nodesToStr.size.also { nodesToStr.add(a) } }
            val bInt = strToNodes.getOrPut(b) { nodesToStr.size.also { nodesToStr.add(b) } }
            if (graph.size <= aInt) {
                graph.add(mutableListOf())
            }
            if (graph.size <= bInt) {
                graph.add(mutableListOf())
            }
            graph[aInt].add(bInt)
            graph[bInt].add(aInt)
        }

        return graph
    }

    fun getAllTrigrams(graph: List<List<Int>>): List<List<Int>> {
        val result = mutableListOf<List<Int>>()
        for (i in 0 .. graph.size - 3) {
            for (j in i + 1 .. graph.size - 2) {
                for (k in j + 1 .. graph.size - 1) {
                    if (graph[i].contains(j) && graph[i].contains(k) && graph[j].contains(k)) {
                        result.add(listOf(i, j, k))
                    }
                }
            }
        }
        return result
    }

    fun part1(input: List<String>): Int {
        val strToNodes = HashMap<String, Int>()
        val nodesToStr = mutableListOf<String>()
        val graph = parseInput(input, strToNodes, nodesToStr)
        return getAllTrigrams(graph).map { it.map { nodesToStr[it] } }.count { it.any { it.startsWith("t") } }
    }

    fun isFull(graph: List<List<Int>>, nodes: List<Int>): Boolean {
        for (i in nodes.indices) {
            for (j in nodes.indices) {
                if (i != j && !graph[nodes[i]].contains(nodes[j])) {
                    return false
                }
            }
        }
        return true
    }

    fun getLargestGraph(subGraph: List<Int>, graph: List<List<Int>>, memory: HashMap<List<Int>, List<Int>>): List<Int> {
        val sorted = subGraph.sorted()
        if (sorted in memory) {
            return memory[sorted]!!
        }
        val common = subGraph.map { graph[it] }.reduce { acc, list -> acc.intersect(list).toList() }
        if (common.isEmpty()) {
            return subGraph
        }
        val filtered = common.filter { node -> isFull(graph, subGraph.toMutableList().also { it.add(node) }) }
        if (filtered.isEmpty()) {
            return subGraph
        }
        memory[sorted] = listOf(filtered.maxBy { graph[it].size }).map { node -> getLargestGraph(subGraph.toMutableList().also { it.add(node) }, graph, memory) }.maxBy { it.size }
        return memory[sorted]!!
    }

    fun part2(input: List<String>): String {
        val strToNodes = HashMap<String, Int>()
        val nodesToStr = mutableListOf<String>()
        val graph = parseInput(input, strToNodes, nodesToStr)
        val result = getAllTrigrams(graph).map { getLargestGraph(it, graph, hashMapOf()) }.maxBy { it.size }
        return result.map { nodesToStr[it] }.sorted().joinToString(",")
    }

    val testInput = readInput(23, true)
    check(part1(testInput) == 7)
    check(part2(testInput) == "co,de,ka,ta")

    val input = readInput(23)
    part1(input).println()
    part2(input).println()
}