package name.zasenko.battlesnake.utils;

import org.apache.commons.lang3.SystemUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * Returns the appropriate working directory for storing application data. The result of this method is platform
 * dependant: On linux, it will return ~/applicationName, on windows, the working directory will be located in the
 * user's application data folder. For Mac OS systems, the working directory will be placed in the proper location
 * in "Library/Application Support".
 * <p/>
 * This method will also make sure that the working directory exists. When invoked, the directory and all required
 * subfolders will be created.
 * Based on https://forums.oracle.com/ords/apexds/post/where-should-my-application-save-its-data-files-2978#comment_323462173683736614484832819178946341737
 *
 * @param applicationName Name of the application, used to determine the working directory.
 * @return the appropriate working directory for storing application data.
 */
public class WorkingDirectoryManger {
    private static Logger log = LoggerFactory.getLogger(WorkingDirectoryManger.class);

    public static File getWorkingDirectory(final String applicationName) {
        final String userHome = System.getProperty("user.home", ".");
        final File workingDirectory;

        if (SystemUtils.IS_OS_LINUX || SystemUtils.IS_OS_SOLARIS) {
            workingDirectory = new File(userHome, '.' + applicationName + '/');
        } else if (SystemUtils.IS_OS_WINDOWS) {
            final String applicationData = System.getenv("APPDATA");
            if (applicationData != null) {
                workingDirectory = new File(applicationData, "." + applicationName + '/');
            }
            else {
                workingDirectory = new File(userHome, '.' + applicationName + '/');
            }

        } else if (SystemUtils.IS_OS_MAC_OSX) {
            workingDirectory = new File(userHome, "Library/Application Support/" + applicationName);
        } else {
            return new File(".");
        }

        if (!workingDirectory.exists()) {
            log.info("Creating working directory {}.", workingDirectory);
            if (!workingDirectory.mkdirs()) {
                throw new RuntimeException("The working directory could not be created: " + workingDirectory);
            }

        }
        return workingDirectory;
    }
}
