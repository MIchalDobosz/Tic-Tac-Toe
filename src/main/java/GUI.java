import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI extends JFrame implements ActionListener, ChangeListener {

    JPanel header;
    JPanel menu;
    PlayField playField;

    JLabel titleLabel;
    JLabel boardSizeLabel;
    JLabel winStreakLabel;
    JLabel numberOfPlayersLabel;
    JLabel numberOfHumanPlayersLabel;

    JButton newGameButton;

    JSlider boardSizeSlider;
    JSlider winStreakSlider;
    JSlider numberOfPlayersSlider;
    JSlider numberOfHumanPlayersSlider;


    public GUI() {
        initializeFrame();
        initializeMenuScreen();
    }

    public void initializeFrame() {
        this.setTitle("Tic-Tac-Toe");
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public void initializeMenuScreen() {
        //header
        header = new JPanel();
        header.setBounds(0,0, 800, 100);
        header.setVisible(true);
        this.add(header);

        //title
        titleLabel = new JLabel();
        titleLabel.setText("Tic-Tac-Toe");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 60));
        header.add(titleLabel);

        //menu
        menu = new JPanel();
        menu.setBounds(250, 100, 300, 400);
        menu.setLayout(new GridLayout(10, 1));
        menu.setVisible(true);
        this.add(menu);

        //board size
        boardSizeLabel = new JLabel();
        boardSizeLabel.setText("Board size: 3x3");
        menu.add(boardSizeLabel);

        boardSizeSlider = new JSlider(3, 10, 3);
        boardSizeSlider.setPaintTicks(true);
        boardSizeSlider.setMinorTickSpacing(1);
        boardSizeSlider.addChangeListener(this);
        menu.add(boardSizeSlider);

        //win streak
        winStreakLabel = new JLabel();
        winStreakLabel.setText("Squares in order to win: 3");
        menu.add(winStreakLabel);

        winStreakSlider = new JSlider(3, 5, 3);
        winStreakSlider.setPaintTicks(true);
        winStreakSlider.setMinorTickSpacing(1);
        winStreakSlider.addChangeListener(this);
        menu.add(winStreakSlider);

        //no. players
        numberOfPlayersLabel = new JLabel();
        numberOfPlayersLabel.setText("Number of players: 2");
        menu.add(numberOfPlayersLabel);

        numberOfPlayersSlider = new JSlider(2, 5, 2);
        numberOfPlayersSlider.setPaintTicks(true);
        numberOfPlayersSlider.setMinorTickSpacing(1);
        numberOfPlayersSlider.addChangeListener(this);
        menu.add(numberOfPlayersSlider);

        //no. human players
        numberOfHumanPlayersLabel = new JLabel();
        numberOfHumanPlayersLabel.setText("Number of human players: 1");
        menu.add(numberOfHumanPlayersLabel);

        numberOfHumanPlayersSlider = new JSlider(0, 5, 1);
        numberOfHumanPlayersSlider.setPaintTicks(true);
        numberOfHumanPlayersSlider.setMinorTickSpacing(1);
        numberOfHumanPlayersSlider.addChangeListener(this);
        menu.add(numberOfHumanPlayersSlider);

        //Start btutton
        newGameButton = new JButton();
        newGameButton.setText("START GAME");
        newGameButton.addActionListener(this);
        menu.add(newGameButton);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == newGameButton) {

            this.getContentPane().removeAll();
            this.repaint();
            playField = new PlayField(boardSizeSlider.getValue(), winStreakSlider.getValue(), numberOfPlayersSlider.getValue(), numberOfHumanPlayersSlider.getValue());
            this.add(playField);
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if(e.getSource() == boardSizeSlider) {
            boardSizeLabel.setText("Board size: " + boardSizeSlider.getValue() + "x" + boardSizeSlider.getValue());
        }

        if(e.getSource() == winStreakSlider) {
            winStreakLabel.setText("Squares in order to win: " + winStreakSlider.getValue());
        }

        if(e.getSource() == numberOfPlayersSlider) {
            numberOfPlayersLabel.setText("Number of players: " + numberOfPlayersSlider.getValue());
        }

        if(e.getSource() == numberOfHumanPlayersSlider) {
            numberOfHumanPlayersLabel.setText("Number of human players: " + numberOfHumanPlayersSlider.getValue());
        }
    }


}
