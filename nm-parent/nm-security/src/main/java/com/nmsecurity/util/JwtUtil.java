package com.nmsecurity.util;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.nmcommon.exception.BusinessException;
import com.nmsecurity.model.AuthRequest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * 
 * @author Jinesh KP
 *
 */

//Basic idea for Create, Validate JWT token

public class JwtUtil {
	private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);
	
	public static String setTokenInfo(AuthRequest authRequest) {
		ObjectMapper mapper = new ObjectMapper();
		String jsonStr = "";
		try {					
			jsonStr = mapper.writeValueAsString(authRequest);
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		String token = generateToken(jsonStr);
		return token;
		
	}
	
	public static String generateToken(String input) {

		Map<String, Object> claims = new HashMap<>();		
		return createToken(claims,input);
	}
	
	//Token expired in 1 hours
	private static String createToken(Map<String, Object> claims,String input) {
		
		return Jwts.builder().setClaims(claims).setSubject(input).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 1))
				.signWith(SignatureAlgorithm.HS256, "SECRET_KEY").compact();
	}
	
	private static Claims extractAllClaims(String token){
		return Jwts.parser().setSigningKey("SECRET_KEY").parseClaimsJws(token).getBody();
	}
	
	private static <T> T extractClaim(String token, Function<Claims,T> claimsResolver){
		final Claims claims = extractAllClaims(token);			
		return claimsResolver.apply(claims);
		
	}

	public static Object extractUserRequestFromToken(String token) {						
		return tokenToObj((String)extractClaim(token,Claims::getSubject));
	}
	
	
	private static Date extractExpiration(String token){
		Date date = extractClaim(token,Claims::getExpiration);
		log.info(" extractExpiration : "+date);
		return date;
	}
	
	private static boolean isTokenExpired(String token) {		
		boolean isTokenExpired = extractExpiration(token).before(new Date());
		log.info(" isTokenExpired : "+isTokenExpired);
		return isTokenExpired;
	}
	
	public static boolean validateToken(String token,Object inputFromFilter) {
		boolean isvalidateToken = false;
		if(inputFromFilter instanceof AuthRequest) {
			//final String fromToken = (String) ;
			AuthRequest authRequest = (AuthRequest) extractUserRequestFromToken(token);
			isvalidateToken = (tokenValidationFactory(authRequest,((AuthRequest)inputFromFilter)) && !isTokenExpired(token));			
		}						
		return isvalidateToken;		
	}
	
	public static AuthRequest tokenToObj(String fromToken) {
		Gson gson = new Gson();
		AuthRequest obj = null;
		try {					
			obj =  gson.fromJson(fromToken, AuthRequest.class);			
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		return obj;
	}
	
	private static boolean tokenValidationFactory(AuthRequest fromToken,AuthRequest inputFromFilter){
		boolean isTokenValid = false;
		if(StringUtils.equals(fromToken.toString(), inputFromFilter.toString())) {
			isTokenValid = true;
		}
		return isTokenValid;
	}
	
	/*public static void main(String[] args) {
		String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ7XCJtb2JpbGVcIjpudWxsLFwiZW1haWxcIjpudWxsLFwib3RwXCI6bnVsbCxcInVzZXJpZFwiOlwiVVNSMDNcIixcInVzZXJOYW1lXCI6XCJVU0VSXCIsXCJwYXNzd29yZFwiOlwiJDJhJDEwJGlVc0t4RVpjbUJQWURZdDFFV3JmdmV3RFF3cDg1SHBXek9wbEJZRVRPbkRzZ09sbkM1YnNxXCIsXCJyb2xlXCI6XCJVc2VyXCIsXCJyb2xlRW5hYmxlXCI6dHJ1ZX0iLCJleHAiOjE1OTM3ODQ4MTcsImlhdCI6MTU5Mzc4MTIxN30.WwbzwR5jxhZDhwRy4P7TTPa4yhoXJQxtaJvGLoTfm0A";
		Gson gson = new Gson();
		//AuthRequest obj = null;
		AuthRequest obj = (AuthRequest) extractUserRequestFromToken(token);
		System.out.println(" "+obj.toString());
		//System.out.println(" "+json);
		try {					
			obj =  gson.fromJson(json, AuthRequest.class);
			System.out.println(" "+obj.toString());
		} catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		
		System.out.println(" "+validateToken(token,obj));
				
		//AuthRequest fromToken = (AuthRequest) extractUserRequestFromToken(token);
		//System.out.println(" "+fromToken.toString());
	}*/
	
}
