package com.intechcore.polarion.extensions.marketplace.plugin_manager.rest.model;

public class Context {
   private String extensionContext;

   public String getBaseUrl() {
      return "/polarion/" + this.extensionContext;
   }

   public String getRestUrl() {
      return "/polarion/" + this.extensionContext + "/rest";
   }

   public String getSwaggerUiUrl() {
      return this.getRestUrl() + "/swagger";
   }

   public String getExtensionContext() {
      return this.extensionContext;
   }

   public void setExtensionContext(String extensionContext) {
      this.extensionContext = extensionContext;
   }

   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof Context)) {
         return false;
      } else {
         Context other = (Context)o;
         if (!other.canEqual(this)) {
            return false;
         } else {
            Object this$extensionContext = this.getExtensionContext();
            Object other$extensionContext = other.getExtensionContext();
            if (this$extensionContext == null) {
               if (other$extensionContext != null) {
                  return false;
               }
            } else if (!this$extensionContext.equals(other$extensionContext)) {
               return false;
            }

            return true;
         }
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof Context;
   }

   public int hashCode() {
      int PRIME = true;
      int result = 1;
      Object $extensionContext = this.getExtensionContext();
      int result = result * 59 + ($extensionContext == null ? 43 : $extensionContext.hashCode());
      return result;
   }

   public String toString() {
      return "Context(extensionContext=" + this.getExtensionContext() + ")";
   }

   public Context() {
   }

   public Context(String extensionContext) {
      this.extensionContext = extensionContext;
   }
}
