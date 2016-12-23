package work.technie.popularmovies.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;
import java.util.Arrays;
import java.util.Locale;

import work.technie.popularmovies.R;
import work.technie.popularmovies.data.MovieContract;

/**
 * Created by anupam on 16/12/16.
 */

public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static void setListPreferenceRegion(ListPreference lp) {

        Locale[] locales = Locale.getAvailableLocales();

        Country entry[] = new Country[locales.length];

        int i = 0;
        for (Locale locale : locales) {
            String name = locale.getDisplayCountry().trim();
            String code = locale.getCountry();
            if (name.length() > 0) {
                boolean flag = true;
                for (int j = 0; j < i; j++) {
                    if (name.equals(entry[j].name)) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    entry[i++] = new Country(name, code);
                }
            }
        }

        Arrays.sort(entry, 0, i);
        CharSequence countryName[] = new CharSequence[i];
        CharSequence countryCode[] = new CharSequence[i];

        for (int j = 0; j < i; j++) {
            countryName[j] = entry[j].name;
            countryCode[j] = entry[j].code;
        }

        lp.setEntries(countryName);
        lp.setEntryValues(countryCode);
    }

    private static void setListPreferenceLanguage(ListPreference lp) {

        Locale[] locales = Locale.getAvailableLocales();

        Language entry[] = new Language[locales.length];

        int i = 0;
        for (Locale locale : locales) {
            String name = locale.getDisplayLanguage().trim();
            String code = locale.getLanguage();
            if (name.length() > 0) {
                boolean flag = true;
                for (int j = 0; j < i; j++) {
                    if (name.equals(entry[j].name)) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    entry[i++] = new Language(name, code);
                }
            }
        }

        Arrays.sort(entry, 0, i);
        CharSequence countryName[] = new CharSequence[i];
        CharSequence countryCode[] = new CharSequence[i];

        for (int j = 0; j < i; j++) {
            countryName[j] = entry[j].name;
            countryCode[j] = entry[j].code;
        }

        lp.setEntries(countryName);
        lp.setEntryValues(countryCode);
    }

    private static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (String mChildren : children) {
                boolean success = deleteDir(new File(dir, mChildren));
                if (!success) {
                    return false;
                }
            }
            return dir.delete();
        } else
            return dir != null && dir.isFile() && dir.delete();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        addPreferencesFromResource(R.xml.pref_settings);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String KEY_REGION = "region";
        String KEY_LANGUAGE = "language";
        String KEY_DELETE = "delete";
        String KEY_REVIEW = "review";

        final ListPreference listPreferenceRegion = (ListPreference) findPreference(KEY_REGION);
        final ListPreference listPreferenceLanguage = (ListPreference) findPreference(KEY_LANGUAGE);

        setListPreferenceRegion(listPreferenceRegion);
        setListPreferenceLanguage(listPreferenceLanguage);

        listPreferenceRegion.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                setListPreferenceRegion(listPreferenceRegion);
                return false;
            }
        });
        int prefIndex = listPreferenceRegion.findIndexOfValue(sharedPreferences.getString(KEY_REGION, ""));
        if (prefIndex >= 0) {
            listPreferenceRegion.setSummary(listPreferenceRegion.getEntries()[prefIndex]);
        }

        listPreferenceLanguage.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                setListPreferenceLanguage(listPreferenceLanguage);
                return false;
            }
        });
        prefIndex = listPreferenceLanguage.findIndexOfValue(sharedPreferences.getString(KEY_LANGUAGE, ""));
        if (prefIndex >= 0) {
            listPreferenceLanguage.setSummary(listPreferenceLanguage.getEntries()[prefIndex]);
        }

        Preference deletePreference = findPreference(KEY_DELETE);
        deletePreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                deleteCache();
                getContentResolver().delete(MovieContract.Movies.buildMovieUri(), null, null);
                getContentResolver().delete(MovieContract.TV.buildTVUri(), null, null);
                getContentResolver().delete(MovieContract.Videos.buildVideosUri(), null, null);
                getContentResolver().delete(MovieContract.SimilarMovies.buildSimilarMovieUri(), null, null);
                getContentResolver().delete(MovieContract.Cast.buildCastUri(), null, null);
                getContentResolver().delete(MovieContract.Crew.buildCrewUri(), null, null);
                getContentResolver().delete(MovieContract.People.buildPersonUri(), null, null);
                getContentResolver().delete(MovieContract.Reviews.buildReviewUri(), null, null);
                getContentResolver().delete(MovieContract.Genres.buildGenreUri(), null, null);
                getContentResolver().delete(MovieContract.MovieDetails.buildMovieDetailsUri(), null, null);
                getContentResolver().delete(MovieContract.FavouritesTVs.buildTVUri(), null, null);
                getContentResolver().delete(MovieContract.FavouritesMovies.buildMovieUri(), null, null);

                getContentResolver().delete(MovieContract.TVDetails.buildTVDetailsUri(), null, null);
                getContentResolver().delete(MovieContract.TVCast.buildCastUri(), null, null);
                getContentResolver().delete(MovieContract.TVCreator.buildTVCreatorUri(), null, null);
                getContentResolver().delete(MovieContract.TVEpisodeRuntime.buildTVEpisodeRuntimeUri(), null, null);
                getContentResolver().delete(MovieContract.TVGenres.buildGenreUri(), null, null);
                getContentResolver().delete(MovieContract.TVNetworks.buildGenreUri(), null, null);
                getContentResolver().delete(MovieContract.TVSeasons.buildGenreUri(), null, null);
                getContentResolver().delete(MovieContract.TVSimilar.buildSimilarTVUri(), null, null);
                getContentResolver().delete(MovieContract.TVVideos.buildTVVideosUri(), null, null);
                getContentResolver().delete(MovieContract.TVEpisodeCrew.buildEpisodeCrewUri(), null, null);
                getContentResolver().delete(MovieContract.TVEpisodeGuestStar.buildEpisodeGuestStarUri(), null, null);
                getContentResolver().delete(MovieContract.TVEpisode.buildEpisodeUri(), null, null);
                getContentResolver().delete(MovieContract.TVSeasonDetails.buildSeasonDetailsUri(), null, null);

                Toast.makeText(getApplicationContext(), "Successfully Deleted", Toast.LENGTH_SHORT).show();

                return false;
            }
        });

        Preference reviewPreference = findPreference(KEY_REVIEW);
        reviewPreference.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                return false;
            }
        });
    }

    private void deleteCache() {
        try {
            File dir = getCacheDir();
            deleteDir(dir);
        } catch (Exception ignored) {
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        Preference preference = findPreference(key);
        if (preference instanceof ListPreference) {
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(sharedPreferences.getString(key, ""));
            if (prefIndex >= 0) {
                preference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    static class Country implements Comparable<Country> {
        final CharSequence name;
        final CharSequence code;

        Country(CharSequence name, CharSequence code) {
            this.name = name;
            this.code = code;
        }

        @Override
        public int compareTo(@NonNull Country another) {
            return this.name.toString().compareTo(another.name.toString());
        }
    }

    static class Language implements Comparable<Language> {
        final CharSequence name;
        final CharSequence code;

        Language(CharSequence name, CharSequence code) {
            this.name = name;
            this.code = code;
        }

        @Override
        public int compareTo(@NonNull Language another) {
            return this.name.toString().compareTo(another.name.toString());
        }
    }
}