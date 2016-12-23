package work.technie.popularmovies.utils;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Toast;

import work.technie.popularmovies.R;

/**
 * Created by anupam on 23/12/16.
 */

public class AppRate {

    private final static int DAYS_UNTIL_PROMPT = 3;
    private final static int LAUNCHES_UNTIL_PROMPT = 3;
    private static final String DONT_SHOW_AGAIN = "DONT_SHOW_AGAIN";
    private static final String LAUNCH_COUNT = "LAUNCH_COUNT";
    private static final String DEBUT_LAUNCH = "DEBUT_LAUNCH";

    public static void initializeRater(final Context mContext) {
        String APP_RATING = "APP_RATING";
        SharedPreferences prefs = mContext.getSharedPreferences(APP_RATING, 0);
        if (prefs.getBoolean(DONT_SHOW_AGAIN, false)) {
            return;
        }
        final SharedPreferences.Editor editor = prefs.edit();
        final long launchCount = prefs.getLong(LAUNCH_COUNT, 0) + 1;
        editor.putLong(LAUNCH_COUNT, launchCount);

        final long[] debutLaunch = {prefs.getLong(DEBUT_LAUNCH, 0)};
        if (debutLaunch[0] == 0) {
            debutLaunch[0] = System.currentTimeMillis();
            editor.putLong(DEBUT_LAUNCH, debutLaunch[0]);
        }
        editor.apply();

        if (launchCount >= LAUNCHES_UNTIL_PROMPT) {
            long minTime = debutLaunch[0] + DAYS_UNTIL_PROMPT * 24 * 60 * 60 * 1000;
            if (System.currentTimeMillis() >= minTime) {

                final AlertDialog dialog = new AlertDialog.Builder(mContext)
                        .setCancelable(false)
                        .setTitle(R.string.rate_our_app)
                        .setMessage(R.string.rate_message)
                        .setPositiveButton(R.string.rate_now, null)
                        .setNegativeButton(R.string.remind_me_later, null)
                        .setNeutralButton(R.string.dont_remind_me, null)
                        .create();
                dialog.show();

                dialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + mContext.getPackageName())));
                        } catch (ActivityNotFoundException e) {
                            Toast.makeText(mContext, mContext.getString(R.string.google_play_store_error), Toast.LENGTH_SHORT).show();
                        }
                        editor.putBoolean(DONT_SHOW_AGAIN, true);
                        editor.apply();
                        dialog.dismiss();
                    }
                });
                dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editor.putLong(LAUNCH_COUNT, 0);
                        debutLaunch[0] = System.currentTimeMillis();
                        editor.putLong(DEBUT_LAUNCH, debutLaunch[0]);
                        editor.apply();
                        dialog.dismiss();
                    }
                });
                dialog.getButton(DialogInterface.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editor.putBoolean(DONT_SHOW_AGAIN, true);
                        editor.apply();
                        dialog.dismiss();
                    }
                });
            }
        }
    }
}