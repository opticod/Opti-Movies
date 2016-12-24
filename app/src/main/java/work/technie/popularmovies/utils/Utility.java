package work.technie.popularmovies.utils;
/*
 * Copyright (C) 2017 Anupam Das
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeIntents;

import work.technie.popularmovies.R;


/**
 * Created by anupam on 19/12/15.
 */
public class Utility {

    public static boolean hasNetworkConnection(Context context) {
        boolean hasConnectedWifi = false;
        boolean hasConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    hasConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    hasConnectedMobile = true;
        }
        return hasConnectedWifi || hasConnectedMobile;
    }


    public static void playVideo(Activity mActivity, String source) {
        if (mActivity != null) {
            String version = YouTubeIntents.getInstalledYouTubeVersionName(mActivity);
            if (version == null) {
                Toast.makeText(mActivity, R.string.youtube_not_installed, Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = YouTubeIntents.createPlayVideoIntentWithOptions(mActivity, source, true, false);
                mActivity.startActivity(intent);
            }
        }
    }
}
