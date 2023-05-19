package com.keepyoga.liveapp;

import android.content.Context;
import android.util.Log;

import com.ss.ttvideoengine.Resolution;
import com.ss.ttvideoengine.download.DownloadTask;
import com.ss.ttvideoengine.download.Downloader;
import com.ss.ttvideoengine.download.IDownloaderListener;
import com.ss.ttvideoengine.utils.Error;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

import static com.ss.ttvideoengine.download.DownloadTask.DOWNLOAD_TASK_STATE_CANCELING;
import static com.ss.ttvideoengine.download.DownloadTask.DOWNLOAD_TASK_STATE_COMPLETED;
import static com.ss.ttvideoengine.download.DownloadTask.DOWNLOAD_TASK_STATE_RUNNING;
import static com.ss.ttvideoengine.download.DownloadTask.DOWNLOAD_TASK_STATE_SUSPENDED;
import static com.ss.ttvideoengine.download.DownloadTask.MESSAGE_RECEIVE_ERROR;

public class DownloadManager implements IDownloaderListener {
    private static final String TAG = "DownloadManager";
    private static final long MIN_MEMORY = 1024 * 1024 * 800;
    private Downloader downloadManager;
    private Context mContext;
    private String mToken = "eyJHZXRQbGF5SW5mb1Rva2VuIjoiQWN0aW9uPUdldFBsYXlJbmZvXHUwMDI2RmlsZVR5cGU9ZXZpZGVvXHUwMDI2Rm9ybWF0PWhsc1x1MDAyNlNzbD0xXHUwMDI2VmVyc2lvbj0yMDIwLTA4LTAxXHUwMDI2VmlkPXYwMjRhYWcxMDAwMmNnZDliamJlbGF1MmNzaWdudDBnXHUwMDI2WC1BbGdvcml0aG09SE1BQy1TSEEyNTZcdTAwMjZYLUNyZWRlbnRpYWw9QUtMVE1XUTFZakZsTWpVMllqTm1ORFkxTmpnMFpHTXpORGRsWm1KaU1UVXhZVGslMkYyMDIzMDUxOSUyRmNuLW5vcnRoLTElMkZ2b2QlMkZyZXF1ZXN0XHUwMDI2WC1EYXRlPTIwMjMwNTE5VDA2MzYxNFpcdTAwMjZYLUV4cGlyZXM9NjA0ODAwXHUwMDI2WC1Ob3RTaWduQm9keT1cdTAwMjZYLVNpZ25hdHVyZT0xZWViNjg5MTU2ZDkxMmZkNjRkNmFhNmFlMzYxYzdkM2YxNzVmODk0ODk0OGRmNDhkYjkyZDNiODc5OTQ1NDk0XHUwMDI2WC1TaWduZWRIZWFkZXJzPVx1MDAyNlgtU2lnbmVkUXVlcmllcz1BY3Rpb24lM0JGaWxlVHlwZSUzQkZvcm1hdCUzQlNzbCUzQlZlcnNpb24lM0JWaWQlM0JYLUFsZ29yaXRobSUzQlgtQ3JlZGVudGlhbCUzQlgtRGF0ZSUzQlgtRXhwaXJlcyUzQlgtTm90U2lnbkJvZHklM0JYLVNpZ25lZEhlYWRlcnMlM0JYLVNpZ25lZFF1ZXJpZXMiLCJUb2tlblZlcnNpb24iOiJWMiJ9";
    private final DecimalFormat format = new DecimalFormat("#.0");
    //    private final SparseArray<DownloadTask> array = new SparseArray<>();
    private List<DownloadTask> allTasks = new ArrayList<>();

    @Override
    public void downloaderDidComplete(Downloader downloader, DownloadTask downloadTask, @Nullable Error error) {
    }

    /**
     * 下载进度回调, timeMS 时间内下载的数据量为 bytesWritten；
     * 如需计算当前任务已下载的总数据量，需要对多次回调 bytesWritten 进行累加
     *
     * @param downloadTask 下载任务
     * @param bytesWritten (单位 Byte)
     * @param timeMS       上次回调与本次回调的时间间隔
     */
    @Override
    public void downloaderWriteData(Downloader downloader, DownloadTask downloadTask, long bytesWritten, long timeMS) {
        Log.e(TAG, "downloaderWriteData---->timeMS=" + timeMS + "===" + bytesWritten);
    }

    @Override
    public void downloaderProgress(Downloader downloader, DownloadTask task, long receivedSize, long totalSize) {
        Log.e(TAG, "downloaderProgress receivedSize:" + receivedSize + "===" + "totalSize:" + totalSize);
    }

    /**
     * 开始/恢复 下载任务
     *
     * @param downloadTask       下载任务
     * @param fileOffset         已下载的数据量
     * @param expectedTotalBytes 该任务要下载的总数据量
     */
    @Override
    public void downloaderDidResume(Downloader downloader, DownloadTask downloadTask, long fileOffset, long expectedTotalBytes) {
        Log.e(TAG, "downloaderDidResume:" + fileOffset + "===" + expectedTotalBytes);
    }

    /**
     * 对应 loadAllTasks 完成的回调
     *
     * @param allTasks 所有任务的列表，包含已完成的，未完成的。
     */
    @Override
    public void downloaderDidLoadAllTask(Downloader downloader, @Nullable List<DownloadTask> allTasks, @Nullable Error error) {
        this.allTasks = allTasks;
        Log.e(TAG, "downloaderLoadAllTask--->allTasks：" + allTasks + "==============error：" + error);
    }

    @Override
    public void downloaderStateChanged(Downloader downloader, DownloadTask downloadTask, int state) {
        switch (state) {
            case DOWNLOAD_TASK_STATE_RUNNING:
                Log.e(TAG, "onDownloadStart:=====videoId:" + downloadTask.getVideoId());
                break;
            case DOWNLOAD_TASK_STATE_SUSPENDED:
                Log.e(TAG, "onDownloadStop:=====videoId:" + downloadTask.getVideoId());
                break;
            case DOWNLOAD_TASK_STATE_CANCELING:
                Log.e(TAG, "onDownloadDelete:=====videoId:" + downloadTask.getVideoId());
                break;
            case MESSAGE_RECEIVE_ERROR:
                Log.e(TAG, "onDownloadERROR:=====videoId:" + downloadTask.getVideoId());
                break;
            case DOWNLOAD_TASK_STATE_COMPLETED:
                Log.e(TAG, "onDownloadCompleted:=====videoId:" + downloadTask.getVideoId());
                break;
        }
    }

    private static class Helper {
        public static DownloadManager instance = new DownloadManager();
    }

    DownloadManager() {
        downloadManager = Downloader.getInstance();
        downloadManager.setMaxDownloadOperationCount(1);
        downloadManager.setLimitFreeDiskSize(MIN_MEMORY);
        downloadManager.setListener(this);
    }

    public static DownloadManager getInstance() {
        return Helper.instance;
    }

    public void setContext(Context context) {
        mContext = context;
        downloadManager.loadAllTasks(mContext);
    }

    public void startDownload(String vid) {
        start(vid, mToken);
    }

    private void start(String videoId, String token) {
        Resolution resolution = Resolution.High;
        if (allTasks.isEmpty()) {
            DownloadTask downloadTask = downloadManager.vidTask(videoId, resolution, token);
            Log.e(TAG, "=========resolution：" + resolution);
            if (downloadTask != null) {
                downloadTask.resume();
            }
        } else {
            Log.e(TAG, "allTasks.size:" + allTasks.size() + "=========resolution：" + resolution);
            DownloadTask task = null;
            for (DownloadTask task1 : allTasks) {
                Log.e(TAG, "task1 = " + task1 + " taskVideoId = " + task1.getVideoId() + " courseVideoId = " + videoId);

                if (task1.getVideoId().equals(videoId)) {
                    task = task1;
                    Log.i(TAG, "task is match.");
                    break;
                } else {
                    Log.w(TAG, "task not match.");
                }
            }
            if (task != null) {
                Log.i(TAG, "task not null. task = " + task + " videoId = " + task.getVideoId());
                task.resume();
            } else {
                DownloadTask downloadTask = downloadManager.vidTask(videoId, resolution, token);
                Log.e(TAG, "=========resolution：" + resolution);
                if (downloadTask != null) {
                    downloadTask.resume();
                }
            }
        }
    }

    /**
     * 停止下载
     */
    public void stop(String vid) {
        for (DownloadTask task : allTasks) {
            if (task.getVideoId().equals(vid)) {
                task.suspend();
                Log.i(TAG, "task suspend. task = " + task + " videoId = " + task.getVideoId());
                break;
            }
        }
    }

    public void deleteDownloadFile(String vid) {
        if (allTasks != null && allTasks.size() > 0) {
            for (DownloadTask task : allTasks) {
                if (task.getVideoId().equals(vid)) {
                    task.invalidateAndCancel();
                }
            }
        }
    }
}
