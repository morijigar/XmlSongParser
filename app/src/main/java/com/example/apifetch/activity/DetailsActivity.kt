package com.example.apifetch.activity

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.apifetch.BR
import com.example.apifetch.R
import com.example.apifetch.base.BaseActivity
import com.example.apifetch.databinding.ActivityDetailBinding
import com.example.apifetch.viewmodel.DetailActivityViewModel
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.util.Util


class DetailsActivity : BaseActivity<ActivityDetailBinding, DetailActivityViewModel>() {

    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.activity_detail
    override val viewModel: DetailActivityViewModel
        get() = ViewModelProvider(this).get(DetailActivityViewModel::class.java)

    lateinit var songTitle: String
    var audioUri: String? = null
    private lateinit var exoPlayer: ExoPlayer
    private var player: SimpleExoPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        songTitle = intent.getStringExtra("songTitle")

        supportActionBar?.title = songTitle;
        supportActionBar?.setDisplayHomeAsUpEnabled(true);



        setObserver()

        viewModel.callSongDetail(this, songTitle)


    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun setObserver() {

        viewModel.listData.observe(this, Observer {
            //viewDataBinding!!.progress.visibility = View.GONE
            viewDataBinding!!.model = it!!
            audioUri = it.link.href

            initializePlayer()


        })


    }

    private fun initializePlayer() {
        if (audioUri != null) {
            player = SimpleExoPlayer.Builder(this).build()
            viewDataBinding?.playerView?.setPlayer(player)
            val mediaItem = MediaItem.fromUri(audioUri!!)
            player?.setMediaItem(mediaItem)

            player?.setPlayWhenReady(playWhenReady);
            player?.seekTo(currentWindow, playbackPosition);
            player?.prepare();
        }
    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >= 24) {
            initializePlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        if (Util.SDK_INT < 24 || player == null) {
            initializePlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT < 24) {
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT >= 24) {
            releasePlayer()
        }
    }

    private var playWhenReady = true;
    private var currentWindow = 0;
    private var playbackPosition = 0L;

    fun releasePlayer() {
        if (player != null) {
            playWhenReady = player!!.getPlayWhenReady();
            playbackPosition = player!!.getCurrentPosition();
            currentWindow = player!!.getCurrentWindowIndex();
            player!!.release();
            player = null;
        }
    }


}
