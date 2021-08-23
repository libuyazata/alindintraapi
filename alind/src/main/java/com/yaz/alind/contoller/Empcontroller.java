package com.yaz.alind.contoller;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.yaz.security.Iconstants;



@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class Empcontroller {

	
	@RequestMapping(value="/welcome", method = RequestMethod.GET)
	public ResponseEntity<String> welcome(@RequestHeader("token") String token) {
		System.out.println("Empcontroller,token: "+token);
		
		// Parsing the jwt token.
		Jws<Claims> claims = Jwts.parser().requireIssuer(Iconstants.ISSUER).setSigningKey(Iconstants.SECRET_KEY).parseClaimsJws(token);

		// Obtaining the claims from the parsed jwt token.
		String user= (String) claims.getBody().get("usr");
		String roles= (String) claims.getBody().get("rol");
		String time= (String) claims.getBody().get("iat");
		
		System.out.println("Jwtauthfilter,user: "+user+",roles: "+roles);
		System.out.println("Time, : "+time);
		return new ResponseEntity<String>("Welcome User!", HttpStatus.OK);
	}
}