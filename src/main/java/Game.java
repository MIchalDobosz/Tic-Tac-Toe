import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Game {

    public static int[][] board;
    public static int boardSize = 3; //board size
    public static int winStreak = 3; //number of marked fields needed to win the game
    public static int playersCount = 2; //number of players
    public static int realPlayer = 0; //human players turn (if set to 0 game plays out without human player)
    public static int counter;

    public static void main(String[] args) {

        int result = 0;
        initializeBoard();
        displayBoard();
        do {
            for(int i=1; i<=playersCount; i++) {
                System.out.println("\nPlayer: " + i);
                makeMove(i);
                displayBoard();
                result = checkWinner(i);
                if(result == 10) {
                    System.out.println("Winner: " + i);
                    break;
                }
                if(result == 1) {
                    System.out.println("Draw");
                    break;
                }
            }
        } while(result == 0);
    }

    public static void initializeBoard() {
        board = new int[boardSize][boardSize];

        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                board[i][j] = 0;
            }
        }
    }

    public static void displayBoard() {
        for (int[] i : board) {
            System.out.println(Arrays.toString(i));
        }
    }

    public static void makeMove(int player) {

        Scanner scanner = new Scanner(System.in);

        if (player == realPlayer) {

            while (true) {
                System.out.print("Input Y: ");
                int x = Integer.parseInt(scanner.next());
                System.out.print("Input X: ");
                int y = Integer.parseInt(scanner.next());

                if (board[x][y] == 0) {
                    board[x][y] = player;
                    break;
                } else {
                    System.out.println("This field is already taken!");
                }
            }

        } else {

            long startMove = System.currentTimeMillis();

            HashMap<String, Integer> bestValue = new HashMap<>();
            bestValue.put("value", -2);
            bestValue.put("depth", 0);
            bestValue.put("gamechangerValue", 0);
            bestValue.put("gamechangerDepth", Integer.MAX_VALUE);

            HashMap<String, Integer> value;

            for (int xOffset = 0; xOffset <= boardSize - winStreak; xOffset++) {
                for (int yOffset = 0; yOffset <= boardSize - winStreak; yOffset++) {


                    value = minimax(player, 0, xOffset, yOffset);
                    System.out.println("Final value: " + value);

                    if (value.get("gamechangerDepth") < bestValue.get("gamechangerDepth") && value.get("value") != -2) {
                        bestValue.put("value", value.get("value"));
                        bestValue.put("depth", value.get("depth"));
                        bestValue.put("gamechangerValue", value.get("gamechangerValue"));
                        bestValue.put("gamechangerDepth", value.get("gamechangerDepth"));
                        bestValue.put("bestX", value.get("bestX"));
                        bestValue.put("bestY", value.get("bestY"));
                    }
                }
            }

            board[bestValue.get("bestX")][bestValue.get("bestY")] = player;

            System.out.println("Counter: " + counter);
            counter = 0;
            long stopMove = System.currentTimeMillis();
            System.out.println("Czas ruchu: " + (stopMove - startMove));
        }
    }

    public static int checkWinner(int player) {

        int rowCount = 0;
        int colCount = 0;
        int marksCount = 0;

        for (int y = 0; y < boardSize; y++) {
            for (int i = 0; i <= boardSize - winStreak; i++) {
                for (int x = i; x < winStreak + i; x++) {

                    if (board[y][x] == player) {
                        rowCount++;
                        if (rowCount == winStreak) {
                            return 10;
                        }
                    }

                    if (board[x][y] == player) {
                        colCount++;
                        if (colCount == winStreak) {
                            return 10;
                        }
                    }

                }
                rowCount = 0;
                colCount = 0;
            }
        }

        int mainDiagonalCount = 0;
        int minorDiagonalCount = 0;
        for (int i = 0; i <= boardSize - winStreak; i++) {
            for (int j = 0; j <= boardSize - winStreak; j++) {
                for (int x = 0; x < winStreak; x++) {
                    for (int y = 0; y < winStreak; y++) {

                        if (board[y + i][y + j] == player) {
                            mainDiagonalCount++;
                            if (mainDiagonalCount == winStreak) {
                                return 10;
                            }
                        }

                        if (board[y + i][winStreak - 1 - y + j] == player) {
                            minorDiagonalCount++;
                            if (minorDiagonalCount == winStreak) {
                                return 10;
                            }
                        }

                    }
                    mainDiagonalCount = 0;
                    minorDiagonalCount = 0;

                }
            }
        }

        for (int y = 0; y < boardSize; y++) {
            for (int x = 0; x < boardSize; x++) {
                if (board[y][x] != 0) {
                    marksCount++;
                }
            }
        }
        if (marksCount == boardSize * boardSize) {
            return 1;
        }

        return 0;
    }

    public static int checkWinnerWithOffset(int player, int xOffset, int yOffset) {

        int rowCount = 0;
        int colCount = 0;
        for (int x = xOffset; x < winStreak + xOffset; x++) {
            for (int y = yOffset; y < winStreak + yOffset; y++) {

                if (board[x][y] == player) {
                    rowCount++;
                    if (rowCount == winStreak) {
                        return 10;
                    }
                }

                if (board[y][x] == player) {
                    colCount++;
                    if (colCount == winStreak) {
                        return 10;
                    }
                }

            }
            rowCount = 0;
            colCount = 0;
        }

        int mainDiagonalCount = 0;
        int minorDiagonalCount = 0;
        for (int x = 0; x < winStreak; x++) {
            for (int y = 0; y < winStreak; y++) {

                if (board[y + xOffset][y + yOffset] == player) {
                    mainDiagonalCount++;
                    if (mainDiagonalCount == winStreak) {
                        return 10;
                    }
                }

                if (board[y + xOffset][winStreak - 1 - y + yOffset] == player) {
                    minorDiagonalCount++;
                    if (minorDiagonalCount == winStreak) {
                        return 10;
                    }
                }
            }
            mainDiagonalCount = 0;
            minorDiagonalCount = 0;
        }
        int marksCount = 0;
        for (int x = xOffset; x < winStreak + xOffset; x++) {
            for (int y = yOffset; y < winStreak + yOffset; y++) {
                if (board[x][y] != 0) {
                    marksCount++;
                }
            }
        }
        if (marksCount == winStreak * winStreak) {
            return 1;
        }
        return 0;
    }

    public static HashMap<String, Integer> minimax(int player, int depth, int xOffset, int yOffset) {
        counter++;
        HashMap<String, Integer> bestValue = new HashMap<>();
        HashMap<String, Integer> value;


        for (int i = 1; i <= playersCount; i++) {
            if (checkWinnerWithOffset(i, xOffset, yOffset) == 10) {
                bestValue.put("value", i);
                bestValue.put("depth", depth);
                return bestValue;
            }
        }

        // Jeśli remis
        if (checkWinnerWithOffset(player, xOffset, yOffset) == 1) {
            if (depth == 0) {
                bestValue.put("value", -2);
                bestValue.put("depth", depth);
                bestValue.put("gamechangerValue", 0);
                bestValue.put("gamechangerDepth", winStreak*winStreak+1);
                return bestValue;
            } else {
                bestValue.put("value", 0);
                bestValue.put("depth", depth);
                return bestValue;
            }
        }

        // Jeśli głębokośc za duża
        if (depth > playersCount+1) {
            bestValue.put("value", 0);
            bestValue.put("depth", depth);
            return bestValue;
        }

        if (depth != 0) {
            if (player == playersCount) {
                player = 1;
            } else {
                player++;
            }
        }

        bestValue.put("value", -2);
        bestValue.put("depth", 0);
        if (depth == 0) {
            bestValue.put("gamechangerValue", 0);
            bestValue.put("gamechangerDepth", playersCount*2+2);
        }

        for (int x = xOffset; x < winStreak + xOffset; x++) {
            for (int y = yOffset; y < winStreak + yOffset; y++) {

                if (board[x][y] == 0) {

                    board[x][y] = player;

                    value = minimax(player, depth + 1, xOffset, yOffset);
                    if (depth == 0) {
                        System.out.println("Depth 0 values: " + value);
                    }

                    if (depth == 0) {
                        if (value.get("depth") < bestValue.get("gamechangerDepth") && value.get("value") != 0) {
                            bestValue.put("gamechangerValue", value.get("value"));
                            bestValue.put("gamechangerDepth", value.get("depth"));
                        }
                    }

                    // Czy gracz wygrał już w tej pętli?
                    if (bestValue.get("value") == player) {
                        // Tak: Czy wygrał teraz?
                        if (value.get("value") == player) {
                            // Tak: Czy wygrał teraz w mniejszej ilości ruchów niż wczesniej?
                            if (value.get("depth") < bestValue.get("depth")) {
                                // Tak: Ten wynik to nowy najlepszy wynik! (szybsza wygrana)
                                bestValue.put("value", value.get("value"));
                                bestValue.put("depth", value.get("depth"));
                                bestValue.put("bestX", x);
                                bestValue.put("bestY", y);
                            }
                        }
                    } else {
                        // Nie: Czy wygrał teraz?
                        if (value.get("value") == player) {
                            //Tak: Ten wynik to nowy najlepszy wynik! (wygrana)
                            bestValue.put("value", value.get("value"));
                            bestValue.put("depth", value.get("depth"));
                            bestValue.put("bestX", x);
                            bestValue.put("bestY", y);
                        } else {
                            //Nie: Czy gracz zremisował już w tej pętli?
                            if (bestValue.get("value") != 0) {
                                //Nie: Czy zremisował teraz?
                                if (value.get("value") == 0) {
                                    //Tak: Ten wynik to nowy najlepszy wynik! (remis)
                                    bestValue.put("value", value.get("value"));
                                    bestValue.put("depth", value.get("depth"));
                                    bestValue.put("bestX", x);
                                    bestValue.put("bestY", y);
                                    if (depth == 0) {
                                        //System.out.println("nowy remis");
                                    }
                                } else {
                                    //Nie: Czy gracz przegrał teraz w większej ilości ruchów?
                                    if (value.get("depth") > bestValue.get("depth")) {
                                        //Tak: Ten wynik to nowy najlepszy wynik (wolniejsza przegrana)
                                        bestValue.put("value", value.get("value"));
                                        bestValue.put("depth", value.get("depth"));
                                        bestValue.put("bestX", x);
                                        bestValue.put("bestY", y);
                                    }
                                }
                            }
                        }
                    }
                    board[x][y] = 0;
                }
            }
        }
        return bestValue;
    }
}