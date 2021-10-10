package com.halilibo.videoplayer

import kotlinx.coroutines.flow.StateFlow

interface VideoPlayerController {

    fun setSource(source: VideoPlayerSource)

    fun play()

    fun pause()

    fun playPauseToggle()

    fun quickSeekForward()

    fun quickSeekRewind()

    fun seekTo(position: Long)

    fun reset()

    fun startMediaSession()

    fun stopMediaSession()

    val state: StateFlow<VideoPlayerState>

    fun dispose()
}