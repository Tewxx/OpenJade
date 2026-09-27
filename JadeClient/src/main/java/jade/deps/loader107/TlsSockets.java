package jade.deps.loader107;

import jade.deps.gson.JsonElement;
import jade.deps.gson.JsonNull;
import jade.deps.gson.JsonObject;
import jade.deps.gson.JsonParser;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.lang.reflect.Array;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SNIHostName;
import javax.net.ssl.SNIServerName;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

final class TlsSockets {

   private TlsSockets() {
   }

   static SSLSocket openTlsSocket(String var0, int var1, int var2, int var3) throws IOException {
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
               return (SSLSocket)RecoveredMethodInterpreter.invokeRecovered(
                  "SlZNAZ1OAtwAAAAMAAAABQAAAE8AAAAEAAAABZ1OA9wAAAAAAAAAAAAAAAEAAAAAAAAAAZ1OAoUAAAAFnU4D3AAAAAEAAAABAAAAAQAAAAEAAAACnU4C5gAAAAQAAAABnU4C3QAAAAKdTgLmAAAABQAAAAGdTgLfAAAAAp1OAuoAAAAGAAAAAp1OAsUAAAAEAAAABZ1OA9wAAAACAAAAAAAAAAEAAAAAAAAAAZ1OAoUAAAACnU4CxQAAAAAAAAACnU4CyQAAAAEAAAAFnU4D3AAAAAMAAAADAAAAAQAAAAEAAAACnU4CyQAAAAIAAAAFnU4D3AAAAAQAAAADAAAAAAAAAAAAAAACnU4CxQAAAAQAAAACnU4CyQAAAAMAAAAFnU4D3AAAAAUAAAACAAAAAAAAAAAAAAAFnU4D3AAAAAYAAAAAAAAAAQAAAAAAAAACnU4CxQAAAAQAAAACnU4CxQAAAAAAAAACnU4CyQAAAAEAAAABnU4C2AAAAAWdTgPcAAAABwAAAAUAAAABAAAAAAAAAAWdTgPcAAAACAAAAAEAAAABAAAAAAAAAAKdTgLmAAAABQAAAAKdTgLFAAAABQAAAAKdTgLJAAAAAwAAAAWdTgPcAAAACQAAAAIAAAAAAAAAAAAAAAKdTgLFAAAABQAAAAWdTgPcAAAACgAAAAEAAAABAAAAAAAAAAKdTgLmAAAABwAAAAKdTgLFAAAABwAAAAWdTgPcAAAACwAAAAAAAAABAAAAAAAAAAWdTgPcAAAADAAAAAIAAAAAAAAAAAAAAAKdTgLFAAAABwAAAAWdTgPcAAAADQAAAAAAAAABAAAAAAAAAAGdTgKFAAAAAp1OAsUAAAAAAAAABZ1OA9wAAAAOAAAAAgAAAAEAAAABAAAABZ1OA9wAAAAPAAAAAQAAAAEAAAAAAAAABZ1OA9wAAAAQAAAAAgAAAAAAAAAAAAAAAp1OAsUAAAAFAAAAAp1OAsUAAAAHAAAABZ1OA9wAAAARAAAAAgAAAAAAAAAAAAAAAp1OAsUAAAAFAAAABZ1OA9wAAAASAAAAAQAAAAAAAAAAAAAAAZ1OAtgAAAACnU4C6gAAAAYAAAACnU4CxQAAAAUAAAACnU4C5gAAAAgAAAACnU4CyQAAAAYAAAACnU4CRgAAAD8AAAACnU4CxQAAAAUAAAACnU4CGgAAADsAAAACnU4CxQAAAAUAAAAFnU4D3AAAABMAAAABAAAAAAAAAAAAAAACnU4CewAAAD0AAAACnU4CxQAAAAQAAAAFnU4D3AAAABQAAAABAAAAAAAAAAAAAAACnU4CewAAAD8AAAACnU4C5gAAAAkAAAACnU4CxQAAAAgAAAABnU4CbAAAAAKdTgLmAAAACgAAAAKdTgLJAAAABgAAAAKdTgJGAAAATQAAAAKdTgLFAAAABQAAAAKdTgIaAAAASQAAAAKdTgLFAAAABQAAAAWdTgPcAAAAFQAAAAEAAAAAAAAAAAAAAAKdTgJ7AAAASwAAAAKdTgLFAAAABAAAAAWdTgPcAAAAFgAAAAEAAAAAAAAAAAAAAAKdTgJ7AAAATQAAAAKdTgLmAAAACwAAAAKdTgLFAAAACgAAAAGdTgJjAAAANgAAAD0AAAA+AAAAFwAAAAgAAAA0AAAAQf////8AAABEAAAASwAAAEwAAAAYAAAAQQAAAEIAAABB/////w==",
                  jade.build.RecoveredHandles.resolve(TlsSockets.class, "invokeOpenTlsSocket"),
                  new Object[]{var0, var1, var2, var3}
               );
         }
      }
   }

   private static String readHttpResponseBody(InputStream var0) throws IOException {
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
                  "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",
                  jade.build.RecoveredHandles.resolve(TlsSockets.class, "invokeReadHttpResponseBody"),
                  new Object[]{var0}
               );
         }
      }
   }

   private static byte[] readChunkedResponseBody(InputStream var0) throws IOException {
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
                  "SlZNAekC5FoAAAAFAAAAAwAAAD0AAAAAAAAABekC5VoAAAAAAAAAAAAAAAEAAAAAAAAAAekC5AMAAAAF6QLlWgAAAAEAAAABAAAAAQAAAAEAAAAC6QLkYAAAAAEAAAAC6QLkQwAAAAAAAAAF6QLlWgAAAAIAAAABAAAAAQAAAAAAAAAC6QLkYAAAAAIAAAAC6QLkQwAAAAIAAAAC6QLknQAAAA4AAAAF6QLlWgAAAAMAAAAAAAAAAQAAAAAAAAAB6QLkAwAAAAXpAuVaAAAABAAAAAAAAAABAAAAAAAAAAXpAuVaAAAABQAAAAIAAAABAAAAAQAAAAHpAuTlAAAAAukC5EMAAAACAAAAAukC5EoAAAA7AAAABekC5VoAAAAGAAAAAgAAAAEAAAAAAAAAAukC5GwAAAADAAAAAukC5E8AAAADAAAAAukC5MYAAAAWAAAAAukC5EMAAAACAAAAAukC5P0AAAAaAAAAAukC5EMAAAACAAAAAekC5FkAAAAC6QLkTwAAAAMAAAAF6QLlWgAAAAcAAAADAAAAAQAAAAAAAAAF6QLlWgAAAAgAAAABAAAAAQAAAAAAAAAC6QLkSgAAABAAAAAF6QLlWgAAAAkAAAACAAAAAQAAAAAAAAAC6QLkbAAAAAQAAAAC6QLkTwAAAAQAAAAC6QLkwQAAACYAAAAC6QLkQwAAAAEAAAAF6QLlWgAAAAoAAAABAAAAAQAAAAAAAAAC6QLkTwAAAAQAAAAB6QLkOgAAAAXpAuVaAAAACwAAAAAAAAABAAAAAAAAAALpAuT+AAAAKwAAAAXpAuVaAAAADAAAAAAAAAABAAAAAAAAAAHpAuQDAAAABekC5VoAAAANAAAAAAAAAAEAAAAAAAAABekC5VoAAAAOAAAAAgAAAAEAAAABAAAAAekC5OUAAAAC6QLkTwAAAAQAAAAC6QLkwAAAADAAAAAC6QLkQwAAAAEAAAAF6QLlWgAAAA8AAAABAAAAAQAAAAAAAAAB6QLk6gAAAALpAuRDAAAAAAAAAALpAuRDAAAAAQAAAALpAuRPAAAABAAAAAXpAuVaAAAAEAAAAAMAAAAAAAAAAAAAAALpAuRDAAAAAAAAAAXpAuVaAAAAEQAAAAEAAAABAAAAAAAAAALpAuSdAAAAPAAAAAXpAuVaAAAAEgAAAAAAAAABAAAAAAAAAAHpAuQDAAAABekC5VoAAAATAAAAAAAAAAEAAAAAAAAABekC5VoAAAAUAAAAAgAAAAEAAAABAAAAAekC5OUAAAAC6QLk/QAAAAQ=",
                  jade.build.RecoveredHandles.resolve(TlsSockets.class, "invokeReadChunkedResponseBody"),
                  new Object[]{var0}
               );
         }
      }
   }

   private static byte[] readFixedLengthResponseBody(InputStream var0, int var1) throws IOException {
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
                  "SlZNAV7qleMAAAADAAAAAwAAABYAAAAAAAAAAl7qlfYAAAABAAAAAl7qlXgAAAAFAAAAAl7qlfYAAAABAAAABV7qlOMAAAAAAAAAAAAAAAEAAAAAAAAAAl7qlUcAAAAKAAAABV7qlOMAAAABAAAAAAAAAAEAAAAAAAAAAV7qlboAAAAFXuqU4wAAAAIAAAAAAAAAAQAAAAAAAAAFXuqU4wAAAAMAAAACAAAAAQAAAAEAAAABXuqVXAAAAAVe6pTjAAAABAAAAAAAAAABAAAAAAAAAAFe6pW6AAAAAl7qlfYAAAABAAAABV7qlOMAAAAFAAAAAgAAAAEAAAABAAAAAl7qldkAAAACAAAAAl7qlfoAAAAAAAAAAl7qlfoAAAACAAAAAl7qlfYAAAABAAAABV7qlOMAAAAGAAAAAwAAAAAAAAAAAAAAAl7qlfoAAAACAAAABV7qlOMAAAAHAAAAAQAAAAEAAAAAAAAAAV7qlVM=",
                  jade.build.RecoveredHandles.resolve(TlsSockets.class, "invokeReadFixedLengthResponseBody"),
                  new Object[]{var0, var1}
               );
         }
      }
   }

   private static void copyBodyBytes(InputStream var0, ByteArrayOutputStream var1, int var2) throws IOException {
      byte var3 = 0;

      while (true) {
         switch (var3) {
            case 0:

               var3 = 1;
               break;
            case 1:
               var3 = 2;
               break;
            default:
               RecoveredMethodInterpreter.invokeRecovered(
                  "SlZNAZOutxMAAAAGAAAABQAAACQAAAAAAAAAApOutwYAAAACAAAAApOutwIAABAAAAAABZOuthMAAAAAAAAAAgAAAAEAAAAAAAAABZOuthMAAAABAAAAAQAAAAEAAAAAAAAAApOutykAAAADAAAAApOutwYAAAACAAAAApOutyUAAAAEAAAAApOutwYAAAAEAAAAApOut40AAAAjAAAAApOutwoAAAAAAAAAApOutwoAAAADAAAAAZOutxAAAAACk663CgAAAAMAAAAFk662EwAAAAIAAAABAAAAAQAAAAAAAAACk663BgAAAAQAAAAFk662EwAAAAMAAAACAAAAAQAAAAAAAAAFk662EwAAAAQAAAAEAAAAAQAAAAAAAAACk663JQAAAAUAAAACk663BgAAAAUAAAACk663jwAAABkAAAAFk662EwAAAAUAAAAAAAAAAQAAAAAAAAABk663SgAAAAWTrrYTAAAABgAAAAAAAAABAAAAAAAAAAWTrrYTAAAABwAAAAIAAAABAAAAAQAAAAGTrresAAAAApOutwoAAAABAAAAApOutwoAAAADAAAAAZOutxAAAAACk663BgAAAAUAAAAFk662EwAAAAgAAAAEAAAAAAAAAAAAAAACk663BgAAAAQAAAACk663BgAAAAUAAAABk663dwAAAAKTrrclAAAABAAAAAKTrre0AAAABwAAAAGTrrei",
                  jade.build.RecoveredHandles.resolve(TlsSockets.class, "invokeCopyBodyBytes"),
                  new Object[]{var0, var1, var2}
               );
               return;
         }
      }
   }

   private static String readAsciiLine(InputStream var0) throws IOException {
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
                  "SlZNAQgb5dgAAAADAAAABAAAACwAAAAAAAAABQgb5NgAAAAAAAAAAAAAAAEAAAAAAAAAAQgb5YEAAAAFCBvk2AAAAAEAAAABAAAAAQAAAAEAAAACCBvl4gAAAAEAAAACCBvlwQAAAAAAAAAFCBvk2AAAAAIAAAABAAAAAQAAAAAAAAABCBvlgQAAAAIIG+XuAAAAAgAAAAEIG+XaAAAAAggb5UcAAAAdAAAAAggb5c0AAAACAAAAAggb5cgAAAAKAAAAAggb5XgAAAAOAAAAAggb5X8AAAAdAAAAAggb5c0AAAACAAAAAggb5cgAAAANAAAAAggb5UcAAAAUAAAAAggb5cEAAAABAAAAAggb5c0AAAACAAAABQgb5NgAAAADAAAAAgAAAAAAAAAAAAAAAggb5cEAAAABAAAABQgb5NgAAAAEAAAAAQAAAAEAAAAAAAAAAggb5ckAACAAAAAAAggb5XwAAAAEAAAABQgb5NgAAAAFAAAAAAAAAAEAAAAAAAAAAQgb5YEAAAAFCBvk2AAAAAYAAAAAAAAAAQAAAAAAAAAFCBvk2AAAAAcAAAACAAAAAQAAAAEAAAABCBvlZwAAAAIIG+XNAAAAAgAAAAEIG+XaAAAAAggb5XgAAAAlAAAAAggb5cEAAAABAAAABQgb5NgAAAAIAAAAAQAAAAEAAAAAAAAAAggb5UIAAAAlAAAAAQgb5dkAAAABCBvlaAAAAAUIG+TYAAAACQAAAAAAAAABAAAAAAAAAAEIG+WBAAAAAggb5cEAAAABAAAABQgb5NgAAAAKAAAAAQAAAAEAAAAAAAAABQgb5NgAAAALAAAAAAAAAAEAAAAAAAAABQgb5NgAAAAMAAAAAwAAAAEAAAABAAAAAQgb5Wg=",
                  jade.build.RecoveredHandles.resolve(TlsSockets.class, "invokeReadAsciiLine"),
                  new Object[]{var0}
               );
         }
      }
   }

   static SSLSocketFactory getPlatformSslSocketFactory() {
      byte var0 = 0;

      while (true) {
         switch (var0) {
            case 0:

               var0 = 1;
               break;
            case 1:
               var0 = 2;
               break;
            default:
               return (SSLSocketFactory)RecoveredMethodInterpreter.invokeRecovered(
                  "SlZNAXHFyq4AAAAAAAAAAQAAAAIAAAAAAAAABXHFy64AAAAAAAAAAAAAAAEAAAAAAAAAAXHFyh4=",
                  jade.build.RecoveredHandles.resolve(TlsSockets.class, "invokeGetPlatformSslSocketFactory"),
                  new Object[0]
               );
         }
      }
   }

   private static SSLSocketFactory createPlatformSslSocketFactory() {
      byte var0 = 0;

      while (true) {
         switch (var0) {
            case 0:

               var0 = 1;
               break;
            case 1:
               var0 = 2;
               break;
            default:
               return (SSLSocketFactory)RecoveredMethodInterpreter.invokeRecovered(
                  "SlZNAReGfWQAAAABAAAABQAAABQAAAABAAAABReGfGQAAAAAAAAAAAAAAAEAAAAAAAAABReGfGQAAAABAAAAAQAAAAEAAAAAAAAAAheGfV4AAAAAAAAAAheGfX0AAAAAAAAAAReGfWUAAAABF4Z9ZQAAAAUXhnxkAAAAAgAAAAAAAAABAAAAAAAAAAEXhn09AAAABReGfGQAAAADAAAAAQAAAAEAAAABAAAABReGfGQAAAAEAAAABAAAAAAAAAAAAAAAAheGfX0AAAAAAAAABReGfGQAAAAFAAAAAQAAAAEAAAAAAAAAAReGfdQAAAACF4Z9XgAAAAAAAAAFF4Z8ZAAAAAYAAAAAAAAAAQAAAAAAAAABF4Z9PQAAAAUXhnxkAAAABwAAAAAAAAABAAAAAAAAAAIXhn19AAAAAAAAAAUXhnxkAAAACAAAAAMAAAABAAAAAQAAAAEXhn3bAAAAAAAAAAwAAAANAAAACQ==",
                  jade.build.RecoveredHandles.resolve(TlsSockets.class, "invokeCreatePlatformSslSocketFactory"),
                  new Object[0]
               );
         }
      }
   }

   private static Object invokeOpenTlsSocket(int var0, Object[] var1) throws Throwable {
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
                     return RecoveredMethodInterpreter.initializeClass(Socket.class);
                  case 1:
                     return new Socket();
                  case 2:
                     return RecoveredMethodInterpreter.initializeClass(InetSocketAddress.class);
                  case 3:
                     return new InetSocketAddress((String)var1[1], ((Number)var1[2]).intValue());
                  case 4:
                     ((Socket)var1[0]).connect((SocketAddress)var1[1], ((Number)var1[2]).intValue());
                     return null;
                  case 5:
                     ((Socket)var1[0]).setSoTimeout(((Number)var1[1]).intValue());
                     return null;
                  case 6:
                     return createPlatformSslSocketFactory();
                  case 7:
                     return ((SSLSocketFactory)var1[0])
                        .createSocket((Socket)var1[1], (String)var1[2], ((Number)var1[3]).intValue(), (((Number)var1[4]).intValue() != 0));
                  case 8:
                     return (SSLSocket)var1[0];
                  case 9:
                     ((SSLSocket)var1[0]).setSoTimeout(((Number)var1[1]).intValue());
                     return null;
                  case 10:
                     return ((SSLSocket)var1[0]).getSSLParameters();
                  case 11:
                     return "HTTPS";
                  case 12:
                     ((SSLParameters)var1[0]).setEndpointIdentificationAlgorithm((String)var1[1]);
                     return null;
                  case 13:
                     return RecoveredMethodInterpreter.initializeClass(SNIHostName.class);
                  case 14:
                     return new SNIHostName((String)var1[1]);
                  case 15:
                     return Collections.singletonList(var1[0]);
                  case 16:
                     ((SSLParameters)var1[0]).setServerNames((List<SNIServerName>)var1[1]);
                     return null;
                  case 17:
                     ((SSLSocket)var1[0]).setSSLParameters((SSLParameters)var1[1]);
                     return null;
                  case 18:
                     ((SSLSocket)var1[0]).startHandshake();
                     return null;
                  case 19:
                     ((SSLSocket)var1[0]).close();
                     return null;
                  case 20:
                     ((Socket)var1[0]).close();
                     return null;
                  case 21:
                     ((SSLSocket)var1[0]).close();
                     return null;
                  case 22:
                     ((Socket)var1[0]).close();
                     return null;
                  case 23:
                     return Integer.valueOf((var1[0] instanceof IOException) ? 1 : 0);
                  case 24:
                     return Integer.valueOf((var1[0] instanceof IOException) ? 1 : 0);
                  default:
                     throw new IllegalArgumentException();
               }
         }
      }
   }

   private static Object invokeReadHttpResponseBody(int var0, Object[] var1) throws Throwable {
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
                     return RecoveredMethodInterpreter.initializeClass(ByteBudgetInputStream.class);
                  case 1:
                     return 1114112L;
                  case 2:
                     return new ByteBudgetInputStream((InputStream)var1[1], ((Number)var1[2]).longValue());
                  case 3:
                     return readAsciiLine((InputStream)var1[0]);
                  case 4:
                     return "HTTP/";
                  case 5:
                     return Integer.valueOf((((String)var1[0]).startsWith((String)var1[1])) ? 1 : 0);
                  case 6:
                     return RecoveredMethodInterpreter.initializeClass(IOException.class);
                  case 7:
                     return "invalid validate response";
                  case 8:
                     return new IOException((String)var1[1]);
                  case 9:
                     return readAsciiLine((InputStream)var1[0]);
                  case 10:
                     return ((String)var1[0]).length();
                  case 11:
                     return ((String)var1[0]).indexOf(((Number)var1[1]).intValue());
                  case 12:
                     return ((String)var1[0]).substring(((Number)var1[1]).intValue(), ((Number)var1[2]).intValue());
                  case 13:
                     return ((String)var1[0]).trim();
                  case 14:
                     return ((String)var1[0]).substring(((Number)var1[1]).intValue());
                  case 15:
                     return ((String)var1[0]).trim();
                  case 16:
                     return "Transfer-Encoding";
                  case 17:
                     return Integer.valueOf((((String)var1[0]).equalsIgnoreCase((String)var1[1])) ? 1 : 0);
                  case 18:
                     return ((String)var1[0]).toLowerCase();
                  case 19:
                     return "chunked";
                  case 20:
                     return Integer.valueOf((((String)var1[0]).contains((CharSequence)var1[1])) ? 1 : 0);
                  case 21:
                     return "Content-Length";
                  case 22:
                     return Integer.valueOf((((String)var1[0]).equalsIgnoreCase((String)var1[1])) ? 1 : 0);
                  case 23:
                     return Integer.parseInt((String)var1[0]);
                  case 24:
                     return readChunkedResponseBody((InputStream)var1[0]);
                  case 25:
                     return readFixedLengthResponseBody((InputStream)var1[0], ((Number)var1[1]).intValue());
                  case 26:
                     return RecoveredMethodInterpreter.initializeClass(String.class);
                  case 27:
                     return StandardCharsets.UTF_8;
                  case 28:
                     return new String((byte[])var1[1], (Charset)var1[2]);
                  case 29:
                     Arrays.fill((byte[])var1[0], (byte)((Number)var1[1]).intValue());
                     return null;
                  case 30:
                     Arrays.fill((byte[])var1[0], (byte)((Number)var1[1]).intValue());
                     return null;
                  default:
                     throw new IllegalArgumentException();
               }
         }
      }
   }

   private static Object invokeReadChunkedResponseBody(int var0, Object[] var1) throws Throwable {
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
                     return RecoveredMethodInterpreter.initializeClass(ByteArrayOutputStream.class);
                  case 1:
                     return new ByteArrayOutputStream();
                  case 2:
                     return readAsciiLine((InputStream)var1[0]);
                  case 3:
                     return RecoveredMethodInterpreter.initializeClass(EOFException.class);
                  case 4:
                     return "validate response ended in chunk header";
                  case 5:
                     return new EOFException((String)var1[1]);
                  case 6:
                     return ((String)var1[0]).indexOf(((Number)var1[1]).intValue());
                  case 7:
                     return ((String)var1[0]).substring(((Number)var1[1]).intValue(), ((Number)var1[2]).intValue());
                  case 8:
                     return ((String)var1[0]).trim();
                  case 9:
                     return Integer.parseInt((String)var1[0], ((Number)var1[1]).intValue());
                  case 10:
                     return ((ByteArrayOutputStream)var1[0]).size();
                  case 11:
                     return 1048576;
                  case 12:
                     return RecoveredMethodInterpreter.initializeClass(IOException.class);
                  case 13:
                     return "validate response too large";
                  case 14:
                     return new IOException((String)var1[1]);
                  case 15:
                     return ((ByteArrayOutputStream)var1[0]).toByteArray();
                  case 16:
                     copyBodyBytes((InputStream)var1[0], (ByteArrayOutputStream)var1[1], ((Number)var1[2]).intValue());
                     return null;
                  case 17:
                     return readAsciiLine((InputStream)var1[0]);
                  case 18:
                     return RecoveredMethodInterpreter.initializeClass(EOFException.class);
                  case 19:
                     return "validate response ended in chunk";
                  case 20:
                     return new EOFException((String)var1[1]);
                  default:
                     throw new IllegalArgumentException();
               }
         }
      }
   }

   private static Object invokeReadFixedLengthResponseBody(int var0, Object[] var1) throws Throwable {
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
                     return 1048576;
                  case 1:
                     return RecoveredMethodInterpreter.initializeClass(IOException.class);
                  case 2:
                     return "invalid validate response length";
                  case 3:
                     return new IOException((String)var1[1]);
                  case 4:
                     return RecoveredMethodInterpreter.initializeClass(ByteArrayOutputStream.class);
                  case 5:
                     return new ByteArrayOutputStream(((Number)var1[1]).intValue());
                  case 6:
                     copyBodyBytes((InputStream)var1[0], (ByteArrayOutputStream)var1[1], ((Number)var1[2]).intValue());
                     return null;
                  case 7:
                     return ((ByteArrayOutputStream)var1[0]).toByteArray();
                  default:
                     throw new IllegalArgumentException();
               }
         }
      }
   }

   private static Object invokeCopyBodyBytes(int var0, Object[] var1) throws Throwable {
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
                     return Math.min(((Number)var1[0]).intValue(), ((Number)var1[1]).intValue());
                  case 1:
                     return new byte[((Number)var1[0]).intValue()];
                  case 2:
                     return Array.getLength(var1[0]);
                  case 3:
                     return Math.min(((Number)var1[0]).intValue(), ((Number)var1[1]).intValue());
                  case 4:
                     return ((InputStream)var1[0]).read((byte[])var1[1], ((Number)var1[2]).intValue(), ((Number)var1[3]).intValue());
                  case 5:
                     return RecoveredMethodInterpreter.initializeClass(EOFException.class);
                  case 6:
                     return "validate response ended early";
                  case 7:
                     return new EOFException((String)var1[1]);
                  case 8:
                     ((ByteArrayOutputStream)var1[0]).write((byte[])var1[1], ((Number)var1[2]).intValue(), ((Number)var1[3]).intValue());
                     return null;
                  default:
                     throw new IllegalArgumentException();
               }
         }
      }
   }

   private static Object invokeReadAsciiLine(int var0, Object[] var1) throws Throwable {
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
                     return RecoveredMethodInterpreter.initializeClass(ByteArrayOutputStream.class);
                  case 1:
                     return new ByteArrayOutputStream();
                  case 2:
                     return ((InputStream)var1[0]).read();
                  case 3:
                     ((ByteArrayOutputStream)var1[0]).write(((Number)var1[1]).intValue());
                     return null;
                  case 4:
                     return ((ByteArrayOutputStream)var1[0]).size();
                  case 5:
                     return RecoveredMethodInterpreter.initializeClass(IOException.class);
                  case 6:
                     return "validate response header too large";
                  case 7:
                     return new IOException((String)var1[1]);
                  case 8:
                     return ((ByteArrayOutputStream)var1[0]).size();
                  case 9:
                     return RecoveredMethodInterpreter.initializeClass(String.class);
                  case 10:
                     return ((ByteArrayOutputStream)var1[0]).toByteArray();
                  case 11:
                     return StandardCharsets.US_ASCII;
                  case 12:
                     return new String((byte[])var1[1], (Charset)var1[2]);
                  default:
                     throw new IllegalArgumentException();
               }
         }
      }
   }

   private static Object invokeGetPlatformSslSocketFactory(int var0, Object[] var1) throws Throwable {
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
                     return createPlatformSslSocketFactory();
                  default:
                     throw new IllegalArgumentException();
               }
         }
      }
   }

   private static Object invokeCreatePlatformSslSocketFactory(int var0, Object[] var1) throws Throwable {
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
                     return "TLS";
                  case 1:
                     return SSLContext.getInstance((String)var1[0]);
                  case 2:
                     return RecoveredMethodInterpreter.initializeClass(SecureRandom.class);
                  case 3:
                     return new SecureRandom();
                  case 4:
                     ((SSLContext)var1[0]).init((KeyManager[])var1[1], (TrustManager[])var1[2], (SecureRandom)var1[3]);
                     return null;
                  case 5:
                     return ((SSLContext)var1[0]).getSocketFactory();
                  case 6:
                     return RecoveredMethodInterpreter.initializeClass(IllegalStateException.class);
                  case 7:
                     return "could not initialize platform TLS";
                  case 8:
                     return new IllegalStateException((String)var1[1], (Throwable)var1[2]);
                  case 9:
                     return Integer.valueOf((var1[0] instanceof Exception) ? 1 : 0);
                  default:
                     throw new IllegalArgumentException();
               }
         }
      }
   }
}
