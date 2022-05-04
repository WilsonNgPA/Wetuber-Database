package ca.ubc.cs304.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class SupporterModel extends Model{
    //CREATE TABLE Supporter (
    //sid Integer,
    //name Char[20],
    //start_time Date,
    //Primary key (sid)
    //)

    private final int sid;
    private final String name;
    private final LocalDate start_time;

    public SupporterModel(int sid, String name, LocalDate start_time) {
        this.sid = sid;
        this.name = name;
        this.start_time = start_time;
    }

    public int getSid() {
        return sid;
    }

    public String getName() {
        return name;
    }

    public LocalDate getStart_time() {
        return start_time;
    }

    public static String[] getFields() {
        return new String[] {"sid", "name", "start_time"};
    }

    public ArrayList<String> getValues() {
        ArrayList<String> result = new ArrayList<>();
        result.add(Integer.toString(getSid()));
        result.add(getName());
        result.add(getStart_time().toString());
        return result;
    }

}
