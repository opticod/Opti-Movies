package work.technie.popularmovies;

import android.graphics.Bitmap;

public class MovieInfo {
    String id;
    String orgLang;
    String orgTitle;
    String overview;
    String relDate;
    String postURL;
    String popularity;
    String votAvg;
    public MovieInfo(String id,String orgLang,String orgTitle,String overview,
                     String relDate,String popularity,String votAvg,String postURL)
    {
        this.id=id;
        this.orgLang=orgLang;
        this.orgTitle=orgTitle;
        this.overview=overview;
        this.relDate=relDate;
        this.popularity=popularity;
        this.votAvg=votAvg;
        this.postURL=postURL;
    }
}