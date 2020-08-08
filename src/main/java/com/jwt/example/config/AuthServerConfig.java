package com.jwt.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import com.jwt.example.service.MyClientDetailsService;

@Configuration
@EnableAuthorizationServer
public class AuthServerConfig extends AuthorizationServerConfigurerAdapter {

	@Autowired
	private MyClientDetailsService service;
	
	@Autowired
	private AuthenticationManager manager;
	
	
	@Bean
	public TokenStore tokenStore() {
		TokenStore store = new JwtTokenStore(converter());
		return store;
	}
	
	
	/**
	 * Symmetric key implementation(not recommended when 
	 * authorization server and resource server are in different application)
	 * 
	 */
//	@Bean
//	public JwtAccessTokenConverter converter() {
//		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//		converter.setSigningKey("secret");
//		return converter;
//	}
	
	
	/**
	 * JWT Asymmetric key implementation
	 * generate keypair command: keytool -genkeypair -alias ssia -keyalg RSA -keypass ssia123 -keystore ssia.jks -storepass ssia123
	 * extract public key command: keytool -list -rfc --keystore ssia.jks | openssl x509 -inform pem -pubkey
	 * IT IS NOT A GOOD PRACTICE TO STORE KEYPAIR IN HE CLASSPATH! 
	 */
	
	@Bean
	public JwtAccessTokenConverter converter() {
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		KeyStoreKeyFactory factory = new KeyStoreKeyFactory(new ClassPathResource("ssia.jks"), "ssia123".toCharArray());
		converter.setKeyPair(factory.getKeyPair("ssia"));
		return converter;
	}
	
	
	/**
	 * configure endpoint security
	 */
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security.tokenKeyAccess("isAuthenticated()");
	}

	
	/**
	 * For client that configured in the database, MUST SPECIFY SCOPE, otherwise cannot be used by the application/
	 * However, if configure it in memory, you can ignore the scope part.
	 */
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.withClientDetails(service);
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.authenticationManager(manager).accessTokenConverter(converter()).tokenStore(tokenStore());
	}

}
