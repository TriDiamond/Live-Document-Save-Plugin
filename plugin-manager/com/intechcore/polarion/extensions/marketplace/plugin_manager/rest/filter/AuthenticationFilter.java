package com.intechcore.polarion.extensions.marketplace.plugin_manager.rest.filter;

import com.polarion.platform.core.PlatformContext;
import com.polarion.platform.security.AuthenticationFailedException;
import com.polarion.platform.security.ISecurityService;
import com.polarion.platform.security.login.AccessToken;
import com.polarion.platform.security.login.IToken;
import java.io.IOException;
import javax.security.auth.Subject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

@Secured
@Provider
public class AuthenticationFilter implements ContainerRequestFilter {
   public static final String BEARER = "Bearer";
   public static final String USER_SUBJECT = "user_subject";
   private final ISecurityService securityService;

   public AuthenticationFilter(ISecurityService securityService) {
      this.securityService = securityService;
   }

   public AuthenticationFilter() {
      this.securityService = (ISecurityService)PlatformContext.getPlatform().lookupService(ISecurityService.class);
   }

   public void filter(ContainerRequestContext requestContext) throws IOException {
      String authorizationHeader = requestContext.getHeaderString("Authorization");
      if (authorizationHeader != null && authorizationHeader.startsWith("Bearer")) {
         String token = authorizationHeader.substring("Bearer".length()).trim();

         try {
            Subject subject = this.validateToken(token);
            requestContext.setProperty("user_subject", subject);
         } catch (AuthenticationFailedException var5) {
            requestContext.abortWith(Response.status(Status.UNAUTHORIZED).build());
         }

      } else {
         throw new NotAuthorizedException("Authorization header must be provided", Response.status(Status.UNAUTHORIZED).header("WWW-Authenticate", "Bearer").build());
      }
   }

   private Subject validateToken(String token) throws AuthenticationFailedException {
      IToken<AccessToken> accessToken = AccessToken.token(token);
      return this.securityService.login().from("REST").authenticator(AccessToken.id()).with(accessToken).perform();
   }
}
