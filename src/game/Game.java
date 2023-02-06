package game;

import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Assignment 1.2
 * Picross basic UI
 * 
 * @author Godswin Torres
 */
public class Game {

    /**
     * UI Frame
     */
    JFrame frame;

    /**
     * Number of squares per row/col
     */
    int dim = 5;

    /**
     * Board buttons
     */
    JButton[][] squareButtons = new JButton[dim][dim];

    /**
     * Function buttons
     */
    JButton[] funcButtons = new JButton[3];

    /**
     * Event log
     */
    JTextArea eventLog;

    /**
     * Mark button
     */
    JCheckBox markBox;

    /**
     * Boolean that describes markbox state
     */
    boolean markState = false;

    /**
     * Array of Languages supported
     */
    String[] languange = { "English", "Spanish" };

    /**
     * Drop down list for language
     */
    JComboBox<String> langList;

    /**
     * Inner class for Action
     */
    Controller control = new Controller();

    public Game() {
        /*
         * Board Panel Center
         */
        JPanel boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(dim, dim, 1, 1));
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                squareButtons[i][j] = new JButton(Integer.toString(dim * i + j + 1));
                squareButtons[i][j].setBackground(Color.GRAY);
                squareButtons[i][j].setFocusable(false);
                squareButtons[i][j].setActionCommand(Integer.toString(dim * i + j + 1));
                squareButtons[i][j].addActionListener(control);

                boardPanel.add(squareButtons[i][j]);

            }
        }

        /*
         * Defining frame
         */
        frame = new JFrame("Godswin Torres");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new BorderLayout(1, 1));
        frame.setResizable(false); // cant manually resize

        /*
         * Top Panel
         * Placed North of the borderlayout
         */
        JPanel topPanel = new JPanel();
        topPanel.setPreferredSize(new Dimension(300, 100));
        topPanel.setLayout(new BorderLayout(1, 1));
        /*
         * Top-Center Panel
         * Holds the numbers
         */
        JPanel rowPanel = new JPanel();
        rowPanel.setLayout(new GridLayout(1, dim, 1, 1));
        for (int i = 1; i <= dim; i++) {
            rowPanel.add(new JLabel("   Col " + String.valueOf(i)));
        }
        rowPanel.setBackground(Color.MAGENTA);

        /*
         * Top Left Panel
         */
        JPanel markPanel = new JPanel();
        markPanel.setPreferredSize(new Dimension(100, 100));
        markPanel.setBackground(Color.CYAN);
        markPanel.setLayout(new BorderLayout(1, 1));
        /*
         * Defining mark box
         */
        markBox = new JCheckBox("Mark");
        markBox.addActionListener(control);
        markBox.setOpaque(false);
        markPanel.add(Box.createRigidArea(new Dimension(20, 0)), BorderLayout.WEST);
        markPanel.add(markBox, BorderLayout.CENTER);

        /*
         * Top-right panel
         * holds the logo
         */
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(Color.GREEN);
        logoPanel.setPreferredSize(new Dimension(200, 100));
        logoPanel.setLayout(new GridLayout());
        JLabel logo = new JLabel();
        logo.setIcon(new ImageIcon("./src/piccross.png"));
        logoPanel.add(logo);

        /*
         * adding the 3 sub-panels to top panel
         */
        topPanel.add(markPanel, BorderLayout.WEST);
        topPanel.add(rowPanel, BorderLayout.CENTER);
        topPanel.add(logoPanel, BorderLayout.EAST);

        /*
         * Left/West side Panel
         */
        JPanel colPanel = new JPanel();
        colPanel.setPreferredSize(new Dimension(100, 300));
        colPanel.setBackground(Color.BLUE);
        colPanel.setLayout(new GridLayout(dim, 1, 1, 1));
        for (int i = 1; i <= dim; i++) {
            colPanel.add(new JLabel("           Row " + String.valueOf(i)));
        }

        /*
         * Right/East side panel
         */
        JPanel comPanel = new JPanel();
        comPanel.setPreferredSize(new Dimension(200, 380));
        comPanel.setLayout(new BoxLayout(comPanel, BoxLayout.Y_AXIS));
        comPanel.setBackground(Color.PINK);
        comPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 5));
        /*
         * Event log for listing all action done by the user
         */
        eventLog = new JTextArea();
        eventLog.setEditable(false);
        JScrollPane scrollV = new JScrollPane(eventLog);
        scrollV.setPreferredSize(new Dimension(180, 180));
        eventLog.append("Game started ...\n");
        langList = new JComboBox<String>(languange);
        langList.setSelectedItem(languange);
        langList.addActionListener(control);

        /*
         * Defining and adding actions to function buttons
         */
        JPanel funcPanel = new JPanel();
        funcPanel.setLayout(new BoxLayout(funcPanel, BoxLayout.X_AXIS));
        funcPanel.setBackground(Color.gray);
        funcButtons[0] = new JButton("Reset");
        funcButtons[1] = new JButton("New Game");
        funcButtons[2] = new JButton("Help");
        funcPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        for (int i = 0; i < funcButtons.length; i++) {
            funcButtons[i].addActionListener(control);
            funcButtons[i].setMargin(new Insets(1, 1, 1, 1));
            funcPanel.add(funcButtons[i]);
            funcPanel.add(Box.createRigidArea(new Dimension(5, 0)));
        }
        /*
         * Attaching different panels and component on right-side panel
         */
        comPanel.add(scrollV);
        comPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        comPanel.add(langList);
        comPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        comPanel.add(funcPanel);

        /*
         * Attaching all main panels to the frame
         */
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(comPanel, BorderLayout.EAST);
        frame.add(colPanel, BorderLayout.WEST);
        frame.add(boardPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        new Game();
        
    }

    /**
     * Inner Class
     * holds all the specific action of a button
     */
    public class Controller implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            /*
             * Action for Board buttons
             */
            for (int i = 0; i < dim; i++) {
                for (int j = 0; j < dim; j++) {
                    if (source == squareButtons[i][j]) {
                        System.out.printf("(%d,%d) is clicked...\n", i, j);
                        eventLog.append("(" + String.valueOf(i) + "," + String.valueOf(j) + ")" + " is clicked...\n");
                        if (markState) {
                            ((Component) source).setBackground(Color.PINK);
                        } else {
                            ((Component) source).setBackground(Color.GREEN);
                        }
                        ((Component) source).setEnabled(false);
                    }
                }
            }
            /*
             * Action for Mark feature
             */
            if (source == markBox) {
                markState = markBox.isSelected();
                if (markState) {
                    System.out.println("Mark is checked...");
                    eventLog.append("Mark is checked...\n");
                } else {
                    System.out.println("Mark is unchecked...");
                    eventLog.append("Mark is unchecked...\n");
                }
            }
            /*
             * Action for function buttons
             */
            for (int i = 0; i < funcButtons.length; i++) {
                if (funcButtons[i] == source) {
                    switch (i) {
                        case 0:
                            System.out.println("Reset is clicked...");
                            eventLog.append("Reset is checked...\n");
                            break;
                        case 1:
                            System.out.println("New Game is clicked...");
                            eventLog.append("New Game is checked...\n");
                            break;
                        case 2:
                            System.out.println("Help is clicked...");
                            eventLog.append("Help is checked...\n");
                            break;
                    }
                }
            }

            /*
             * Action for Language select
             */
            if (source == langList) {
                System.out.println(langList.getSelectedItem() + "is selected...");
                eventLog.append(langList.getSelectedItem() + "is selected...\n");
            }

        }

    }

}
