package com.keepyoga.liveapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import androidx.activity.ComponentActivity

/*
 * FileName: DemoActivity
 * Author: Elite.Yang
 * Date: 2023/5/16
 * Description: 
 */
class DemoActivity : ComponentActivity() {
    companion object {
        const val TAG = "DemoActivity"
    }

    private val mVideoIdList = arrayListOf(
        "v024aag10002cgd9bmjelau2csignt40",
        "v024aag10002cgd9bjbelau2csignt0g",
        "v034aag10002cgd9bgjelaub2qt1crig",
        "v024aag10002cgd9bnrelau64p0v2k30"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)

        DownloadManager.getInstance().setContext(this.applicationContext)

        requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            findViewById<CheckBox>(R.id.video1).setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    DownloadManager.getInstance().startDownload(mVideoIdList[0])
                } else {
                    DownloadManager.getInstance().stop(mVideoIdList[0])
                }
            }

            findViewById<CheckBox>(R.id.video2).setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    DownloadManager.getInstance().startDownload(mVideoIdList[1])
                } else {
                    DownloadManager.getInstance().stop(mVideoIdList[1])
                }
            }
            findViewById<CheckBox>(R.id.video3).setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    DownloadManager.getInstance().startDownload(mVideoIdList[2])
                } else {
                    DownloadManager.getInstance().stop(mVideoIdList[2])
                }
            }

            findViewById<CheckBox>(R.id.video4).setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    DownloadManager.getInstance().startDownload(mVideoIdList[3])
                } else {
                    DownloadManager.getInstance().stop(mVideoIdList[3])
                }
            }

            findViewById<Button>(R.id.delete1).setOnClickListener {
                DownloadManager.getInstance().deleteDownloadFile(mVideoIdList[0])
            }

            findViewById<Button>(R.id.delete2).setOnClickListener {
                DownloadManager.getInstance().deleteDownloadFile(mVideoIdList[1])
            }

            findViewById<Button>(R.id.delete3).setOnClickListener {
                DownloadManager.getInstance().deleteDownloadFile(mVideoIdList[2])
            }

            findViewById<Button>(R.id.delete4).setOnClickListener {
                DownloadManager.getInstance().deleteDownloadFile(mVideoIdList[3])
            }
        }
    }
}