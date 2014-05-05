package de.dawen.gitbackup.entity;

/**
 * Created by dwendlandt on 21/04/14.
 */
public class Repository {

    /**
     * repository source path
     */
    private String src;

    /**
     * name that is used as prefix for archive name
     */
    private String name;

    /**
     * Getter for name
     *
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for name
     *
     * @param name String
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the source path
     *
     * @return String
     */
    public String getSrc() {
        return src;
    }

    /**
     * Setter for the source path
     *
     * @param src String
     */
    public void setSrc(String src) {
        this.src = src;
    }

}
