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
@MapperScan(basePackages = "com.tdu.develop.models.mapper", sqlSessionFactoryRef = "modelSqlSessionFactory")
public class ModelDataSourceConfig {

    private static final String PACKAGE = "com.tdu.develop.model.mapper";
    private static final String MAPPER_LOCATION = "classpath:mybatis/model/*.xml";

    @Value("${spring.datasource.druid.develop_model.url}")
    private String url;

    @Value("${spring.datasource.druid.develop_model.username}")
    private String user;

    @Value("${spring.datasource.druid.develop_model.password}")
    private String password;

    @Value("${spring.datasource.druid.develop_model.driver-class-name}")
    private String driverClass;

    @Bean(name = "modelDataSource")
    public DataSource modelDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean(name = "modelTransactionManager")
    public DataSourceTransactionManager modelTransactionManager() {
        return new DataSourceTransactionManager(modelDataSource());
    }

    @Bean(name = "modelSqlSessionFactory")
    public SqlSessionFactory modelSqlSessionFactory(@Qualifier("modelDataSource") DataSource modelDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(modelDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(ModelDataSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();

    }
}
