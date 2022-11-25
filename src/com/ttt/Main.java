package com.ttt;

import java.util.Scanner;

// Tic Tac Toe program
public class Main {

    public static void main(String[] args) {

        // Why doesn't java just have a built in clear console functionality >:(
        System.out.println();
        System.out.println("Welcome to TicTacToe.");

        // Creates a new ttt instance

        while (true) {
            // Prints menu options
            boolean flag = true;
            int userChoice = 0;
            MenuOptions();

            Scanner scan = new Scanner(System.in);
            // Guessing safe input to convert in try catch?

            // Gets user's choice -- worry about sec later
            try {
                userChoice = scan.nextInt();
            }
            catch (Exception e) {
                // Wrong number input exception class?
                flag = false;
                System.out.println("Incorrect input. Please only enter either 1, 2 or 3.\n");
            }

            if (flag) {
                if (userChoice == 1) {
                    // Play against the AI
                    TicTacToe newAIGame = new TicTacToe();

                    // Get player's name
                    Scanner getPlayerName = new Scanner(System.in);
                    System.out.print("Please enter your name: ");

                    String name = getPlayerName.next();

                    // Creates a new AI game
                    newAIGame.NewGame(name, "bot", true);
                }
                else if (userChoice == 2) {
                    // Play against another local player
                    TicTacToe newPlayersGame = new TicTacToe();

                    // Get player 1's name
                    Scanner getPlayer1Name = new Scanner(System.in);
                    System.out.print("Please enter Player 1's name: ");

                    // TODO: make sure its safe
                    String p1Name = getPlayer1Name.next();

                    // Get player 2's name
                    Scanner getPlayer2Name = new Scanner(System.in);
                    System.out.print("Please enter Player 2's name: ");

                    // TODO: make sure its safe
                    String p2Name = getPlayer2Name.next();

                    // Create new player game
                    newPlayersGame.NewGame(p1Name, p2Name, false);
                }
                else if (userChoice == 0) {
                    break;
                }
                else {
                    System.out.println("Incorrect input. Please only enter either a whole number between 0 and 3.\n");
                }
            }
        }
    }

    // Prints out menu options to console
    public static void MenuOptions() {
        System.out.print("Please select an option by entering it's corresponding number:" + "\n" +
                "1. Play against AI" + "\n" +
                "2. Play against local 2nd player" + "\n" +
                "0. Quit" + "\n" +
                ">>> ");
    }


}
