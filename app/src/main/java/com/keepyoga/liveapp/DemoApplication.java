package com.keepyoga.liveapp;

import android.app.Application;

import com.bytedance.playerkit.player.cache.CacheKeyFactory;
import com.bytedance.playerkit.player.source.MediaSource;
import com.bytedance.playerkit.player.source.Quality;
import com.bytedance.playerkit.player.source.Track;
import com.bytedance.playerkit.player.source.TrackSelector;
import com.bytedance.playerkit.player.volcengine.VolcPlayerInit;
import com.pandora.ttlicense2.LicenseManager;
import com.ss.ttvideoengine.DataLoaderHelper;
import com.ss.ttvideoengine.TTVideoEngine;
import com.ss.ttvideoengine.download.Downloader;
import com.ss.ttvideoengine.utils.TTVideoEngineLog;

import java.util.List;

import androidx.annotation.NonNull;

/*
 * FileName: DemoApplication
 * Author: Elite.Yang
 * Date: 2023/5/16
 * Description:
 */
public class DemoApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //火山视频
//        TTVideoEngineLog.turnOn(TTVideoEngineLog.LOG_DEBUG, 1);
//        AVMDLLog.turnOn(AVMDLLog.LOG_DEBUG, 1);
//        LicenseManager.turnOnLogcat(true);
//
//        File videoCacheDir = new File(getCacheDir(), "video_cache");
//        if (!videoCacheDir.exists()) videoCacheDir.mkdirs();
//        VodConfig.Builder vodBuilder = new VodConfig.Builder(this)
//                .setCacheDirPath(videoCacheDir.getAbsolutePath())
//                .setMaxCacheSize(300 * 1024 * 1024);
//
//        Env.init(new Config.Builder()
//                .setApplicationContext(this)
//                .setAppID("102987")
//                .setAppName("live_app_test")
//                // 合法版本号应大于、等于 2 个分隔符，如："1.3.2"
//                .setAppVersion(BuildConfig.VERSION_NAME)
//                .setAppChannel("sxyLiveApp")
//                // 将 license 文件拷贝到 app 的 assets 文件夹中，并设置 LicenseUri
//                // 下面 LicenseUri 对应工程中 assets 路径为：assets/license/vod.lic
//                .setLicenseUri("assets:///license2/l-102987-ch-vod-a-419691.lic")
//                .setVodConfig(vodBuilder.build())
//                .build());
//
//        TTVideoEngine.setIntValue(DataLoaderHelper.DATALOADER_KEY_ENABLE_HLS_PROXY, 1);
//        //下载相关
////        TTVideoEngine.setStringValue(DATALOADER_KEY_STRING_DOWNLOAD_DIR, getExternalCacheDir().getAbsolutePath());
//        try {
//            // 启动数据模块
//            TTVideoEngine.startDataLoader(this);
//        } catch (Exception e) {
//            // 数据模块开启失败
//            e.printStackTrace();
//        }
//
//        // 空闲磁盘大小界限设置为 1G，若剩余磁盘大小不足 1G，返回空间不足的错误
//        Downloader.getInstance().setLimitFreeDiskSize(1024 * 1024 * 1024);

        initLiveSDK();
    }

    private void initLiveSDK() {
        if (BuildConfig.DEBUG) {
            TTVideoEngineLog.turnOn(TTVideoEngineLog.LOG_DEBUG, 1);
            LicenseManager.turnOnLogcat(true);
//            L.ENABLE_LOG = true;
        }
        TTVideoEngine.setIntValue(DataLoaderHelper.DATALOADER_KEY_ENABLE_HLS_PROXY, 1);

        VolcPlayerInit.AppInfo appInfo = new VolcPlayerInit.AppInfo.Builder()
                .setAppId("102987")
                .setAppName("live_app_test")
                .setAppRegion("china")
                .setAppChannel("sxyLiveApp")
                // 合法版本号应大于、等于 2 个分隔符，如："1.3.2"
                .setAppVersion(BuildConfig.VERSION_NAME)
                .setLicenseUri("assets:///license2/l-102987-ch-vod-a-419691.lic")
                .build();

        final int qualityRes = Quality.QUALITY_RES_720;

        final TrackSelector trackSelector = new TrackSelector() {
            @NonNull
            @Override
            public Track selectTrack(int type, int trackType, @NonNull List<Track> tracks, @NonNull MediaSource source) {
                for (Track track : tracks) {
                    Quality quality = track.getQuality();
                    if (quality != null) {
                        if (quality.getQualityRes() == qualityRes) {
                            return track;
                        }
                    }
                }
                return tracks.get(0);
            }
        };

        VolcPlayerInit.init(getApplicationContext(), appInfo, CacheKeyFactory.DEFAULT, trackSelector);

        try {
            // 启动数据模块
            TTVideoEngine.startDataLoader(this);
        } catch (Exception e) {
            // 数据模块开启失败
            e.printStackTrace();
        }

        // 空闲磁盘大小界限设置为 1G，若剩余磁盘大小不足 1G，返回空间不足的错误
        Downloader.getInstance().setLimitFreeDiskSize(1024 * 1024 * 1024);
    }
}
