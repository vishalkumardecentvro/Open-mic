package com.myapp.openmic.fragments.videos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerSupportFragment
import com.myapp.openmic.R
import com.myapp.openmic.YoutubeConfig
import com.myapp.openmic.databinding.FragmentVideosBinding

class VideosFragment : Fragment() {
  private var _binding : FragmentVideosBinding ? = null
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    _binding = FragmentVideosBinding.inflate(LayoutInflater.from(context),container,false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    instantiate()
    initialize()
    listen()
    load()
  }

  private fun instantiate(){

  }

  private fun initialize(){

  }

  private fun listen(){



  }

  private fun load(){

  }
}