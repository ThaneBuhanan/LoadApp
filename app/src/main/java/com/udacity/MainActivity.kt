package com.udacity

import android.app.DownloadManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : AppCompatActivity() {

    private var downloadID: Long = 0
    private lateinit var fileName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

        custom_button.setOnClickListener {

            when (radioGroup.checkedRadioButtonId) {
                -1 -> {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.describe_download),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                R.id.glideDownload -> {
                    custom_button.buttonState = ButtonState.Loading
                    download(getString(R.string.glide_url))
                    fileName = getString(R.string.glide_download)
                }
                R.id.loadAppDownload -> {
                    custom_button.buttonState = ButtonState.Loading
                    download(getString(R.string.loadApp_url))
                    fileName = getString(R.string.load_app_download)
                }
                R.id.retrofitDownload -> {
                    custom_button.buttonState = ButtonState.Loading
                    download(getString(R.string.retrofit_url))
                    fileName = getString(R.string.retrofit_download)
                }
            }

        }

        createChannel(
            getString(R.string.load_app_channel_id),
            getString(R.string.load_app_channel_name),
        )
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)

            if (downloadID != id) {
                return
            }

            val notificationManager = ContextCompat.getSystemService(
                requireNotNull(context),
                NotificationManager::class.java
            ) as NotificationManager

            custom_button.buttonState = ButtonState.Completed
            val status = getString(R.string.status_success)
            notificationManager.sendNotification(
                "Download Complete.",
                applicationContext,
                fileName,
                status
            )

        }
    }

    private fun download(url: String) {
        val request =
            DownloadManager.Request(Uri.parse(url))
                .setTitle(getString(R.string.app_name))
                .setDescription(getString(R.string.app_description))
                .setRequiresCharging(false)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)

        val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        downloadID =
            downloadManager.enqueue(request)// enqueue puts the download request in the queue.
    }

    private fun createChannel(channelId: String, channelName: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            )
                .apply {
                    setShowBadge(false)
                }
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "Time for breakfast"

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
}
