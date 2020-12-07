package day4

private val REQUIRED_FIELDS = arrayOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")
private val EYE_COLS = arrayOf("amb", "blu", "brn", "gry", "grn", "hzl", "oth")

fun main() {
    var lineIn: String?

    var valids = 0;

    var curPassport = ArrayList<String>();

    while(true) {
        lineIn = readLine()
        if(lineIn == "end") {
            break
        } else {
            if(lineIn.isNullOrEmpty()) {
                if(validate(curPassport)) {
                    valids++
                    println("valid")
                } else {
                    println("invalid")
                }
                curPassport.clear()
                println(valids)
            } else {
                curPassport.add(lineIn);
            }
        }
    }
}

private fun validate(passport: List<String>): Boolean {
    val keyvalues = HashMap<String,String>()

    for(line in passport) {
        val kvs = line.split(" ");
        for(kv in kvs) {
            val kv_split = kv.split(":")
            keyvalues.put(kv_split[0], kv_split[1])
        }
    }
    for(req_key in REQUIRED_FIELDS) {
        if(!keyvalues.keys.contains(req_key)) {
            return false
        }
    }
    for(e in keyvalues.entries) {
        when(e.key) {
            "byr" -> if(!(1920..2002).contains(e.value.toInt())) return false
            "iyr" -> if(!(2010..2020).contains(e.value.toInt())) return false
            "eyr" -> if(!(2020..2030).contains(e.value.toInt())) return false
            "hgt" -> {
                if(e.value.endsWith("cm")) {
                    if(!(150..193).contains(e.value.removeSuffix("cm").toInt())) return false
                } else if(e.value.endsWith("in")) {
                    if(!(59..76).contains(e.value.removeSuffix("in").toInt())) return false
                } else {
                    return false
                }
            }
            "hcl" -> if(!"""#[0-9a-f]{6}""".toRegex().matches(e.value)) return false
            "pid" -> if(!"""[0-9]{9}""".toRegex().matches(e.value)) return false
            "ecl" -> if(!EYE_COLS.contains(e.value)) return false
        }
    }
    return true
}