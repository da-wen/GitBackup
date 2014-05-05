package de.dawen.gitbackup;


import de.dawen.gitbackup.entity.Configuration;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

/**
 * Start the app
 *
 */
public class App
{

    /**
     * it is my first java project. Have to learn how to create dic an more
     *
     * @param args
     * @throws FileNotFoundException
     */
    public static void main( String[] args ) throws FileNotFoundException {

        if(0 == args.length) {
            throw new IllegalArgumentException("no arguments given");
        }

        //read config file an parse yaml
        try {
            InputStream input = new FileInputStream(new File(args[0]));
            Yaml yaml = new Yaml(new Constructor(Configuration.class));
            Configuration config = (Configuration) yaml.load(input);

            //create git backup instance
            GitBackup gitbackup = new GitBackup(config);
            //kick it
            gitbackup.run();

        } catch (FileNotFoundException e) {
            System.out.println("the given configuration file was not found");
        }





    }


}
