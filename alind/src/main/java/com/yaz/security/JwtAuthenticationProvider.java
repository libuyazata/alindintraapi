package com.yaz.security;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Used for checking the token from the request and supply the UserDetails if the token is valid
 *
 * @author pascal alma
 */
@Component
public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    @Autowired
    private JwtTokenValidator jwtTokenValidator;
    
    

    public void setJwtTokenValidator(JwtTokenValidator jwtTokenValidator) {
		this.jwtTokenValidator = jwtTokenValidator;
	}

	@Override
    public boolean supports(Class<?> authentication) {
        return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
        String token = jwtAuthenticationToken.getToken();

        JwtUserDto parsedUser = jwtTokenValidator.parseToken(token);

        if (parsedUser == null) {
//            throw new JwtTokenMalformedException("JWT token is not valid");
        	try {
				throw new Exception("JWT token is not valid");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

        List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(parsedUser.getRole());

        return (UserDetails) new AuthenticatedUser(parsedUser.getId(), parsedUser.getUsername(), token, authorityList);
    }

}

