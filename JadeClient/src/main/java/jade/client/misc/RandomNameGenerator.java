// Jade recovery: original class: jade.deps.eLz.p3zOZz
package jade.client.misc;

import java.util.Random;

public class RandomNameGenerator {
   private static final Random random = new Random();
   private static final String[] Ziz1 = new String[]{
      "Shadow", "Void", "Ghost", "Dark", "Neon", "Frost", "Blaze", "Storm", "Swift", "Steel", "Nova", "Apex", "Hyper", "Toxic", "Silent", "Lunar"
   };
   private static final String[] SUFFIXES = new String[]{"gg", "pw", "op", "hd", "mc", "dev", "pvp", "alt"};

   public static String generateName() {
      String var0 = Ziz1[random.nextInt(Ziz1.length)];
      return random.nextBoolean() ? var0 + (100 + random.nextInt(900)) : var0 + "_" + SUFFIXES[random.nextInt(SUFFIXES.length)];
   }
}
