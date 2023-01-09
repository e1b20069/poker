package oit.is.z1661.poker.poker.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.context.annotation.Bean;

@EnableWebSecurity
public class PokerAuthConfiguration {
  @Bean
  public InMemoryUserDetailsManager userDetailsService() {

    UserBuilder users = User.builder();

    UserDetails user1 = users
        .username("user1")
        .password("$2y$10$NuE6pUNnNdUMUc7g0MIg9.Tw6kwbRXJ7tDPxrha16PNFNxkjEXHk.")
        .roles("USER")
        .build();
    UserDetails user2 = users
        .username("user2")
        .password("$2y$10$V7r1bljsP5BRQhKZ1o2wpO/IsLx4t4.ROfGKrouY0g3.0U2UsZIEy")
        .roles("USER")
        .build();
    UserDetails user3 = users
        .username("user3")
        .password("$2y$10$8DBbHlF4Q/B8AhlXtL0s3u/n9AtILjF6wpFU.nHOfshgumoQPIdYq")
        .roles("USER")
        .build();
    UserDetails user4 = users
        .username("user4")
        .password("$2y$10$kKOIJva187Rha3Q484O0LeI32eZtbX24ULn35VMPxIvH.x7nTNCeG")
        .roles("USER")
        .build();
    UserDetails admin = users
        .username("admin")
        .password("$2y$10$QIUUNZaV0ehC0Ulk2wIKseox9B4.0tkasKV.3h4NWSy3kdVgdDAm6")
        .roles("ADMIN")
        .build();

    return new InMemoryUserDetailsManager(user1, user2, user3, user4, admin);
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.formLogin();
    http.authorizeHttpRequests()
        .mvcMatchers("/start/**").authenticated();
    http.logout().logoutSuccessUrl("/");
    http.csrf().disable();
    http.headers().frameOptions().disable();
    return http.build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
