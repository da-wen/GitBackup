package de.dawen.gitbackup.archiver;

import org.apache.log4j.Logger;

import java.io.*;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * ZipArchiver creates a zip backup of a repository
 */
public class ZipArchiver extends AbstractArchiver {

    /**
     * Archive type ending
     */
    private static String fileEnding = "zip";

    public ZipArchiver(Logger logger, String name, Date backupDate, File repoPath, File backupPath) {
        date = backupDate;
        repository = repoPath;
        backup = backupPath;
        append = name;
        this.logger = logger;
    }

    @Override
    public void create() {
        createBackupDir();

        String name = append + "_" + getDateTime() + "." + fileEnding;
        String zipFile = backup.getAbsolutePath() + File.separator + name;

        try {
            logger.info("Creating zip file " + zipFile);
            FileOutputStream fos = new FileOutputStream(zipFile);
            ZipOutputStream zos = new ZipOutputStream(fos);
            addDirToArchive(zos, repository);
            zos.close();
        } catch(IOException ioe) {
            logger.error("Error creating zip file: " + ioe.getMessage());
        }
    }

    /**
     * adds a directory to a zip file, recursively
     *
     * @param zos ZipOutputStream
     * @param srcFile File
     */
    private void addDirToArchive(ZipOutputStream zos, File srcFile) {
        File[] files = srcFile.listFiles();

        for (File file : files) {
            if (file.isDirectory()) {
                addDirToArchive(zos, file);
                continue;
            }

            try {
                logger.debug("Adding file: " + file.getPath());
                // create byte buffer
                byte[] buffer = new byte[1024];

                FileInputStream fis = new FileInputStream(file);

                zos.putNextEntry(new ZipEntry(repository.toURI().relativize(file.toURI()).getPath()));

                int length;

                while ((length = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, length);
                }

                zos.closeEntry();

                // close the InputStream
                fis.close();

            } catch (IOException ioe) {
                logger.error("can not add to zip file. " + ioe.getMessage());
            }

        }
    }

}
