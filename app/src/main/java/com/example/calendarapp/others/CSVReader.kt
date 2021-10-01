package com.example.calendarapp.others

import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.Reader

class CSVReader(reader: Reader) {
    private val separator: Char = ','
    private val quotechar: Char = '"'
    private val br: BufferedReader = BufferedReader(reader)

    private var hasNext = true

    private val nextLine: String?
        @Throws(IOException::class)
        get() {
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
        // eg. next line = "le "h" du,c\n suti" , "12333"
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
                // start quote
                if (c == quotechar) {
                    if (nextLine.length > i + 1
                        && nextLine[i + 1] == quotechar
                    ) {
                        sb.append(nextLine[i + 1])
                        i++
                        //Log.e()
                    } else {
                        inQuotes = !inQuotes
                        if (i > 2
                            && nextLine[i - 1] != this.separator
                            && nextLine.length > i + 1
                            && nextLine[i + 1] != this.separator
                        ) {
                            sb.append(c)
                        }
                    }
                } else if (c == separator && !inQuotes) { // end quote
                    tokensOnThisLine.add(sb.toString())
                    sb = StringBuffer() // next token
                } else {
                    if (nextLine[i] == '\\' && nextLine[i + 1] == 'n') {
                        sb.append("\n")
                        i++
                    } else sb.append(c)
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

}