package com.intechcore.polarion.extensions.marketplace.plugin_manager.util;

import com.polarion.platform.core.PlatformContext;
import com.polarion.platform.security.ISecurityService;
import java.util.Objects;
import java.util.concurrent.Callable;
import javax.security.auth.Subject;
import org.jetbrains.annotations.Nullable;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public final class ApiControllerUtils {
   private static final ISecurityService securityService = (ISecurityService)PlatformContext.getPlatform().lookupService(ISecurityService.class);

   public static <T> T callPrivileged(Callable<T> callable) {
      try {
         ISecurityService var10000 = securityService;
         Subject var10001 = getUserSubject();
         Objects.requireNonNull(callable);
         return var10000.doAsUser(var10001, callable::call);
      } catch (Throwable var2) {
         throw var2;
      }
   }

   @Nullable
   public static Subject getUserSubject() {
      ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
      if (requestAttributes != null) {
         return (Subject)requestAttributes.getRequest().getAttribute("user_subject");
      } else {
         throw new IllegalStateException("Cannot find request attributes in the request context");
      }
   }

   private ApiControllerUtils() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }
}
