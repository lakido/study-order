package org.study.data.init;

import java.io.File;

public class CheckFolderForDataBase {
    public static void checkFolder() {
        File file = new File("DataBaseSource");
        if (!file.exists()) {
            file.mkdir();
        }
    }
}
