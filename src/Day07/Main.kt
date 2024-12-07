package Day07

import println
import readInput
import java.math.BigInteger

class Operation(val check: (BigInteger, BigInteger) -> Boolean, val op: (BigInteger, BigInteger) -> BigInteger) {
    companion object {
        val operations = listOf(
            Operation({ a, b -> a >= b }, BigInteger::minus),
            Operation({ a, b -> a % b == BigInteger.ZERO }, { a, b -> a / b }),
            Operation({ a, b -> a % BigInteger.TEN.pow(b.toString().length) == b }, { a, b -> a / BigInteger.TEN.pow(b.toString().length) })
        )
    }
}

fun main() {
    fun <T> List<T>.withoutLast(): List<T> {
        return this.subList(0, this.size - 1)
    }

    fun compute(result: BigInteger, numbers: List<BigInteger>, operations: List<Operation>): Boolean {
        if (numbers.size == 1) {
            return result == numbers[0]
        }
        for (operation in operations) {
            if (operation.check(result, numbers.last()) && compute(operation.op(result, numbers.last()), numbers.withoutLast(), operations)) {
                return true
            }
        }
        return false
    }

    fun part1(input: List<String>): BigInteger {
        return input.map { it.split(": ")[0].toBigInteger() to it.split(": ")[1].split(' ').map { it.toBigInteger() } }
            .filter { compute(it.first, it.second, Operation.operations.subList(0, 2)) }
            .sumOf { it.first }
    }

    fun part2(input: List<String>): BigInteger {
        return input.map { it.split(": ")[0].toBigInteger() to it.split(": ")[1].split(' ').map { it.toBigInteger() } }
            .filter { compute(it.first, it.second, Operation.operations) }
            .sumOf { it.first }
    }

    val testInput = readInput(7, true)
    check(part1(testInput) == BigInteger.valueOf(3749))
    check(part2(testInput) == BigInteger.valueOf(11387))

    val input = readInput(7)
    part1(input).println()
    part2(input).println()
}