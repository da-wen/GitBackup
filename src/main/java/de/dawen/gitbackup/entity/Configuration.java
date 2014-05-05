package de.dawen.gitbackup.entity;

import java.util.List;

/**
 * Created by dwendlandt on 21/04/14.
 */
public class Configuration {

    /**
     * log level for the app
     */
    private String logLevel;

    /**
     * enables console output
     */
    private Boolean consoleOutput;

    /**
     * peth where the log are located
     */
    private String logPath;

    /**
     * target dir, where all the packed files are
     */
    private String backupDir;

    /**
     * the temporary dir, where every repository wille be cloned to
     */
    private String tmpDir;

    /**
     * archive formats
     */
    private List<String> archives;

    /**
     * all repositories
     */
    private List<Repository> repositories;


    /**
     * Getter for backupDir
     *
     * @return String
     */
    public String getBackupDir() {
        return backupDir;
    }

    /**
     * Setter for backupDir
     *
     * @param backupDir String
     */
    public void setBackupDir(String backupDir) {
        this.backupDir = backupDir;
    }

    /**
     * getter for respositories list
     *
     * @return List
     */
    public List<Repository> getRepositories() {
        return repositories;
    }

    /**
     * Setter for repositories list
     *
     * @param repositories List<Repository>
     */
    public void setRepositories(List<Repository> repositories) {
        this.repositories = repositories;
    }

    /**
     * Getter for tmpDir
     *
     * @return String
     */
    public String getTmpDir() {
        return tmpDir;
    }

    /**
     * Setter for tmpDir
     *
     * @param tmpDir String
     */
    public void setTmpDir(String tmpDir) {
        this.tmpDir = tmpDir;
    }

    public String getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(String logLevel) {
        this.logLevel = logLevel;
    }

    public String getLogPath() {
        return logPath;
    }

    public void setLogPath(String logPath) {
        this.logPath = logPath;
    }

    public Boolean getConsoleOutput() {
        return consoleOutput;
    }

    public void setConsoleOutput(Boolean consoleOutput) {
        this.consoleOutput = consoleOutput;
    }

    public List<String> getArchives() {
        return archives;
    }

    public void setArchives(List<String> archives) {
        this.archives = archives;
    }
}
