package day19

open class Rule {
    class Terminal(var char: Char): Rule()
    class Reference(var rules: List<List<Int>>): Rule()
}