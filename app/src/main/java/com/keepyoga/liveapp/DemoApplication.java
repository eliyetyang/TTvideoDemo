package com.keepyoga.liveapp;

import android.app.Application;

import com.pandora.common.env.Env;
import com.pandora.common.env.config.Config;
import com.pandora.common.env.config.VodConfig;
import com.pandora.ttlicense2.LicenseManager;
import com.ss.mediakit.medialoader.AVMDLLog;
import com.ss.ttvideoengine.DataLoaderHelper;
import com.ss.ttvideoengine.TTVideoEngine;
import com.ss.ttvideoengine.download.Downloader;
import com.ss.ttvideoengine.utils.TTVideoEngineLog;

import java.io.File;

import static com.ss.ttvideoengine.DataLoaderHelper.DATALOADER_KEY_STRING_DOWNLOAD_DIR;

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
        TTVideoEngineLog.turnOn(TTVideoEngineLog.LOG_DEBUG, 1);
        AVMDLLog.turnOn(AVMDLLog.LOG_DEBUG, 1);
        LicenseManager.turnOnLogcat(true);

        File videoCacheDir = new File(getCacheDir(), "video_cache");
        if (!videoCacheDir.exists()) videoCacheDir.mkdirs();
        VodConfig.Builder vodBuilder = new VodConfig.Builder(this)
                .setCacheDirPath(videoCacheDir.getAbsolutePath())
                .setMaxCacheSize(300 * 1024 * 1024);

        Env.init(new Config.Builder()
                .setApplicationContext(this)
                .setAppID("102987")
                .setAppName("live_app_test")
                // 合法版本号应大于、等于 2 个分隔符，如："1.3.2"
                .setAppVersion(BuildConfig.VERSION_NAME)
                .setAppChannel("sxyLiveApp")
                // 将 license 文件拷贝到 app 的 assets 文件夹中，并设置 LicenseUri
                // 下面 LicenseUri 对应工程中 assets 路径为：assets/license/vod.lic
                .setLicenseUri("assets:///license2/l-102987-ch-vod-a-419691.lic")
                .setVodConfig(vodBuilder.build())
                .build());

        TTVideoEngine.setIntValue(DataLoaderHelper.DATALOADER_KEY_ENABLE_HLS_PROXY, 1);
        //下载相关
        TTVideoEngine.setStringValue(DATALOADER_KEY_STRING_DOWNLOAD_DIR, "download dir");
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
