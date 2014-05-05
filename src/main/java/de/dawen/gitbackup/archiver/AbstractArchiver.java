package de.dawen.gitbackup.archiver;

import org.apache.log4j.Logger;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * AbstractArchiver
 */
public abstract class AbstractArchiver implements ArchiverInterface{

    /**
     * Date format that is appended to the backupfile
     */
    protected DateFormat dateFormatter= new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");

    /**
     * global date that is given for each archive
     */
    protected Date date;

    /**
     * logger that should hold a console and a file appender
     */
    protected Logger logger;

    /**
     * path where the cloned repository is located
     */
    protected File repository;

    /**
     * pathe where the backups are located
     */
    protected File backup;

    /**
     * the string that is appended to the archive name
     */
    protected String append;

    /**
     * gets formatted datetime as string
     *
     * @return String
     */
    protected String getDateTime() {
        return dateFormatter.format(date);
    }

    /**
     * Creates a backup dir. If fails it logs an error
     */
    protected void createBackupDir() {
        if(!backup.exists()) {
            if(!backup.mkdirs()) {
                logger.error("can not create backup directory (" + backup + ")");
            }
        }
    }
}
