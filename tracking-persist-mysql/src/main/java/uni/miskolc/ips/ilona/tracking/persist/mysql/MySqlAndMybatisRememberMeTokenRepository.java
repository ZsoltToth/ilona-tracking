package uni.miskolc.ips.ilona.tracking.persist.mysql;

import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import uni.miskolc.ips.ilona.tracking.persist.mysql.mappers.RememberMeTokenMapper;
import uni.miskolc.ips.ilona.tracking.persist.mysql.model.PersistenceTokenMapper;

public class MySqlAndMybatisRememberMeTokenRepository implements PersistentTokenRepository {

	private static Logger logger = LogManager.getLogger(MySqlAndMybatisRememberMeTokenRepository.class);

	private SqlSessionFactory sessionFactory;

	public MySqlAndMybatisRememberMeTokenRepository(final String host, final int port, final String database,
			final String user, final String password) {
		ClassLoader loader = getClass().getClassLoader();
		InputStream inputStream = loader.getResourceAsStream("mybatis-configurationa.xml");

		String urlPattern = "jdbc:mysql://%s:%s/%s";
		String connectionURL = String.format(urlPattern, host, port, database);
		Properties props = new Properties();
		props.put("driver", "com.mysql.jdbc.Driver");
		props.put("url", connectionURL);
		props.put("username", user);
		props.put("password", password);

		this.sessionFactory = (new SqlSessionFactoryBuilder()).build(inputStream, props);
	}

	@Override
	public void createNewToken(PersistentRememberMeToken token) {
		SqlSession session = sessionFactory.openSession();
		try {
			RememberMeTokenMapper mapper = session.getMapper(RememberMeTokenMapper.class);
			mapper.createNewToken(token);
			session.commit();
		} catch (Exception e) {
			logger.error("Token creation error! Cause: " + e.getMessage());
			// no throw?!
		} finally {
			session.close();
		}
	}

	@Override
	public PersistentRememberMeToken getTokenForSeries(String series) {
		SqlSession session = sessionFactory.openSession();
		try {
			RememberMeTokenMapper mapper = session.getMapper(RememberMeTokenMapper.class);
			PersistenceTokenMapper tokenMapper = mapper.getTokenForSeries(series);
			System.out.println("TOKEN LONG: " + tokenMapper.getDate());
			PersistentRememberMeToken restoredToken = new PersistentRememberMeToken(tokenMapper.getUsername(),
					tokenMapper.getSeries(), tokenMapper.getTokenValue(), new Date((long)tokenMapper.getDate() * 1000L));
			System.out.println(restoredToken.getDate());
			return restoredToken;
		} catch (Exception e) {
			logger.error("Token restore error! Cause: " + e.getMessage());
			// no throw?!
		} finally {
			session.close();
		}
		return null;
	}

	@Override
	public void removeUserTokens(String userid) {
		SqlSession session = sessionFactory.openSession();
		try {
			RememberMeTokenMapper mapper = session.getMapper(RememberMeTokenMapper.class);
			mapper.removeUserTokens(userid);
			session.commit();
		} catch (Exception e) {
			logger.error("Token restore error! Cause: " + e.getMessage());
			// no throw?!
		} finally {
			session.close();
		}
	}

	@Override
	public void updateToken(String series, String tokenValue, Date lastUsed) {
		SqlSession session = sessionFactory.openSession();
		try {
			RememberMeTokenMapper mapper = session.getMapper(RememberMeTokenMapper.class);
			mapper.updateToken(series, tokenValue, lastUsed);
			session.commit();
		} catch (Exception e) {
			logger.error("Token restore error! Cause: " + e.getMessage());
			// no throw?!
		} finally {
			session.close();
		}

	}

}
