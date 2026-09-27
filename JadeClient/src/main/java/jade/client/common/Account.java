// Jade recovery: original class: jade.deps.eLz.DW1rQ7QNLm
package jade.client.common;

public class Account {
   private String Jij;
   private String uuid;
   private String ori;
   private String refreshToken;
   private AccountType accountType;
   private LoginMethod loginMethod;
   private String HMt;
   private String CFo6;
   private long dtv;
   private transient String statusMessage;

   public Account(String var1, String var2, String var3, String var4, AccountType var5) {
      this.Jij = var1;
      this.uuid = var2;
      this.ori = var3;
      this.refreshToken = var4;
      this.accountType = var5 == null ? AccountType.CRACKED : var5;
      this.loginMethod = LoginMethod.MICROSOFT;
      this.HMt = "";
      this.CFo6 = "";
      this.dtv = 0L;
   }

   public String zYgb() {
      return this.Jij;
   }

   public String getUuid() {
      return this.uuid;
   }

   public String qRa8673() {
      return this.ori;
   }

   public String getRefreshToken() {
      return this.refreshToken;
   }

   public AccountType FZa4() {
      return this.accountType;
   }

   public LoginMethod getLoginMethod() {
      return this.loginMethod == null ? LoginMethod.MICROSOFT : this.loginMethod;
   }

   public String getFolderId() {
      return this.HMt == null ? "" : this.HMt;
   }

   public String getProxy() {
      return this.CFo6 == null ? "" : this.CFo6;
   }

   public long Kk50() {
      return this.dtv;
   }

   public String getStatusMessage() {
      return this.statusMessage == null ? "" : this.statusMessage;
   }

   public void setUsername(String var1) {
      this.Jij = var1;
   }

   public void setUuid(String var1) {
      this.uuid = var1;
   }

   public void setAccessToken(String var1) {
      this.ori = var1;
   }

   public void setRefreshToken(String var1) {
      this.refreshToken = var1;
   }

   public void setLoginMethod(LoginMethod var1) {
      this.loginMethod = var1 == null ? LoginMethod.MICROSOFT : var1;
   }

   public void setFolderId(String var1) {
      this.HMt = var1 == null ? "" : var1;
   }

   public void setProxy(String var1) {
      this.CFo6 = var1 == null ? "" : var1.trim();
   }

   public void setTimestamp(long var1) {
      this.dtv = var1;
   }

   public void SxxXv(String var1) {
      this.statusMessage = var1 == null ? "" : var1.trim();
   }

   public boolean gDhkZ5() {
      return this.getProxy().length() > 0;
   }

   public boolean hasStatusMessage() {
      return this.getStatusMessage().length() > 0;
   }
}
