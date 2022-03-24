import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode
import kotlin.math.pow

fun main() {

    while (true) {
        while (true) {
            println("Enter two numbers in format: {source base} {target base} (To quit type /exit)")
            val format = readln()
//        exit
            if (format == "/exit") {
                return
            }

            val fromTo = format.split(" ").toMutableList()

            if (fromTo.size < 2 || fromTo.size > 2){
                println("Please enter 2 valid numbers")
                break
            }


            while (true) {
                println("Enter number in base ${fromTo[0]} to convert to base ${fromTo[1]} (To go back type /back)")
                val convert = readln().split(".").toMutableList()
                var toBase = ""
                var toDecimal = ""


                if (convert[0] == "/back") {
                    break
                }

                if (convert.size < 2) {
                    if (fromTo[0].toBigInteger() == 10.toBigInteger()) {
                        println("Conversion result:${converterFrom(convert[0], fromTo[1].toInt())}")
                    } else {
                        val toDecimal = converterTo(convert[0], fromTo[0].toInt())
                        println("Conversion result:${converterFrom(toDecimal, fromTo[1].toInt())}")
                    }
                } else {
                    println("there is fraction part")
                    if (fromTo[0].toBigInteger() == 10.toBigInteger()) {

                       toBase = converterFrom(convert[0], fromTo[1].toInt())

                    } else {
                        toDecimal = converterTo(convert[0], fromTo[0].toInt())
                        toBase = converterFrom(toDecimal.toString(), fromTo[1].toInt())

                    }
                    val fracToDecimal = converFractToDecimal(convert[1], fromTo[0].toInt())
                    println("Conversion result of converting frac to Decimal: $fracToDecimal")
                    val decimalFrac = decimalFracTo(fracToDecimal, fromTo[1].toInt())
                    println("testing of decimal to frac conversion: $decimalFrac")


                    println("Conversion result : ${toBase}.${decimalFrac}")


                }

            }
        }
    }
}

fun converterFrom(decimalNumber: String, targetBase: Int): String {
    var result: String = "Not a Valid base, there is no base$targetBase"
    var string: String = ""
    var i = decimalNumber.toBigInteger()
    val alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    if (targetBase > 36 || targetBase < 2) {

    } else {
        while (i > BigInteger.ZERO) {
            val stringElement = (i % targetBase.toBigInteger())
            string += alphabet[stringElement.toInt()].toString()
            i = (i / targetBase.toBigInteger())
            result = string.reversed()
        }
    }
    return result
}
// base2/base8/base16 to decimal
fun converterTo(sourceNumber: String, targetBaseTo: Int): String {
    var result: String = "Not a Valid base, there is no base$targetBaseTo"
    var string = BigInteger.ZERO
    val alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    val inputNumber = mutableListOf<String>()
    if (targetBaseTo > 36 || targetBaseTo < 2) {

    } else {
        for (i in sourceNumber) {
            val number = alphabet.indexOf(i.uppercase())
            inputNumber.add(number.toString())
        }
        for (i in 0 until inputNumber.size) {
            val stringElement = (Math.pow(targetBaseTo.toDouble(), i.toDouble()).toBigDecimal()
                .toBigInteger() * (inputNumber.reversed()[i]).toBigInteger())
            string += stringElement
            result = string.toString()
        }
    }
    return result
}

fun converFractToDecimal(sourceNumberFrac: String, targetBaseTo: Int): String {
    var result: String = "Not a Valid base, there is no base$targetBaseTo"
    var string = BigDecimal.ZERO
    val alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    val inputNumber = mutableListOf<String>()
    if (targetBaseTo > 36 || targetBaseTo < 2) {

    } else {
        for (i in sourceNumberFrac) {
            val number = alphabet.indexOf(i.uppercase())
            inputNumber.add(number.toString())
        }
        for (i in 0 until inputNumber.size) {
            println("inputNumber[i] ${inputNumber[i]}")
            println("BigDecimal.ONE.setScale(5, RoundingMode.CEILING) ${BigDecimal.ONE.setScale(5, RoundingMode.CEILING)}")
            println("targetBaseTo.toDouble().pow(i).toBigDecimal() ${(targetBaseTo.toDouble().pow(i+1)).toBigDecimal()}")
            val stringElement = inputNumber[i].toBigDecimal() * BigDecimal.ONE.setScale(5, RoundingMode.CEILING) / targetBaseTo.toDouble().pow(i+1).toBigDecimal()
            string += stringElement
            result = string.toString()
        }
    }
    return result
}



fun decimalFracTo(decimalNumber: String, targetBaseTwo: Int): String{
        val fracDecimal = decimalNumber.toBigDecimal()
        val targetBaseTo = targetBaseTwo
        var baseFraction = ""

        val alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        var convertNumber = fracDecimal
        while (convertNumber > BigDecimal.ZERO) {
            val converting = (convertNumber * targetBaseTo.toBigDecimal()).toString().split(".").toMutableList()
            println(converting)
            convertNumber = ("0" + "." + converting[1]).toDouble().toBigDecimal()
            println(convertNumber)
            baseFraction += alphabet[converting[0].toInt()].toString()
            if (baseFraction.length > 4) break
        }
    when {
        baseFraction.isEmpty() -> baseFraction += "00000"
        baseFraction.length == 1 -> baseFraction += "0000"
        baseFraction.length == 2 -> baseFraction += "000"
        baseFraction.length == 3 -> baseFraction += "00"
        baseFraction.length == 4 -> baseFraction += "0"
    }
        println("0.$baseFraction")
    return baseFraction
    }


