GitBackup
=========

GitBackup is a simple app for backing up all your git repositories.
All you need is access to the repositories (by deploykey or priv user key for example) and a configuration file in yaml format.


How to compile
==============
This project is build with maven. Please install maven first, before you run the package command from within the cloned sources
```
mvn package
```

*the jar file are located in the target folder*


How to use
==========
There are two jar files in the target folder. If you dont want to check the dependencies, use the -with-dependencies.jat file

The only parameter that is required is the configuration yaml file

```
java -jar /path/to/jarfile/gitbackup-1.0-SNAPSHOT-jar-with-dependencies.jar /path/to/config.yml
```

(you can move the jar file and rename it)


Configuration example
=====================
```
### Logging ###

# possible log levels: ALL, INFO, DEBUG, ERROR
logLevel: ALL

# show log output in console. Possible values(true, false)
consoleOutput: true

# creates archives of given types. Possible types: zip, tar, gzip
archives: [zip, tar, gzip]


### Directories

#path where the logfiles go into
logPath: /tmp/gitbackup/logs

# path where your backups will be stored
backupDir: /tmp/gitbackup/backups

#path where the repositories will be cloned into
tmpDir: /tmp/gitbackup/tmp


### Repository configurations

repositories:
    - src: https://github.com/Seldaek/monolog.git
      name: monolog
    - src: https://github.com/da-wen/PhpRedisBundle.git
      name: PhpRedisBundle
```

ToDo
====
- time tracking in log and console output