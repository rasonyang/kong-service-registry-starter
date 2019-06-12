# kong-service-registry-starter

> Spring-boot Kong Service Registry starter for website https://www.pamarin.com

# Maven 
```xml
<dependency>
    <groupId>com.pamarin.kong</groupId>
    <artifactId>kong-service-registry-starter</artifactId>
    <version>${project.version}</version>
</dependency>
```

# Cofiguration Properties (classpath:application.properties)
```properties
kong.adminUrl=http://localhost:8001
kong.serviceRegistry.name="app"
kong.serviceRegistry.url=http://localhost:8080
kong.serviceRegistry.routePaths[0]=/
kong.serviceRegistry.enabled=false
```
