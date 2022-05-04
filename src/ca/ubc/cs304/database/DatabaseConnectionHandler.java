package ca.ubc.cs304.database;

import ca.ubc.cs304.model.*;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static ca.ubc.cs304.ui.TransactionWindow.notSupportWindow;

/**
 * This class handles all database related transactions
 */
public class DatabaseConnectionHandler {
	// Use this version of the ORACLE_URL if you are running the code off of the server
//	private static final String ORACLE_URL = "jdbc:oracle:thin:@dbhost.students.cs.ubc.ca:1522:stu";
	// Use this version of the ORACLE_URL if you are tunneling into the undergrad servers
	private static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1522:stu";
	private static final String EXCEPTION_TAG = "[EXCEPTION]";
	private static final String WARNING_TAG = "[WARNING]";

	private Connection connection = null;

	public DatabaseConnectionHandler() {
		try {
			// Load the Oracle JDBC driver
			// Note that the path could change for new drivers
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
		} catch (SQLException e) {
			notSupportWindow(EXCEPTION_TAG + " " + e.getMessage());
		}
	}

	public void databaseSetup() {
		channelSetup();
		wetuberSetup();
		uploadVideoSetup();
		supporterSetup();
		subscribeSetup();
	}

	public void channelSetup() {
		dropXTableIfExists("channel");

		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate("CREATE TABLE channel (cid integer PRIMARY KEY, name varchar2(20) UNIQUE, num_subs integer, num_post integer, total_video_views integer, password varchar2(20))");
			stmt.close();
		} catch (SQLException e) {
			notSupportWindow(EXCEPTION_TAG + " " + e.getMessage());
		}

		ChannelModel channel1 = new ChannelModel(001, "Amy_Cooking", 234, 5, 5065, "IloveCats");
		insertChannel(channel1);

		ChannelModel channel2 = new ChannelModel(002, "Bob_Paints", 1834, 1,  50345, "Tree34");
		insertChannel(channel2);

		ChannelModel channel3 = new ChannelModel(003, "Kid_Sings", 10, 12, 140, "Bee22");
		insertChannel(channel3);

		ChannelModel channel4 = new ChannelModel(004, "Sunny_Vlog", 52, 25, 6750, "dogfox6");
		insertChannel(channel4);

		ChannelModel channel5 = new ChannelModel(005, "we_news",  10650, 1230, 9320670, "w3news");
		insertChannel(channel5);
	}

	public void wetuberSetup() {
		dropXTableIfExists("wetuber");

		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate("CREATE TABLE wetuber (wid integer PRIMARY KEY, name varchar2(20), email varchar2(50) UNIQUE, date_birth Date)");
			stmt.close();
		} catch (SQLException e) {
			notSupportWindow(EXCEPTION_TAG + " " + e.getMessage());
		}

		WetuberModel wetuber1 = new WetuberModel(001, "Apple_pie", "lovepie@163.com", LocalDate.of(2002, 3, 2));
		insertWetuber(wetuber1);

		WetuberModel wetuber2 = new WetuberModel(002, "EsportinToday", "eplayer88@gmail.com", LocalDate.of(1988, 5, 1));
		insertWetuber(wetuber2);

		WetuberModel wetuber3 = new WetuberModel(003, "Coco the Cat", "cattycatty@gmail.com", LocalDate.of(2000, 12, 20));
		insertWetuber(wetuber3);

		WetuberModel wetuber4 = new WetuberModel(004, "Sunny", "sleep1527@gmail.com", LocalDate.of(1995, 11, 25));
		insertWetuber(wetuber4);

		WetuberModel wetuber5 = new WetuberModel(005, "Color_sky", "bob0820@gmail.com", LocalDate.of(2003, 8, 20));
		insertWetuber(wetuber5);
	}

	public void uploadVideoSetup() {
		dropXTableIfExists("video");

		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate("CREATE TABLE video (vid integer PRIMARY KEY, cid integer not null, start_date Date, title varchar2(100), description varchar2(200), num_dislike integer, num_like integer, link varchar2(2083) UNIQUE, FOREIGN KEY (cid) REFERENCES channel ON DELETE CASCADE)");
			stmt.close();
		} catch (SQLException e) {
			notSupportWindow(EXCEPTION_TAG + " " + e.getMessage());
		}

		UploadVideoModel uploadVideo1 = new UploadVideoModel(001, 001, LocalDate.of(2020, 8, 20), "Spaghetti",
				"Dinner Time!", 14, 47, "www.wetube.com/fhsd87h4");
		uploadVideo(uploadVideo1);

		UploadVideoModel uploadVideo2 = new UploadVideoModel(002, 002, LocalDate.of(2020, 9, 4), "Trees",
				"Forest Painting", 3, 167, "www.wetube.com/dsfjf445");
		uploadVideo(uploadVideo2);

		UploadVideoModel uploadVideo3 = new UploadVideoModel(003, 001, LocalDate.of(2020, 12, 25), "Sandwich",
				"Lunch Time!", 57, 2, "www.wetube.com/fj82j344");
		uploadVideo(uploadVideo3);

		UploadVideoModel uploadVideo4 = new UploadVideoModel(004, 003, LocalDate.of(2021, 5, 30), "ABC Song",
				"I love the alphabets hehe", 9, 96, "www.wetube.com/oghkio84");
		uploadVideo(uploadVideo4);

		UploadVideoModel uploadVideo5 = new UploadVideoModel(005, 004, LocalDate.of(2022, 1, 1), "Terrible Day",
				"I stepped on poop so sad", 1, 23, "www.wetube.com/retjo4i5635");
		uploadVideo(uploadVideo5);
	}



	public void supporterSetup() {
		dropXTableIfExists("supporter");
		dropXTableIfExists("subscriber");

		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate("CREATE TABLE supporter (sid integer PRIMARY KEY, name varchar2(20), start_time Date)");
			stmt.close();
		} catch (SQLException e) {
			notSupportWindow(EXCEPTION_TAG + " " + e.getMessage());
		}

		SupporterModel supporter1 = new SupporterModel(001, "Anon_hacker", LocalDate.of(2020, 2, 20));
		insertSupporter(supporter1);

		SupporterModel supporter2 = new SupporterModel(002, "Welkin", LocalDate.of(2022, 3, 2));
		insertSupporter(supporter2);

		SupporterModel supporter3 = new SupporterModel(003, "A good husband", LocalDate.of(2021, 12, 25));
		insertSupporter(supporter3);

		SupporterModel supporter4 = new SupporterModel(004, "Sotired", LocalDate.of(2022, 1, 15));
		insertSupporter(supporter4);

		SupporterModel supporter5 = new SupporterModel(005, "4Chicken", LocalDate.of(2022, 1, 15));
		insertSupporter(supporter5);
	}

	public void subscribeSetup() {
		dropXTableIfExists("subscribe");
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate("CREATE TABLE subscribe (cid integer, sid integer, start_date Date, duration integer, PRIMARY KEY (cid, sid), FOREIGN KEY (cid) REFERENCES channel ON DELETE CASCADE, FOREIGN KEY (sid) REFERENCES supporter ON DELETE CASCADE)");
			stmt.close();
		} catch (SQLException e) {
			notSupportWindow(EXCEPTION_TAG + " " + e.getMessage());
		}
		SubscribeModel subscribe1 = new SubscribeModel(001, 001, LocalDate.of(2022, 1, 1), 2);
		insertSubscribe(subscribe1);

		SubscribeModel subscribe2 = new SubscribeModel(003, 004, LocalDate.of(2022, 1, 3), 15);
		insertSubscribe(subscribe2);

		SubscribeModel subscribe3 = new SubscribeModel(003, 001, LocalDate.of(2022, 4, 5), 5);
		insertSubscribe(subscribe3);

		SubscribeModel subscribe4 = new SubscribeModel(004, 005, LocalDate.of(2022, 11, 12), 6);
		insertSubscribe(subscribe4);

		SubscribeModel subscribe5 = new SubscribeModel(002, 001, LocalDate.of(2022, 12, 31), 50);
		insertSubscribe(subscribe5);

		SubscribeModel subscribe6 = new SubscribeModel(004, 001, LocalDate.of(2022, 12, 31), 50);
		insertSubscribe(subscribe6);

		SubscribeModel subscribe7 = new SubscribeModel(005,001, LocalDate.of(2022, 12, 31), 50);
		insertSubscribe(subscribe7);

	}


	public void dropXTableIfExists(String x) {
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("select table_name from user_tables");

			while(rs.next()) {
				if(rs.getString(1).toLowerCase().equals(x)) {
					stmt.execute("DROP TABLE " + x + " CASCADE CONSTRAINTS");
					break;
				}
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			notSupportWindow(EXCEPTION_TAG + " " + e.getMessage());
		}
	}


	public String[] getColumnNames(String tableName) {
		return switch (tableName) {
			case "channel" -> ChannelModel.getFields();
			case "video" -> UploadVideoModel.getFields();
			case "wetuber" -> WetuberModel.getFields();
			case "supporter" -> SupporterModel.getFields();
			case "subscribe" -> SubscribeModel.getFields();
			default -> new String[0];
		};
	}

	public ArrayList<Model> getInfo(String name) {
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM " + name);
			ResultSetMetaData rsmd = rs.getMetaData();
			if (name == "channel") {
				ArrayList<Model> result = new ArrayList<>();
				while (rs.next()) {
					result.add(new ChannelModel(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4),
							rs.getInt(5), rs.getString(6)));
				}
				return result;
			} else if (name == "wetuber") {
				ArrayList<Model> result = new ArrayList<>();
				while (rs.next()) {
					result.add(new WetuberModel(rs.getInt(1), rs.getString(2), rs.getString(3), getLocalDate(rs.getDate(4))));
				}
				return result;

			} else if (name == "video") {
				ArrayList<Model> result = new ArrayList<>();
				while (rs.next()) {
					result.add(new UploadVideoModel(rs.getInt(1), rs.getInt(2), getLocalDate(rs.getDate(3)), rs.getString(4),
							rs.getString(5), rs.getInt(6), rs.getInt(7), rs.getString(8)));
				}
				return result;

			} else if (name == "supporter") {
				ArrayList<Model> result = new ArrayList<>();
				while (rs.next()) {
					result.add(new SupporterModel(rs.getInt(1), rs.getString(2), getLocalDate(rs.getDate(3))));
				}
				return result;

			} else if (name == "subscribe") {
				ArrayList<Model> result = new ArrayList<>();
				while (rs.next()) {
					result.add(new SubscribeModel(rs.getInt(1), rs.getInt(2), getLocalDate(rs.getDate(3)), rs.getInt(4)));
				}
				return result;

			}

		} catch (SQLException e) {
			notSupportWindow(EXCEPTION_TAG + " " + e.getMessage());
		}

		return new ArrayList<Model>();
	}

	public LocalDate getLocalDate(Date dateToConvert) {
		return dateToConvert.toLocalDate();
	}


	//******DELETE OPERATIONS******
	public void deleteChannel(int cid) {
		try {
			PreparedStatement ps = connection.prepareStatement("DELETE FROM channel WHERE cid = ?");
			ps.setInt(1, cid);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				notSupportWindow(WARNING_TAG + " Channel " + cid + " does not exist!");
			}

			connection.commit();
			System.out.println("Operation completed");

			ps.close();

		} catch (SQLException e) {
			notSupportWindow(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void deleteWetuber(int wid) {
		try {
			PreparedStatement ps = connection.prepareStatement("DELETE FROM wetuber WHERE wid = ?");
			ps.setInt(1, wid);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				notSupportWindow(WARNING_TAG + " Wetuber " + wid + " does not exist!");
			}

			connection.commit();
			System.out.println("Operation completed");

			ps.close();

		} catch (SQLException e) {
			notSupportWindow(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	//******INSERT OPERATIONS******
	public void insertChannel(ChannelModel model) {
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO channel VALUES (?,?,?,?,?,?)");
			ps.setInt(1, model.getCid());
			ps.setString(2, model.getName());
			ps.setInt(3, model.getNum_subs());
			ps.setInt(4, model.getNum_post());
			ps.setInt(5, model.getTotal_video_views());
			ps.setString(6, model.getPassword());

			ps.executeUpdate();
			connection.commit();
			System.out.println("Operation completed");

			ps.close();
		} catch (SQLException e) {
			notSupportWindow(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void insertWetuber(WetuberModel model) {
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO wetuber VALUES (?,?,?,?)");
			ps.setInt(1, model.getWid());
			ps.setString(2, model.getName());
			ps.setString(3, model.getEmail());
			ps.setDate(4, Date.valueOf(model.getDate_birth()));

			ps.executeUpdate();
			connection.commit();
			System.out.println("Operation completed");

			ps.close();
		} catch (SQLException e) {
			notSupportWindow(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void insertSubscribe(SubscribeModel model) {
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO subscribe VALUES (?,?,?,?)");
			ps.setInt(1, model.getCid());
			ps.setInt(2, model.getSid());
			ps.setDate(3, Date.valueOf(model.getDate()));
			ps.setInt(4, model.getDuration());

			ps.executeUpdate();
			connection.commit();
			System.out.println("Operation completed");

			ps.close();
		} catch (SQLException e) {
			notSupportWindow(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}


	//insert supporter
	public void insertSupporter(SupporterModel model) {
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO supporter VALUES (?,?,?)");
			ps.setInt(1, model.getSid());
			ps.setString(2, model.getName());
			ps.setDate(3, Date.valueOf(model.getStart_time()));

			ps.executeUpdate();

			connection.commit();
			System.out.println("Operation completed");

			ps.close();

		} catch (SQLException e) {
			notSupportWindow(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void uploadVideo(UploadVideoModel model) {
		try {
			PreparedStatement ps = connection.prepareStatement("INSERT INTO video VALUES (?,?,?,?,?,?,?,?)");
			ps.setInt(1, model.getVid());
			ps.setInt(2, model.getCid());
			ps.setDate(3, Date.valueOf(model.getDate()));
			ps.setString(4, model.getTitle());
			ps.setString(5, model.getDescription());
			ps.setInt(6, model.getNum_dislike());
			ps.setInt(7, model.getNum_like());
			ps.setString(8, model.getLink());

			ps.executeUpdate();
			connection.commit();
			System.out.println("Operation completed");

			ps.close();
		} catch (SQLException e) {
			notSupportWindow(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	//******UPDATE OPERATIONS******
	public void updatePassword(int cid, String password) {
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE channel SET password = ? WHERE cid = ?");
			ps.setString(1, password);
			ps.setInt(2, cid);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				notSupportWindow(WARNING_TAG + " Channel " + cid + " does not exist!");
			}
			connection.commit();
			System.out.println("Operation completed");

			ps.close();
		} catch (SQLException e) {
			notSupportWindow(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}

	public void updateChannelName(int cid, String name) {
		try {
			PreparedStatement ps = connection.prepareStatement("UPDATE channel SET name = ? WHERE cid = ?");
			ps.setString(1, name);
			ps.setInt(2, cid);

			int rowCount = ps.executeUpdate();
			if (rowCount == 0) {
				notSupportWindow(WARNING_TAG + " Channel " + cid + " does not exist!");
			}

			connection.commit();

			ps.close();
		} catch (SQLException e) {
			notSupportWindow(EXCEPTION_TAG + " " + e.getMessage());
			rollbackConnection();
		}
	}


	//******SELECTION QUERY******
	//return all videos with the keyword in title
	public List<UploadVideoModel> getVideoByTitleKeyword(String keyword) {
		ArrayList<UploadVideoModel> result = new ArrayList<UploadVideoModel>();

		try {
			PreparedStatement ps = connection.prepareStatement("SELECT * FROM video WHERE title LIKE ('%' || ? || '%')");
			ps.setString(1, keyword);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				UploadVideoModel model = new UploadVideoModel(rs.getInt("vid"),
						rs.getInt("cid"),
						getLocalDate(rs.getDate("start_date")),
						rs.getString("title"),
						rs.getString("description"),
						rs.getInt("num_dislike"),
						rs.getInt("num_like"),
						rs.getString("link"));
				result.add(model);
			}

			rs.close();
			ps.close();
		} catch (SQLException e) {
			notSupportWindow(EXCEPTION_TAG + " " + e.getMessage());
		}

		return result;
	}

	//******PROJECTION QUERY******
	public List<String[]> projection(String[] attr) {
		List<String[]> result = new ArrayList<>();

		try {
			StringBuilder query = new StringBuilder(attr[0]);
			for (int i = 1; i < attr.length; i++) {
				query.append(", ").append(attr[i]);
			}
			PreparedStatement ps = connection.prepareStatement("SELECT " + query + " FROM wetuber");
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				String[] r = new String[attr.length];
				for (int i = 0; i < attr.length; i++) {
					r[i] = rs.getString(attr[i]);
				}
				result.add(r);
			}
		} catch (SQLException e) {
			notSupportWindow(EXCEPTION_TAG + " " + e.getMessage());
		}

		return result;
	}


	//******JOIN QUERY******
	//Given a vid, find the channel info
	// and the name of the wetuber who owns the channel
	public List<List<String>> getChannel(int vid) {
		List<List<String>> result = new ArrayList<List<String>>();

		try{
			PreparedStatement ps = connection.prepareStatement("SELECT DISTINCT v.vid, v.title, c.name, c.num_subs " +
					"FROM video v, channel c " +
					"WHERE v.vid = ? " +
					"AND v.cid = c.cid");
			ps.setInt(1, vid);
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				List<String> list= new ArrayList<String>();
				list.add(Integer.toString(rs.getInt("vid")));
				list.add(rs.getString("title"));
				list.add(rs.getString("name"));
				list.add(Integer.toString(rs.getInt("num_subs")));
				result.add(list);
			}

			rs.close();
			ps.close();

		} catch (SQLException e) {
			notSupportWindow(EXCEPTION_TAG + " " + e.getMessage());
		}
		return result;
	}

	//******AGGREGATION QUERY******
	//return the highest number of subscribers out of all the channels
	public String[] highestSub() {
		String[] result = new String[3];
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT cid, name, num_subs FROM channel " +
					"WHERE num_subs = (SELECT MAX(num_subs) FROM channel)");
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				result[0] = rs.getString("cid");
				result[1] = rs.getString("name");
				result[2] = rs.getString("num_subs");
			}

			rs.close();
			ps.close();

		} catch (SQLException e) {
			notSupportWindow(EXCEPTION_TAG + " " + e.getMessage());
		}
		return result;
	}

	//******NESTED AGGREGATION QUERY WITH GROUP-BY******
	//return cid and name of the channels where:
	// (1) the average likes of the videos uploaded on that channel is at least 20
	// (2) the channel has at least 2 videos uploaded
	// (3) the channel has at least 10 subscribers
	public List<ChannelModel> activeChannels() {
		List<ChannelModel> result = new ArrayList<>();
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT c.cid, c.name " +
					"FROM channel c, video v " +
					"WHERE c.cid = v.cid " +
					"GROUP BY c.cid, c.name " +
					"HAVING AVG(v.num_like) >= 20 AND COUNT(v.vid) >= 2 AND MAX(c.num_subs) >= 10");
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				ChannelModel model = new ChannelModel(rs.getInt("cid"), rs.getString("name"),
						-1, -1, -1, null);
				result.add(model);
			}

			rs.close();
			ps.close();

		} catch (SQLException e) {
			notSupportWindow(EXCEPTION_TAG + " " + e.getMessage());
		}
		return result;
	}

	public String[][] userPerDay() {
		List<String[]> result = new ArrayList<>();
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT start_time, COUNT(sid) FROM supporter GROUP BY start_time");
			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				String[] r = new String[] {rs.getString(1), rs.getString(2)};
				result.add(r);
			}

			rs.close();
			ps.close();

		} catch (SQLException e) {
			notSupportWindow(EXCEPTION_TAG + " " + e.getMessage());
		}

		return result.toArray(new String[result.size()][]);
	}

	// public String[][] aggregation(String table, String select, String group)

	//******DIVISION QUERY******
	public List<SupporterModel> subscribedAll() {
		List<SupporterModel> result = new ArrayList<>();
		try {
			PreparedStatement ps = connection.prepareStatement("SELECT DISTINCT sup.sid, sup.name " +
					"FROM supporter sup " +
					"WHERE NOT EXISTS " +
					"((SELECT cid FROM channel c) MINUS " +
					"(SELECT DISTINCT sub.cid FROM subscribe sub WHERE sub.sid = sup.sid))");


			ResultSet rs = ps.executeQuery();

			while(rs.next()) {
				SupporterModel model = new SupporterModel(rs.getInt("sid"), rs.getString("name"), null);
				result.add(model);
			}

			rs.close();
			ps.close();

		} catch (SQLException e) {
			notSupportWindow(EXCEPTION_TAG + " " + e.getMessage());
		}
		return result;
	}

	public boolean login(String username, String password) {
		try {
			if (connection != null) {
				connection.close();
			}

			connection = DriverManager.getConnection(ORACLE_URL, username, password);
			connection.setAutoCommit(false);

			System.out.println("\nConnected to Oracle!");
			return true;
		} catch (SQLException e) {
			notSupportWindow(EXCEPTION_TAG + " " + e.getMessage());
			return false;
		}
	}

	private void rollbackConnection() {
		try  {
			connection.rollback();
		} catch (SQLException e) {
			notSupportWindow(EXCEPTION_TAG + " " + e.getMessage());
		}
	}


}
