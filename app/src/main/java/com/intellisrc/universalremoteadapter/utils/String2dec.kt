package com.intellisrc.universalremoteadapter.utils

import android.os.Build
import android.util.Log
import timber.log.Timber
import java.util.*
import kotlin.math.ceil

/**
 * Created by josapedmoreno on 1/31/16.
 */
class String2dec {
    var data = arrayOf<String>()

    /**
     * <p>
     *     preforms some calculations on the codesets we have in order to make them work with certain models of phone.
     *
     *     HTC devices need formula 1
     *     Samsungs want formula 2
     *
     *     Samsung Pre-4.4.3 want nothing, so just return the input data
     * </p>
     */
    fun string2dec(irData: IntArray, frequency: Int): IntArray {
        val formula = shouldEquationRun()
        try {
            //Should we run any computations on the irData?
            if (formula != 0) {
                for (i in irData.indices) {
                    if (formula == 1) {
                        irData[i] =
                            (1000000L * irData[i].toString().toLong() / frequency).toInt()
                    } else if (formula == 2) {
                        //this is the samsung formula as per http://developer.samsung.com/android/technical-docs/Workaround-to-solve-issues-with-the-ConsumerIrManager-in-Android-version-lower-than-4-4-3-KitKat
                        irData[i] = ceil(irData[i] * 26.27272727272727).toInt()
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return irData
    }

    fun string2dec(irData: ArrayList<Int>, frequency: Int): ArrayList<Int> {
        val formula = shouldEquationRun()
        try {
            //Should we run any computations on the irData?
            if (formula != 0) {
                for (i in irData.indices) {
                    if (formula == 1) {
                        irData[i] =
                            (1000000L * irData[i].toString().toLong() / frequency).toInt()
                    } else if (formula == 2) {
                        //this is the samsung formula as per http://developer.samsung.com/android/technical-docs/Workaround-to-solve-issues-with-the-ConsumerIrManager-in-Android-version-lower-than-4-4-3-KitKat
                        irData[i] = ceil(irData[i] * 26.27272727272727).toInt()
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return irData
    }

    /**
     * <p>
     *     This method figures out if we should be running the equation in string2dec,
     *     which is based on the type of device. Some need it to run in order to function, some need it NOT to run
     *
     *     HTC needs it on (HTC One M8)
     *     Samsung needs occasionally a special formula, depending on the version
     *     Android 5.0+ need it on.
     *     Other devices DO NOT need anything special.
     * </p>
     */
    private fun shouldEquationRun(): Int {
        //Some notes on what Build.X will return
        //System.out.println(Build.MODEL); //One M8
        //System.out.println(Build.MANUFACTURER); //htc
        //System.out.println(Build.VERSION.SDK_INT); //19

        //Samsung's way of finding out if their OS is too new to work without a formula:
        //int lastIdx = Build.VERSION.RELEASE.lastIndexOf(".");
        //System.out.println(Build.VERSION.RELEASE.substring(lastIdx+1)); //4

        //handle HTC
        if (Build.MANUFACTURER.equals("HTC", ignoreCase = true)) {
            Timber.tag(TAG).d("HTC Handheld Systems")
            return 1
        }
        //handle Lollipop (Android 5.0.1 == SDK 21) / beyond
        if (Build.VERSION.SDK_INT >= 21) {
            Timber.tag(TAG).d("Android 5.0.1 Lollipop / SDK 21")
            return 1
        }
        //handle Samsung PRE-Android 5
        if (Build.MANUFACTURER.equals("SAMSUNG", ignoreCase = true)) {
            Timber.tag(TAG).d("Samsung Handheld Systems")
            if (Build.VERSION.SDK_INT >= 19) {
                val lastIdx = Build.VERSION.RELEASE.lastIndexOf(".")
                val VERSION_MR = Integer.valueOf(Build.VERSION.RELEASE.substring(lastIdx + 1))
                return if (VERSION_MR < 3) {
                    // Before version of Android 4.4.2
                    //Note: NO formula here, not even the other one
                    Timber.tag(TAG).d("Samsung Android Version <= 4.4.2")
                    0
                } else {
                    // Later version of Android 4.4.3
                    //run the special samsung formula here
                    Timber.tag(TAG).d("Samsung Android Version >= 4.4.3")
                    2
                }
            }
        }
        //if something else...
        return 0
    }

    companion object {
        private const val TAG = "string2dec"
    }
}