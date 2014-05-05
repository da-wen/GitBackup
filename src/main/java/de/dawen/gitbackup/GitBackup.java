package de.dawen.gitbackup;

import de.dawen.gitbackup.archiver.ArchiverFactory;
import de.dawen.gitbackup.archiver.ArchiverInterface;
import de.dawen.gitbackup.archiver.ZipArchiver;
import de.dawen.gitbackup.repository.GitClient;
import de.dawen.gitbackup.entity.Configuration;
import de.dawen.gitbackup.entity.Repository;
import de.dawen.gitbackup.repository.RepositoryClientInterface;
import org.apache.log4j.*;

import java.io.File;
import java.util.Date;

/**
 * GitBackup processes all given repositories and creates backups, by given yaml config
 */
public class GitBackup {

    /**
     * Application configuration object. Read from yaml file
     */
    private Configuration configuration;

    /**
     * Global date, when started the backup process
     */
    private Date date;

    /**
     * Logger that holds a file and a console appender
     */
    private Logger logger = Logger.getRootLogger();

    /**
     * Constructor for GitBackup
     *
     * @param config Configuration
     */
    public GitBackup(Configuration config) {
        configuration = config;
        date = new Date();
        configureLogger();
    }

    /**
     * Goes through all configured repositories and starts the backup process
     */
    public void run(){
        logger.info("Starting with the backups");

        for(int i = 0; i < configuration.getRepositories().size(); i++) {
            Repository repository = configuration.getRepositories().get(i);
            GitClient client = new GitClient(logger, repository, getTmpDir(repository));
            processRespository(client);
        }
    }

    /**
     * Starts cloning the repository and creates archives that are set by config
     *
     * @param client RepositoryClientInterface
     */
    private void processRespository(RepositoryClientInterface client) {
        //clone repo
        if(client.cloneRepo()) {
            File backupDir = getBackupDir(client.getRepository());
            File tmpDir = client.getPath();
            String name = client.getRepository().getName() + "_master";

            for(String archiveType: configuration.getArchives()) {
                ArchiverInterface archive = ArchiverFactory.create(archiveType, logger, name, date, tmpDir, backupDir);
                archive.create();
            }

            //remove repository when finished backup
            FileUtils.delete(tmpDir);

            logger.info("finished repository backup " + client.getRepository().getName());
        }
    }


    /**
     * gets the fill path of the backup dir of a repository
     *
     * @param respository Repository
     * @return File
     */
    private File getBackupDir(Repository respository) {
        return new File(configuration.getBackupDir() + File.separator + respository.getName());
    }

    /**
     * gets the full path of tmp dir, where to clone into
     *
     * @param repository Repository
     * @return File
     */
    private File getTmpDir(Repository repository) {
        return new File(configuration.getTmpDir() + File.separator + repository.getName());
    }

    /**
     * configures the logger, based on configuration
     */
    private void configureLogger() {
        try {
            //SimpleLayout layout = new SimpleLayout();
            PatternLayout layout = new PatternLayout( "%d{ISO8601} %-5p [%t] %c: %m%n" );
            //console logger
            if(configuration.getConsoleOutput()) {
                ConsoleAppender consoleAppender = new ConsoleAppender( layout );
                logger.addAppender( consoleAppender );
            }
            //file logger
            if(checkLogPath(configuration.getLogPath())) {
                DailyRollingFileAppender fileAppender =
                        new DailyRollingFileAppender( layout, configuration.getLogPath() + File.separator + "gitbackup.log", "'.'yyyy-MM-dd_HH-mm" );

                logger.addAppender(fileAppender);
            }

            logger.setLevel( Level.toLevel(configuration.getLogLevel()) );
        } catch( Exception ex ) {
            System.out.println("can not configure logger" + ex.getMessage());
        }
    }

    /**
     * checks if log directory exists. Creates the dir if possible and checks if dir is writable
     *
     * @param logPath String
     * @return Boolean
     */
    private static Boolean checkLogPath(String logPath) {
        File path = new File(logPath);

        if(!path.exists()) {
            return path.mkdirs();
        }

        if(!path.canWrite()) {
            return false;
        }

        return true;
    }

}

