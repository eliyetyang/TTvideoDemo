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
        Pair("v024aag10002cgd9bjbelau2csignt0g", "eyJHZXRQbGF5SW5mb1Rva2VuIjoiQWN0aW9uPUdldFBsYXlJbmZvXHUwMDI2RmlsZVR5cGU9ZXZpZGVvXHUwMDI2Rm9ybWF0PWhsc1x1MDAyNlNzbD0xXHUwMDI2VmVyc2lvbj0yMDIwLTA4LTAxXHUwMDI2VmlkPXYwMjRhYWcxMDAwMmNnZDliamJlbGF1MmNzaWdudDBnXHUwMDI2WC1BbGdvcml0aG09SE1BQy1TSEEyNTZcdTAwMjZYLUNyZWRlbnRpYWw9QUtMVE1XUTFZakZsTWpVMllqTm1ORFkxTmpnMFpHTXpORGRsWm1KaU1UVXhZVGslMkYyMDIzMDYwOSUyRmNuLW5vcnRoLTElMkZ2b2QlMkZyZXF1ZXN0XHUwMDI2WC1EYXRlPTIwMjMwNjA5VDA3MjUwMlpcdTAwMjZYLUV4cGlyZXM9NjA0ODAwXHUwMDI2WC1Ob3RTaWduQm9keT1cdTAwMjZYLVNpZ25hdHVyZT1mNWEzMWQxZDYwM2M4YjYyZjUzOWYyOWYwZjE4NTRhOWQ3MGFmNTk0YjM2ZDBhN2RiNGU3YTc3NGM0ZWY2NmQxXHUwMDI2WC1TaWduZWRIZWFkZXJzPVx1MDAyNlgtU2lnbmVkUXVlcmllcz1BY3Rpb24lM0JGaWxlVHlwZSUzQkZvcm1hdCUzQlNzbCUzQlZlcnNpb24lM0JWaWQlM0JYLUFsZ29yaXRobSUzQlgtQ3JlZGVudGlhbCUzQlgtRGF0ZSUzQlgtRXhwaXJlcyUzQlgtTm90U2lnbkJvZHklM0JYLVNpZ25lZEhlYWRlcnMlM0JYLVNpZ25lZFF1ZXJpZXMiLCJUb2tlblZlcnNpb24iOiJWMiJ9"),
        Pair("v034aag10002cgd9bgjelaub2qt1crig", "eyJHZXRQbGF5SW5mb1Rva2VuIjoiQWN0aW9uPUdldFBsYXlJbmZvXHUwMDI2RmlsZVR5cGU9ZXZpZGVvXHUwMDI2Rm9ybWF0PWhsc1x1MDAyNlNzbD0xXHUwMDI2VmVyc2lvbj0yMDIwLTA4LTAxXHUwMDI2VmlkPXYwMzRhYWcxMDAwMmNnZDliZ2plbGF1YjJxdDFjcmlnXHUwMDI2WC1BbGdvcml0aG09SE1BQy1TSEEyNTZcdTAwMjZYLUNyZWRlbnRpYWw9QUtMVE1XUTFZakZsTWpVMllqTm1ORFkxTmpnMFpHTXpORGRsWm1KaU1UVXhZVGslMkYyMDIzMDYwOSUyRmNuLW5vcnRoLTElMkZ2b2QlMkZyZXF1ZXN0XHUwMDI2WC1EYXRlPTIwMjMwNjA5VDA3MjUwMlpcdTAwMjZYLUV4cGlyZXM9NjA0ODAwXHUwMDI2WC1Ob3RTaWduQm9keT1cdTAwMjZYLVNpZ25hdHVyZT1hMThkMzJkMzg0ZjFhZjFmNjg0MWE3NmE4N2ZhMjE5ODg4NGMwMDU4OTRjZjhjOTQzNTg1OTMyYjViOTg0ODQ1XHUwMDI2WC1TaWduZWRIZWFkZXJzPVx1MDAyNlgtU2lnbmVkUXVlcmllcz1BY3Rpb24lM0JGaWxlVHlwZSUzQkZvcm1hdCUzQlNzbCUzQlZlcnNpb24lM0JWaWQlM0JYLUFsZ29yaXRobSUzQlgtQ3JlZGVudGlhbCUzQlgtRGF0ZSUzQlgtRXhwaXJlcyUzQlgtTm90U2lnbkJvZHklM0JYLVNpZ25lZEhlYWRlcnMlM0JYLVNpZ25lZFF1ZXJpZXMiLCJUb2tlblZlcnNpb24iOiJWMiJ9"),
        Pair("v024aag10002chf0566qibocle03151g", "eyJHZXRQbGF5SW5mb1Rva2VuIjoiQWN0aW9uPUdldFBsYXlJbmZvXHUwMDI2RmlsZVR5cGU9ZXZpZGVvXHUwMDI2Rm9ybWF0PWhsc1x1MDAyNlNzbD0xXHUwMDI2VmVyc2lvbj0yMDIwLTA4LTAxXHUwMDI2VmlkPXYwMjRhYWcxMDAwMmNoZjA1NjZxaWJvY2xlMDMxNTFnXHUwMDI2WC1BbGdvcml0aG09SE1BQy1TSEEyNTZcdTAwMjZYLUNyZWRlbnRpYWw9QUtMVE1XUTFZakZsTWpVMllqTm1ORFkxTmpnMFpHTXpORGRsWm1KaU1UVXhZVGslMkYyMDIzMDYwOSUyRmNuLW5vcnRoLTElMkZ2b2QlMkZyZXF1ZXN0XHUwMDI2WC1EYXRlPTIwMjMwNjA5VDA3MjUwMlpcdTAwMjZYLUV4cGlyZXM9NjA0ODAwXHUwMDI2WC1Ob3RTaWduQm9keT1cdTAwMjZYLVNpZ25hdHVyZT02YzVjNTBhNjNmMDkxMjU4ZjJlZDc3ODExY2VjMjM5NWM5MDBlOTQwMmZmYzc1MmU2ZDQzMGM2YTViZjU3NDlkXHUwMDI2WC1TaWduZWRIZWFkZXJzPVx1MDAyNlgtU2lnbmVkUXVlcmllcz1BY3Rpb24lM0JGaWxlVHlwZSUzQkZvcm1hdCUzQlNzbCUzQlZlcnNpb24lM0JWaWQlM0JYLUFsZ29yaXRobSUzQlgtQ3JlZGVudGlhbCUzQlgtRGF0ZSUzQlgtRXhwaXJlcyUzQlgtTm90U2lnbkJvZHklM0JYLVNpZ25lZEhlYWRlcnMlM0JYLVNpZ25lZFF1ZXJpZXMiLCJUb2tlblZlcnNpb24iOiJWMiJ9"),
        Pair("v024aag10002cgd9bnrelau64p0v2k30", "eyJHZXRQbGF5SW5mb1Rva2VuIjoiQWN0aW9uPUdldFBsYXlJbmZvXHUwMDI2RmlsZVR5cGU9ZXZpZGVvXHUwMDI2Rm9ybWF0PWhsc1x1MDAyNlNzbD0xXHUwMDI2VmVyc2lvbj0yMDIwLTA4LTAxXHUwMDI2VmlkPXYwMjRhYWcxMDAwMmNnZDlibnJlbGF1NjRwMHYyazMwXHUwMDI2WC1BbGdvcml0aG09SE1BQy1TSEEyNTZcdTAwMjZYLUNyZWRlbnRpYWw9QUtMVE1XUTFZakZsTWpVMllqTm1ORFkxTmpnMFpHTXpORGRsWm1KaU1UVXhZVGslMkYyMDIzMDYwOSUyRmNuLW5vcnRoLTElMkZ2b2QlMkZyZXF1ZXN0XHUwMDI2WC1EYXRlPTIwMjMwNjA5VDA3MjUwMlpcdTAwMjZYLUV4cGlyZXM9NjA0ODAwXHUwMDI2WC1Ob3RTaWduQm9keT1cdTAwMjZYLVNpZ25hdHVyZT1hZWJmMDY0M2ZmYmYwNzMxOTU3YmZkYjk2NGVkZWVkNzcxOTVjZjFjMjVjMzk0NGEwZDk2NjU4Yjg1YzZkNWM0XHUwMDI2WC1TaWduZWRIZWFkZXJzPVx1MDAyNlgtU2lnbmVkUXVlcmllcz1BY3Rpb24lM0JGaWxlVHlwZSUzQkZvcm1hdCUzQlNzbCUzQlZlcnNpb24lM0JWaWQlM0JYLUFsZ29yaXRobSUzQlgtQ3JlZGVudGlhbCUzQlgtRGF0ZSUzQlgtRXhwaXJlcyUzQlgtTm90U2lnbkJvZHklM0JYLVNpZ25lZEhlYWRlcnMlM0JYLVNpZ25lZFF1ZXJpZXMiLCJUb2tlblZlcnNpb24iOiJWMiJ9")
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
                    DownloadManager.getInstance()
                        .startDownload(
                            mVideoIdList[0].first,
                            mVideoIdList[0].second
                        )
                } else {
                    DownloadManager.getInstance().stop(mVideoIdList[0].first)
                }
            }

            findViewById<CheckBox>(R.id.video2).setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    DownloadManager.getInstance().startDownload(
                        mVideoIdList[1].first,
                        mVideoIdList[1].second
                    )
                } else {
                    DownloadManager.getInstance().stop(mVideoIdList[1].first)
                }
            }
            findViewById<CheckBox>(R.id.video3).setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    DownloadManager.getInstance().startDownload(
                        mVideoIdList[2].first,
                        mVideoIdList[2].second
                    )
                } else {
                    DownloadManager.getInstance().stop(mVideoIdList[2].first)
                }
            }

            findViewById<CheckBox>(R.id.video4).setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    DownloadManager.getInstance().startDownload(
                        mVideoIdList[3].first,
                        mVideoIdList[3].second
                    )
                } else {
                    DownloadManager.getInstance().stop(mVideoIdList[3].first)
                }
            }

            findViewById<Button>(R.id.delete1).setOnClickListener {
                DownloadManager.getInstance().deleteDownloadFile(mVideoIdList[0].first)
            }

            findViewById<Button>(R.id.delete2).setOnClickListener {
                DownloadManager.getInstance().deleteDownloadFile(mVideoIdList[1].first)
            }

            findViewById<Button>(R.id.delete3).setOnClickListener {
                DownloadManager.getInstance().deleteDownloadFile(mVideoIdList[2].first)
            }

            findViewById<Button>(R.id.delete4).setOnClickListener {
                DownloadManager.getInstance().deleteDownloadFile(mVideoIdList[3].first)
            }
        }
    }
}