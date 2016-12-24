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

package work.technie.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieInfo implements Parcelable {
    public final static Parcelable.Creator<MovieInfo> CREATOR = new Parcelable.Creator<MovieInfo>() {
        @Override
        public MovieInfo createFromParcel(Parcel parcel) {
            return new MovieInfo(parcel);
        }

        @Override
        public MovieInfo[] newArray(int size) {
            return new MovieInfo[size];
        }
    };
    private final String id;
    private final String orgLang;
    private final String orgTitle;
    private final String overview;
    private final String relDate;
    private final String postURL;
    private final String popularity;
    private final String votAvg;

    public MovieInfo(String id, String orgLang, String orgTitle, String overview,
                     String relDate, String popularity, String votAvg, String postURL) {
        this.id = id;
        this.orgLang = orgLang;
        this.orgTitle = orgTitle;
        this.overview = overview;
        this.relDate = relDate;
        this.popularity = popularity;
        this.votAvg = votAvg;
        this.postURL = postURL;
    }

    private MovieInfo(Parcel in) {
        this.id = in.readString();
        this.orgLang = in.readString();
        this.orgTitle = in.readString();
        this.overview = in.readString();
        this.relDate = in.readString();
        this.popularity = in.readString();
        this.votAvg = in.readString();
        this.postURL = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(orgLang);
        dest.writeString(orgTitle);
        dest.writeString(overview);
        dest.writeString(relDate);
        dest.writeString(popularity);
        dest.writeString(votAvg);
        dest.writeString(postURL);
    }
}