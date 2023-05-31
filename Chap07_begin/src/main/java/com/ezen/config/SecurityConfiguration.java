package com.ezen.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
	
	@Autowired
	private DataSource dataSource;
	
	//Member 객체를 Spring 시큐리티의 UserDetails 객체로 변환하는 서비스
	@Autowired
	private BoardUserDetailService boardUserDetailService;
	/*
	 * security - 사용자 인증 정보를 가지고 있는 객체
	 */
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
		security.authorizeRequests().antMatchers("/").permitAll();
		//authenticated() - id, pwd를 통해 인증된 회원만 접근 가능한 URL지정
		security.authorizeRequests().antMatchers("/member/**").authenticated();
		security.authorizeRequests().antMatchers("/manager/**").hasRole("MANAGER");
		security.authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");
		
		security.csrf().disable();  //Cross Site Request Forgerty disable - csrf:타인의 아이디를 이용해 공격하는 행위
		security.formLogin();		//프로그래머가 만든 화면을 사용하겠다.
		// loginPage() - 지정한 URL을 요청하면 로그인 화면을 표시
		//security.formLogin().defaultSuccessUrl("/loginSuccess",true); //해당 URL을 타고 로그인이 되는 것이다.	true-리다이렉트	
		security.formLogin().loginPage("/login").defaultSuccessUrl("/loginSuccess",true);
		
		//접근 권한 없음 설정
		security.exceptionHandling().accessDeniedPage("/accessDenied");
		
		// 로그아웃 처리
		security.logout().invalidateHttpSession(true).logoutSuccessUrl("/login");
		 
		//테이블에서 사용자 조회 후, UserDetails 객체 변환 호출
		security.userDetailsService(boardUserDetailService);
		
		return security.build();  //위에서 설정한 권한 정보를 스프링 Security에 적용
		
	}
	
	/*
	 * 비밀번호 암호화
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	
	/*
	 * 메모리에 사용자 정보를 생성하고
	 * 로그인 인증 처리에 사용 (테스트용)
*/
	/*@Autowired
	public void autheticate(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.inMemoryAuthentication()
		.withUser("member")
		.password("{noop}1234")
		.roles("MEMBER");
	
		auth.inMemoryAuthentication()
		.withUser("manager")
		.password("{noop}1234")
		.roles("MANAGER");
		
		auth.inMemoryAuthentication()
		.withUser("admin")
		.password("{noop}1234")
		.roles("ADMIN");
	
	
	//사용자 인증 정보를 db에서 읽어와 설정
	String query1 = "SELECT id username, concat('{noop}',password) password, 'true' enabled FROM member WHERE id=?"; //사용자 정보 조회

	String query2 = "SELECT id username, role FROM member WHERE id=?"; //권한 조회
	
	auth.jdbcAuthentication()
		.dataSource(dataSource)
		.usersByUsernameQuery(query1)	//사용자 정보 조회 수행
		.authoritiesByUsernameQuery(query2);	//사용자 권한 조회 수행
		
}*/
	
	
}
