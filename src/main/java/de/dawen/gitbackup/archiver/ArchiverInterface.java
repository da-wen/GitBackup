package de.dawen.gitbackup.archiver;

import java.io.File;

/**
 * Interface for creating a archiver
 */
public interface ArchiverInterface {

    /**
     * Creates an archive of a repository
     */
    public void create();

}
