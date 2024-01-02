package com.intechcore.polarion.extensions.marketplace.plugin_manager.service.bundle;

import java.util.HashMap;
import java.util.Map;

public enum BundleState {
   UNINSTALLED(1),
   INSTALLED(2),
   RESOLVED(4),
   STARTING(8),
   STOPPING(16),
   ACTIVE(32),
   START_TRANSIENT(1),
   START_ACTIVATION_POLICY(2),
   STOP_TRANSIENT(1),
   SIGNERS_ALL(1),
   SIGNERS_TRUSTED(2);

   private static final Map<Integer, BundleState> BY_INTEGER = new HashMap();
   private final int value;

   private BundleState(int value) {
      this.value = value;
   }

   public static BundleState of(int value) {
      return (BundleState)BY_INTEGER.get(value);
   }

   // $FF: synthetic method
   private static BundleState[] $values() {
      return new BundleState[]{UNINSTALLED, INSTALLED, RESOLVED, STARTING, STOPPING, ACTIVE, START_TRANSIENT, START_ACTIVATION_POLICY, STOP_TRANSIENT, SIGNERS_ALL, SIGNERS_TRUSTED};
   }

   static {
      BundleState[] var0 = values();
      int var1 = var0.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         BundleState s = var0[var2];
         BY_INTEGER.put(s.value, s);
      }

   }
}
