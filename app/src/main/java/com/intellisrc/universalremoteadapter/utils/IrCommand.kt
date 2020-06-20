package com.intellisrc.universalremoteadapter.utils

import com.intellisrc.universalremoteadapter.utils.IrCommandBuilder.SequenceDefinition
import timber.log.Timber

class IrCommand(val frequency: Int, val pattern: IntArray) {

    object NEC {
        private const val FREQUENCY = 38028 // T = 26.296 us
        private const val HDR_MARK = 342
        private const val HDR_SPACE = 171
        private const val BIT_MARK = 21
        private const val ONE_SPACE = 60
        private const val ZERO_SPACE = 21
        private val SEQUENCE_DEFINITION = IrCommandBuilder.simpleSequence(
            BIT_MARK,
            ONE_SPACE,
            BIT_MARK,
            ZERO_SPACE
        )

        fun buildNEC(bitCount: Int, data: Int): IrCommand {
            return IrCommandBuilder.irCommandBuilder(FREQUENCY)
                .pair(HDR_MARK, HDR_SPACE)
                .sequence(SEQUENCE_DEFINITION, bitCount, data)
                .mark(BIT_MARK)
                .build()
        }
    }

    object Sony {
        private const val FREQUENCY = 40000 // T = 25 us
        private const val HDR_MARK = 96
        private const val HDR_SPACE = 24
        private const val ONE_MARK = 48
        private const val ZERO_MARK = 24
        private val SEQUENCE_DEFINITION = IrCommandBuilder.simpleSequence(
            ONE_MARK,
            HDR_SPACE,
            ZERO_MARK,
            HDR_SPACE
        )

        fun buildSony(bitCount: Int, data: Long): IrCommand {
            return IrCommandBuilder.irCommandBuilder(FREQUENCY)
                .pair(HDR_MARK, HDR_SPACE)
                .sequence(SEQUENCE_DEFINITION, bitCount, data shl 64 - bitCount)
                .build()
        }
    }

    object RC5 {
        private const val FREQUENCY = 36000 // T = 27.78 us
        private const val T1 = 32
        private val SEQUENCE_DEFINITION: SequenceDefinition = object : SequenceDefinition {
            override fun one(builder: IrCommandBuilder, index: Int) {
                builder.reversePair(T1, T1)
            }

            override fun zero(builder: IrCommandBuilder, index: Int) {
                builder.pair(T1, T1)
            }
        }

        // Note: first bit must be a one (start bit)
        fun buildRC5(bitCount: Int, data: Long): IrCommand {
            return IrCommandBuilder.irCommandBuilder(FREQUENCY)
                .mark(T1)
                .space(T1)
                .mark(T1)
                .sequence(SEQUENCE_DEFINITION, bitCount, data shl 64 - bitCount)
                .build()
        }
    }

    object RC6 {
        private const val FREQUENCY = 36000 // T = 27.78 us
        private const val HDR_MARK = 96
        private const val HDR_SPACE = 32
        private const val T1 = 16
        val SEQUENCE_DEFINITION: SequenceDefinition = object : SequenceDefinition {
            private fun getTime(index: Int): Int {
                return if (index == 3) T1 + T1 else T1
            }

            override fun one(builder: IrCommandBuilder, index: Int) {
                val t = getTime(index)
                builder.pair(t, t)
            }

            override fun zero(builder: IrCommandBuilder, index: Int) {
                val t = getTime(index)
                builder.reversePair(t, t)
            }
        }

        // Caller needs to take care of flipping the toggle bit
        fun buildRC6(bitCount: Int, data: Long): IrCommand {
            return IrCommandBuilder.irCommandBuilder(FREQUENCY)
                .pair(HDR_MARK, HDR_SPACE)
                .pair(T1, T1)
                .sequence(SEQUENCE_DEFINITION, bitCount, data shl 64 - bitCount)
                .build()
        }
    }

    object DISH {
        private const val FREQUENCY = 56000 // T = 17.857 us
        private const val HDR_MARK = 22
        private const val HDR_SPACE = 342
        private const val BIT_MARK = 22
        private const val ONE_SPACE = 95
        private const val ZERO_SPACE = 157
        private const val TOP_BIT = 0x8000
        private val SEQUENCE_DEFINITION = IrCommandBuilder.simpleSequence(
            BIT_MARK,
            ONE_SPACE,
            BIT_MARK,
            ZERO_SPACE
        )

        fun buildDISH(bitCount: Int, data: Int): IrCommand {
            return IrCommandBuilder.irCommandBuilder(FREQUENCY)
                .pair(HDR_MARK, HDR_SPACE)
                .sequence(SEQUENCE_DEFINITION, TOP_BIT.toLong(), bitCount, data.toLong())
                .build()
        }
    }

    object Sharp {
        private const val FREQUENCY = 38000 // T = 26.315 us
        private const val BIT_MARK = 9
        private const val ONE_SPACE = 69
        private const val ZERO_SPACE = 30
        private const val DELAY = 46
        private const val INVERSE_MASK = 0x3FF
        private const val TOP_BIT = 0x4000
        private val SEQUENCE_DEFINITION = IrCommandBuilder.simpleSequence(
            BIT_MARK,
            ONE_SPACE,
            BIT_MARK,
            ZERO_SPACE
        )

        fun buildSharp(bitCount: Int, data: Int): IrCommand {
            return IrCommandBuilder.irCommandBuilder(FREQUENCY)
                .sequence(
                    SEQUENCE_DEFINITION,
                    TOP_BIT.toLong(),
                    bitCount,
                    data.toLong()
                )
                .pair(BIT_MARK, ZERO_SPACE)
                .delay(DELAY)
                .sequence(
                    SEQUENCE_DEFINITION,
                    TOP_BIT.toLong(),
                    bitCount,
                    (data xor INVERSE_MASK.toLong().toInt()).toLong()
                )
                .pair(BIT_MARK, ZERO_SPACE)
                .delay(DELAY)
                .build()
        }
    }

    object Panasonic {
        private const val FREQUENCY = 35000 // T = 28.571 us
        private const val HDR_MARK = 123
        private const val HDR_SPACE = 61
        private const val BIT_MARK = 18
        private const val ONE_SPACE = 44
        private const val ZERO_SPACE = 14
        private const val ADDRESS_TOP_BIT = 0x8000
        private const val ADDRESS_LENGTH = 16
        private const val DATA_LENGTH = 32
        private val SEQUENCE_DEFINITION = IrCommandBuilder.simpleSequence(
            BIT_MARK,
            ONE_SPACE,
            BIT_MARK,
            ZERO_SPACE
        )

        fun buildPanasonic(address: Int, data: Int): IrCommand {
            return IrCommandBuilder.irCommandBuilder(FREQUENCY)
                .pair(HDR_MARK, HDR_SPACE)
                .sequence(
                    SEQUENCE_DEFINITION,
                    ADDRESS_TOP_BIT.toLong(),
                    ADDRESS_LENGTH,
                    address.toLong()
                )
                .sequence(SEQUENCE_DEFINITION, DATA_LENGTH, data)
                .mark(BIT_MARK)
                .build()
        }
    }

    object JVC {
        private const val FREQUENCY = 38000 // T = 26.316 us
        private const val HDR_MARK = 304
        private const val HDR_SPACE = 152
        private const val BIT_MARK = 23
        private const val ONE_SPACE = 61
        private const val ZERO_SPACE = 21
        private val SEQUENCE_DEFINITION = IrCommandBuilder.simpleSequence(
            BIT_MARK,
            ONE_SPACE,
            BIT_MARK,
            ZERO_SPACE
        )

        fun buildJVC(bitCount: Int, data: Long, repeat: Boolean): IrCommand {
            val builder = IrCommandBuilder.irCommandBuilder(FREQUENCY)
            if (!repeat) builder.pair(HDR_MARK, HDR_SPACE)
            return builder.sequence(SEQUENCE_DEFINITION, bitCount, data shl 64 - bitCount)
                .mark(BIT_MARK)
                .build()
        }
    }

    object Pronto {
        fun buildPronto(protoText: String): IrCommand {
            val codeParts =
                protoText.split(" ".toRegex()).toTypedArray()
            val protoSequence = IntArray(codeParts.size)
            for (i in codeParts.indices) {
                protoSequence[i] = codeParts[i].toInt(16)
            }
            Timber.tag(TAG).d("$protoSequence")
            return buildPronto(protoSequence)
        }

        fun buildPronto(protoSequence: IntArray): IrCommand {
            val frequency = (1000000 / (protoSequence[1] * 0.241246)).toInt()
            val builder = IrCommandBuilder.irCommandBuilder(frequency)
            var i = 4
            while (i < protoSequence.size) {
                builder.pair(protoSequence[i], protoSequence[i + 1])
                i += 2
            }
            return builder.build()
        }
    }

    companion object {
        const val TAG = "IrCommand"
    }
}