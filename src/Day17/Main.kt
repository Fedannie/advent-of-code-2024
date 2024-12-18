package Day17

import println
import readInput
import java.lang.Math.pow

fun main() {
    fun getComboOperand(registers: List<Long>, operand: Long): Long {
        return when (operand) {
            0L,1L,2L,3L -> operand
            4L,5L,6L -> registers[operand.toInt() - 4]
            else -> -1L
        }
    }

    fun calculate(registers: MutableList<Long>, result: MutableList<Long>, opcode: Long, operand: Long, i: Int): Int {
        when (opcode) {
            0L -> {
                registers[0] = (registers[0] / pow(2.0, getComboOperand(registers, operand).toDouble())).toLong()
            }
            1L -> {
                registers[1] = registers[1] xor operand
            }
            2L -> {
                registers[1] = getComboOperand(registers, operand) % 8
            }
            3L -> {
                if (registers[0] != 0L) {
                    return operand.toInt()
                }
            }
            4L -> {
                registers[1] = registers[1] xor registers[2]
            }
            5L -> {
                result += getComboOperand(registers, operand) % 8
            }
            6L -> {
                registers[1] = (registers[0] / pow(2.0, getComboOperand(registers, operand).toDouble())).toLong()
            }
            7L -> {
                registers[2] = (registers[0] / pow(2.0, getComboOperand(registers, operand).toDouble())).toLong()
            }
        }
        return i + 2
    }

    fun solve(registers: MutableList<Long>, program: List<Long>): List<Long> {
        val result = mutableListOf<Long>()
        var i = 0
        while (i < program.size - 1) {
            i = calculate(registers, result, program[i], program[i + 1], i)
        }
        return result
    }

    fun part1(input: List<String>): String {
        val registers = input.subList(0, 3).map { it.split(": ")[1].toLong() }.toMutableList()
        val program = input.last().split(": ")[1].split(',').map { it.toLong() }
        return solve(registers, program).joinToString(",")
    }

    fun Long.toString(radix: Int, length: Int): String {
        return if (length <= 0) "" else this.toString(radix).padStart(length, '0')
    }

    fun part2(input: List<String>): Long {
        val program = input.last().split(": ")[1].split(',').map { it.toLong() }
        val candidates = mutableListOf<Long>()
        for (i in 0 .. 7) {
            val registers = mutableListOf(i.toLong(), 0L, 0L)
            val result = solve(registers, program)
            if (result.first() == program.last()) {
                candidates.add(i.toLong())
            }
        }
        for (i in 0 .. program.size - 2) {
            val size = candidates.size
            for (j in 0 until size) {
                val next = candidates.removeFirst()
                for (m in 0 .. 7) {
                    val registers = mutableListOf(next * 8 + m.toLong(), 0L, 0L)
                    val result = solve(registers, program)
                    if (result.first() == program[program.size - 2 - i]) {
                        candidates.add(next * 8 + m)
                    }
                }
            }
        }
        return candidates.min()
    }

    val testInput = readInput(17, true)
    check(part1(testInput) == "4,6,3,5,6,3,5,2,1,0")
    check(part2(testInput) == 29328L)

    val input = readInput(17)
    part1(input).println()
    part2(input).println()
}

