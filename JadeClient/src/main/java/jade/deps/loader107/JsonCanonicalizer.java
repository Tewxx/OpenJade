package jade.deps.loader107;

import jade.deps.gson.JsonArray;
import jade.deps.gson.JsonElement;
import jade.deps.gson.JsonObject;
import jade.deps.gson.JsonParser;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

final class JsonCanonicalizer {
   private JsonCanonicalizer() {
   }

   static byte[] computeSignatureBytes(JsonObject var0) {
      byte var1 = 0;

      while (true) {
         switch (var1) {
            case 0:

               var1 = 1;
               break;
            case 1:
               var1 = 2;
               break;
            default:
               return (byte[])RecoveredMethodInterpreter.invokeRecovered(
                  "SlZNAaAvfNAAAAACAAAAAgAAAA0AAAAAAAAAAqAvfMkAAAAAAAAABaAvfdAAAAAAAAAAAQAAAAEAAAAAAAAABaAvfdAAAAABAAAAAQAAAAEAAAAAAAAAAqAvfOoAAAABAAAAAqAvfMkAAAABAAAABaAvfdAAAAACAAAAAAAAAAEAAAAAAAAABaAvfdAAAAADAAAAAgAAAAEAAAAAAAAAAaAvfIcAAAACoC98yQAAAAEAAAAFoC990AAAAAQAAAABAAAAAQAAAAAAAAAFoC990AAAAAUAAAAAAAAAAQAAAAAAAAAFoC990AAAAAYAAAACAAAAAQAAAAAAAAABoC98YA==",
                  jade.build.RecoveredHandles.resolve(JsonCanonicalizer.class, "recoveredComputeSignatureBytes"),
                  new Object[]{var0}
               );
         }
      }
   }

   static JsonObject parseJsonObject(String var0) {
      byte var1 = 0;

      while (true) {
         switch (var1) {
            case 0:

               var1 = 1;
               break;
            case 1:
               var1 = 2;
               break;
            default:
               return (JsonObject)RecoveredMethodInterpreter.invokeRecovered(
                  "SlZNAaf8HTQAAAABAAAAAgAAAAcAAAAAAAAABaf8HDQAAAAAAAAAAAAAAAEAAAAAAAAAAaf8HW0AAAAFp/wcNAAAAAEAAAABAAAAAQAAAAEAAAACp/wdLQAAAAAAAAAFp/wcNAAAAAIAAAACAAAAAQAAAAAAAAAFp/wcNAAAAAMAAAABAAAAAQAAAAAAAAABp/wdhA==",
                  jade.build.RecoveredHandles.resolve(JsonCanonicalizer.class, "recoveredParseJsonObject"),
                  new Object[]{var0}
               );
         }
      }
   }

   private static JsonElement canonicalizeElement(JsonElement var0) {
      byte var1 = 0;

      while (true) {
         switch (var1) {
            case 0:

               var1 = 1;
               break;
            case 1:
               var1 = 2;
               break;
            default:
               return (JsonElement)RecoveredMethodInterpreter.invokeRecovered(
                  "SlZNAfWM88wAAAAGAAAABAAAAFEAAAAAAAAAAvWM89UAAAAAAAAABfWM8swAAAAAAAAAAQAAAAEAAAAAAAAAAvWM81UAAAA1AAAAAvWM89UAAAAAAAAABfWM8swAAAABAAAAAQAAAAEAAAAAAAAAAvWM8/YAAAABAAAABfWM8swAAAACAAAAAAAAAAEAAAAAAAAAAfWM85UAAAAF9YzyzAAAAAMAAAABAAAAAQAAAAEAAAAC9Yzz9gAAAAIAAAAC9Yzz1QAAAAEAAAAF9YzyzAAAAAQAAAABAAAAAQAAAAAAAAAF9YzyzAAAAAUAAAABAAAAAQAAAAAAAAAC9Yzz9gAAAAMAAAAC9Yzz1QAAAAMAAAAF9YzyzAAAAAYAAAABAAAAAQAAAAAAAAAC9YzzVQAAABsAAAAC9Yzz1QAAAAMAAAAF9YzyzAAAAAcAAAABAAAAAQAAAAAAAAAF9YzyzAAAAAgAAAABAAAAAQAAAAAAAAAC9Yzz9gAAAAQAAAAC9Yzz1QAAAAIAAAAC9Yzz1QAAAAQAAAAF9YzyzAAAAAkAAAABAAAAAQAAAAAAAAAF9YzyzAAAAAoAAAACAAAAAQAAAAAAAAAB9YzzmwAAAAL1jPNrAAAADgAAAAL1jPPVAAAAAgAAAAX1jPLMAAAACwAAAAEAAAAAAAAAAAAAAAX1jPLMAAAADAAAAAAAAAABAAAAAAAAAAH1jPOVAAAABfWM8swAAAANAAAAAQAAAAEAAAABAAAAAvWM8/YAAAADAAAAAvWM89UAAAACAAAABfWM8swAAAAOAAAAAQAAAAEAAAAAAAAAAvWM8/YAAAAEAAAAAvWM89UAAAAEAAAABfWM8swAAAAPAAAAAQAAAAEAAAAAAAAAAvWM81UAAAAzAAAAAvWM89UAAAAEAAAABfWM8swAAAAQAAAAAQAAAAEAAAAAAAAABfWM8swAAAARAAAAAQAAAAEAAAAAAAAAAvWM8/YAAAAFAAAAAvWM89UAAAADAAAAAvWM89UAAAAFAAAAAvWM89UAAAABAAAAAvWM89UAAAAFAAAABfWM8swAAAASAAAAAgAAAAEAAAAAAAAABfWM8swAAAATAAAAAQAAAAEAAAAAAAAABfWM8swAAAAUAAAAAwAAAAAAAAAAAAAAAvWM82sAAAAkAAAAAvWM89UAAAADAAAAAfWM83wAAAAC9Yzz1QAAAAAAAAAF9YzyzAAAABUAAAABAAAAAQAAAAAAAAAC9YzzVQAAAE8AAAAC9Yzz1QAAAAAAAAAF9YzyzAAAABYAAAABAAAAAQAAAAAAAAAC9Yzz9gAAAAEAAAAF9YzyzAAAABcAAAAAAAAAAQAAAAAAAAAB9YzzlQAAAAX1jPLMAAAAGAAAAAEAAAABAAAAAQAAAAL1jPP2AAAAAgAAAAH1jPPPAAAAAvWM8/oAAAADAAAAAvWM89kAAAADAAAAAvWM89UAAAABAAAABfWM8swAAAAZAAAAAQAAAAEAAAAAAAAAAvWM824AAABNAAAAAvWM89UAAAACAAAAAvWM89UAAAABAAAAAvWM89kAAAADAAAABfWM8swAAAAaAAAAAgAAAAEAAAAAAAAABfWM8swAAAAbAAAAAQAAAAEAAAAAAAAABfWM8swAAAAcAAAAAgAAAAAAAAAAAAAAA/WM80gAAAADAAAAAQAAAAL1jPNrAAAAQQAAAAL1jPPVAAAAAgAAAAH1jPN8AAAAAvWM89UAAAAAAAAAAfWM83w=",
                  jade.build.RecoveredHandles.resolve(JsonCanonicalizer.class, "recoveredCanonicalizeElement"),
                  new Object[]{var0}
               );
         }
      }
   }

   private static String serializeElement(JsonElement var0) {
      byte var1 = 0;

      while (true) {
         switch (var1) {
            case 0:

               var1 = 1;
               break;
            case 1:
               var1 = 2;
               break;
            default:
               return (String)RecoveredMethodInterpreter.invokeRecovered(
                  "SlZNAWIXD/MAAAABAAAAAQAAAAMAAAAAAAAAAmIXD+oAAAAAAAAABWIXDvMAAAAAAAAAAQAAAAEAAAAAAAAAAWIXD0M=",
                  jade.build.RecoveredHandles.resolve(JsonCanonicalizer.class, "recoveredSerializeElement"),
                  new Object[]{var0}
               );
         }
      }
   }

   private static Object recoveredComputeSignatureBytes(int var0, Object[] var1) throws Throwable {
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
                     return canonicalizeElement((JsonElement)var1[0]);
                  case 1:
                     return (JsonObject)var1[0];
                  case 2:
                     return "signature";
                  case 3:
                     return ((JsonObject)var1[0]).remove((String)var1[1]);
                  case 4:
                     return serializeElement((JsonElement)var1[0]);
                  case 5:
                     return StandardCharsets.UTF_8;
                  case 6:
                     return ((String)var1[0]).getBytes((Charset)var1[1]);
                  default:
                     throw new IllegalArgumentException();
               }
         }
      }
   }

   private static Object recoveredParseJsonObject(int var0, Object[] var1) throws Throwable {
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
                     return RecoveredMethodInterpreter.initializeClass(JsonParser.class);
                  case 1:
                     return new JsonParser();
                  case 2:
                     return ((JsonParser)var1[0]).parse((String)var1[1]);
                  case 3:
                     return ((JsonElement)var1[0]).getAsJsonObject();
                  default:
                     throw new IllegalArgumentException();
               }
         }
      }
   }

   private static Object recoveredCanonicalizeElement(int var0, Object[] var1) throws Throwable {
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
                     return Integer.valueOf((((JsonElement)var1[0]).isJsonObject()) ? 1 : 0);
                  case 1:
                     return ((JsonElement)var1[0]).getAsJsonObject();
                  case 2:
                     return RecoveredMethodInterpreter.initializeClass(ArrayList.class);
                  case 3:
                     return new ArrayList();
                  case 4:
                     return ((JsonObject)var1[0]).entrySet();
                  case 5:
                     return ((Set)var1[0]).iterator();
                  case 6:
                     return Integer.valueOf((((Iterator)var1[0]).hasNext()) ? 1 : 0);
                  case 7:
                     return ((Iterator)var1[0]).next();
                  case 8:
                     return (Entry)var1[0];
                  case 9:
                     return ((Entry)var1[0]).getKey();
                  case 10:
                     return Integer.valueOf((((List)var1[0]).add(var1[1])) ? 1 : 0);
                  case 11:
                     Collections.sort((List)var1[0]);
                     return null;
                  case 12:
                     return RecoveredMethodInterpreter.initializeClass(JsonObject.class);
                  case 13:
                     return new JsonObject();
                  case 14:
                     return ((List)var1[0]).iterator();
                  case 15:
                     return Integer.valueOf((((Iterator)var1[0]).hasNext()) ? 1 : 0);
                  case 16:
                     return ((Iterator)var1[0]).next();
                  case 17:
                     return (String)var1[0];
                  case 18:
                     return ((JsonObject)var1[0]).get((String)var1[1]);
                  case 19:
                     return canonicalizeElement((JsonElement)var1[0]);
                  case 20:
                     ((JsonObject)var1[0]).add((String)var1[1], (JsonElement)var1[2]);
                     return null;
                  case 21:
                     return Integer.valueOf((((JsonElement)var1[0]).isJsonArray()) ? 1 : 0);
                  case 22:
                     return ((JsonElement)var1[0]).getAsJsonArray();
                  case 23:
                     return RecoveredMethodInterpreter.initializeClass(JsonArray.class);
                  case 24:
                     return new JsonArray();
                  case 25:
                     return ((JsonArray)var1[0]).size();
                  case 26:
                     return ((JsonArray)var1[0]).get(((Number)var1[1]).intValue());
                  case 27:
                     return canonicalizeElement((JsonElement)var1[0]);
                  case 28:
                     ((JsonArray)var1[0]).add((JsonElement)var1[1]);
                     return null;
                  default:
                     throw new IllegalArgumentException();
               }
         }
      }
   }

   private static Object recoveredSerializeElement(int var0, Object[] var1) throws Throwable {
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
                     return ((JsonElement)var1[0]).toString();
                  default:
                     throw new IllegalArgumentException();
               }
         }
      }
   }
}
