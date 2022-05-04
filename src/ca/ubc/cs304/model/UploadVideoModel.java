package ca.ubc.cs304.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class UploadVideoModel extends Model{
    //CREATE TABLE Upload_Video_1 (
    //vid Integer,
    //cid Integer Not Null,
    //date Date,
    //title Char[100],
    //description Char[200],
    //num_dislike Integer,
    //num_like Integer,
    //link Url_link,
    //Primary Key (vid),
    //Unique (link),
    //Foreign Key (cid) references Channel
    //)
    private final int vid;
    private final int cid;
    private final LocalDate date;
    private final String title;
    private final String description;
    private final int num_dislike;
    private final int num_like;
    private final String link;

    public UploadVideoModel(int vid, int cid, LocalDate date, String title, String description, int num_dislike,
                            int num_like, String link) {
        this.vid = vid;
        this.cid = cid;
        this.date = date;
        this.title = title;
        this.description = description;
        this.num_dislike = num_dislike;
        this.num_like = num_like;
        this.link = link;
    }

    public int getVid() {
        return vid;
    }

    public int getCid() {
        return cid;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getNum_dislike() {
        return num_dislike;
    }

    public int getNum_like() {
        return num_like;
    }

    public String getLink() {
        return link;
    }

    public static String[] getFields() {
        return new String[] {"vid", "cid", "date", "title", "description", "num_dislike", "num_like", "link"};
    }

    public ArrayList<String> getValues() {
        ArrayList<String> result = new ArrayList<>();
        result.add(Integer.toString(getVid()));
        result.add(Integer.toString(getCid()));
        result.add(getDate().toString());
        result.add(getTitle());
        result.add(getDescription());
        result.add(Integer.toString(getNum_dislike()));
        result.add(Integer.toString(getNum_like()));
        result.add(getLink());
        return result;
    }


}
