import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PlayField extends JPanel implements MouseListener, ActionListener {

    int cellSize = 50;
    int boardSize;
    int boardPxSize;
    int winStreak;
    int playersCount;
    int realPlayer;
    int currentPlayer = 1;
    Game game;


    public PlayField(int boardSize, int winStreak, int playersCount, int realPlayer) {

        this.boardSize = boardSize;
        this.winStreak = winStreak;
        this.playersCount = playersCount;
        this.realPlayer = realPlayer;
        boardPxSize = cellSize * boardSize;
        game = new Game(boardSize, winStreak, playersCount, realPlayer);
        this.setBounds(800/2-boardPxSize/2, 600/2-boardPxSize/2-20, boardPxSize, boardPxSize);
        this.addMouseListener(this);

    }


    @Override
    public void paint(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.setColor(Color.BLACK);
        g2D.setStroke(new BasicStroke(2));

        for(int i = 1; i < boardSize; i++) {

            g2D.drawLine(0, cellSize*i, boardSize*cellSize, cellSize*i); //horizontal
            g2D.drawLine(cellSize*i, 0, cellSize*i, boardSize*cellSize); //vertical
        }

        for (int x = 0; x < boardSize; x++) {
            for (int y = 0; y < boardSize; y++) {
                if (game.board[x][y] != 0) {
                    Color color = getColor(game.board[x][y]);
                    g2D.setColor(color);
                    g2D.fillRect(x*cellSize+4, y*cellSize+4, cellSize-8, cellSize-8);
                }
            }
        }
    }

    public Color getColor(int player) {

        switch (player) {
            case 1:
                return Color.red;
            case 2:
                return Color.green;
            case 3:
                return Color.blue;
            case 4:
                return Color.cyan;
            case 5:
                return Color.MAGENTA;
            case 6:
                return Color.orange;
            case 7:
                return Color.pink;
            case 8:
                return Color.yellow;
            case 9:
                return Color.black;
            case 10:
                return Color.darkGray;
            default:
                return Color.red;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

        int result;
        boolean squareFound = false;
        if (currentPlayer <= realPlayer) {
            do {
                int posX = e.getX();
                int posY = e.getY();
                for (int x = 0; x < boardSize; x++) {
                    for (int y = 0; y < boardSize; y++) {
                        if (posX > x * cellSize && posX < x * cellSize + cellSize && posY > y * cellSize && posY < y * cellSize + cellSize) {
                            if (game.board[x][y] == 0) {
                                game.board[x][y] = currentPlayer;
                                result = game.checkWinner(currentPlayer);
                                if (result == 10) {
                                    System.out.println("Wygrywa gracz " + currentPlayer);
                                }
                                if (result == 1) {
                                    System.out.println("Remis");
                                }
                                squareFound = true;
                                if (currentPlayer == playersCount) {
                                    currentPlayer = 1;
                                } else {
                                    currentPlayer++;
                                }
                                repaint();
                                break;
                            }
                        }
                    }
                }
            } while(!squareFound);
        } else {
            System.out.println("Gracz " + currentPlayer);
            game.makeMove(currentPlayer);
            game.displayBoard();
            System.out.println();
            result = game.checkWinner(currentPlayer);
            if (result == 10) {
                System.out.println("Wygrywa gracz " + currentPlayer);
            }
            if (result == 1) {
                System.out.println("Remis");
            }
            if (currentPlayer == playersCount) {
                currentPlayer = 1;
            } else {
                currentPlayer++;
            }
            repaint();
        }



    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
