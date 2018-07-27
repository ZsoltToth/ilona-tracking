package uni.miskolc.ips.ilona.tracking.persist.mysql;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import uni.miskolc.ips.ilona.tracking.model.PasswordRecoveryToken;
import uni.miskolc.ips.ilona.tracking.persist.SecurityFunctionsDAO;
import uni.miskolc.ips.ilona.tracking.persist.exception.OperationExecutionErrorException;
import uni.miskolc.ips.ilona.tracking.persist.exception.PasswordRecoveryTokenNotFoundException;
import uni.miskolc.ips.ilona.tracking.persist.exception.UserNotFoundInDatabaseException;
import uni.miskolc.ips.ilona.tracking.persist.mysql.mappers.SecurityFunctionsUserMapper;
import uni.miskolc.ips.ilona.tracking.persist.mysql.model.PasswordRecoveryTokenMapper;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Properties;

public class MySqlAndMybatisSecurityFunctionsDAOImpl implements SecurityFunctionsDAO {

    private static Logger logger = LogManager.getLogger(MySqlAndMybatisSecurityFunctionsDAOImpl.class);

    private SqlSessionFactory sessionFactory;

    public MySqlAndMybatisSecurityFunctionsDAOImpl() {

    }

    public MySqlAndMybatisSecurityFunctionsDAOImpl(final String host, final int port, final String database,
                                                   final String user, final String password) throws FileNotFoundException {
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
    public void updatePassword(String userid, String hashedPassword)
            throws UserNotFoundInDatabaseException, OperationExecutionErrorException {
        SqlSession session = sessionFactory.openSession();
        try {
            SecurityFunctionsUserMapper mapper = session.getMapper(SecurityFunctionsUserMapper.class);
            int updated = mapper.updatePassword(userid, hashedPassword);
            if (updated == 0) {
                throw new UserNotFoundInDatabaseException("User not found!");
            }
            session.commit();
        } catch (Exception e) {
            if (e instanceof UserNotFoundInDatabaseException) {
                logger.error("User has not found with id: " + userid + "  Error: " + e.getMessage());
                throw new UserNotFoundInDatabaseException("User has not found with id: " + userid + "  Error: " + e.getMessage());
            }
            logger.error("Error: " + e.getMessage());
            throw new OperationExecutionErrorException("Error: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void updateEnabled(String userid, boolean enabled)
            throws UserNotFoundInDatabaseException, OperationExecutionErrorException {
        SqlSession session = sessionFactory.openSession();
        try {
            SecurityFunctionsUserMapper mapper = session.getMapper(SecurityFunctionsUserMapper.class);
            int updated = mapper.updateEnabled(userid, enabled);
            if (updated == 0) {
                throw new UserNotFoundInDatabaseException("User not found!");
            }
            session.commit();
        } catch (Exception e) {
            if (e instanceof UserNotFoundInDatabaseException) {
                logger.error("User has not found with id: " + userid + "  Error: " + e.getMessage());
                throw new UserNotFoundInDatabaseException("User has not found with id: " + userid + "  Error: " + e.getMessage());
            }
            logger.error("Error: " + e.getMessage());
            throw new OperationExecutionErrorException("Error: " + e.getMessage());
        } finally {
            session.close();
        }

    }

    @Override
    public void updateAccountExpiration(String userid, Date expiration)
            throws UserNotFoundInDatabaseException, OperationExecutionErrorException {
        SqlSession session = sessionFactory.openSession();
        try {
            SecurityFunctionsUserMapper mapper = session.getMapper(SecurityFunctionsUserMapper.class);
            int updated = mapper.updateAccountExpiration(userid, expiration);
            if (updated == 0) {
                throw new UserNotFoundInDatabaseException("User not found!");
            }
            session.commit();
        } catch (Exception e) {
            if (e instanceof UserNotFoundInDatabaseException) {
                logger.error("User has not found with id: " + userid + "  Error: " + e.getMessage());
                throw new UserNotFoundInDatabaseException("User has not found with id: " + userid + "  Error: " + e.getMessage());
            }
            logger.error("Error: " + e.getMessage());
            throw new OperationExecutionErrorException("Error: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void updateRoles(String userid, Collection<String> roles)
            throws UserNotFoundInDatabaseException, OperationExecutionErrorException {
        SqlSession session = sessionFactory.openSession();
        try {
            SecurityFunctionsUserMapper mapper = session.getMapper(SecurityFunctionsUserMapper.class);
            int updated = mapper.eraseRoles(userid);
            if (updated == 0) {
                throw new UserNotFoundInDatabaseException("User not found!");
            }
            updated = mapper.insertRoles(userid, roles);
            // throw integrity Constraint?!
            if (updated == 0) {
                throw new OperationExecutionErrorException();
            }
            session.commit();
        } catch (Exception e) {
            if (e instanceof UserNotFoundInDatabaseException
                    || e.getCause() instanceof MySQLIntegrityConstraintViolationException) {
                logger.error("User has not found with id: " + userid + "  Error: " + e.getMessage());
                throw new UserNotFoundInDatabaseException("User has not found with id: " + userid + "  Error: " + e.getMessage());
            }
            logger.error("Error: " + e.getMessage());
            throw new OperationExecutionErrorException("Error: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public int eraseBadLogins(String userid) throws UserNotFoundInDatabaseException, OperationExecutionErrorException {
        SqlSession session = sessionFactory.openSession();
        int affectedRows = 0;
        try {
            SecurityFunctionsUserMapper mapper = session.getMapper(SecurityFunctionsUserMapper.class);
            affectedRows = mapper.eraseBadLogins(userid);
            session.commit();
        } catch (Exception e) {
            logger.error("Error: " + e.getMessage());
            throw new OperationExecutionErrorException("Error: " + e.getMessage());
        } finally {
            session.close();
        }
        return affectedRows;
    }

    @Override
    public void updateBadLogins(String userid, Collection<Date> badLogins)
            throws UserNotFoundInDatabaseException, OperationExecutionErrorException {
        SqlSession session = sessionFactory.openSession();
        try {
            SecurityFunctionsUserMapper mapper = session.getMapper(SecurityFunctionsUserMapper.class);
            mapper.eraseBadLogins(userid);
            if (badLogins != null && badLogins.size() != 0) {
                Collection<Double> millisecondsDate = new ArrayList<Double>();
                for (Date date : badLogins) {
                    millisecondsDate.add(date.getTime() * 0.001);
                }
                mapper.insertBadLogins(userid, millisecondsDate);
            }
            session.commit();
        } catch (Exception e) {
            if (e.getCause() instanceof MySQLIntegrityConstraintViolationException) {
                logger.error("User has not found with id: " + userid + "  Error: " + e.getMessage());
                throw new UserNotFoundInDatabaseException("User has not found with id: " + userid + "  Error: " + e.getMessage());
            }
            logger.error("Error: " + e.getMessage());
            throw new OperationExecutionErrorException("Error: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void updateLockedAndUntilLocked(String userid, boolean nonLocked, Date lockedUntil, boolean deleteBadLogins)
            throws UserNotFoundInDatabaseException, OperationExecutionErrorException {
        SqlSession session = sessionFactory.openSession();
        try {
            SecurityFunctionsUserMapper mapper = session.getMapper(SecurityFunctionsUserMapper.class);
            Double millisecDate = lockedUntil.getTime() * 0.001;
            int updated = mapper.updateLockedAndUntilLocked(userid, nonLocked, millisecDate);
            if (updated == 0) {
                throw new UserNotFoundInDatabaseException("User not found!");
            }
            if (deleteBadLogins == true) {
                mapper.eraseBadLogins(userid);
            }
            session.commit();
        } catch (Exception e) {
            if (e instanceof UserNotFoundInDatabaseException) {
                logger.error("User has not found with id: " + userid + "  Error: " + e.getMessage());
                throw new UserNotFoundInDatabaseException("User has not found with id: " + userid + "  Error: " + e.getMessage());
            }
            logger.error("Error: " + e.getMessage());
            throw new OperationExecutionErrorException("Error: " + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public void storePasswordResetToken(PasswordRecoveryToken token) throws OperationExecutionErrorException {
        SqlSession session = sessionFactory.openSession();
        try {
            SecurityFunctionsUserMapper mapper = session.getMapper(SecurityFunctionsUserMapper.class);
            mapper.erasePasswordToken(token.getUserid());
            double dateWithMillis = token.getTokenValidUntil().getTime() * 0.001;
            mapper.storePasswordToken(token.getUserid(), token.getToken(), dateWithMillis);
            session.commit();

        } catch (Exception e) {
            String tokenMessage = "Token is null!";
            if (token != null) {
                tokenMessage = token.toString();
            }
            if (e.getCause() instanceof MySQLIntegrityConstraintViolationException) {
                logger.error(
                        "There is no user with this id! Token details: " + tokenMessage + " Error: " + e.getMessage());
                throw new OperationExecutionErrorException("Password recovery token storage error:" + e.getMessage());
            }
            logger.error("Password recovery token storage error! Token details: " + tokenMessage + " Error: "
                    + e.getMessage());
            throw new OperationExecutionErrorException("Password recovery token storage error! Token details: "
                    + tokenMessage + " Error: " + e.getMessage(), e);
        } finally {
            session.close();
        }

    }

    @Override
    public PasswordRecoveryToken restorePasswordResetToken(PasswordRecoveryToken token)
            throws PasswordRecoveryTokenNotFoundException, OperationExecutionErrorException {
        SqlSession session = sessionFactory.openSession();
        PasswordRecoveryToken recoveryToken = new PasswordRecoveryToken();
        try {
            SecurityFunctionsUserMapper mapper = session.getMapper(SecurityFunctionsUserMapper.class);
            PasswordRecoveryTokenMapper tokenMapper = mapper.readPasswordToken(token.getUserid());

            if (tokenMapper == null) {
                throw new PasswordRecoveryTokenNotFoundException("Token not found!");
            }

            recoveryToken.setUserid(tokenMapper.getUserid());
            recoveryToken.setToken(tokenMapper.getToken());
            recoveryToken.setTokenValidUntil(new Date(tokenMapper.getValidUntil()));

        } catch (Exception e) {
            String tokenMessage = "Token is null!";
            if (token != null) {
                tokenMessage = token.toString();
            }
            if (e instanceof PasswordRecoveryTokenNotFoundException) {

                logger.error("Password recovery token not found: Token details:" + tokenMessage + " Error: "
                        + e.getMessage());
                throw new OperationExecutionErrorException("Password recovery token not found: Token details:"
                        + tokenMessage + " Error: " + e.getMessage(), e);
            }
            logger.error(
                    "Password recovery database error: Token details:" + tokenMessage + " Error: " + e.getMessage());
            throw new OperationExecutionErrorException(
                    "Password recovery token not found: Token details:" + tokenMessage + " Error: " + e.getMessage(),
                    e);
        } finally {
            session.close();
        }
        return recoveryToken;
    }

    @Override
    public void deletePasswordRecoveryToken(String userid) throws OperationExecutionErrorException {
        SqlSession session = sessionFactory.openSession();
        try {
            SecurityFunctionsUserMapper mapper = session.getMapper(SecurityFunctionsUserMapper.class);
            mapper.erasePasswordToken(userid);
            session.commit();
        } catch (Exception e) {
            logger.error("Password recovery token database error: " + e.getMessage());
            throw new OperationExecutionErrorException("Password recovery token database error: " + e.getMessage(), e);
        }

    }

}
