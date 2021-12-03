package edu.cpcc.labs.taskbook.auth;

public interface AuthConstants {
	 public static final String SECRET = "The-Dark-Invader-OB1-R2D2";
	 public static final long EXPIRATION_TIME = 432_000_000; 
	 public static final String TOKEN_PREFIX = "Bearer ";
	 public static final String HEADER_STRING = "Authorization";
	 public static final String SIGN_UP_URL = "/api/my-post-service/users/register";
	 public static final String SIGN_IN_URL = "/api/my-post-service/users/login";
	 
}
