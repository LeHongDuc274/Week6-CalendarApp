package com.example.calendarapp.others

import java.io.BufferedReader
import java.io.IOException
import java.io.Reader

class CSVReader

@JvmOverloads constructor(
    reader: Reader,
    private val separator: Char = DEFAULT_SEPARATOR,
    private val quotechar: Char = DEFAULT_QUOTE_CHARACTER,
    private val skipLines: Int = DEFAULT_SKIP_LINES
) {
    private val br: BufferedReader = BufferedReader(reader)
    private var hasNext = true
    private var linesSkiped: Boolean = false

    private val nextLine: String?
        @Throws(IOException::class)
        get() {
            if (!this.linesSkiped) {
                for (i in 0 until skipLines) {
                    br.readLine()
                }
                this.linesSkiped = true
            }
            val nextLine = br.readLine()
            if (nextLine == null) {
                hasNext = false
            }
            return if (hasNext) nextLine else null
        }

    @Throws(IOException::class)
    fun readNext(): Array<String>? {
        val nextLine = nextLine
        return if (hasNext) parseLine(nextLine) else null
    }

    @Throws(IOException::class)
    private fun parseLine(nextLine: String?): Array<String>? {
        var nextLine: String? = nextLine ?: return null
        val tokensOnThisLine = ArrayList<String>()
        var sb = StringBuffer()
        var inQuotes = false
        do {
            if (inQuotes) {
                sb.append("\n")
                nextLine = nextLine
                if (nextLine == null)
                    break
            }
            var i = 0
            while (i < nextLine!!.length) {

                val c = nextLine[i]
                if (c == quotechar) {

                    if (inQuotes
                        && nextLine.length > i + 1
                        && nextLine[i + 1] == quotechar
                    ) {
                        sb.append(nextLine[i + 1])
                        i++
                    } else {
                        inQuotes = !inQuotes
                        if (i > 2

                            && nextLine[i - 1] != this.separator

                            && nextLine.length > i + 1 &&
                            nextLine[i + 1] != this.separator
                        ) {
                            sb.append(c)
                        }
                    }
                } else if (c == separator && !inQuotes) {
                    tokensOnThisLine.add(sb.toString())
                    sb = StringBuffer()
                } else {
                    sb.append(c)
                }
                i++
            }
        } while (inQuotes)
        tokensOnThisLine.add(sb.toString())
        return tokensOnThisLine.toTypedArray()
    }

    @Throws(IOException::class)
    fun close() {
        br.close()
    }

    companion object {
        val DEFAULT_SEPARATOR = ','
        val DEFAULT_QUOTE_CHARACTER = '"'
        val DEFAULT_SKIP_LINES = 0
    }
}