// Jade recovery: recovered class name: InjectionBootstrapContext
package jade.inject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.RandomAccessFile;
import java.util.Arrays;
import java.util.regex.Pattern;

public final class InjectionBootstrapContext {
   private static final Pattern NONCE = Pattern.compile("^[0-9a-f]{32}$");
   private static final String NATIVE_NONCE_PROPERTY = "jade.inject.bootstrap.nonce.v1";
   private static final String NATIVE_ENVELOPE_PROPERTY = "jade.inject.bootstrap.envelope.v1";
   private static final String NATIVE_KEY_PROPERTY = "jade.inject.bootstrap.key.v1";
   private static final String NATIVE_NODE_PROPERTY = "jade.inject.bootstrap.node.v2";
   private static final String NATIVE_JAR_PROPERTY = "jade.inject.bootstrap.jar.v1";
   private static BufferedWriter writer;
   private static BufferedReader channelReader;
   private static RandomAccessFile channelFile;
   private static volatile String statusState = "";
   private static volatile String statusMessage = "";
   private static volatile String agentJarPath;
   private static boolean initialized;
   private static volatile String visualNonce;
   private static String[] memoryState;

   private InjectionBootstrapContext() {
   }

   public static synchronized void importMemoryState(String nonce, String envelope, String key, String node) {
      if (memoryState == null
         && nonce != null
         && NONCE.matcher(nonce).matches()
         && envelope != null
         && envelope.length() <= 262144
         && key != null
         && key.length() <= 64
         && node != null
         && node.matches("[0-9a-f]{64}")) {
         memoryState = new String[]{nonce, envelope, key, node};
      } else {
         throw new SecurityException("invalid memory bootstrap context");
      }
   }

   static synchronized void discardMemoryState() {
      if (memoryState != null) {
         Arrays.fill(memoryState, null);
         memoryState = null;
      }
   }

   public static synchronized void beginBootstrap(String nonce) throws Exception {
      visualNonce = null;
      closeChannel();
      initialized = false;
      statusState = "";
      statusMessage = "";
      System.clearProperty("jade.local.status");
      System.clearProperty("jade.local.message");
      System.setProperty("jade.local.nonce", nonce == null ? "" : nonce);
      agentJarPath = null;
      initialize(nonce);
   }

   public static synchronized void initialize(String nonce) throws Exception {
      if (!initialized) {
         if (nonce == null || !NONCE.matcher(nonce).matches()) {
            throw new SecurityException("invalid injection bootstrap nonce");
         }
         if (memoryState != null) {
            String[] pending = memoryState;
            memoryState = null;

            try {
               if (!nonce.equals(pending[0])) {
                  throw new SecurityException(
                     "memory bootstrap nonce mismatch"
                  );
               }
            } finally {
               Arrays.fill(pending, null);
            }
         }

         initialized = true;
         visualNonce = nonce;
      }
   }

   public static String visualNonce() {
      return visualNonce;
   }

   public static synchronized void report(String state, String message) {
      statusState = singleLine(state);
      statusMessage = singleLine(message);
      System.setProperty("jade.local.message", statusMessage);
      System.setProperty("jade.local.status", statusState);
      System.out.println("[Jade] " + statusState + ": " + statusMessage);
      if (writer != null) {
         try {
            writer.write("STATUS ");
            writer.write(statusState);
            writer.write(9);
            writer.write(statusMessage);
            writer.write(10);
            writer.flush();
         } catch (Exception var3) {
         }
      }
   }

   public static String currentState() {
      return statusState;
   }

   public static String currentMessage() {
      return statusMessage;
   }

   public static String currentAgentJarPath() {
      return agentJarPath;
   }

   private static void closeChannel() {
      try {
         if (writer != null) {
            writer.close();
         }
      } catch (Exception var3) {
      }

      try {
         if (channelReader != null) {
            channelReader.close();
         }
      } catch (Exception var2) {
      }

      try {
         if (channelFile != null) {
            channelFile.close();
         }
      } catch (Exception var1) {
      }

      writer = null;
      channelReader = null;
      channelFile = null;
   }

   private static String takeProperty(String key) {
      String value = System.getProperty(key);
      if (value != null) {
         System.clearProperty(key);
      }

      return value;
   }

   private static String singleLine(String value) {
      return String.valueOf(value).replace('\r', ' ').replace('\n', ' ').replace('\t', ' ');
   }
}
