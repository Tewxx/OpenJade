// Jade recovery: original class: jade.deps.eLz.wROLb26MJd
package jade.client.common;

import jade.client.module.render.blockesp.BlockEspParser$0;
import jade.client.module.render.blockesp.BlockEspParser$1;
import jade.client.module.render.blockesp.BlockEspParser;
import jade.client.setting.BlockListSetting;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class BlockListSearchModel {
   private final BlockListSetting blockListSetting;
   private final BlockListSearchModel$1 VPZrz;
   private List<BlockEspParser$1> searchResults = Collections.emptyList();
   private String KCuryU;
   private List<BlockEspParser$0> oDsu0 = Collections.emptyList();

   public BlockListSearchModel(BlockListSetting var1) {
      this(var1, new BlockListSearchModel$1() {
         @Override
         public List<BlockEspParser$1> search(String var1, BlockListSetting var2) {
            return BlockEspParser.parseGroupedEntries(var1, var2);
         }
      });
   }

   public BlockListSearchModel(BlockListSetting var1, BlockListSearchModel$1 var2) {
      this.blockListSetting = var1;
      this.VPZrz = var2;
   }

   public String cuWb2() {
      int var1 = this.blockListSetting.getEntries().size();
      return this.blockListSetting.getName() + (var1 == 0 ? "" : " (" + var1 + ")");
   }

   public int getSelectedCount() {
      return this.blockListSetting.getEntries().size();
   }

   public int getRowCount() {
      return this.isGroupExpanded() ? this.oDsu0.size() + 2 : this.searchResults.size();
   }

   public boolean isGroupExpanded() {
      return this.KCuryU != null;
   }

   public BlockEspParser$1 getSearchResult(int var1) {
      return this.searchResults.get(var1);
   }

   public List<BlockEspParser$0> m249() {
      return this.oDsu0;
   }

   public String getGroupLabel() {
      return this.oDsu0.isEmpty() ? this.KCuryU : this.oDsu0.get(0).displayName;
   }

   public void search(String var1) {
      this.collapseGroup();
      this.searchResults = this.VPZrz.search(var1, this.blockListSetting);
   }

   public void searchIfEmpty(String var1) {
      if (!var1.isEmpty() && this.searchResults.isEmpty()) {
         this.searchResults = this.VPZrz.search(var1, this.blockListSetting);
      }
   }

   public void refreshSearch(String var1) {
      this.searchResults = this.VPZrz.search(var1, this.blockListSetting);
   }

   public void HklNdz(int var1) {
      BlockEspParser$1 var2 = this.searchResults.get(var1);
      this.KCuryU = var2.baseRegistryName;
      this.oDsu0 = new ArrayList<>();

      for (BlockEspParser$0 var4 : var2.nh7) {
         if (!this.blockListSetting.containsEntry(var4.registryKey)) {
            this.oDsu0.add(var4);
         }
      }
   }

   public String RPCwOkb(int var1) {
      if (!this.isGroupExpanded()) {
         return this.searchResults.get(var1).nh7.get(0).registryKey;
      } else {
         return var1 == 1 ? this.KCuryU + ":*" : this.oDsu0.get(var1 - 2).registryKey;
      }
   }

   public BlockEspParser$0 amyrcW(long var1) {
      return this.oDsu0.isEmpty() ? null : this.oDsu0.get((int)(var1 / 1000L % this.oDsu0.size()));
   }

   public void collapseGroup() {
      this.KCuryU = null;
      this.oDsu0 = Collections.emptyList();
   }

   public void fVru() {
      this.searchResults = Collections.emptyList();
      this.collapseGroup();
   }
}
