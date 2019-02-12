/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cskcrm.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author ckeller22
 */
public class LoginLogger {

    private static final Path logFile = Paths.get("src/logging/" + LocalDate.now().format(DateTimeFormatter.ofPattern("MM-dd-yyyy")) + ".txt");
    private static final String timestampPattern = "MM/dd/yyyy hh:mm:ss a";

    //Requirement J. Log files will be output into src/logging.
    public static void logSignOut(String username) {
        if (Files.exists(logFile)) {
            addToFile(LocalDateTime.now().format(DateTimeFormatter.ofPattern(timestampPattern)) + " : " + username + " logged out \n");
        } else {
            createFile(LocalDateTime.now().format(DateTimeFormatter.ofPattern(timestampPattern)) + " : " + username + " logged out \n");
        }
    }

    public static void logValidSignIn(String username) {
        if (Files.exists(logFile)) {
            addToFile(LocalDateTime.now().format(DateTimeFormatter.ofPattern(timestampPattern)) + " : " + username + " logged in \n");
        } else {
            createFile(LocalDateTime.now().format(DateTimeFormatter.ofPattern(timestampPattern)) + " : " + username + " logged in \n");
        }
    }

    public static void logInvalidSignIn(String username) {
        if (Files.exists(logFile)) {
            addToFile(LocalDateTime.now().format(DateTimeFormatter.ofPattern(timestampPattern)) + " : " + username + " attempted to login with invalid credentials \n");
        } else {
            createFile(LocalDateTime.now().format(DateTimeFormatter.ofPattern(timestampPattern)) + " : " + username + " attempted to login with invalid credentials \n");
        }
    }

    public static void addToFile(String message) {
        try (BufferedWriter writer = Files.newBufferedWriter(logFile, StandardOpenOption.APPEND)) {
            writer.write(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void createFile(String message) {
        try (BufferedWriter writer = Files.newBufferedWriter(logFile, StandardOpenOption.CREATE)) {
            writer.write(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
