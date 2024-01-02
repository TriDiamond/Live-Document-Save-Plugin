package com.intechcore.polarion.extensions.marketplace.plugin_manager.service.bundle;

import com.intechcore.polarion.extensions.marketplace.plugin_manager.ManagerPlugin;
import java.util.Arrays;
import java.util.Collections;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.ws.rs.NotFoundException;
import org.jetbrains.annotations.NotNull;
import org.osgi.framework.Bundle;

public class BundleService {
   private static final String EXTENSIONS_PATH = "reference:file:extensions";
   private static final String PLUGINS_PATH = "reference:file:plugins";

   @NotNull
   public static BundleService getInstance() {
      return BundleService.InstanceHolder.instance;
   }

   public List<BundleInformation> getBundles() {
      Bundle[] bundles = ManagerPlugin.getInstance().getBundleContext().getBundles();
      return (List)Arrays.stream(bundles).map(this::map).collect(Collectors.toList());
   }

   private static BundleType getBundleType(Bundle bundle) {
      String location = bundle.getLocation();
      location = location.replace("initial@", "");
      if (location.startsWith("reference:file:extensions")) {
         return BundleType.EXTENSION;
      } else {
         return location.startsWith("reference:file:plugins") ? BundleType.PLUGIN : BundleType.OTHER;
      }
   }

   public BundleInformation getBundle(long id) {
      Optional<Bundle> bundle = Optional.ofNullable(ManagerPlugin.getInstance().getBundleContext().getBundle(id));
      return this.map((Bundle)bundle.orElseThrow(() -> {
         return new NotFoundException("No such Bundle");
      }));
   }

   public BundleInformation map(Bundle bundle) {
      List<String> keys = Collections.list(bundle.getHeaders().keys());
      Stream var10000 = keys.stream();
      Function var10001 = Function.identity();
      Dictionary var10002 = bundle.getHeaders();
      Objects.requireNonNull(var10002);
      Map<String, String> attributes = (Map)var10000.collect(Collectors.toMap(var10001, var10002::get));
      return BundleInformation.builder().bundleId(bundle.getBundleId()).state(BundleState.of(bundle.getState())).location(bundle.getLocation()).symbolicName(bundle.getSymbolicName()).bundleVersion(bundle.getVersion().toString()).lastModified(bundle.getLastModified()).bundleType(getBundleType(bundle)).attributes(attributes).build();
   }

   private static class InstanceHolder {
      public static final BundleService instance = new BundleService();
   }
}
