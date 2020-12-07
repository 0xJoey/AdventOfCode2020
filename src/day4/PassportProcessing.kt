package day4

private val REQUIRED_FIELDS = arrayOf("byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid")

fun main() {
    var lineIn: String?

    var valids = 0;

    var curPassport = ArrayList<String>()

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
    val keys = ArrayList<String>()

    for(line in passport) {
        val kvs = line.split(" ");
        for(kv in kvs) {
            keys.add(kv.split(":")[0])
        }
    }
    for(req_key in REQUIRED_FIELDS) {
        if(!keys.contains(req_key)) {
            return false
        }
    }
    return true
}