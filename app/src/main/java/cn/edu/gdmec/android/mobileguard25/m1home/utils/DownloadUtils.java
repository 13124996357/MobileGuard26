package cn.edu.gdmec.android.mobileguard25.m1home.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.webkit.MimeTypeMap;


class DownloadUtils {
    static DownloadUtils downloadUtils;

    void downloadApk(String url, String targetFile, Context context){
            DownloadManager.Request request=new DownloadManager.Request(Uri.parse(url));
            request.setAllowedOverRoaming(false);
            MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
            String mimeString=mimeTypeMap.getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(url));
            request.setMimeType(mimeString);

            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
            request.setVisibleInDownloadsUi(true);

            request.setDestinationInExternalPublicDir("/download",targetFile);
        context.getSystemService(Context.DOWNLOAD_SERVICE);


    }
    }


