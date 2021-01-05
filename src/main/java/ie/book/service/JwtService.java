package ie.book.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ie.book.domain.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {

	@Value("${security.jwt.expiration}")
	private String expiration;
	
	@Value("${security.jwt.secret-key}")
	private String secretKey;
	
	public String addToken(User user) {
		long expString = Long.valueOf(expiration);
		LocalDateTime dateHourExpiration = LocalDateTime.now().plusDays(expString);
		Instant instant = dateHourExpiration.atZone(ZoneId.systemDefault()).toInstant();
		Date date = Date.from(instant);
		
		return Jwts
				.builder()
				.setSubject(user.getEmail())
				.setExpiration(date)
				.signWith(SignatureAlgorithm.HS512, secretKey)
				.compact();
	}
	
	private Claims getClaims(String token) throws ExpiredJwtException {
		return Jwts
				.parser()
				.setSigningKey(secretKey)
				.parseClaimsJws(token)
				.getBody();
	}
	
	public boolean tokenValid(String token) {
		try {
			Claims claims = getClaims(token);
			Date dateExpiration = claims.getExpiration();
			LocalDateTime date =
					dateExpiration.toInstant()
						.atZone(ZoneId.systemDefault()).toLocalDateTime();
			return !LocalDateTime.now().isAfter(date);
		} catch (Exception e) {
			return false;
		}
	}
	
	public String getEmailUser(String token) throws ExpiredJwtException {
		return (String) getClaims(token).getSubject();
	}
}