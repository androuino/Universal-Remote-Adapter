package com.intellisrc.universalremoteadapter.utils.schedulers

interface Scheduler {
    fun execute(runnable: Runnable?)
}