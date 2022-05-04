package ca.ubc.cs304.controller;

import ca.ubc.cs304.database.DatabaseConnectionHandler;
import ca.ubc.cs304.delegates.LoginWindowDelegate;
import ca.ubc.cs304.delegates.TerminalTransactionsDelegate;
import ca.ubc.cs304.model.*;
import ca.ubc.cs304.ui.LoginWindow;
import ca.ubc.cs304.ui.TransactionWindow;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * This is the main controller class that will orchestrate everything.
 */
public class Wetuber implements LoginWindowDelegate, TerminalTransactionsDelegate {
	private DatabaseConnectionHandler dbHandler = null;
	private LoginWindow loginWindow = null;

	public Wetuber() {
		dbHandler = new DatabaseConnectionHandler();
	}
	
	private void start() {
		loginWindow = new LoginWindow();
		loginWindow.showFrame(this);
	}

	public String[] getTableNames() {
		return new String[] {"channel", "supporter", "subscribe", "video", "wetuber"};
	}


	@Override
	public String[][] activeChannels() {
		List<ChannelModel> get = dbHandler.activeChannels();
		String[][] result = new String[get.size()][];
		for (int i = 0; i < get.size(); i++) {
			result[i] = get.get(i).getValues().toArray(new String[0]);
		}
		return result;
	}

	@Override
	public String[][] projectionWetuber(String[] inputs) {
		List<String[]> get = dbHandler.projection(inputs);
		String[][] result = new String[get.size()][];
		for (int i = 0; i < get.size(); i++) {
			result[i] = get.get(i);
		}
		return result;
	}

	@Override
	public String[][] getVideo(String input) {
		List<UploadVideoModel> get = dbHandler.getVideoByTitleKeyword(input);
		String[][] result = new String[get.size()][];
		for (int i = 0; i < get.size(); i++) {
			result[i] = get.get(i).getValues().toArray(new String[0]);
		}
		return result;
	}

	@Override
	public String[] highestSub() {
		return dbHandler.highestSub();
	}

	@Override
	public String[][] subPerDay() {
		return dbHandler.userPerDay();
	}

	@Override
	public void changeChannel(String id, String input) {
		dbHandler.updatePassword(Integer.parseInt(id), input);
	}

	@Override
	public void renameChannel(String id, String input) {
		dbHandler.updateChannelName(Integer.parseInt(id), input);
	}

	@Override
	public String[] getColumnNames(String tname) {
		return dbHandler.getColumnNames(tname);
	}

	@Override
	public String[][] join(String vid) {
		List<String[]> result = new ArrayList<>();
		for (List<String> l: dbHandler.getChannel(Integer.parseInt(vid))) {
			result.add(l.toArray(new String[l.size()]));
		}
		return result.toArray(new String[result.size()][]);
	}

	public String[][] show(String tname) {
		ArrayList<Model> showInfo = dbHandler.getInfo(tname);
		ArrayList<String[]> rs = new ArrayList<>();
		for(int i = 0; i < showInfo.size(); i++) {
			ArrayList<String> channelValues = showInfo.get(i).getValues();
			rs.add(channelValues.toArray(new String[channelValues.size()]));
		}
		return rs.toArray(new String[rs.size()][]);
	}

	public String[][] subscribedAll() {
		List<SupporterModel> result = dbHandler.subscribedAll();
		ArrayList<String[]> rs = new ArrayList<>();
		for (int i = 0; i < result.size(); i++) {
			rs.add(new String[] {"" + result.get(i).getSid(), result.get(i).getName()});
		}
		return rs.toArray(new String[rs.size()][]);
	}

	@Override
	public void insert(String table, String[] inputs) {
		switch (table) {
			case ("wetuber"):
				dbHandler.insertWetuber(new WetuberModel(
						Integer.parseInt(inputs[0]),
						inputs[1],
						inputs[2],
						LocalDate.parse(inputs[3])
				));
				break;
			case ("channel"):
				dbHandler.insertChannel(new ChannelModel(
						Integer.parseInt(inputs[0]),
						inputs[1],
						Integer.parseInt(inputs[2]),
						Integer.parseInt(inputs[3]),
						Integer.parseInt(inputs[4]),
						inputs[5]
				));
				break;
			case ("supporter"):
				dbHandler.insertSupporter(new SupporterModel(
						Integer.parseInt(inputs[0]),
						inputs[1],
						LocalDate.parse(inputs[2])
				));
				break;
			case ("subscribe"):
				dbHandler.insertSubscribe(new SubscribeModel(
						Integer.parseInt(inputs[0]),
						Integer.parseInt(inputs[1]),
						LocalDate.parse(inputs[2]),
						Integer.parseInt(inputs[3])
				));
				break;
			case ("video"):
				dbHandler.uploadVideo(new UploadVideoModel(
						Integer.parseInt(inputs[0]),
						Integer.parseInt(inputs[1]),
						LocalDate.parse(inputs[2]),
						inputs[3],
						inputs[4],
						Integer.parseInt(inputs[5]),
						Integer.parseInt(inputs[6]),
						inputs[7]
				));
				break;
		}
	}

	@Override
	public void delete(String table, int id) {
		switch (table) {
			case ("wetuber"):
				dbHandler.deleteWetuber(id);
				break;
			case ("channel"):
				dbHandler.deleteChannel(id);
				break;
		}
	}

	// retrieved from Branch example in tutorial
	/**
	 * LoginWindowDelegate Implementation
	 * 
     * connects to Oracle database with supplied username and password
     */ 
	public void login(String username, String password) {
		boolean didConnect = dbHandler.login(username, password);

		if (didConnect) {
			// Once connected, remove login window and start text transaction flow
			loginWindow.dispose();

			TransactionWindow transactionWindow = new TransactionWindow();
			transactionWindow.setupDatabase(this);
			transactionWindow.showFrame(this);
		} else {
			loginWindow.handleLoginFailed();

			if (loginWindow.hasReachedMaxLoginAttempts()) {
				loginWindow.dispose();
				System.out.println("You have exceeded your number of allowed attempts");
				System.exit(-1);
			}
		}
	}

    /**
	 * TerminalTransactionsDelegate Implementation
	 *
     * The TerminalTransaction instance tells us that the user is fine with dropping any existing table
     * called branch and creating a new one for this project to use
     */
	public void databaseSetup() {
		dbHandler.databaseSetup();
	}
    
	/**
	 * Main method called at launch time
	 */
	public static void main(String args[]) {
		Wetuber wetuber = new Wetuber();
		wetuber.start();
	}
}
