package com.zbjdl.boss.admin.configuration;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
/**
 * Mybatis配置类
 * @author yejiyong
 *
 */
@Configuration
@MapperScan(basePackages="com.zbjdl.boss.admin.repository")
public class MybatisConfiguration {

//    @Bean(name="sqlSessionFactory")
//    @Autowired
//    @Qualifier("datasource")
//    public SqlSessionFactory sqlSessionFactory(DataSource datasource) throws Exception {
//        final SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
//        sqlSessionFactory.setDataSource(datasource);
//        sqlSessionFactory.setConfigLocation(new ClassPathResource("mybatisConfig.xml"));
//        sqlSessionFactory.setFailFast(true);
//        sqlSessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:sqlmap/*.xml"));
//		
//        return sqlSessionFactory.getObject();
//    }
}
