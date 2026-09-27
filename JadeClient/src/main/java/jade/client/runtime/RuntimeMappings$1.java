// Jade recovery: recovered class name: RuntimeMappings; original class: jade.deps.eLz.n9Wea2$1
package jade.client.runtime;

import jade.deps.asm.commons.Remapper;

import java.util.Map;

public final class RuntimeMappings$1 extends Remapper {
   final RuntimeMappings this$0;

   private final boolean reverse;
   private final boolean allowFallbacks;
   private final boolean minecraftFallbacksOnly;

   RuntimeMappings$1(RuntimeMappings var1, boolean reverse, boolean allowFallbacks) {
      this(var1, reverse, allowFallbacks, false);
   }

   RuntimeMappings$1(RuntimeMappings var1, boolean reverse, boolean allowFallbacks, boolean minecraftFallbacksOnly) {
      this.this$0 = var1;
      this.reverse = reverse;
      this.allowFallbacks = allowFallbacks;
      this.minecraftFallbacksOnly = minecraftFallbacksOnly;
   }

   private boolean canFallback(String owner) {
      return this.allowFallbacks
         && (
            !this.minecraftFallbacksOnly
               || owner != null && owner.startsWith("net/minecraft/")
         );
   }

   @Override
   public String map(String internalName) {
      Map<String, String> selected = this.reverse ? RuntimeMappings.access$200(this.this$0) : RuntimeMappings.access$300(this.this$0);
      String mapped = selected.get(internalName);
      return mapped == null ? internalName : mapped;
   }

   @Override
   public String mapFieldName(String owner, String name, String descriptor) {
      Map<String, String> exact = this.reverse ? RuntimeMappings.access$400(this.this$0) : RuntimeMappings.access$500(this.this$0);
      Map<String, String> fallback = this.reverse ? RuntimeMappings.access$600(this.this$0) : RuntimeMappings.access$700(this.this$0);
      String mapped = exact.get(owner + "/" + name);
      if (mapped == null) {
         mapped = RuntimeMappings.access$800(this.this$0, owner, name, this.reverse);
      }

      if (mapped == null && this.canFallback(owner)) {
         mapped = fallback.get(name);
      }

      return mapped == null ? name : mapped;
   }

   @Override
   public String mapMethodName(String owner, String name, String descriptor) {
      if (name.startsWith("<")) {
         return name;
      } else {
         Map<String, String> exact = this.reverse ? RuntimeMappings.access$900(this.this$0) : RuntimeMappings.access$1000(this.this$0);
         Map<String, String> fallback = this.reverse ? RuntimeMappings.access$1100(this.this$0) : RuntimeMappings.access$1200(this.this$0);
         String mapped = exact.get(
            owner
               + "/"
               + name
               + " "
               + descriptor
         );
         if (mapped == null) {
            mapped = RuntimeMappings.access$1300(this.this$0, owner, name, descriptor, this.reverse);
         }

         if (mapped == null && this.canFallback(owner)) {
            mapped = fallback.get(name + " " + descriptor);
         }

         return mapped == null ? name : mapped;
      }
   }
}
