package ca.ubc.cs304.ui;

import ca.ubc.cs304.delegates.TerminalTransactionsDelegate;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TransactionWindow extends JFrame implements ActionListener {
    private String[] tables;
    private String tableName = "";
    private String[][] data;
    private String[] header;
    private String loadTable = "";

    private final JTable tbl = new JTable();
    private final CardLayout cl = new CardLayout();
    private final JPanel cards = new JPanel(cl);
    private final JLabel showInfo = new JLabel("This is show info");

    private TerminalTransactionsDelegate delegate;

    public TransactionWindow() {
        super("Wetuber Database");
    }

    public void setupDatabase(TerminalTransactionsDelegate delegate) {
        this.delegate = delegate;
        delegate.databaseSetup();
    }

    public void refresh(String tname) {
        header = delegate.getColumnNames(tname);
        updateTable(delegate.show(tname), this.header);
    }

    public void updateTable(String[][] data, String[] header) {
        this.data = data;
        tbl.setModel(new DefaultTableModel(data, header) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }

    public void showFrame(TerminalTransactionsDelegate delegate) {
        this.delegate = delegate;

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 550, 300);
        JPanel content = new JPanel(new BorderLayout(0, 0));
        JPanel def = new JPanel();
        JPanel channel = new JPanel();
        JPanel supporter = new JPanel();
        JPanel video = new JPanel();
        JPanel wetuber = new JPanel();
        JPanel subscribe = new JPanel();
        cards.add(def, "basic");
        cards.add(channel, "channel");
        cards.add(supporter, "supporter");
        cards.add(video, "video");
        cards.add(wetuber, "wetuber");
        cards.add(subscribe, "subscribe");
        cl.show(cards, "basic");
        setContentPane(content);
        setLocationRelativeTo(null);

        tables = delegate.getTableNames();

        JButton bRename = new JButton("Rename");
        bRename.addActionListener(e -> {
            if (!loadTable.equals("channel")) {
                showInfo.setText("Please load table channel first");
            } else if (tbl.getSelectedRow() == -1) {
                showInfo.setText("Please select a channel to rename");
            } else {
                String input = JOptionPane.showInputDialog(null, "Please enter the new name",
                        "Rename", JOptionPane.PLAIN_MESSAGE);

                String id = (String) tbl.getModel().getValueAt(tbl.getSelectedRow(), 0);
                delegate.renameChannel(id, input);
                refresh(loadTable);
            }
        });

        JButton bPassword = new JButton("Change Password");
        bPassword.addActionListener(e -> {
            if (!loadTable.equals("channel")) {
                showInfo.setText("Please load table channel first");
            } else if (tbl.getSelectedRow() == -1) {
                showInfo.setText("Please select a channel to change its password");
            } else {
                String input = JOptionPane.showInputDialog(null, "Please enter the new password",
                        "Change password", JOptionPane.PLAIN_MESSAGE);

                String id = (String) tbl.getModel().getValueAt(tbl.getSelectedRow(), 0);
                delegate.changeChannel(id, input);
                refresh(loadTable);
            }
        });

        // add WHERE clause
        JButton bSelection = new JButton("Search video");
        bSelection.addActionListener(e -> {
            if (!loadTable.equals("video")) {
                showInfo.setText("Please load table video first");
            } else {
                String input = JOptionPane.showInputDialog(null, "Please enter a key word",
                        "Searching", JOptionPane.PLAIN_MESSAGE);

                String[][] rs = delegate.getVideo(input);
                updateTable(rs, this.header);
            }
        });

        // add SELECT clause
        JButton bProjection = new JButton("Search column");
        bProjection.addActionListener(e -> {
            if (loadTable.length() != 0) {
                if (!loadTable.equals("wetuber")) {
                    showInfo.setText("Please load table wetuber first");
                } else {
                    String[] inputs = prepareProjection();
                    if (inputs == null) return;
                    String[][] rs = delegate.projectionWetuber(inputs);
                    updateTable(rs, inputs);
                }
            }
        });

        // select multiple tables
        JButton bJoin = new JButton("Get channel");
        bJoin.addActionListener(e -> {
            if (!loadTable.equals("video")) {
                showInfo.setText("Please load table video first");
            } else if (tbl.getSelectedRow() == -1) {
                showInfo.setText("Please select a video");
            } else {
                String id = (String) tbl.getModel().getValueAt(tbl.getSelectedRow(), 0);
                header = new String[] {"vid", "title", "name", "num_subs"};
                updateTable(delegate.join(id), header);
            }
        });

        JButton bsubCount = new JButton("Find channel with highest sub");
        bsubCount.addActionListener(e -> {
            String[] rs = delegate.highestSub();
            updateTable(new String[][]{rs}, new String[]{"cid", "name", "max_sub"});
        });

        // Find active channel
        JButton bAdvance = new JButton("Find active channel");
        bAdvance.addActionListener(e -> {
            String[][] rs = delegate.activeChannels();
            updateTable(rs, new String[]{"cid", "name"});
        });

        // Find subscribers who have subsribed to all channels
        JButton bsubAll = new JButton("Find subscriber");
        bsubAll.addActionListener(e -> {
            String[][] rs = delegate.subscribedAll();
            header = new String[] {"sid", "name"};
            updateTable(rs, this.header);
        });

        JButton buserPerDay = new JButton("Supporters per day");
        buserPerDay.addActionListener(e -> {
            String[][] rs = delegate.subPerDay();
            header = new String[] {"date", "num_sup"};
            updateTable(rs, this.header);
        });


        content.add(showInfo, BorderLayout.NORTH);
        content.add(new JScrollPane(tbl), BorderLayout.CENTER);
        content.add(cards, BorderLayout.SOUTH);

        channel.add(searchTab());
        channel.add(insertButton());
        channel.add(deleteButton());
        channel.add(bRename);
        channel.add(bPassword);
        channel.add(bAdvance);
        channel.add(bsubCount);

        supporter.add(searchTab());
        supporter.add(insertButton());
        supporter.add(bsubAll);
        supporter.add(buserPerDay);

        subscribe.add(searchTab());
        subscribe.add(insertButton());

        video.add(searchTab());
        video.add(insertButton());
        video.add(bSelection);
        video.add(bJoin);

        wetuber.add(searchTab());
        wetuber.add(insertButton());
        wetuber.add(deleteButton());
        wetuber.add(bProjection);

        def.add(searchTab());

        this.pack();
        this.setVisible(true);
    }

    private JButton insertButton() {
        // handle insert option
        JButton bInsert = new JButton("Insert");
        bInsert.addActionListener(e -> {
            if (loadTable.length() != 0) {
                String[] inputs = prepareInsert();
                if (inputs == null) return;
                try {
                    delegate.insert(loadTable, inputs);
                    refresh(loadTable);
                } catch (Exception err) {
                    notSupportWindow("Invalid inputs");
                }
            } else {
                showInfo.setText("Please load a table first");
            }
        });
        return bInsert;
    }

    private JButton deleteButton() {
        // handle delete option
        JButton bDelete = new JButton("Delete");
        bDelete.addActionListener(e -> {
            if (loadTable.length() == 0) {
                showInfo.setText("Please load a table first");
            } else if (tbl.getSelectedRow() != -1) {
                String id = (String) tbl.getModel().getValueAt(tbl.getSelectedRow(), 0);
                delegate.delete(loadTable, Integer.parseInt(id));
                refresh(loadTable);
            } else {
                showInfo.setText("Delete failed, choose a row to delete");
            }

        });

        return bDelete;
    }

    private JComponent searchTab() {
        JPanel panel = new JPanel();

        JLabel ltable = new JLabel("Table: ");
        JComboBox<String> ctable = createComboBox(tables);
        ctable.addItemListener(e -> tableName = e.getItem().toString());
        // Handle search option
        JButton bSearch = new JButton("Load");
        bSearch.addActionListener(e -> {
            if (ctable.getSelectedIndex() != 0) {
                showInfo.setText(tableName + " loaded");
                loadTable = tableName;
                cl.show(cards, loadTable);
                this.refresh(loadTable);
                ctable.setSelectedIndex(0);
            } else {
                showInfo.setText("Please choose a table to load");
            }
        });

        panel.add(ltable);
        panel.add(ctable);
        panel.add(bSearch);

        return panel;
    }

    private JComboBox<String> createComboBox(String[] list) {
        JComboBox<String> table = new JComboBox<>();
        table.addItem("--Please choose--");

        for (String s: list) {
            table.addItem(s);
        }
        return table;
    }

    private String[] prepareInsert() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        ArrayList<JTextField> inputs = new ArrayList<>();
        String[] result = new String[header.length];

        for (String s: header) {
            JPanel line = new JPanel();
            line.setLayout(new GridLayout(1, 2));
            line.add(new JLabel(s.toUpperCase() + ": "));
            JTextField text = new JTextField(10);
            inputs.add(text);
            line.add(text);
            panel.add(line);
        }

        int option = JOptionPane.showConfirmDialog(null, panel, "Please enter the values",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            for (int i = 0; i < header.length; i++) {
                result[i] = inputs.get(i).getText();
            }
        } else {
            return null;
        }

        return result;
    }

    private String[] prepareSelection() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        ArrayList<JTextField> inputs = new ArrayList<>();
        ArrayList<String> result = new ArrayList<>();

        List<String> ops = Arrays.asList(" = ", " < ", " > ");
        ArrayList<JComboBox<String>> inputOp = new ArrayList<>();

        for (String s: header) {
            JPanel line = new JPanel();
            JTextField text = new JTextField(10);
            JComboBox<String> operator = new JComboBox<>();
            for (String s1 : ops) {
                operator.addItem(s1);
            }

            line.setLayout(new GridLayout(1, 3));
            line.add(new JLabel(s.toUpperCase() + ": "));
            inputOp.add(operator);
            line.add(operator);
            inputs.add(text);
            line.add(text);
            panel.add(line);
        }

        int option = JOptionPane.showConfirmDialog(null, panel, "Please enter the values",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            for (int i = 0; i < header.length; i++) {
                String value = inputs.get(i).getText();
                if (value.length() > 0) {
                    result.add(header[i] + ops.get(inputOp.get(i).getSelectedIndex()) + value);
                }
            }

            return result.toArray(new String[result.size()]);
        }
        return null;
    }

    private String[] prepareProjection() {
        return popupWindow(Arrays.copyOfRange(header, 1, header.length), "Please select values");
    }

    private String[] prepareJoin() {
        return popupWindow(tables, "Please select tables");
    }

    private void popupTable(String[][] table, String[] header) {
        JTable t = new JTable();
        t.setModel(new DefaultTableModel(table, header));

        JOptionPane.showMessageDialog(null, t, "result", JOptionPane.PLAIN_MESSAGE);
    }

    private String[] popupWindow(String[] options, String message) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        ArrayList<JCheckBox> inputs = new ArrayList<>();
        ArrayList<String> result = new ArrayList<>();

        for (String s : options) {
            JCheckBox cb = new JCheckBox(s);
            inputs.add(cb);
            panel.add(cb);
        }

        int option = JOptionPane.showConfirmDialog(null, panel, message,
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            for (int i = 0; i < options.length; i++) {
                if (inputs.get(i).isSelected()) {
                    result.add(options[i]);
                }
            }

            return result.toArray(new String[result.size()]);
        }
        return null;
    }

    private String[] prepareAggregation() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        String[] result = new String[2];

        String[] aggr = new String[] {"MIN", "MAX", "AVG", "COUNT"};
        JComboBox<String> operation = createComboBox(aggr);
        JComboBox<String> attribute = createComboBox(header);
        JLabel group = new JLabel("GROUP BY");
        JComboBox<String> another   = createComboBox(header);
        another.removeItemAt(0);

        JPanel one = new JPanel();
        one.add(operation);
        one.add(attribute);
        JPanel two = new JPanel();
        two.add(group);
        two.add(another);
        panel.add(one);
        panel.add(two);

        int option = JOptionPane.showConfirmDialog(null, panel, "Build your query",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            if (operation.getSelectedIndex() != 0 && attribute.getSelectedIndex() != 0) {
                result[1] = aggr[operation.getSelectedIndex() - 1] + "(" + header[attribute.getSelectedIndex() - 1] + ")";
            } else {
                return null;
            }


            if (another.getSelectedIndex() != 0) {
                result[0] = header[another.getSelectedIndex()];
            } else {
                return null;
            }

            return result;
        }
        return new String[1];
    }

    public static void notSupportWindow(String msg) {
        JOptionPane.showMessageDialog(null, msg,
                "Error", JOptionPane.WARNING_MESSAGE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
