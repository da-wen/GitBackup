package de.dawen.gitbackup.archiver;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.log4j.Logger;
import java.io.*;
import java.util.Date;
import java.util.zip.GZIPOutputStream;

/**
 * GzipArchiver creates a gzip backup of a repository
 */
public class GzipArchiver extends AbstractArchiver {

    /**
     * Archive type ending
     */
    private static String fileEnding = "tar.gz";

    /**
     * Constructor for TarArchiver
     * @param logger Logger
     * @param name String
     * @param backupDate Date
     * @param repoPath File
     * @param backupPath File
     */
    public GzipArchiver(Logger logger, String name, Date backupDate, File repoPath, File backupPath) {
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
        String gzip = backup.getAbsolutePath() + File.separator + name;

        TarArchiveOutputStream tos = null;
        try {
            tos = new TarArchiveOutputStream(
            new GZIPOutputStream(
            new BufferedOutputStream(new FileOutputStream(gzip))));
            // Add data to out and flush stream

            addDirToArchive(tos, repository);

            tos.close();
        } catch (IOException ioe)  {
            logger.error("can not add to gzip file. " + ioe.getMessage());
        }

    }

    /**
     * adds a directory to a gzip file, recursively
     *
     * @param tos TarArchiveOutputStream
     * @param srcFile File
     */
    private void addDirToArchive(TarArchiveOutputStream tos, File srcFile) {
        File[] files = srcFile.listFiles();

        for (File file : files) {
            if (file.isDirectory()) {
                addDirToArchive(tos, file);
                continue;
            }

            try {
                logger.debug("Adding file: " + file.getPath());

                TarArchiveEntry entry = new TarArchiveEntry(file.getPath());
                entry.setName(repository.toURI().relativize(file.toURI()).getPath());
                entry.setSize(file.length());
                tos.putArchiveEntry(entry);
                IOUtils.copy(new FileInputStream(file), tos);
                tos.closeArchiveEntry();

            } catch (IOException ioe) {
                logger.error("can not add to gzip file. " + ioe.getMessage());
            }

        }
    }

}
