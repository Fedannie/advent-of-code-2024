package Day22

import println
import readInput

fun main() {
    val memory = hashMapOf<Long, Long>()
    fun mix(a: Long, b: Long): Long {
        return a xor b
    }

    fun prune(a: Long): Long {
        return a % 16777216
    }

    fun calculate(secret: Long): Long {
        if (secret !in memory) {
            val a = prune(mix(secret, secret * 64))
            val b = prune(mix(a, a / 32))
            memory[secret] = prune(mix(b, b * 2048))
        }
        return memory[secret]!!
    }

    fun part1(input: List<String>): Long {
        return input.map { it.toLong() }.sumOf {
            var secret = it
            repeat(2000) {
                secret = calculate(secret)
            }
            secret
        }
    }

    fun <T> calculateBananas(prices: List<List<Int>>, changes: List<List<T>>, change: T): Int {
        return changes.mapIndexed { index, it ->
            val priceIndex = it.indexOf(change)
            if (priceIndex != -1) {
                prices[index][priceIndex + 4]
            } else {
                0
            }
        }.sum()
    }

    fun part2(input: List<String>): Int {
        val prices = input.map { it.toLong() }.map {
            val result = mutableListOf(it)
            repeat(2000) {
                result.add(calculate(result.last()))
            }
            result
        }.map { it.map { (it % 10).toInt() } }
        val changes = prices.map { it.zipWithNext().map { (a, b) -> b - a }.zipWithNext().zipWithNext().zipWithNext().map { it.toString() } }
        val changesDistinct = prices.flatMap { it.zipWithNext().map { (a, b) -> b - a }.zipWithNext().zipWithNext().zipWithNext().map { it.toString() } }.distinct()

        var m = 0
        for (i in changesDistinct.indices) {
            val bananas = calculateBananas(prices, changes, changesDistinct[i])
            if (bananas > m) {
                m = bananas
                println("i: $i/${changesDistinct.size}, m: $m")
            }
        }
        return m
    }

    val testInput = readInput(22, true)
    check(part1(testInput) == 37990510L)
    check(part2(testInput) == 23)

    val input = readInput(22)
    part1(input).println()
    part2(input).println()
}