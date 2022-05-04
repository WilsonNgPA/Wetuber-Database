package ca.ubc.cs304.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class WetuberModel extends Model{
    //CREATE TABLE Wetuber (
    //  wid Integer,
    //  name Char[20],
    //  email Char[50],
    //  date_birth Date,
    //  Primary Key (wid),
    //  Unique (email),
    //)
    private final int wid;
    private final String name;
    private final String email;
    private final LocalDate date_birth;

    public WetuberModel(int wid, String name, String email, LocalDate date_birth) {
        this.wid = wid;
        this.name = name;
        this.email = email;
        this.date_birth = date_birth;
    }

    public int getWid() {
        return wid;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getDate_birth() {
        return date_birth;
    }

    public static String[] getFields() {
        return new String[] {"wid", "name", "email", "date_birth"};
    }

    public ArrayList<String> getValues() {
        ArrayList<String> result = new ArrayList<>();
        result.add(Integer.toString(getWid()));
        result.add(getName());
        result.add(getEmail());
        result.add(getDate_birth().toString());
        return result;
    }


}
