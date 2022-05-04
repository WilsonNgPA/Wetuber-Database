package ca.ubc.cs304.model;

import java.util.ArrayList;

public class ChannelModel extends Model{
    //CREATE TABLE Channel (
    //  cid Integer,
    //  name Char[20],
    //  num_subs Integer,
    //  num_post Integer,
    //  total_video_views Integer,
    //  password Char[20],
    //  Primary Key (cid),
    //  Unique (name),
    //)
    private final int cid;
    private final String name;
    private final int num_subs;
    private final int num_post;
    private final int total_video_views;
    private final String password;

    public ChannelModel(int cid, String name, int num_subs, int num_post, int total_video_views, String password) {
        this.cid = cid;
        this.name = name;
        this.num_subs = num_subs;
        this.num_post = num_post;
        this.total_video_views = total_video_views;
        this.password = password;
    }

    public int getCid() {
        return cid;
    }

    public String getName() {
        return name;
    }

    public int getNum_subs() {
        return num_subs;
    }

    public int getNum_post() {
        return num_post;
    }

    public int getTotal_video_views() {
        return total_video_views;
    }

    public String getPassword() {
        return password;
    }

    public static String[] getFields() {
        return new String[] {"cid", "name", "num_subs", "num_post", "total_video_views", "password"};
    }

    public ArrayList<String> getValues() {
        ArrayList<String> result = new ArrayList<>();
        result.add(Integer.toString(getCid()));
        result.add(getName());
        result.add(Integer.toString(getNum_subs()));
        result.add(Integer.toString(getNum_post()));
        result.add(Integer.toString(getTotal_video_views()));
        result.add(getPassword());
        return result;
    }

}
