package Ali.ashique.ITCENTERFEESMANAGEMENT.configuration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsService userService;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/webjars/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/login");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).
                passwordEncoder(getPasswordEncoder());
    }

    @Bean
    public SecurityContext getAuthentication() {
        return SecurityContextHolder.getContext();
    }

}

//        auth.inMemoryAuthentication().
//
//                withUser("ali").
//                password(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("123")).
//                roles("USER").
//                and().
//                withUser("sham").
//                password(new StandardPasswordEncoder("53cr3t").encode("456")).
//                roles("ADMIN");

//        System.out.println("Password is:"+new StandardPasswordEncoder("53cr3t").encode("456"));
//    @Bean
//    protected UserDetailsService userDetailsService() {
//
//        List<UserDetails> users=new LinkedList<>();
//        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//        users.add(User.builder().username("ashiq").password(passwordEncoder.encode("ali")).roles("USER").build());
//        users.add(User.withUsername("alisham").password("shams").roles("USER").build());
//        users.add(User.builder().username("ali").password(passwordEncoder.encode("al")) .roles("USER").build());
//
//        return new InMemoryUserDetailsManager(users);
//    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests().anyRequest().authenticated().and().formLogin().loginPage("/login").permitAll();
//    }
