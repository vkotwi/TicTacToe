package com.ttt;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class TicTacToe {

    private class Player {
        String name;
        boolean Comp;
        char character; // Make customisable?
    }

    private class Board {
        private class Tile
        {
            int location;
            boolean free;
            char symbol;
        }

        ArrayList<Tile> boardTiles;
        String[] boardStructure = new String[]{"|", "|", "\n___|___|___\n", "|", "|", "\n___|___|___\n", "|", "|", "\n"};

        /*
        // Generates new board
        private void GenerateNewBoard() {
            boardTiles = new ArrayList<Tile>();

            // Generates initial tiles for board
            for (int i = 1; i < 10; i++) {
                Tile tile = new Tile();
                tile.location = i;
                tile.free = true;
                boardTiles.add(tile);
            }
        }
        */

    }

    private static class WinCondition{
        static int[][] winConditions;

        private static void generateWinCondList(){
            // I'm not sorry
            WinCondition.winConditions = new int[8][3];
            int[] temp0 = new int[]{0, 1, 2};
            int[] temp1 = new int[]{3, 4, 5};
            int[] temp2 = new int[]{6, 7, 8};
            int[] temp3 = new int[]{0, 3, 6};
            int[] temp4 = new int[]{1, 4, 7};
            int[] temp5 = new int[]{2, 5, 8};
            int[] temp6 = new int[]{0, 4, 8};
            int[] temp7 = new int[]{2, 4, 6};

            winConditions[0] = temp0;
            winConditions[1] = temp1;
            winConditions[2] = temp2;
            winConditions[3] = temp3;
            winConditions[4] = temp4;
            winConditions[5] = temp5;
            winConditions[6] = temp6;
            winConditions[7] = temp7;
        }
    }

    // Why does new Board.Tile cause issues?
    private Board CreateNewBoard() {
        // Move board gen to here? best practice?
        Board board = new Board();
        board.boardTiles = new ArrayList<Board.Tile>();
        WinCondition.generateWinCondList();

        // Generates initial tiles for board and sets they're location's from 1-9
        for (int i = 1; i < 11; i++) {
            //same as Board.Tile tile = board.Tile().new;
            Board.Tile tile = board.new Tile();
            tile.location = i; tile.free = true; tile.symbol = ' ';
            board.boardTiles.add(tile);
        }
        return board;
    }

    public void NewGame(String player1, String player2, boolean isCompGame) {
        // Create new players in here
        Player p1 = CreateNewPlayer(player1, false, 'O');
        Player p2 = CreateNewPlayer(player2, isCompGame, 'X');

        int choice = 0;
        boolean tryAgain;
        // Coin flip
        while (true) {
            tryAgain = false;
            System.out.print("Please select an option:" + "\n" +
                    "1. Player 1 goes first" + "\n" +
                    "2. Player 2 goes first" + "\n" +
                    "3. Random" + "\n" +
                    ">>> ");

            Scanner opt = new Scanner(System.in);
            // Guessing safe input to convert in try catch?

            // Gets user's choice -- worry about sec later
            try {
                choice = opt.nextInt();
            }
            catch (Exception e) {
                System.out.print("Invalid option, please try again");
                tryAgain  = true;
            }

            if (!tryAgain) {
                if (choice == 3) {
                    int ind = ThreadLocalRandom.current().nextInt(1, 3);
                    choice = ind;
                    System.out.println(ind);
                }

                if (choice == 1) {
                    RunGame(p1, p2);
                    break;
                } else if (choice == 2) {
                    RunGame(p2, p1);
                    break;
                } else {
                    System.out.println("Incorrect input. Please only enter either 1, 2 or 3.\n");
                }
            }
        }
    }

    private Player CreateNewPlayer(String name, boolean isBot, char symbol) {
        Player p = new Player();
        p.name = name; p.Comp = isBot; p.character = symbol;
        return p;
    }

    private void DrawBoard(ArrayList<Board.Tile> bTiles, String[] bStructure) {
        // Prints out board's corresponding number if the tile is free or symbol if it is not free
        for (int i = 0; i < bTiles.size()-1; i++)
        {
            if (bTiles.get(i).free) { System.out.print(" " + bTiles.get(i).location + " "); }
            else {System.out.print(" " + bTiles.get(i).symbol + " ");}
            System.out.print(bStructure[i]);
        }
        System.out.println("   |" + "   |");
    }

    protected void RunGame(Player player1, Player player2) {
        // Scanner locSelected = new Scanner(System.in);

        // Creates a new empty board
        Board gameBoard = CreateNewBoard();

        Player playerTurn = player1;
        int location = 0;
        int turn = 0;
        boolean noErr = true;
        // Runs the game till a player wins or both players draw
        while (turn < 9) {
            // Remove function and just put here?
            System.out.println("It's " + playerTurn.name + "'s turn");

            // Checks if it's the Comp's turn. If not, checks if it's a player's turn
            if (playerTurn.Comp) {
                // Randomly selects tiles till a free tile is found
                System.out.print("The bot's thinking.");

                location = CompPlayerTurn(gameBoard.boardTiles);
                gameBoard.boardTiles.get(location).free = false;
                gameBoard.boardTiles.get(location).symbol = playerTurn.character; // Remove symbol thing?

                location = location + 1;
                System.out.println("The bot chose: " + location);

                System.out.println();
            }
            else {
                // Prints tictactoe board in console
                DrawBoard(gameBoard.boardTiles, gameBoard.boardStructure);

                System.out.println("Please select an empty location from 1-9 on the board.");
                System.out.print(">>> ");

                while (true) {
                    // Get player's input
                    Scanner locSelected = new Scanner(System.in);
                    noErr = true;
                    try {
                        location = locSelected.nextInt() - 1;
                    }
                    catch (Exception e) {
                        noErr = false;
                        System.out.println("Please only enter a number visible on the current board.");
                    }

                    if (noErr) {
                        // Checks if location player entered is taken
                        if (!gameBoard.boardTiles.get(location).free) {
                            System.out.println("That tile is not free, please pick another tile.");
                        } else {
                            // Changes tile to state it's not free and adds the symbol to the tile
                            gameBoard.boardTiles.get(location).symbol = playerTurn.character;
                            gameBoard.boardTiles.get(location).free = false;
                            break;
                        }
                    }
                    System.out.print(">>> ");
                }
            }

            DrawBoard(gameBoard.boardTiles, gameBoard.boardStructure);

            boolean winner = CheckWinCondition(gameBoard.boardTiles, player1, player2);
            if (winner) { break; }
            if (turn == 8)
            {
                System.out.println("Game ended in a draw.\n");
                break;
            }

            if      (playerTurn == player1) { playerTurn = player2; }
            else if (playerTurn == player2) { playerTurn = player1; }
            System.out.println("");
            turn++;
        }
    }

    private boolean CheckWinCondition(ArrayList<Board.Tile> tiles, Player p1, Player p2) {
        // If any of these tiles are empty, no one has won the game yet
        int[][] wc = WinCondition.winConditions;
        if (!(tiles.get(0).free && tiles.get(4).free && tiles.get(8).free)) {

            for (int i = 0; i < 8; i++) {
                // Doesn't check win condition for current combination if any of the tiles in the winning combination are not occupied
                if ( (tiles.get(wc[i][0]).symbol != ' ' && tiles.get(wc[i][1]).symbol != ' ' && tiles.get(wc[i][2]).symbol != ' ')) {

                    if ( ( tiles.get(wc[i][0]).symbol == tiles.get(wc[i][1]).symbol ) && (tiles.get(wc[i][1]).symbol == tiles.get(wc[i][2]).symbol) )
                    {
                        if (p1.character == tiles.get(wc[i][0]).symbol) { System.out.println("The winner is " + p1.name); }
                        else { System.out.println("The winner is " + p2.name); }
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // The Comp the player plays against. Chooses a tile to place symbol on
    private static int CompPlayerTurn(ArrayList<Board.Tile> boardTiles) {
        // Comp will check to see if the player will win the next turn
        // If so, it will choose a tile in an attempt to stop the player from winning
        // Else, The Comp will focus on winning the game
        // If the Comp's attempt to win is blocked, it will pick a new win condition should a new one be available.
        // If not, it will pick a random tile

        int[][] winCond = WinCondition.winConditions;
        int CompTile = -1;
        int CompWinTile = -1;
        boolean blockedPlayer = false;

        // Comp checks each tile for player's symbol, then checks other tiles that include that tile in win conditions and selects the 3rd tile to block them
        // Checks all 9 tiles to see if one is occupied by the player's symbol
        for (int i = 0; i < 9; i++) {
            // If it is, the Comp checks all possible ways the player could win with that tile, and attempts to block it
            if (boardTiles.get(i).symbol == 'O') {
                // Checks all 8 win conditions
                for (int j = 0; j < 8; j++ ) {
                    // Checks if at least 2 or the 3 tiles are occupied
                    boolean tile1 = boardTiles.get(winCond[j][0]).symbol == 'O';
                    boolean tile2 = boardTiles.get(winCond[j][1]).symbol == 'O';
                    boolean tile3 = boardTiles.get(winCond[j][2]).symbol == 'O';

                    // Checks if at least 2 of the 3 tiles are occupied by the player to stop them from winning, if not, checks for a different win condition
                    int tile = (tile1 && tile2 && boardTiles.get(winCond[j][2]).free) ? winCond[j][2]
                             : (tile1 && tile3 && boardTiles.get(winCond[j][1]).free) ? winCond[j][1]
                             : (tile2 && tile3 && boardTiles.get(winCond[j][0]).free) ? winCond[j][0]
                             : -1;

                    // Comp attempts to block player
                    if (!(tile == -1)) {
                        CompTile = tile;
                        blockedPlayer = true;
                    }

                    // Runs if Comp has tried to stop the player from winning and not found a way to win this turn yet.
                    if (blockedPlayer && CompWinTile == -1) {
                        boolean CompTile1 = boardTiles.get(winCond[j][0]).symbol == 'X';
                        boolean CompTile2 = boardTiles.get(winCond[j][1]).symbol == 'X';
                        boolean CompTile3 = boardTiles.get(winCond[j][2]).symbol == 'X';

                        CompWinTile = ( CompTile1 && CompTile2 && boardTiles.get(winCond[j][2]).free ) ? winCond[j][2]
                                    : ( CompTile1 && CompTile3 && boardTiles.get(winCond[j][1]).free ) ? winCond[j][1]
                                    : ( CompTile2 && CompTile3 && boardTiles.get(winCond[j][0]).free ) ? winCond[j][0]
                                    : -1;
                    }
                    // Check for tiles with X to try and achieve a win condition for the Comp
                    else if (!(tile1 || tile2 || tile3)) {
                        boolean CompTile1 = boardTiles.get(winCond[j][0]).symbol == 'X';
                        boolean CompTile2 = boardTiles.get(winCond[j][1]).symbol == 'X';
                        boolean CompTile3 = boardTiles.get(winCond[j][2]).symbol == 'X';

                        if (CompTile1 || CompTile2 || CompTile3) {
                            if      (boardTiles.get(winCond[j][0]).free) { CompTile = winCond[j][0]; }
                            else if (boardTiles.get(winCond[j][1]).free) { CompTile = winCond[j][1]; }
                            else if (boardTiles.get(winCond[j][2]).free) { CompTile = winCond[j][2]; }
                        }
                    }
                }
            }
        }

        if (CompWinTile > -1) {
            return CompWinTile;
        }
        else if (blockedPlayer) {
            return CompTile;
        }
        else if (CompTile == -1) {
            while (true) {
                Random rand = new Random();
                CompTile = rand.nextInt(9); // chooses number between 0 and 9
                if (boardTiles.get(CompTile).free) {
                    break;
                }
            }
        }
        return CompTile;
    }

}
