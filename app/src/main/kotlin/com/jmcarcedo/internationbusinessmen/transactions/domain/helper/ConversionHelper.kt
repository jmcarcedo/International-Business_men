package com.jmcarcedo.internationbusinessmen.transactions.domain.helper

import com.jmcarcedo.internationbusinessmen.transactions.domain.model.Rate
import com.jmcarcedo.internationbusinessmen.transactions.domain.model.Transaction

object ConversionHelper {

    private const val STATIC_DECIMAL_NUMBER =
        2 //For as far as I could see all transactions and rates only have two decimals
    private const val DECIMALS_DELIMITER = "."

    private lateinit var rates: MutableList<Rate>

    fun convertTransactions(
        transactions: List<Transaction>,
        currency: String,
        rates: List<Rate>
    ): Transaction {
        this.rates = rates.toMutableList()
        val convertedAmounts =
            transactions.map { convertCurrency(it.currency, currency, it.amount) }
        val totalAmount = convertedAmounts.sum().denormalizedInteger(STATIC_DECIMAL_NUMBER)

        return Transaction(
            amount = totalAmount,
            currency = currency
        )
    }

    private fun convertCurrency(from: String, to: String, amount: String): Int {
        return amount.normalizedInteger().let { normalizedAmount ->
            if (from == to) {
                normalizedAmount
            } else {
                getConvertedAmount(from, to, normalizedAmount).normalizedInteger()
            }
        }
    }

    private fun getConvertedAmount(
        from: String,
        to: String,
        normalizedAmount: Int
    ): String {
        val rate = getConversionRate(from, to)
        return (normalizedAmount * rate).denormalizedInteger()
    }

    private fun getConversionRate(from: String, to: String): Int {
        val fromToRates =
            rates.firstOrNull { it.from == from && it.to == to } ?: generateRates(from, to)
        return fromToRates.rate.normalizedInteger()
    }

    private fun generateRates(from: String, to: String): Rate {
        val directRateGenerated = generateRate(from, to)
        val inverseRateGenerated = generateRate(to, from)
        rates.addAll(listOf(directRateGenerated, inverseRateGenerated))
        return directRateGenerated
    }

    private fun generateRate(from: String, to: String): Rate {
        val fromRate = rates.first { it.from == from }
        val normalizedToConvertionRate = getConversionRate(fromRate.to, to)
        val normalizedFromConversionRate = fromRate.rate.normalizedInteger()
        val normalizedRate = (normalizedFromConversionRate * normalizedToConvertionRate)
        return Rate(from, to, normalizedRate.denormalizedInteger())
    }

    /**
     * To avoid using floating points numbers, strings must be turn into integers but moving the
     * decimals to the integer body. This could be done splitting the integer and the decimal one,
     * treating the decimal (it should result in a two digits number) and then rejoining them.
     *
     * This approach considers that every number should only have and operate with two decimals.
     */
    private fun String.normalizedInteger(): Int {
        val splitNumber = split(DECIMALS_DELIMITER)
        val decimals = splitNumber.last().run {
            when (length) {
                0 -> this + "00"
                1 -> this + "0"
                STATIC_DECIMAL_NUMBER -> this
                else -> applyBankersRounding()
            }
        }
        return (splitNumber.first() + decimals).toInt()
    }

    /**
     * One integer numbers are operated they should be denormalized having their decimals calculated.
     * There are two scenarios here:
     *  - Normalized Integers which haven't had been used and so their last two digits are the decimals
     *  - Normalized Integers used in a conversion. The only possible operation is the product so, to be
     *  denormalized the number of decimals present in the result Integer is the double of the normalized number
     */
    private fun Int.denormalizedInteger(normalizedFactor: Int = STATIC_DECIMAL_NUMBER * 2): String {
        return toString().run {
            val integer = if (length <= normalizedFactor) {
                "0"
            } else {
                dropLast(normalizedFactor)
            }
            val decimals = if (length <= STATIC_DECIMAL_NUMBER) {
                this
            } else {
                takeLast(normalizedFactor).applyBankersRounding()
            }
            integer + DECIMALS_DELIMITER + decimals
        }

    }

    private fun String.applyBankersRounding(decimals: Int = STATIC_DECIMAL_NUMBER): String {
        return take(decimals + 1).let { number ->
            when (number.takeLast(1).toInt()) {
                in 0..4 -> number.take(decimals)
                in 6..9 -> number.take(decimals).toInt().plus(1).toString()
                else -> {
                    number.take(decimals).toInt().run {
                        if (rem(2) == 0) {
                            this
                        } else {
                            inc()
                        }
                    }.toString()
                }
            }
        }
    }
}