package com.intechcore.polarion.extensions.marketplace.plugin_manager.rest.filter;

import com.polarion.platform.core.PlatformContext;
import com.polarion.platform.security.ISecurityService;
import java.io.IOException;
import javax.security.auth.Subject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Secured
@Provider
public class LogoutFilter implements ContainerResponseFilter {
   private final ISecurityService securityService;

   public LogoutFilter(ISecurityService securityService) {
      this.securityService = securityService;
   }

   public LogoutFilter() {
      this.securityService = (ISecurityService)PlatformContext.getPlatform().lookupService(ISecurityService.class);
   }

   public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
      Subject subject = (Subject)requestContext.getProperty("user_subject");
      if (subject != null) {
         this.securityService.logout(subject);
      }

   }
}
