package com.example.a4thtaskappkts.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.a4thtaskappkts.Model.Model
import com.example.a4thtaskappkts.R

class Adapter1(private var list: List<Model>?, private var context: Context) :
    RecyclerView.Adapter<Adapter1.ViewHolder?>() {
    var lastVideoView: VideoView? = null
    var lastImageView: ImageView? = null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
         return list!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val videoModel = list?.get(position)
        videoModel?.let { holder.bind(it) }
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var thumbNail: ImageView = itemView.findViewById(R.id.thumbNail)
        var videoView: VideoView = itemView.findViewById(R.id.videoView)
        var typeImage: ImageView = itemView.findViewById(R.id.type_image)
        var type_txt: TextView = itemView.findViewById(R.id.type_txt)
        var dollarsTxt: TextView = itemView.findViewById(R.id.dollarsTxt)
        var userNameTxt: TextView = itemView.findViewById(R.id.userNameTxt)
        var country: ImageView = itemView.findViewById(R.id.country)
        var level: TextView = itemView.findViewById(R.id.level)
        var language: TextView = itemView.findViewById(R.id.language)
        var location: TextView = itemView.findViewById(R.id.location)
        var linear_layout1: LinearLayout = itemView.findViewById(R.id.linear_layout1)



        @SuppressLint("ClickableViewAccessibility")
        fun bind(videoModel: Model) {

            // VideoView
            val videoUri = Uri.parse(videoModel.video)
            videoView.setVideoURI(videoUri)
            videoView.setMediaController(null)

            // thumbNail
            Glide.with(context).load(videoModel.thumbNail).centerCrop().placeholder(R.drawable.img).error(R.drawable.baseline_language_24).into(thumbNail)

            // Type
            Glide.with(context).load(videoModel.type).centerCrop().placeholder(R.drawable.img).error(R.drawable.baseline_language_24).into(typeImage)

            // Country
            Glide.with(context).load(videoModel.countryImage).centerCrop().placeholder(R.drawable.img).error(R.drawable.baseline_language_24).into(country)

            // UserName
            userNameTxt.text = videoModel.userName

            // Language
            language.text = videoModel.language

            // Level
            level.text = videoModel.level

            // Dollar
            dollarsTxt.text = videoModel.dollars

            // Location
            location.text = videoModel.location

            // type text
            type_txt.text = videoModel.typeTxt


            // VideoView pause play code starts here
            videoView.stopPlayback()
            thumbNail.visibility = View.VISIBLE
            videoView.setVisibility(View.GONE)

            videoView.setOnPreparedListener { mp -> mp.setVolume(0f, 0f) }

            linear_layout1.setOnTouchListener { _, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        if (!videoView.isPlaying) {
                            startVideo(videoView)
                            thumbNail.visibility = View.GONE
                            videoView.visibility = View.VISIBLE
                        }
                    }
                    MotionEvent.ACTION_UP -> {
                        pauseVideo(videoView)
                        thumbNail.visibility = View.VISIBLE
                        videoView.visibility = View.GONE
                    }
                }
                true
            }
        }

        private fun startVideo(videoView: VideoView) {
            lastVideoView?.let {
                if (it != videoView) {
                    pauseVideo(it)
                }
            }
            videoView.start()
            lastVideoView = videoView
            lastImageView = thumbNail
        }

        private fun pauseVideo(videoView: VideoView) {
            videoView.pause()
            lastImageView?.visibility = View.VISIBLE
            lastVideoView = null
            lastImageView = null
        }
    }
}