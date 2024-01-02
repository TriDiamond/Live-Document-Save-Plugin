package com.intechcore.polarion.extensions.marketplace.plugin_manager.rest;

import com.intechcore.polarion.extensions.marketplace.plugin_manager.rest.controller.ExtensionInfoApiController;
import com.intechcore.polarion.extensions.marketplace.plugin_manager.rest.controller.ExtensionInfoInternalController;
import com.intechcore.polarion.extensions.marketplace.plugin_manager.rest.controller.SwaggerController;
import com.intechcore.polarion.extensions.marketplace.plugin_manager.rest.controller.SwaggerDefinitionController;
import com.intechcore.polarion.extensions.marketplace.plugin_manager.rest.exception.BadRequestExceptionMapper;
import com.intechcore.polarion.extensions.marketplace.plugin_manager.rest.exception.ForbiddenExceptionMapper;
import com.intechcore.polarion.extensions.marketplace.plugin_manager.rest.exception.IllegalArgumentExceptionMapper;
import com.intechcore.polarion.extensions.marketplace.plugin_manager.rest.exception.InternalServerErrorExceptionMapper;
import com.intechcore.polarion.extensions.marketplace.plugin_manager.rest.exception.NotFoundExceptionMapper;
import com.intechcore.polarion.extensions.marketplace.plugin_manager.rest.exception.UncaughtExceptionMapper;
import com.intechcore.polarion.extensions.marketplace.plugin_manager.rest.filter.AuthenticationFilter;
import com.intechcore.polarion.extensions.marketplace.plugin_manager.rest.filter.LogoutFilter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Application;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.jetbrains.annotations.NotNull;

public class RestApplication extends Application {
   @NotNull
   public Set<Class<?>> getClasses() {
      Set<Class<?>> classes = new HashSet();
      classes.addAll(this.getExceptionMappers());
      classes.addAll(this.getFilters());
      classes.addAll(this.getControllerClasses());
      classes.add(JacksonFeature.class);
      return classes;
   }

   @NotNull
   protected Set<Class<?>> getExceptionMappers() {
      return new HashSet(Arrays.asList(BadRequestExceptionMapper.class, IllegalArgumentExceptionMapper.class, InternalServerErrorExceptionMapper.class, ForbiddenExceptionMapper.class, NotFoundExceptionMapper.class, UncaughtExceptionMapper.class));
   }

   @NotNull
   protected Set<Class<?>> getFilters() {
      return new HashSet(Arrays.asList(AuthenticationFilter.class, LogoutFilter.class));
   }

   @NotNull
   protected Set<Class<?>> getControllerClasses() {
      return new HashSet(Arrays.asList(ExtensionInfoApiController.class, ExtensionInfoInternalController.class, SwaggerController.class, SwaggerDefinitionController.class));
   }
}
