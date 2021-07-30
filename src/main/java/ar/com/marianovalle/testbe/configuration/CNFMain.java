package ar.com.marianovalle.testbe.configuration;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import ar.com.marianovalle.testbe.jwt.JWTEntryPoint;
import ar.com.marianovalle.testbe.jwt.JWTTokenFilter;
import ar.com.marianovalle.testbe.service.interfaces.INTUser;

/**
 * General configuration.
 * @author Mariano Valle
 * @version 1.0
 *
 */
@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CNFMain extends WebSecurityConfigurerAdapter implements WebMvcConfigurer {

	@Autowired
	INTUser ssiUser;

	@Autowired
	JWTEntryPoint jwtEntryPoint;
	private static final String ANT_MATCHER_AUTH = "/auth/**";
	private static final String ANT_MATCHER_MAILS = "/mails/**";
	private static final String ANT_MATCHER_SCRIPTS = "/scripts/**";
	private static final String ANT_MATCHER_STYLES = "/styles/**";
	private static final String ANT_MATCHER_IMAGES = "/images/**";
	private static final String ANT_MATCHER_ERRORS = "/error/**";

	private static final String ANT_MATCHER_SWAGGER_DOCS = "/v2/api-docs";
	private static final String ANT_MATCHER_SWAGGER_CONFIGURATION = "/configuration/ui";
	private static final String ANT_MATCHER_SWAGGER_SECURITY = "/configuration/security";
	private static final String ANT_MATCHER_SWAGGER_WEB_JARS = "/webjars/**";
	private static final String ANT_MATCHER_SWAGGER_CONTEXT_ROOT = "/context-root/**";

	private static final String[] ANT_MATCHERS = { ANT_MATCHER_AUTH, ANT_MATCHER_MAILS, ANT_MATCHER_SCRIPTS,
			ANT_MATCHER_STYLES, ANT_MATCHER_IMAGES, ANT_MATCHER_ERRORS, ANT_MATCHER_SWAGGER_DOCS,
			ANT_MATCHER_SWAGGER_CONFIGURATION, ANT_MATCHER_SWAGGER_SECURITY,
			ANT_MATCHER_SWAGGER_WEB_JARS, ANT_MATCHER_SWAGGER_CONTEXT_ROOT };

	@Bean
	public JWTTokenFilter jwtTokenFilter() {
		return new JWTTokenFilter();
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}

	/**
	 * Agregar usuarios y configurarlos. Se pueden agregar usuarios de
	 * memoria: auth.inMemoryAuthentication() .WithUser("admin")
	 * .password("{noop}123") .roles("ADMIN", "USER")
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(this.ssiUser).passwordEncoder(passwordEncoder());
	}

	/**
	 * Restringir las urls.
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		/**
		 * Serán ignorados por la seguridad.
		 */
		web.ignoring().antMatchers(ANT_MATCHERS);
	}

	/**
	 * HttpSecurity: Permite configurar la seguridad basada en web para solicitudes
	 * HTTP específicas. De forma predeterminada, se aplicará a todas las
	 * solicitudes, pero se puede restringir utilizando
	 * requestMatcher(RequestMatcher) u otros métodos similares.
	 */
	//@Override
	public void configure2(HttpSecurity http) throws Exception {
		http
		
				// ----------------------------------------------------------------------------------------
				// Para usar localmente
				// .cors()
				.cors().disable()
				// Para usar en la web descomentar y comentar @CrossOrigin en las rest.
				//.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues()).and()
				// ----------------------------------------------------------------------------------------
				.authorizeRequests()
				// .antMatchers("/rest/**").authenticated()
				// .antMatchers(ANT_MATCHER_AUTH).permitAll()
				.antMatchers().permitAll()
				.anyRequest().authenticated().and()
				// Significa que se puede acceder a todas las interfaces relacionadas con el
				// inicio de sesión sin autenticación.
				.formLogin()
				// Procesador de inicio de sesión exitoso
				.successHandler(new AuthenticationSuccessHandler() {
					@Override
					public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
							Authentication a) throws IOException, ServletException {
						// Para cambiar el cuerpo de los métodos generados.
						response.setStatus(HttpServletResponse.SC_OK);
					}
				}).failureHandler(new AuthenticationFailureHandler() {
					@Override
					public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
							AuthenticationException ae) throws IOException, ServletException {
						// Para cambiar el cuerpo de los métodos generados.
						response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					}
				})
				// Vaya a la página de inicio de sesión / login_page, la URL de acceso sigue
				// siendo /login
				.loginPage("/login_page").loginProcessingUrl("/auth/login").and()
				// Función de cierre de sesión.
				.logout().logoutUrl("/auth/logout").logoutSuccessHandler(new LogoutSuccessHandler() {
					@Override
					public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
							Authentication a) throws IOException, ServletException {
						// Para cambiar el cuerpo de los métodos generados.
						response.setStatus(HttpServletResponse.SC_NO_CONTENT);
					}
				})
				// Borrar información del usuario.
				//.clearAuthentication(true)
				// Borrar la sesión del usuario.
				.invalidateHttpSession(true).and().exceptionHandling().authenticationEntryPoint(jwtEntryPoint).and()
				// Disabled CSRF protection.
				.csrf().disable();

		//http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
	//@Autowired
	//AuthenticationSuccessHandler successHandler;
	
	@Override
	public void configure(HttpSecurity http) throws Exception {
	    http
	    .authorizeRequests()
	        //.antMatchers(ANT_MATCHER_AUTH).access("hasRole('USER')")
	    	.antMatchers(ANT_MATCHER_AUTH).hasAnyRole("ADMIN")
	    	.antMatchers(ANT_MATCHER_AUTH).hasAnyRole("USER")
	    .and().exceptionHandling()
	        .accessDeniedPage("/403")
	    .and().formLogin()
	    .and().logout()
	        .logoutSuccessUrl("/login?logout=true")
	        .invalidateHttpSession(true)
	    .and()
	        .csrf()
	            .disable();		
/*
		http
		.csrf().disable()
		.authorizeRequests()
		.antMatchers("/admin/dashboard").hasAnyRole("ADMIN")
		.antMatchers("/user/dashboard").hasAnyRole("USER")
		.and().formLogin().loginPage("/login")
			.successHandler(successHandler)
		.permitAll()
		.and().logout()
		.and().exceptionHandling().accessDeniedPage("/accessdenied");		
*/		
		
//		http
//		.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues()).and()
//        .authorizeRequests()
//        .antMatchers("/login/**").permitAll()
//        .anyRequest().authenticated();

/*		
		// ----------------------------------------------------------------------------------------
		// Para usar localmente
		// .cors()
		// .cors().disable()
		// Para usar en la web descomentar y comentar @CrossOrigin en las rest.
		.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues()).and()
		// ----------------------------------------------------------------------------------------
		.authorizeRequests()
		//.antMatchers("/rest/**").authenticated()
		//.antMatchers(ANT_MATCHER_AUTH).permitAll()
		.antMatchers(ANT_MATCHER_AUTH).authenticated()
		.anyRequest().authenticated().and()
		// Significa que se puede acceder a todas las interfaces relacionadas con el
		// inicio de sesión sin autenticación.
		.formLogin()
		// Procesador de inicio de sesión exitoso
		.successHandler(new AuthenticationSuccessHandler() {
			@Override
			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
					Authentication a) throws IOException, ServletException {
				// Para cambiar el cuerpo de los métodos generados.
				response.setStatus(HttpServletResponse.SC_OK);
			}
		}).failureHandler(new AuthenticationFailureHandler() {
			@Override
			public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
					AuthenticationException ae) throws IOException, ServletException {
				// Para cambiar el cuerpo de los métodos generados.
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			}
		})
		// Vaya a la página de inicio de sesión / login_page, la URL de acceso sigue
		// siendo /login
		.loginPage("/login_page").loginProcessingUrl("/auth/login").and()
		// Función de cierre de sesión.
		.logout().logoutUrl("/auth/logout").logoutSuccessHandler(new LogoutSuccessHandler() {
			@Override
			public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
					Authentication a) throws IOException, ServletException {
				// Para cambiar el cuerpo de los métodos generados.
				response.setStatus(HttpServletResponse.SC_NO_CONTENT);
			}
		})
		// Borrar información del usuario.
		.clearAuthentication(true)
		// Borrar la sesión del usuario.
		.invalidateHttpSession(true).and().exceptionHandling().authenticationEntryPoint(jwtEntryPoint).and()
		// Disabled CSRF protection.
		.csrf().disable();
*/
		http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

	}	
	

	/**
	 * Para usar los DTO.
	 * 
	 * @return ModelMapper
	 */
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedMethods("*").allowedHeaders("*");
	}

}
