package com.searcher.app;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {
    private static final FileManager MANAGER = new FileManager();

    private FileManager() {

    }

    public static FileManager getInstance() {
        return MANAGER;
    }

    public List<File> getDirectories(File directory) {
        List<File> directories = new ArrayList<>();
        File[] files = directory.listFiles();

        for (File file : files) {
            if (file.isDirectory()) {
                directories.add(file);
                directories.addAll(getDirectories(file));
            }
        }

        return directories;
    }

    public List<File> getFiles(File directory) {
        List<File> files = new ArrayList<>();
        File[] directories = directory.listFiles();

        for (File file : directories) {
            if (file.isDirectory()) {
                files.addAll(getFiles(file));
            } else if (file.isFile()) {
                files.add(file);
            }
        }

        return files;
    }

    public List<File> search(File directory, String text) throws IOException {
        List<File> results = new ArrayList<>();
        List<File> files = getFiles(directory);
        for (File file : files) {
            StringBuilder builder = readFile(file, text);

            if (builder.toString().contains(text.toLowerCase())) {
                System.out.println(text);
                results.add(file);
            }
        }
        return results;
    }

    public StringBuilder readFile(File file, String searched) throws IOException {
        StringBuilder builder = new StringBuilder();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        while ((line = reader.readLine()) != null) {
            builder.append(line.toLowerCase()).append("\n");

            if (searched != null && line.contains(searched)) {
                System.out.println(file.getAbsolutePath() + ":" +line);
            }
        }
        reader.close();
        return builder;
    }

    public void print(List<File> files) {
        for (File file : files) {
            System.out.println(file.getAbsolutePath());
        }
    }

    public void save(List<File> files, File destination) throws IOException {
        if (!destination.getParentFile().exists()) {
            destination.mkdirs();
        }

        PrintWriter builder = new PrintWriter(destination);
        for (File file : files) {
            builder.append("+++++++++++++++++++++++++++++++++++++++++++++++++").append("\n");
            builder.append(file.getAbsolutePath()).append("\n");
            builder.append("--------------------------").append("\n");
            builder.append(readFile(file.getAbsoluteFile(), null)).append("\n");
            builder.append("--------------------------").append("\n");
        }

        builder.flush();
        builder.close();
    }
}
