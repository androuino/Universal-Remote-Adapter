package com.intellisrc.universalremoteadapter.utils.schedulers

import java.util.concurrent.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BackgroundSchedulers @Inject internal constructor() : Scheduler {
    private val poolWorkQueue: BlockingQueue<Runnable> =
        LinkedBlockingQueue()
    var executor: Executor = ThreadPoolExecutor(
        CORE_POOL_SIZE,
        MAXIMUM_POOL_SIZE,
        KEEP_ALIVE.toLong(),
        TimeUnit.SECONDS,
        poolWorkQueue
    ) as Executor // from ModernAsyncTask

    override fun execute(runnable: Runnable?) {
        executor.execute(runnable)
    }

    companion object {
        private const val CORE_POOL_SIZE = 8
        private const val MAXIMUM_POOL_SIZE = 128
        private const val KEEP_ALIVE = 1
    }
}