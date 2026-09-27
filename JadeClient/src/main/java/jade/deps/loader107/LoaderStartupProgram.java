package jade.deps.loader107;

import java.util.function.BooleanSupplier;

public final class LoaderStartupProgram {
   private LoaderStartupProgram() {
   }

   public static void runStartupProgram() {
      RecoveredMethodInterpreter.invokeRecovered(
         "SlZNAbW0JuQAAAAAAAAABgAAAEQAAAAAAAAAArW0JvQAAAAMAAAABbW0J+QAAAAAAAAAAQAAAAEAAAAAAAAAAbW0Jr0AAAABtbQm5wAAAAK1tCb0AAAAVwAAAAW1tCfkAAAAAQAAAAMAAAAAAAAAAAAAAAG1tCa9AAAAAbW0JuAAAAABtbQm5wAAAAW1tCfkAAAAAgAAAAMAAAAAAAAAAAAAAAG1tCa9AAAAAbW0JuEAAAABtbQm4gAAAAW1tCfkAAAAAwAAAAMAAAAAAAAAAAAAAAG1tCa9AAAAAbW0JuIAAAACtbQm9AAAADEAAAAFtbQn5AAAAAQAAAADAAAAAAAAAAAAAAABtbQmvQAAAAG1tCbjAAAAAbW0JucAAAAFtbQn5AAAAAUAAAADAAAAAAAAAAAAAAABtbQmvQAAAAG1tCbsAAAAAbW0JucAAAAFtbQn5AAAAAYAAAADAAAAAAAAAAAAAAABtbQmvQAAAAK1tCb0AAAABgAAAAK1tCb0AAAAMQAAAAW1tCfkAAAABwAAAAMAAAAAAAAAAAAAAAG1tCa9AAAAArW0JvQAAAAHAAAAAbW0JuAAAAAFtbQn5AAAAAgAAAADAAAAAAAAAAAAAAABtbQmvQAAAAK1tCb0AAAACAAAAAG1tCbnAAAABbW0J+QAAAAJAAAAAwAAAAAAAAAAAAAAAbW0Jr0AAAACtbQm9AAAAAkAAAACtbQm9AAAAG0AAAAFtbQn5AAAAAoAAAADAAAAAAAAAAAAAAABtbQmvQAAAAK1tCb0AAAACgAAAAG1tCbnAAAABbW0J+QAAAALAAAAAwAAAAAAAAAAAAAAAbW0Jr0AAAACtbQm9AAAAAsAAAABtbQm5wAAAAW1tCfkAAAADAAAAAMAAAAAAAAAAAAAAAG1tCbhAAAABbW0J+QAAAANAAAAAQAAAAEAAAAAAAAAAbW0Jr0AAAABtbQm5wAAAAW1tCfkAAAADgAAAAAAAAABAAAAAAAAAAW1tCfkAAAADwAAAAMAAAAAAAAAAAAAAAG1tCa9AAAAAbW0JuAAAAAFtbQn5AAAABAAAAAAAAAAAQAAAAAAAAAFtbQn5AAAABEAAAADAAAAAAAAAAAAAAABtbQm4AAAAAW1tCfkAAAAEgAAAAEAAAABAAAAAAAAAAG1tCa9AAAAAbW0JucAAAAFtbQn5AAAABMAAAAAAAAAAQAAAAAAAAAFtbQn5AAAABQAAAADAAAAAAAAAAAAAAAFtbQn5AAAABUAAAADAAAAAAAAAAAAAAABtbQmVQ==",
         jade.build.RecoveredHandles.resolve(LoaderStartupProgram.class, "invokeRunStartupProgram"),
         new Object[0]
      );
   }

   private static boolean hasForgeLaunchHandler() {
      return (((Number)RecoveredMethodInterpreter.invokeRecovered(
            "SlZNAehhHd8AAAACAAAAAwAAAA4AAAABAAAABehhHN8AAAAAAAAAAAAAAAEAAAAAAAAABehhHN8AAAABAAAAAQAAAAEAAAAAAAAAAuhhHeUAAAAAAAAABehhHN8AAAACAAAAAAAAAAEAAAAAAAAAAehhHdwAAAAC6GEdxgAAAAAAAAAF6GEc3wAAAAMAAAADAAAAAQAAAAAAAAAB6GEdiAAAAALoYR14AAAADAAAAALoYR3lAAAAAQAAAAHoYR3cAAAAAehhHXMAAAAB6GEd2wAAAAHoYR1zAAAAAwAAAAgAAAAJAAAABA==",
            jade.build.RecoveredHandles.resolve(LoaderStartupProgram.class, "invokeCheckForgeLaunchClass"),
            new Object[0]
         )).intValue() != 0);
   }

   private static void installLocalLoader() {
      RecoveredMethodInterpreter.invokeRecovered(
         "SlZNAWiYKiAAAAAAAAAAAQAAAAQAAAAAAAAABWiYKyAAAAAAAAAAAAAAAAEAAAAAAAAABWiYKyAAAAABAAAAAQAAAAEAAAAAAAAABWiYKyAAAAACAAAAAQAAAAAAAAAAAAAAAWiYKpE=",
         jade.build.RecoveredHandles.resolve(LoaderStartupProgram.class, "invokeInstallLocalLoader"),
         new Object[0]
      );
   }

   private static Object invokeRunStartupProgram(int var0, Object[] var1) throws Throwable {
      switch (var0) {
         case 0:
            return new int[((Number)var1[0]).intValue()];
         case 1:
            ((int[])var1[0])[((Number)var1[1]).intValue()] = ((Number)var1[2]).intValue();
            return null;
         case 2:
            ((int[])var1[0])[((Number)var1[1]).intValue()] = ((Number)var1[2]).intValue();
            return null;
         case 3:
            ((int[])var1[0])[((Number)var1[1]).intValue()] = ((Number)var1[2]).intValue();
            return null;
         case 4:
            ((int[])var1[0])[((Number)var1[1]).intValue()] = ((Number)var1[2]).intValue();
            return null;
         case 5:
            ((int[])var1[0])[((Number)var1[1]).intValue()] = ((Number)var1[2]).intValue();
            return null;
         case 6:
            ((int[])var1[0])[((Number)var1[1]).intValue()] = ((Number)var1[2]).intValue();
            return null;
         case 7:
            ((int[])var1[0])[((Number)var1[1]).intValue()] = ((Number)var1[2]).intValue();
            return null;
         case 8:
            ((int[])var1[0])[((Number)var1[1]).intValue()] = ((Number)var1[2]).intValue();
            return null;
         case 9:
            ((int[])var1[0])[((Number)var1[1]).intValue()] = ((Number)var1[2]).intValue();
            return null;
         case 10:
            ((int[])var1[0])[((Number)var1[1]).intValue()] = ((Number)var1[2]).intValue();
            return null;
         case 11:
            ((int[])var1[0])[((Number)var1[1]).intValue()] = ((Number)var1[2]).intValue();
            return null;
         case 12:
            ((int[])var1[0])[((Number)var1[1]).intValue()] = ((Number)var1[2]).intValue();
            return null;
         case 13:
            return new Runnable[((Number)var1[0]).intValue()];
         case 14:
            return (Runnable) LoaderStartupProgram::installLocalLoader;
         case 15:
            ((Object[])var1[0])[((Number)var1[1]).intValue()] = var1[2];
            return null;
         case 16:
            return (Runnable) (() -> {
            });
         case 17:
            ((Object[])var1[0])[((Number)var1[1]).intValue()] = var1[2];
            return null;
         case 18:
            return new BooleanSupplier[((Number)var1[0]).intValue()];
         case 19:
            return (Runnable) LoaderStartupProgram::hasForgeLaunchHandler;
         case 20:
            ((Object[])var1[0])[((Number)var1[1]).intValue()] = var1[2];
            return null;
         case 21:
            StartupProgramRunner.runStartupProgram((int[])var1[0], (Runnable[])var1[1], (BooleanSupplier[])var1[2]);
            return null;
         default:
            throw new IllegalArgumentException();
      }
   }

   private static Object invokeCheckForgeLaunchClass(int var0, Object[] var1) throws Throwable {
      switch (var0) {
         case 0:
            return LoaderStartupProgram.class;
         case 1:
            return ((Class)var1[0]).getClassLoader();
         case 2:
            return "net.minecraftforge.fml.relauncher.FMLLaunchHandler";
         case 3:
            return Class.forName((String)var1[0], (((Number)var1[1]).intValue() != 0), (ClassLoader)var1[2]);
         case 4:
            return Integer.valueOf((var1[0] instanceof ClassNotFoundException) ? 1 : 0);
         default:
            throw new IllegalArgumentException();
      }
   }

   private static Object invokeInstallLocalLoader(int var0, Object[] var1) throws Throwable {
      switch (var0) {
         case 0:
            return LoaderStartupProgram.class;
         case 1:
            return ((Class)var1[0]).getClassLoader();
         case 2:
            LocalCoreLoader.bootLocalCore((ClassLoader)var1[0]);
            return null;
         default:
            throw new IllegalArgumentException();
      }
   }
}
