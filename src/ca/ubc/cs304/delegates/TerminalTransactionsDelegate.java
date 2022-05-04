package ca.ubc.cs304.delegates;

/**
 * This interface uses the delegation design pattern where instead of having
 * the TerminalTransactions class try to do everything, it will only
 * focus on handling the UI. The actual logic/operation will be delegated to the 
 * controller class (in this case Bank).
 * 
 * TerminalTransactions calls the methods that we have listed below but 
 * Bank is the actual class that will implement the methods.
 */
public interface TerminalTransactionsDelegate {
	public void databaseSetup();

	public String[] getTableNames();
	String[][] activeChannels();
	String[][] projectionWetuber(String[] inputs);
	String[][] getVideo(String input);
	String[] highestSub();
	String[][] subPerDay();

	// change password
	void changeChannel(String id, String input);

	void renameChannel(String id, String input);

	String[] getColumnNames(String tname);

	String[][] join(String id);
	String[][] show(String tname);
	String[][] subscribedAll();

	void insert(String loadTable, String[] inputs);

	void delete(String table, int id);
}
