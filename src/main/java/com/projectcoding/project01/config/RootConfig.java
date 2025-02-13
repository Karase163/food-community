package com.projectcoding.project01.config;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

// root-context.xml과 동일
@Configuration
@ComponentScan(basePackages = { "com.projectcoding.project01.service" })
@ComponentScan(basePackages = { "com.projectcoding.project01.aspect" })
@MapperScan(basePackages = { "com.projectcoding.project01.reviewpersistence", "com.projectcoding.project01.persistence",
		"com.projectcoding.project01.questionpersistence" })
// 패키지 경로로 Mapper 스캐닝
@EnableAspectJAutoProxy // aspect가 자동으로 proxy 기능을 사용한다. 정도로 인식된다.
public class RootConfig {

	@Bean // 스프링 bean으로 설정. xml의 <bean>태그와 동일
	public DataSource dataSource() { // DataSource 객체 리턴 메소드
		// HikariConfig : DBCP 라이브러리 | DataBase Connection Pool 라이브러리
		HikariConfig config = new HikariConfig(); // 설정 객체
		config.setDriverClassName("oracle.jdbc.OracleDriver");
		config.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:xe");
		config.setUsername("Project"); // DB 사용자 아이디
		config.setPassword("1234"); // DB 사용자 비밀번호

		config.setMaximumPoolSize(10); // 최대 풀(Pool) 크기 설정
		config.setConnectionTimeout(30000); // Connection 타임 아웃 설정(30초)
		HikariDataSource ds = new HikariDataSource(config);

		return ds; // ds 객체 리 턴
	}

	@Bean
	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource());
		return (SqlSessionFactory) sqlSessionFactoryBean.getObject();
	}

	// 트랜잭션 매니저 객체를 빈으로 등록 | DB와 상호작용을 해야하니까 @Bean 사용
	// 트랜잭션 사용 용도 DB에서 데이터 관련 연산 (insert,update,delete)를 2개 이상 사용할 때 적용시킨다.
	// 주의) select는 데이터 관련 연산 없이 데이터를 가져오기 때문에 insert, update, delete 와 중복되어도 상관없다.
	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dataSource());
	}

} // end RootConfig