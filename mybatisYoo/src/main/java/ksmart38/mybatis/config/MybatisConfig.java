package ksmart38.mybatis.config;

import java.io.IOException;
import java.nio.Buffer;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

import org.apache.ibatis.session.SqlSessionFactory;
import org.jasypt.encryption.StringEncryptor;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration  //설정관련클래스이다.
@MapperScan(value="ksmart38.mybatis.dao", sqlSessionFactoryRef = "mybatisSqlSessionFactory")
@EnableTransactionManagement
public class MybatisConfig {
	//1.datasource DBCP(hikariConfig pool 생성)
	@Bean(name="hikariDatasource")
	public DataSource datasource(@Qualifier("jasyptStringEncryptor")StringEncryptor stringEncryptor) {
		/*
		String dbcUrl = stringEncryptor.decrypt("");
		String Username = stringEncryptor.decrypt("");
		String Password= stringEncryptor.decrypt("");
		*/
		HikariConfig hikariConfig =  new HikariConfig();
		hikariConfig.setDriverClassName("net.sf.log4jdbc.sql.jdbcapi.DriverSpy");
		hikariConfig.setJdbcUrl("jdbc:log4jdbc:mysql://localhost:3306/ksmart38db?serverTimezone=UTC&characterEncoding=UTF8");
		hikariConfig.setUsername("root");
		hikariConfig.setPassword("java0000");
		hikariConfig.setMaximumPoolSize(15);
		hikariConfig.setMaxLifetime(1000*60*30);
		
		return new HikariDataSource(hikariConfig);
	}
	//2. Mybatis 연동을위한 경로
	@Bean(name="mybatisSqlSessionFactory")
	public SqlSessionFactory mybatisSqlSessionFactory(@Qualifier("hikariDatasource") DataSource dataSource,ApplicationContext context) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		sqlSessionFactoryBean.setMapperLocations(context.getResources("classpath:mapper/**/*.xml"));
		sqlSessionFactoryBean.setTypeAliasesPackage("ksmart38.mybatis.domain");
		return sqlSessionFactoryBean.getObject();
	}
	// Mybatis SqlSessionTemplate 설정
	@Bean(name="mybatisSqlSessionTemplate")
	public SqlSessionTemplate mybatisSqlSessionTemplate(@Qualifier("mybatisSqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
		
	}
	// Mybatis SqlSessionTrasaction 설정
	@Bean(name="mybatisSqlSessionTrasaction")
	public PlatformTransactionManager transactionManager(@Qualifier("hikariDatasource") DataSource dataSource,ApplicationContext context) {
		return new DataSourceTransactionManager(dataSource);
	}
	
	
}
