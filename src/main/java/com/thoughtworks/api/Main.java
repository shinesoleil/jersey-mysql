package com.thoughtworks.api;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.thoughtworks.api.jersey.Api;
import com.thoughtworks.api.records.Models;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.servlet.ServletRegistration;
import org.glassfish.grizzly.servlet.WebappContext;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spi.Container;
import org.glassfish.jersey.server.spi.ContainerLifecycleListener;
import org.glassfish.jersey.servlet.ServletContainer;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;

import javax.inject.Inject;
import javax.ws.rs.core.Application;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static com.google.inject.Guice.createInjector;
import static java.util.Arrays.asList;
import static org.jvnet.hk2.guice.bridge.api.GuiceBridge.getGuiceBridge;

//
//import com.google.common.eventbus.EventBus;
//import com.google.inject.AbstractModule;
//import com.google.inject.Injector;
//import com.google.inject.Provider;
//import com.orbitz.consul.Consul;
//import com.thoughtworks.api.core.ConsulRepository;
//import com.thoughtworks.api.core.LogFetcher;
//import com.thoughtworks.api.core.LoggerFetcher;
//import com.thoughtworks.api.records.DefaultEventHandler;
//import com.thoughtworks.api.records.EventHandler;
//import com.thoughtworks.api.resources.services.AuthorizationService;
//import com.thoughtworks.api.resources.services.DefaultAuthorizationService;
//import org.glassfish.grizzly.http.server.HttpServer;
//import org.glassfish.grizzly.servlet.ServletRegistration;
//import org.glassfish.grizzly.servlet.WebappContext;
//import org.glassfish.hk2.api.ServiceLocator;
//import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
//import org.glassfish.jersey.server.ResourceConfig;
//import org.glassfish.jersey.server.spi.Container;
//import org.glassfish.jersey.server.spi.ContainerLifecycleListener;
//import org.glassfish.jersey.servlet.ServletContainer;
//import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;
//
//import javax.inject.Inject;
//import javax.ws.rs.core.Application;
//import java.net.URI;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Properties;
//
//import static com.google.inject.Guice.createInjector;
//import static java.util.Arrays.asList;
//import static org.jvnet.hk2.guice.bridge.api.GuiceBridge.getGuiceBridge;

public class Main {
  public static void main(String[] args) throws Exception {
    String contextPath = System.getenv().getOrDefault("CONTEXT_PATH", "/");
    WebappContext context = new WebappContext("Jersey-MySQL-API", contextPath);

    ServletRegistration servletRegistration = context.addServlet("ServletContainer",
      new ServletContainer((ResourceConfig) initContainer().getInstance(Application.class)));

    servletRegistration.addMapping("/*");

    HttpServer server = GrizzlyHttpServerFactory.createHttpServer(URI.create("http://localhost:8080/"));
    context.deploy(server);

    server.start();
    while (true) {
      try {
        Thread.sleep(10000);
      } catch (InterruptedException e) {
        server.shutdownNow();
      }
    }
  }

  private static Injector initContainer() throws Exception {
    Properties properties = new Properties();
//    String host = System.getenv("DB_API_HOST");
//    String port = System.getenv("DB_API_PORT");
//    String username = System.getenv("DB_API_USERNAME");
//    String password = System.getenv("DB_API_PASSWORD");

    //temporarily settings
    String host = "localhost";
    String port = "3306";

    String username = "mysql";
    String password = "mysql";


    String connectURL = String.format(
      "jdbc:mysql://%s:%s/jerseyDB?user=%s&password=%s&allowMultiQueries=true&zeroDateTimeBehavior=convertToNull",
      host,
      port,
      username,
      password
    );
    properties.setProperty("db.url", connectURL);
//    String consulURI = System.getenv().getOrDefault("CONSUL_URI", "http://127.0.0.1:8500/");
//    Consul consul = Consul.builder().withUrl(consulURI).build();
    List<AbstractModule> modules = new ArrayList<>(asList(new AbstractModule[]{
      new Models("development", properties),
      new AbstractModule() {
        @Override
        protected void configure() {
//          bind(LogFetcher.class).to(LoggerFetcher.class);
//          bind(Consul.class).toInstance(consul);
//          bind(ConsulRepository.class).to(com.thoughtworks.api.records.ConsulRepository.class);
          bind(Application.class).toProvider(ApplicationProvider.class);
//          bind(EventHandler.class).to(DefaultEventHandler.class);
//          bind(EventBus.class).toProvider(new Provider<EventBus>() {
//            @javax.inject.Inject
//            Injector injector;
//
////            @javax.inject.Inject
////            EventHandler eventHandler;
//            @Override
//            public EventBus get() {
//              EventBus eventBus = new EventBus();
////              eventBus.register(eventHandler);
//              return eventBus;
//            }
//          });
//          bind(AuthorizationService.class).to(DefaultAuthorizationService.class);
        }
      }
    }));
    return createInjector(modules);
  }

  private static class ApplicationProvider implements Provider<javax.ws.rs.core.Application> {
    @Inject
    Injector injector;

    @Override
    public javax.ws.rs.core.Application get() {
      Api api = new Api();

      api.register(new ContainerLifecycleListener() {
        @Override
        public void onStartup(Container container) {
          bridge(container.getApplicationHandler().getServiceLocator(), injector);
        }

        @Override
        public void onReload(Container container) {

        }

        @Override
        public void onShutdown(Container container) {

        }
      });

      return ResourceConfig.forApplication(api);
    }

    private void bridge(ServiceLocator serviceLocator, Injector injector) {
      getGuiceBridge().initializeGuiceBridge(serviceLocator);
      serviceLocator.getService(GuiceIntoHK2Bridge.class).bridgeGuiceInjector(injector);
    }
  }

}
