package com.searcher.app;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class SearcherApp {
    public static void main(String... args) throws IOException {
        List<File> directories = FileManager.getInstance().search(new File(Constants.path), "https://efreshlynow.com");
        FileManager.getInstance().save(directories, new File(new File("D:\\"), "results.txt"));
    }
}
