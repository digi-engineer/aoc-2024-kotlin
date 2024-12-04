fun main() {
    val mulRegex = """mul\((\d{1,3}),(\d{1,3})\)""" // Alternatively can use named captured groups using "(?<theName>PATTERN)""
    val dontRegex = """don't\(\)"""
    val doRegex = """do\(\)"""

    fun MatchResult.multiplyNumbers(): Int {
        val (first, second) = this.destructured
        // Alternatively can use the following
//        val first = this.groupValues[1]
//        val second = this.groupValues[2]
        return first.toInt() * second.toInt()
    }

    fun part1_flatMap_version(input: List<String>): Int {
        val regex = mulRegex.toRegex()
        val result = input.flatMap { line ->
            regex.findAll(line).map {
                it.groupValues[1].toInt() to it.groupValues[2].toInt()
            }.map { it.first * it.second }.toList()
        }.sum()
        return result
    }

    fun part1(input: List<String>): Int {
        val regex = mulRegex.toRegex()
        val result = input.sumOf { line ->
            regex.findAll(line).sumOf { match ->
                match.multiplyNumbers()
            }
        }
        return result
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        var enabled = true
        input.forEach { line ->
            """$mulRegex|$dontRegex|$doRegex""".toRegex().findAll(line).forEach { match ->
                when (match.value) {
                    "don't()" -> enabled = false
                    "do()" -> enabled = true
                    else -> if (enabled) sum += match.multiplyNumbers()
                }
            }
        }
        return sum
    }

    // Read a sample test input from the `src/Day03_part1_test.txt` file:
    val part1TestInput = readInput("Day03_part1_test")
    check(part1(part1TestInput) == 161)

    // Read a sample test input from the `src/Day03_part2_test.txt` file:
    val part2TestInput = readInput("Day03_part2_test")
    check(part2(part2TestInput) == 48)

    // Read the input from the `src/Day03.txt` file.
    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
