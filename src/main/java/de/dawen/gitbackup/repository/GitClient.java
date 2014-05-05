package de.dawen.gitbackup.repository;

import de.dawen.gitbackup.FileUtils;
import de.dawen.gitbackup.entity.Repository;
import org.apache.log4j.Logger;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;
import java.io.IOException;

/**
 * GitClient is performing operations from git repositories
 */
public class GitClient implements RepositoryClientInterface {

    /**
     * temporary path where the repo will be cloned into
     */
    private File path;

    /**
     * repository entitiy with needed informations
     */
    private Repository repository;

    /**
     * Logger for writing to console and into logfile
     */
    private Logger logger;

    /**
     *
     * @param logger Logger
     * @param repo Repository
     * @param tmpPath File
     */
    public GitClient(Logger logger, Repository repo, File tmpPath) {
        path = tmpPath;
        repository = repo;
        this.logger = logger;
    }

    /**
     * Clones a repository to spcified path. Deletes path if exists.
     *
     * @return boolean
     */
    public boolean cloneRepo() {
        //delete directory if exists
        FileUtils.delete(path);

        try {
            logger.info("Cloning from " + repository.getSrc() + " to " + path);

            Git.cloneRepository()
                    .setURI(repository.getSrc())
                    .setDirectory(path)
                    .call();

        } catch (GitAPIException e) {
            logger.error("Can not clone repository (" + repository.getSrc() + ") " + e.getMessage());
            return false;
        }

        return true;
    }

    /**
     * getter for repository
     *
     * @return Repository
     */
    public Repository getRepository() {
        return repository;
    }

    /**
     * getter for tmp path
     *
     * @return File
     */
    public File getPath() {
        return path;
    }

}
