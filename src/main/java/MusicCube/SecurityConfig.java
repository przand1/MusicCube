package MusicCube;

import org.springframework.context.annotation.Configuration;


@Configuration
public class SecurityConfig
//extends WebSecurityConfigurerAdapter
{
/*
    @Override
    public void configure(final AuthenticationManagerBuilder auth)
            throws Exception
    {
        auth.inMemoryAuthentication().withUser("User")
                .password("User").roles("USER");
    }
*/
/*
    @Override
    protected void configure(final HttpSecurity http) throws Exception
    {
        http.cors();

        http.authorizeRequests()
                .antMatchers("/signIn").permitAll()
            .antMatchers("/**").access("hasRole('USER')")    // equivalent to <http auto-config="true">
            .and().formLogin()
            .and().httpBasic()
            .and().logout()
// CSRF is enabled by default (will discuss later)
            .and().csrf().disable();


    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(ImmutableList.of("*"));
        configuration.setAllowedMethods(ImmutableList.of("HEAD",
                "GET", "POST", "PUT", "DELETE", "PATCH"));
        // setAllowCredentials(true) is important, otherwise:
        // The value of the 'Access-Control-Allow-Origin' header in the response must not be the wildcard '*' when the request's credentials mode is 'include'.
        configuration.setAllowCredentials(true);
        // setAllowedHeaders is important! Without it, OPTIONS preflight request
        // will fail with 403 Invalid CORS request
        configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type", "Origin", "X-Requested-With", "Accept"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
*/
}
