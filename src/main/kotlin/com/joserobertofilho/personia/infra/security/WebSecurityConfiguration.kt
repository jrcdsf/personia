import com.fasterxml.jackson.databind.ObjectMapper
import com.joserobertofilho.personia.infra.security.JWTAuthenticationFilter
import com.joserobertofilho.personia.infra.security.JWTAuthorizationFilter
import com.joserobertofilho.personia.infra.util.JWTUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder


@Configuration
@EnableWebSecurity
class WebSecurityConfiguration: WebSecurityConfigurerAdapter() {

    @Autowired
    private lateinit var userDetailsService: UserDetailsService

    @Autowired
    private lateinit var jwtUtil: JWTUtil

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @Autowired
    private lateinit var bCryptPasswordEncoder: BCryptPasswordEncoder


    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/", "/signup").permitAll()
            .anyRequest().authenticated()


        http.addFilter(JWTAuthenticationFilter(authenticationManager(), jwtUtil = jwtUtil, objectMapper))
        http.addFilter(JWTAuthorizationFilter(authenticationManager(), jwtUtil = jwtUtil, userDetailService = userDetailsService))
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }



    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder)
    }

}