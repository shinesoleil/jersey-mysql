package com.thoughtworks.api.records;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.thoughtworks.api.core.OrderRepository;
import com.thoughtworks.api.core.ProductRepository;
import com.thoughtworks.api.core.UserRepository;
import com.thoughtworks.api.core.PaymentRepository;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.*;
import org.apache.ibatis.type.TypeHandler;
import org.mybatis.guice.mappers.MapperProvider;
import org.mybatis.guice.session.SqlSessionManagerProvider;
import org.mybatis.guice.transactional.Transactional;
import org.mybatis.guice.transactional.TransactionalMethodInterceptor;

import java.io.Reader;
import java.util.Collection;
import java.util.Properties;

import static com.google.inject.matcher.Matchers.*;
import static com.google.inject.name.Names.named;
import static com.google.inject.util.Providers.guicify;
import static org.apache.ibatis.io.Resources.getResourceAsReader;

public class Models extends AbstractModule {
  private static final String DEFAULT_CONFIG_RESOURCE = "mybatis/config.xml";

  private final String classPathResource;

  private final String environmentId;

  private final Properties properties;

  private ClassLoader resourcesClassLoader = getDefaultClassLoader();

  private ClassLoader driverClassLoader = getDefaultClassLoader();

  public Models(String environment) {
    this(environment, DEFAULT_CONFIG_RESOURCE, new Properties());
  }

  public Models(String environment, Properties properties) {
    this(environment, DEFAULT_CONFIG_RESOURCE, properties);
  }

  public Models(String environment, String classPathResource, Properties properties) {
    this.environmentId = environment;
    this.classPathResource = classPathResource;
    this.properties = properties;
  }

  @Override
  protected void configure() {
      bindPersistence();
    bind(ProductRepository.class).to(com.thoughtworks.api.records.ProductRepository.class);
    bind(UserRepository.class).to(com.thoughtworks.api.records.UserRepository.class);
    bind(OrderRepository.class).to(com.thoughtworks.api.records.OrderRepository.class);
    bind(PaymentRepository.class).to(com.thoughtworks.api.records.PaymentRepository.class);
//    bind(UserRepository.class).to(com.thoughtworks.api.records.UserRepository.class);
//    bind(StackRepository.class).to(com.thoughtworks.api.records.StackRepository.class);
//    bind(ApplicationRepository.class).to(com.thoughtworks.api.records.ApplicationRepository.class);
//    bind(RouteRepository.class).to(com.thoughtworks.api.records.RouteRepository.class);
//    bind(DomainRepository.class).to(com.thoughtworks.api.records.DomainRepository.class);
//    bind(KeyRepository.class).to(com.thoughtworks.api.records.KeyRepository.class);
//    bind(EventRepository.class).to(com.thoughtworks.api.records.EventRepository.class);
//    bind(OrganizationRepository.class).to(com.thoughtworks.api.records.OrganizationRepository.class);
//    bind(UserService.class).toInstance(new UserService() {
//      @Inject
//      UserRepository userRepository;
//
//      @Override
//      public Optional<User> current(ContainerRequestContext requestContext) {
//        final String token = requestContext.getHeaderString("Authorization");
//        if (token == null) {
//          return Optional.empty();
//        }
//        return userRepository.getUserByAuth(token);
//      }
//    });
  }

  private void bindPersistence() {
    try {
      bindSqlManager();
      bindTransactionalInterceptor();
      bindSqlSessionFactory();
      bind(ClassLoader.class)
        .annotatedWith(named("JDBC.driverClassLoader"))
        .toInstance(driverClassLoader);
    } finally {
      resourcesClassLoader = getDefaultClassLoader();
      driverClassLoader = getDefaultClassLoader();
    }
  }

  private void bindSqlSessionFactory() {
    try (Reader reader = getResourceAsReader(getResourceClassLoader(), classPathResource)) {
      SqlSessionFactory sessionFactory = new SqlSessionFactoryBuilder().build(reader,
        environmentId,
        properties);
      bind(SqlSessionFactory.class).toInstance(sessionFactory);

      Configuration configuration = sessionFactory.getConfiguration();

      bindObjectFactory(configuration);
      bindMappers(configuration);
      bindTypeHandlers(configuration);
      bindInterceptors(configuration);
    } catch (Exception e) {
      addError("Impossible to read classpath resource '%s', see nested exceptions: %s",
        classPathResource,
        e.getMessage());
      e.printStackTrace();
    }
  }

  private void bindObjectFactory(Configuration configuration) {
    requestInjection(configuration.getObjectFactory());
  }

  private void bindInterceptors(Configuration configuration) {
    Collection<Interceptor> interceptors = configuration.getInterceptors();
    for (Interceptor interceptor : interceptors) {
      requestInjection(interceptor);
    }
  }

  private void bindTypeHandlers(Configuration configuration) {
    Collection<TypeHandler<?>> allTypeHandlers = configuration.getTypeHandlerRegistry().getTypeHandlers();
    for (TypeHandler<?> handler : allTypeHandlers) {
      requestInjection(handler);
    }
  }

  private void bindMappers(Configuration configuration) {
    Collection<Class<?>> mapperClasses = configuration.getMapperRegistry().getMappers();
    for (Class<?> mapperType : mapperClasses) {
      bindMapper(mapperType);
    }
  }

  private void bindTransactionalInterceptor() {
    TransactionalMethodInterceptor interceptor = new TransactionalMethodInterceptor();
    requestInjection(interceptor);
    bindInterceptor(any(), annotatedWith(Transactional.class), interceptor);
    bindInterceptor(annotatedWith(Transactional.class), not(annotatedWith(Transactional.class)), interceptor);
  }

  private void bindSqlManager() {
    bind(SqlSessionManager.class).toProvider(SqlSessionManagerProvider.class).in(Scopes.SINGLETON);
    bind(SqlSession.class).to(SqlSessionManager.class).in(Scopes.SINGLETON);
  }

  final <T> void bindMapper(Class<T> mapperType) {
    bind(mapperType).toProvider(guicify(new MapperProvider<T>(mapperType))).in(Scopes.SINGLETON);
  }

  protected final ClassLoader getResourceClassLoader() {
    return resourcesClassLoader;
  }

  private ClassLoader getDefaultClassLoader() {
    return getClass().getClassLoader();
  }
}
