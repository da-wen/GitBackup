package de.dawen.gitbackup.repository;

import de.dawen.gitbackup.entity.Repository;

import java.io.File;

/**
 * Created by dwendlandt on 24/04/14.
 */
public interface RepositoryClientInterface {

    /**
     * clones a repository
     *
     * @return boolean
     */
    public boolean cloneRepo();

    /**
     * getter for repository
     *
     * @return Repository
     */
    public Repository getRepository();

    /**
     * getter for tmp path
     *
     * @return File
     */
    public File getPath();

}
