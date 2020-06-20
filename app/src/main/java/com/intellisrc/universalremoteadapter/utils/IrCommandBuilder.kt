package com.intellisrc.universalremoteadapter.utils

import java.util.*

class IrCommandBuilder private constructor(val frequency: Int) {
    private val buffer: MutableList<Int>
    private var lastMark: Boolean?
    private fun appendSymbol(mark: Boolean, interval: Int): IrCommandBuilder {
        if (lastMark == null || lastMark != mark) {
            buffer.add(interval)
            lastMark = mark
        } else {
            val lastIndex = buffer.size - 1
            buffer[lastIndex] = buffer[lastIndex] + interval
        }
        return this
    }

    fun mark(interval: Int): IrCommandBuilder {
        return appendSymbol(true, interval)
    }

    fun space(interval: Int): IrCommandBuilder {
        return appendSymbol(false, interval)
    }

    fun pair(on: Int, off: Int): IrCommandBuilder {
        return mark(on).space(off)
    }

    fun reversePair(off: Int, on: Int): IrCommandBuilder {
        return space(off).mark(on)
    }

    fun delay(ms: Int): IrCommandBuilder {
        return space(ms * frequency / 1000)
    }

    fun sequence(definition: SequenceDefinition, length: Int, data: Int): IrCommandBuilder {
        return sequence(definition, TOP_BIT_32, length, data.toLong())
    }

    fun sequence(definition: SequenceDefinition, length: Int, data: Long): IrCommandBuilder {
        return sequence(definition, TOP_BIT_64, length, data)
    }

    fun sequence(
        definition: SequenceDefinition,
        topBit: Long,
        length: Int,
        data: Long
    ): IrCommandBuilder {
        var data = data
        for (index in 0 until length) {
            if (data and topBit != 0L) {
                definition.one(this, index)
            } else {
                definition.zero(this, index)
            }
            data = data shl 1
        }
        return this
    }

    fun build(): IrCommand {
        return IrCommand(frequency, buildSequence())
    }

    fun buildSequence(): IntArray {
        return buildRawSequence(buffer)
    }

    fun getBuffer(): List<Int> {
        return buffer
    }

    interface SequenceDefinition {
        fun one(builder: IrCommandBuilder, index: Int)
        fun zero(builder: IrCommandBuilder, index: Int)
    }

    companion object {
        const val TOP_BIT_32 = 0x1L shl 31
        const val TOP_BIT_64 = 0x1L shl 63
        fun irCommandBuilder(frequency: Int): IrCommandBuilder {
            return IrCommandBuilder(frequency)
        }

        fun simpleSequence(
            oneMark: Int,
            oneSpace: Int,
            zeroMark: Int,
            zeroSpace: Int
        ): SequenceDefinition {
            return object : SequenceDefinition {
                override fun one(builder: IrCommandBuilder, index: Int) {
                    builder.pair(oneMark, oneSpace)
                }

                override fun zero(builder: IrCommandBuilder, index: Int) {
                    builder.pair(zeroMark, zeroSpace)
                }
            }
        }

        fun buildRawSequence(vararg rawData: Int): IntArray {
            return rawData
        }

        fun buildRawSequence(buffer: List<Int>): IntArray {
            val result = IntArray(buffer.size)
            for (i in buffer.indices) {
                result[i] = buffer[i]
            }
            return result
        }

        fun buildRawSequence(dataStream: Iterable<Int>): IntArray {
            if (dataStream is List<*>) {
                return buildRawSequence(dataStream as List<Int>)
            }
            val buffer = ArrayList<Int>()
            for (data in dataStream) {
                buffer.add(data)
            }
            return buildRawSequence(buffer)
        }
    }

    init {
        buffer = ArrayList()
        lastMark = null
    }
}