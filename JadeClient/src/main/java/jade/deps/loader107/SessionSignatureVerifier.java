package jade.deps.loader107;

import jade.deps.eddsa.EdDSAEngine;
import jade.deps.eddsa.EdDSAPublicKey;
import jade.deps.eddsa.spec.EdDSANamedCurveTable;
import jade.deps.eddsa.spec.EdDSAParameterSpec;
import jade.deps.eddsa.spec.EdDSAPublicKeySpec;
import jade.deps.gson.JsonElement;
import jade.deps.gson.JsonObject;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Base64.Decoder;
import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

final class SessionSignatureVerifier {
   private SessionSignatureVerifier() {
   }

   static byte[] verifySignedSessionPayload(JsonObject var0, String var1) {
      byte var2 = 0;

      while (true) {
         switch (var2) {
            case 0:

               var2 = 1;
               break;
            case 1:
               var2 = 2;
               break;
            default:
               return (byte[])RecoveredMethodInterpreter.invokeRecovered(
                  "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",
                  jade.build.RecoveredHandles.resolve(SessionSignatureVerifier.class, "invokeSignatureOperation"),
                  new Object[]{var0, var1}
               );
         }
      }
   }

   private static byte[] computeHmacSha256(byte[] var0, byte[] var1, byte[] var2, int var3) throws Exception {
      byte var4 = 0;

      while (true) {
         switch (var4) {
            case 0:

               var4 = 1;
               break;
            case 1:
               var4 = 2;
               break;
            default:
               return (byte[])RecoveredMethodInterpreter.invokeRecovered(
                  "SlZNAZ0kvSkAAAAKAAAABQAAAEAAAAACAAAABZ0kvCkAAAAAAAAAAAAAAAEAAAAAAAAABZ0kvCkAAAABAAAAAQAAAAEAAAAAAAAAAp0kvRMAAAAEAAAAAp0kvTAAAAAEAAAABZ0kvCkAAAACAAAAAAAAAAEAAAAAAAAAAZ0kvXAAAAACnSS9MAAAAAEAAAAFnSS8KQAAAAMAAAAAAAAAAQAAAAAAAAAFnSS8KQAAAAQAAAADAAAAAQAAAAEAAAAFnSS8KQAAAAUAAAACAAAAAAAAAAAAAAACnSS9MAAAAAQAAAACnSS9MAAAAAAAAAAFnSS8KQAAAAYAAAACAAAAAQAAAAAAAAACnSS9EwAAAAUAAAABnSS9KAAAAAKdJL0TAAAABgAAAAKdJL0wAAAABAAAAAWdJLwpAAAABwAAAAAAAAABAAAAAAAAAAGdJL1wAAAAAp0kvTAAAAAFAAAABZ0kvCkAAAAIAAAAAAAAAAEAAAAAAAAABZ0kvCkAAAAJAAAAAwAAAAEAAAABAAAABZ0kvCkAAAAKAAAAAgAAAAAAAAAAAAAAAp0kvTAAAAAEAAAAAp0kvTAAAAACAAAABZ0kvCkAAAALAAAAAgAAAAAAAAAAAAAAAp0kvTAAAAAEAAAAAZ0kvS0AAAAFnSS8KQAAAAwAAAACAAAAAAAAAAAAAAACnSS9MAAAAAQAAAAFnSS8KQAAAA0AAAABAAAAAQAAAAAAAAACnSS9EwAAAAYAAAACnSS9PAAAAAMAAAAFnSS8KQAAAA4AAAABAAAAAQAAAAAAAAACnSS9EwAAAAcAAAACnSS9MAAAAAYAAAABnSS9KgAAAAKdJL0wAAAABwAAAAGdJL0qAAAAAp0kvTwAAAADAAAABZ0kvCkAAAAPAAAABQAAAAAAAAAAAAAAAp0kvTAAAAAHAAAAAp0kvRMAAAAIAAAAAp0kvTAAAAAFAAAAAZ0kvSoAAAAFnSS8KQAAABAAAAACAAAAAAAAAAAAAAACnSS9MAAAAAYAAAACnSS97wAAADMAAAACnSS9MAAAAAYAAAABnSS9KgAAAAWdJLwpAAAAEQAAAAIAAAAAAAAAAAAAAAKdJL0wAAAACAAAAAGdJL2ZAAAAAp0kvRMAAAAJAAAAAp0kvTAAAAAFAAAAAZ0kvSoAAAAFnSS8KQAAABIAAAACAAAAAAAAAAAAAAACnSS9MAAAAAYAAAACnSS97wAAAD4AAAACnSS9MAAAAAYAAAABnSS9KgAAAAWdJLwpAAAAEwAAAAIAAAAAAAAAAAAAAAKdJL0wAAAACQAAAAGdJL2WAAAAEAAAACsAAAA1/////wAAADUAAAA2AAAANf////8=",
                  jade.build.RecoveredHandles.resolve(SessionSignatureVerifier.class, "invokeHmacOperation"),
                  new Object[]{var0, var1, var2, var3}
               );
         }
      }
   }

   private static Object invokeSignatureOperation(int var0, Object[] var1) throws Throwable {
      byte var2 = 0;

      while (true) {
         switch (var2) {
            case 0:

               var2 = 1;
               break;
            case 1:
               var2 = 2;
               break;
            default:
               switch (var0) {
                  case 0:
                     return "signature";
                  case 1:
                     return Integer.valueOf((((JsonObject)var1[0]).has((String)var1[1])) ? 1 : 0);
                  case 2:
                     Arrays.fill((byte[])var1[0], (byte)((Number)var1[1]).intValue());
                     return null;
                  case 3:
                     Arrays.fill((byte[])var1[0], (byte)((Number)var1[1]).intValue());
                     return null;
                  case 4:
                     Arrays.fill((byte[])var1[0], (byte)((Number)var1[1]).intValue());
                     return null;
                  case 5:
                     Arrays.fill((byte[])var1[0], (byte)((Number)var1[1]).intValue());
                     return null;
                  case 6:
                     return "signature";
                  case 7:
                     return ((JsonObject)var1[0]).get((String)var1[1]);
                  case 8:
                     return ((JsonElement)var1[0]).getAsString();
                  case 9:
                     return Base64.getDecoder();
                  case 10:
                     return ((Decoder)var1[0]).decode((String)var1[1]);
                  case 11:
                     return JsonCanonicalizer.computeSignatureBytes((JsonObject)var1[0]);
                  case 12:
                     return "Ed25519";
                  case 13:
                     return EdDSANamedCurveTable.getByName((String)var1[0]);
                  case 14:
                     return RecoveredMethodInterpreter.initializeClass(EdDSAPublicKeySpec.class);
                  case 15:
                     return ServerPublicKeyProvider.getPublicKeyBytes();
                  case 16:
                     return new EdDSAPublicKeySpec((byte[])var1[1], (EdDSAParameterSpec)var1[2]);
                  case 17:
                     return RecoveredMethodInterpreter.initializeClass(EdDSAPublicKey.class);
                  case 18:
                     return new EdDSAPublicKey((EdDSAPublicKeySpec)var1[1]);
                  case 19:
                     return RecoveredMethodInterpreter.initializeClass(EdDSAEngine.class);
                  case 20:
                     return ((EdDSAParameterSpec)var1[0]).getHashAlgorithm();
                  case 21:
                     return MessageDigest.getInstance((String)var1[0]);
                  case 22:
                     return new EdDSAEngine((MessageDigest)var1[1]);
                  case 23:
                     ((EdDSAEngine)var1[0]).initVerify((PublicKey)var1[1]);
                     return null;
                  case 24:
                     ((EdDSAEngine)var1[0]).update((byte[])var1[1]);
                     return null;
                  case 25:
                     return Integer.valueOf((((EdDSAEngine)var1[0]).verify((byte[])var1[1])) ? 1 : 0);
                  case 26:
                     Arrays.fill((byte[])var1[0], (byte)((Number)var1[1]).intValue());
                     return null;
                  case 27:
                     Arrays.fill((byte[])var1[0], (byte)((Number)var1[1]).intValue());
                     return null;
                  case 28:
                     Arrays.fill((byte[])var1[0], (byte)((Number)var1[1]).intValue());
                     return null;
                  case 29:
                     Arrays.fill((byte[])var1[0], (byte)((Number)var1[1]).intValue());
                     return null;
                  case 30:
                     return StandardCharsets.UTF_8;
                  case 31:
                     return ((String)var1[0]).getBytes((Charset)var1[1]);
                  case 32:
                     return "jade-session-v1";
                  case 33:
                     return StandardCharsets.UTF_8;
                  case 34:
                     return ((String)var1[0]).getBytes((Charset)var1[1]);
                  case 35:
                     return computeHmacSha256((byte[])var1[0], (byte[])var1[1], (byte[])var1[2], ((Number)var1[3]).intValue());
                  case 36:
                     Arrays.fill((byte[])var1[0], (byte)((Number)var1[1]).intValue());
                     return null;
                  case 37:
                     Arrays.fill((byte[])var1[0], (byte)((Number)var1[1]).intValue());
                     return null;
                  case 38:
                     Arrays.fill((byte[])var1[0], (byte)((Number)var1[1]).intValue());
                     return null;
                  case 39:
                     Arrays.fill((byte[])var1[0], (byte)((Number)var1[1]).intValue());
                     return null;
                  case 40:
                     Arrays.fill((byte[])var1[0], (byte)((Number)var1[1]).intValue());
                     return null;
                  case 41:
                     Arrays.fill((byte[])var1[0], (byte)((Number)var1[1]).intValue());
                     return null;
                  case 42:
                     Arrays.fill((byte[])var1[0], (byte)((Number)var1[1]).intValue());
                     return null;
                  case 43:
                     Arrays.fill((byte[])var1[0], (byte)((Number)var1[1]).intValue());
                     return null;
                  case 44:
                     Arrays.fill((byte[])var1[0], (byte)((Number)var1[1]).intValue());
                     return null;
                  case 45:
                     Arrays.fill((byte[])var1[0], (byte)((Number)var1[1]).intValue());
                     return null;
                  case 46:
                     Arrays.fill((byte[])var1[0], (byte)((Number)var1[1]).intValue());
                     return null;
                  case 47:
                     Arrays.fill((byte[])var1[0], (byte)((Number)var1[1]).intValue());
                     return null;
                  case 48:
                     return Integer.valueOf((var1[0] instanceof Exception) ? 1 : 0);
                  case 49:
                     return Integer.valueOf((var1[0] instanceof Exception) ? 1 : 0);
                  case 50:
                     return Integer.valueOf((var1[0] instanceof Exception) ? 1 : 0);
                  default:
                     throw new IllegalArgumentException();
               }
         }
      }
   }

   private static Object invokeHmacOperation(int var0, Object[] var1) throws Throwable {
      byte var2 = 0;

      while (true) {
         switch (var2) {
            case 0:

               var2 = 1;
               break;
            case 1:
               var2 = 2;
               break;
            default:
               switch (var0) {
                  case 0:
                     return "HmacSHA256";
                  case 1:
                     return Mac.getInstance((String)var1[0]);
                  case 2:
                     return RecoveredMethodInterpreter.initializeClass(SecretKeySpec.class);
                  case 3:
                     return "HmacSHA256";
                  case 4:
                     return new SecretKeySpec((byte[])var1[1], (String)var1[2]);
                  case 5:
                     ((Mac)var1[0]).init((Key)var1[1]);
                     return null;
                  case 6:
                     return ((Mac)var1[0]).doFinal((byte[])var1[1]);
                  case 7:
                     return RecoveredMethodInterpreter.initializeClass(SecretKeySpec.class);
                  case 8:
                     return "HmacSHA256";
                  case 9:
                     return new SecretKeySpec((byte[])var1[1], (String)var1[2]);
                  case 10:
                     ((Mac)var1[0]).init((Key)var1[1]);
                     return null;
                  case 11:
                     ((Mac)var1[0]).update((byte[])var1[1]);
                     return null;
                  case 12:
                     ((Mac)var1[0]).update((byte)((Number)var1[1]).intValue());
                     return null;
                  case 13:
                     return ((Mac)var1[0]).doFinal();
                  case 14:
                     return new byte[((Number)var1[0]).intValue()];
                  case 15:
                     System.arraycopy(var1[0], ((Number)var1[1]).intValue(), var1[2], ((Number)var1[3]).intValue(), ((Number)var1[4]).intValue());
                     return null;
                  case 16:
                     Arrays.fill((byte[])var1[0], (byte)((Number)var1[1]).intValue());
                     return null;
                  case 17:
                     Arrays.fill((byte[])var1[0], (byte)((Number)var1[1]).intValue());
                     return null;
                  case 18:
                     Arrays.fill((byte[])var1[0], (byte)((Number)var1[1]).intValue());
                     return null;
                  case 19:
                     Arrays.fill((byte[])var1[0], (byte)((Number)var1[1]).intValue());
                     return null;
                  default:
                     throw new IllegalArgumentException();
               }
         }
      }
   }
}
