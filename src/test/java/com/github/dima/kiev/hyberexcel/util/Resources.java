package com.github.dima.kiev.hyberexcel.util;

import com.github.dima.kiev.hyberexcel.exceptions.FileNotFoundExceptionUnchecked;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;

public class Resources {

    public static String getAbsPathOf(String resFile) {
        return getFile(resFile).getAbsolutePath();
    }

    public static Path asPath(String resFile) {
        return getFile(resFile).toPath();
    }

    private static File getFile(String path) {
        URL url = Resources.class.getClassLoader().getResource(path);
        File file;
        try {
            file = new File(url.toURI());
        } catch (URISyntaxException e) {
            file = new File(url.getPath());
        } catch (NullPointerException e) {
            throw new FileNotFoundExceptionUnchecked("File not found in resources: " + path, e);
        }

        return file;
    }

}
