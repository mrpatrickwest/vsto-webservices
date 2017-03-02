package edu.rpi.tw.vsto;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Properties;

@SpringBootApplication
public class VstoWebServicesApplication {

    private static final Logger log = LoggerFactory.getLogger(VstoWebServicesApplication.class);

    public static void main(String[] args) {
		final SpringApplication app = new SpringApplication(VstoWebServicesApplication.class);
        final ApplicationContext ctx = app.run(VstoWebServicesApplication.class, args);

        if (log.isDebugEnabled()) {
            System.out.println("Let's inspect the beans provided by Spring Boot:");

            final String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (final String beanName : beanNames) {
                System.out.println(beanName);
            }
        }

    }

    @PostConstruct

    @Bean(name = "dataSource")
	@Primary
	public DataSource dataSource() {
		final Properties dsProps = new Properties();
		dsProps.setProperty("url", "jdbc:mysql://127.0.0.1:3306/CEDARCATALOG?connectionTimeout=60000&socketTimeout=60000&autoReconnect=true&useUnicode=yes&characterEncoding=UTF-8");
		dsProps.setProperty("user", "root");
		dsProps.setProperty("password", "level-42");
		dsProps.setProperty("autoReconnect", "true");
		dsProps.setProperty("autoReconnectForPools", "true");
		dsProps.setProperty("failOverReadOnly", "false");
		dsProps.setProperty("cachePreparedStatements", "true");
		dsProps.setProperty("cacheCallableStatements", "true");
		dsProps.setProperty("cacheResultSetMetadata", "false");
		dsProps.setProperty("cacheServerConfiguration", "false");
		dsProps.setProperty("useUnicode", "true");
		dsProps.setProperty("characterEncoding", "UTF-8");
		dsProps.setProperty("connectTimeout", 60000 + "");
		dsProps.setProperty("socketTimeout", 60000 + "");

		final HikariConfig config = new HikariConfig();
		config.setDataSourceClassName("com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource");
		config.setDataSourceProperties(dsProps);

		final HikariDataSource ds = new HikariDataSource(config);

		ds.setConnectionTimeout(60000);
		ds.setIdleTimeout(300000);
		ds.setMaxLifetime(300000);
		ds.setMaximumPoolSize(15);
		ds.setPoolName("VSTO Connection Pool");

		return ds;
	}

}
