package com.example.calendarapp.others

import java.io.IOException
import java.io.PrintWriter
import java.io.Writer

class CSVWriter @JvmOverloads constructor(
    writer: Writer,
    private val separator: Char = DEFAULT_SEPARATOR,
    private val quotechar: Char = DEFAULT_QUOTE_CHARACTER,
    private val escapechar: Char = DEFAULT_ESCAPE_CHARACTER,
    private val lineEnd: String = DEFAULT_LINE_END
) {
    private val pw = PrintWriter(writer)

    fun writerNext(nextLine: Array<String?>?) {
        if (nextLine == null)
            return
        val sb = StringBuffer()
        for (i in nextLine.indices) {
            if (i != 0) {
                sb.append(separator)
            }
            val nextElement = nextLine[i] ?: continue
            if (quotechar != NO_QUOTE_CHARACTER)
                sb.append(quotechar)
            for (element in nextElement) {
                if (escapechar != NO_ESCAPE_CHARACTER && element == quotechar) {
                    sb.append(escapechar).append(element)
                } else if (escapechar != NO_ESCAPE_CHARACTER && element == escapechar) {
                    sb.append(escapechar).append(element)
                } else {
                    sb.append(element)
                }
            }
            if (quotechar != NO_QUOTE_CHARACTER)
                sb.append(quotechar)
        }
        sb.append(lineEnd)
        pw.write(sb.toString())
    }
    @Throws(IOException::class)
    fun close() {
        pw.flush()
        pw.close()
    }
    @Throws(IOException::class)
    fun flush() {
        pw.flush()

    }

    companion object {
        val DEFAULT_ESCAPE_CHARACTER = '"'
        val DEFAULT_SEPARATOR = ','
        val DEFAULT_QUOTE_CHARACTER = '"'
        val NO_QUOTE_CHARACTER = '\u0000'
        val NO_ESCAPE_CHARACTER = '\u0000'
        val DEFAULT_LINE_END = "\n"
    }
}