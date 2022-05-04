package ca.ubc.cs304.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class SubscribeModel extends Model{
    //CREATE TABLE Subscribe (
    //cid Integer,
    //sid Integer,
    //date Date,
    //duration Integer,
    //Primary key (cid, sid),
    //Foreign key (cid) references Channel,
    //Foreign key (sid) references Subscriber
    //)
    private final int cid;
    private final int sid;
    private final LocalDate date;
    private final int duration;

    public SubscribeModel(int cid, int sid, LocalDate date, int duration) {
        this.cid = cid;
        this.sid = sid;
        this.date = date;
        this.duration = duration;
    }

    public int getCid() {
        return cid;
    }

    public int getSid() {
        return sid;
    }

    public LocalDate getDate() {
        return date;
    }

    public int getDuration() {
        return duration;
    }

    public static String[] getFields() {
        return new String[] {"cid", "sid", "date", "duration"};
    }

    public ArrayList<String> getValues() {
        ArrayList<String> result = new ArrayList<>();
        result.add("" + getCid());
        result.add("" + getSid());
        result.add(getDate().toString());
        result.add("" + getDuration());
        return result;
    }
}
