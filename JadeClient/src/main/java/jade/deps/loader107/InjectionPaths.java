// Jade recovery: recovered class name: InjectionPaths
package jade.deps.loader107;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public final class InjectionPaths {
   private static final String PROPERTY = "jade.injection.dataDir";

   private InjectionPaths() {
   }

   public static synchronized File dataDirectory(File minecraftDataDirectory) {
      String configured = System.getProperty("jade.injection.dataDir");
      File directory = configured != null && !configured.trim().isEmpty()
         ? new File(configured)
         : new File(
            minecraftDataDirectory == null ? new File(".") : minecraftDataDirectory,
            "jade"
         );
      if (!directory.isDirectory()) {
         directory.mkdirs();
      }

      if (configured != null && !configured.trim().isEmpty() && minecraftDataDirectory != null) {
         migrateMissingFiles(new File(minecraftDataDirectory, "jade"), directory);
      }

      return directory;
   }

   public static void configureForAgent(File jar) {
      File directory = appDataDirectory();
      String profile = System.getenv("USERPROFILE");
      File root = profile != null && !profile.trim().isEmpty()
         ? new File(profile, "Jade")
         : new File(
            System.getProperty("user.home"),
            "Jade"
         );
      if (!directory.isDirectory()) {
         directory.mkdirs();
      }

      migrateMissingFiles(new File(root, "data"), directory);
      System.setProperty("jade.injection.dataDir", directory.getAbsolutePath());
   }

   private static File appDataDirectory() {
      String appData = System.getenv("APPDATA");
      if (appData != null && !appData.trim().isEmpty()) {
         return new File(appData, ".jade");
      } else {
         String userHome = System.getProperty("user.home");
         return new File(
            userHome != null && !userHome.trim().isEmpty()
               ? new File(userHome)
               : new File("."),
            ".jade"
         );
      }
   }

   private static void migrateMissingFiles(File source, File destination) {
      if (source != null && destination != null && source.isDirectory()) {
         try {
            if (source.getCanonicalFile().equals(destination.getCanonicalFile())) {
               return;
            }
         } catch (IOException var12) {
            return;
         }

         File[] children = source.listFiles();
         if (children != null) {
            if (destination.isDirectory() || destination.mkdirs()) {
               for (File child : children) {
                  File target = new File(destination, child.getName());
                  if (child.isDirectory()) {
                     migrateMissingFiles(child, target);
                  } else if (child.isFile() && !target.exists()) {
                     try {
                        Files.copy(child.toPath(), target.toPath(), StandardCopyOption.COPY_ATTRIBUTES);
                     } catch (IOException var11) {
                        try {
                           Files.copy(child.toPath(), target.toPath());
                        } catch (IOException var10) {
                        }
                     }
                  }
               }
            }
         }
      }
   }
}
