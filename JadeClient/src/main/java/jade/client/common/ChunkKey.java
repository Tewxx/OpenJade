// Jade recovery: original class: jade.deps.eLz.KCtglrY
package jade.client.common;

public final class ChunkKey {
   private final int chunkX;
   private final int chunkZ;

   public ChunkKey(int var1, int var2) {
      this.chunkX = var1;
      this.chunkZ = var2;
   }

   public int getChunkX() {
      return this.chunkX;
   }

   public int getChunkZ() {
      return this.chunkZ;
   }

   public long toLong() {
      return packCoords(this.chunkX, this.chunkZ);
   }

   public static long packCoords(int var0, int var1) {
      return (long)var0 << 32 | var1 & 4294967295L;
   }
}
