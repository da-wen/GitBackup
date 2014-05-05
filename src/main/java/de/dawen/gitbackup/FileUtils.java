package de.dawen.gitbackup;

import java.io.File;

/**
 * Created by dwendlandt on 22/04/14.
 */
public class FileUtils {

    /**
     * deletes files and directories
     *
     * @param path File
     */
    public static void delete(File path) {
        if(path.exists()) {
            if (path.isDirectory()) {

                File files[] = path.listFiles();
                for (File file : files) {
                    //delete each file in folder
                    delete(file);
                }

                //delete the folder
                path.delete();

            } else {
                //delete file i it is not a folder
                path.delete();
            }
        }
    }
}
