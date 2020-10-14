package com.tdu.develop.common.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author 志阳
 * @create 2019-08-05-17:33
 */
@Configuration
@MapperScan(basePackages = "com.tdu.develop.user.mapper", sqlSessionFactoryRef = "userSqlSessionFactory")
public class UserDataSourceConfig {
    private static final String PACKAGE = "com.tdu.develop.user.mapper";
    private static final String MAPPER_LOCATION = "classpath:mybatis/user/*.xml";

    @Value("${spring.datasource.druid.develop_user.url}")
    private String url;

    @Value("${spring.datasource.druid.develop_user.username}")
    private String user;

    @Value("${spring.datasource.druid.develop_user.password}")
    private String password;

    @Value("${spring.datasource.druid.develop_user.driver-class-name}")
    private String driverClass;

    @Bean(name = "userDataSource")
    @Primary
    public DataSource userDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(user);
        dataSource.setPassword(password);
        return dataSource;
    }

    @Bean(name = "userTransactionManager")
    @Primary
    public DataSourceTransactionManager userTransactionManager() {
        return new DataSourceTransactionManager(userDataSource());
    }

    @Bean(name = "userSqlSessionFactory")
    @Primary
    public SqlSessionFactory userSqlSessionFactory(@Qualifier("userDataSource") DataSource userDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(userDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(UserDataSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();

    }
}
