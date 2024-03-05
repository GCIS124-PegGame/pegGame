package PEGGAME2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class FileHandlerImpl implements FileHandler {

    @Override
    public String askUserForFileNameAndAddContent() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter File Name : ");
            String fileName = scanner.next();
            System.out.println("Your File " + fileName + " is Made");

            // Prepare game instructions and dummy board
            String content = "Hello User! Welcome to the Peg Game.\n" +
                    "Rules of the Peg Game:\n" +
                    "> Board Setup:\n" +
                    "  - The game is typically played on a board with holes across the board, traditionally in a square.\n" +
                    "> Peg Placement:\n" +
                    "  - At the beginning of the game, all holes on the board are filled with pegs except for one hole, usually at the center of the board.\n" +
                    "> Peg Movement:\n" +
                    "  - Pegs can only move by jumping over other pegs, similar to checkers.\n" +
                    "  - A peg can jump over an adjacent peg horizontally or vertically to land in an empty hole, provided there is an empty hole immediately on the other side of the peg being jumped over.\n" +
                    "  - The jumped-over peg is removed from the board.\n" +
                    "> Objective:\n" +
                    "  - The goal of the game is to remove as many pegs as possible by jumping over them until no more moves are possible.\n" +
                    "  - The ideal outcome is to end the game with only one peg left on the board, preferably in the center hole.\n" +
                    "> Valid Moves:\n" +
                    "  - A peg cannot jump over another peg diagonally.\n" +
                    "  - A peg cannot move to an adjacent hole if it is not directly in line with the peg and an empty hole.\n" +
                    "> End of the Game:\n" +
                    "  - The game ends when no more valid moves are possible.\n" +
                    "  - If there is only one peg left on the board, the player wins. However, if more than one peg remains, the player loses.\n" +
                    "Good Luck, Player! Below is a dummy board to assist you with your moves:\n";

            // Create the dummy board
            int rows = 4;
            int cols = 4;
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    if (i == rows - 1 && j == cols - 1) {
                        content += ". ";
                    } else {
                        content += "o ";
                    }
                }
                content += "\n";
            }

            // Write content to the file
            try (FileWriter writer = new FileWriter(fileName);
                 PrintWriter print = new PrintWriter(writer)) {
                print.println(content);
                System.out.println("File created successfully: " + fileName);
            } catch (IOException e) {
                System.out.println("Error in making the File: " + e.getMessage());
            }

            return fileName;
        } catch (Exception e) {
            System.out.println("Error in creating file: " + e.getMessage());
            return null;
        }
    }

    @Override
    public Collection<Location> readFromFile(String fileName) {
        Collection<Location> initialPegLocations = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            // Skip the instructions
            for (int i = 0; i < 11; i++) {
                reader.readLine();
            }
            // Read the game board
            String line;
            int row = 1;
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.trim().split(" ");
                for (int col = 1; col <= tokens.length; col++) {
                    if (tokens[col - 1].equals("o")) {
                        initialPegLocations.add(new Location(row, col));
                    }
                }
                row++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return initialPegLocations;
    }
}
