package com.github.dima.kiev.hyberexcel.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class TestUtils {

    public static String createTempFileWithTemplate(String templateFileNameResource) {

        Path tmpFilePath = null;
        try {
            tmpFilePath = Files.createTempFile("hyberexcel-" + UUID.randomUUID().toString(), ".xlsx");
            Files.copy(Resources.asPath(templateFileNameResource), tmpFilePath, StandardCopyOption.REPLACE_EXISTING);
            new File(tmpFilePath.toString()).deleteOnExit();
        } catch (IOException e) {
            throw new RuntimeException(e); // todo: own exception FileCreationError
        }
        return tmpFilePath.toString();
    }

}
