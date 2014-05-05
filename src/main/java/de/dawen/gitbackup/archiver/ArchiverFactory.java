package de.dawen.gitbackup.archiver;

import org.apache.log4j.Logger;

import java.io.File;
import java.util.Date;

/**
 * AbstractArchiver holds shared vars and methods
 */
public class ArchiverFactory {

    /**
     * creates the right logger based on the archive type
     *
     * @param archiveType String
     * @param logger Logger
     * @param name String
     * @param backupDate Date
     * @param repoPath File
     * @param backupPath File
     * @return
     */
    public static ArchiverInterface create(String archiveType,
                                           Logger logger,
                                           String name,
                                           Date backupDate,
                                           File repoPath,
                                           File backupPath) {

        switch(archiveType) {
            case "tar":
                return new TarArchiver(logger, name, backupDate, repoPath, backupPath);

            case "gzip":
                return new GzipArchiver(logger, name, backupDate, repoPath, backupPath);

            case "zip":
                return new ZipArchiver(logger, name, backupDate, repoPath, backupPath);

            default:
                logger.warn("archive type [" + archiveType + "] is not recognized. Used zip as default");
                return new ZipArchiver(logger, name, backupDate, repoPath, backupPath);

        }
    }

}
