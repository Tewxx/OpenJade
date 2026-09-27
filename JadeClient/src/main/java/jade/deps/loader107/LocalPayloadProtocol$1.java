package jade.deps.loader107;

final class LocalPayloadProtocol$1 {
   private final int statusCode;
   private final String responseBody;

   LocalPayloadProtocol$1(int var1, String var2) {
      this.statusCode = var1;
      this.responseBody = var2;
   }

   static int getStatusCode(LocalPayloadProtocol$1 var0) {
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
               return var0.statusCode;
         }
      }
   }

   static String getResponseBody(LocalPayloadProtocol$1 var0) {
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
               return var0.responseBody;
         }
      }
   }
}
