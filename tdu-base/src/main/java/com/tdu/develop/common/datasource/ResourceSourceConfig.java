package com.tdu.develop.common.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author 志阳
 * @create 2019-08-06-9:34
 */
@Configuration
@MapperScan(basePackages = "com.tdu.develop.resource.mapper",sqlSessionFactoryRef = "resourceSqlSessionFactory")
public class ResourceSourceConfig {

    private static final String PACKAGE = "com.tdu.develop.mapper.resource";
    private static final String MAPPER_LOCATION = "classpath:mybatis/resource/*.xml";

    @Value("${spring.datasource.druid.develop_resource.url}")
    private String url;

    @Value("${spring.datasource.druid.develop_resource.username}")
    private String user;

    @Value("${spring.datasource.druid.develop_resource.password}")
    private String password;

    @Value("${spring.datasource.druid.develop_resource.driver-class-name}")
    private String driverClass;

    @Bean(name = "resourceDataSource")
    public DataSource resourceDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean(name = "resourceTransactionManager")
    public DataSourceTransactionManager sourceTransactionManager() {
        return new DataSourceTransactionManager(resourceDataSource());
    }

    @Bean(name = "resourceSqlSessionFactory")
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("resourceDataSource") DataSource resourceDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(resourceDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(ResourceSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();

    }
}
