package de.dawen.gitbackup.archiver;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.log4j.Logger;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

/**
 * Created by dwendlandt on 05/05/14.
 */
public class TarArchiver extends AbstractArchiver {

    /**
     * Archive type ending
     */
    private static String fileEnding = "tar";

    /**
     * Constructor for TarArchiver
     * @param logger Logger
     * @param name String
     * @param backupDate Date
     * @param repoPath File
     * @param backupPath File
     */
    public TarArchiver(Logger logger, String name, Date backupDate, File repoPath, File backupPath) {
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
        String tarFile = backup.getAbsolutePath() + File.separator + name;

        try {
            logger.info("Creating zip file " + tarFile);
            FileOutputStream fos = new FileOutputStream(tarFile);

            TarArchiveOutputStream tos = new TarArchiveOutputStream(fos);

            addDirToArchive(tos, repository);
            tos.close();
        } catch(IOException ioe) {
            logger.error("Error creating zip file: " + ioe.getMessage());
        }
    }

    /**
     * adds a directory to a zip file, recursively
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
                logger.error("can not add to tar file. " + ioe.getMessage());
            }

        }
    }

}
