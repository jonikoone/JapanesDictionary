package com.jonikoone.dictionaryforlearning.util

import kotlinx.coroutines.*
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

interface SuspendWork<T> : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    var job: Job?
    val delaySuspend: Long
    val work: (T) -> Unit

    fun createJob(t: T) = launch {
        delay(delaySuspend)
        work(t)
    }

    fun backgroundWork(t: T) {
        job?.cancel()
        job = createJob(t)
    }
}

/*
class SuspendWorkDefault<T>(override val delaySuspend: Long = 300) : SuspendWork<T> {
    override var job: Job? = null
    override lateinit var work: (T) -> Unit
*//*override val work: (T) -> Unit = {
        Timber.d("Default implementation is not override!")
    }*//*

}*/
