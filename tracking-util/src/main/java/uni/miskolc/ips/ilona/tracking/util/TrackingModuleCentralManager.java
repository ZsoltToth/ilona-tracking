package uni.miskolc.ips.ilona.tracking.util;

/**
 * <h2>This class provides the center program flow managament for the tracking
 * system.</h2><br>
 * This class is responsible for managing the program flow in the system.<br>
 * In this class,the manager can change the behaviour of the modul.<br>
 * Example:<br>
 * -
 * 
 * @author Patrik / A5USl0
 *
 */
public class TrackingModuleCentralManager {

	/*
	 * user bejelentkezett és az admin megváltoztatta az adatait, akkor
	 * frissüljön a user is? kéne egy lista, amibe bele lehet tenni az adatokat
	 * és onnan kivenné a user dátum alapján a változtatás és így csak akkor
	 * frissíti, ha a bejelentkezés hamarabbi, mint a változtatás
	 * Map<String,Date> => Map<userid, módosítás dátuma>
	 */

	/*
	 * TIME CONSTANTS
	 */
	// =======================================================================================
	/*
	 * Time intervals in milliseconds: 1 hour: 3600000L 1 day: 86400000L 1 week:
	 * 604800000L 1 month: 2678400000L 1 year: 31536000000L
	 */
	public static final long oneHourInMilliseconds = 3_600_000L;

	public static final long oneDayInMiliseconds = 86_400_000L;

	public static final long oneWeekInMilliseconds = 604_800_000L;

	public static final long oneMonthInMilliseconds = 2_678_400_000L;

	public static final long oneYearInMilliseconds = 31_536_000_000L;

	/*
	 * LOCKS
	 */
	// ===================================================================================
	private Object enabledCheckEnabledLock = new Object();

	private Object accountExpirationCheckEnabledLock = new Object();

	private Object accountExpirationTimeLock = new Object();

	private Object credentialsValidityPeriodLock = new Object();

	private Object lockedCheckEnabledLock = new Object();

	private Object badLoginsUpperBoundLock = new Object();

	private Object lockedTimeAfterBadLoginsLock = new Object();

	private Object passwordRecoveryTokenValidityTimeLock = new Object();

	/*
	 * ACCOUNT CHECK RESTRICTIONS
	 */
	// =====================================================================================

	private boolean enabledCheckEnabled = true;

	private boolean accountExpirationCheckEnabled = true;

	private long accountExpirationTime = 31_536_000_000L;

	/**
	 * Credentials validity time in milliseconds.
	 */
	private long credentialsValidityPeriod = 31536000000L;

	private long badLoginsUpperBound = 10;

	private boolean lockedCheckEnabled = true;

	/*
	 * Default: 1 hour
	 */
	private long lockedTimeAfterBadLogins = 3600000L;

	/*
	 * Default: one day
	 */
	private long passwordRecoveryTokenValidityTime = 86_400_000L;

	/*
	 * Setters / getters with lock/field
	 */
	// =======================================================================================

	public long getCredentialsValidityPeriod() {
		synchronized (credentialsValidityPeriodLock) {
			return this.credentialsValidityPeriod;
		}

	}

	public void setCredentialsValidityPeriod(long credentialsValidityPeriod) {
		synchronized (credentialsValidityPeriodLock) {
			this.credentialsValidityPeriod = credentialsValidityPeriod;
		}
	}

	public long getPasswordRecoveryTokenValidityTime() {
		synchronized (passwordRecoveryTokenValidityTimeLock) {
			return this.passwordRecoveryTokenValidityTime;
		}

	}

	public void setPasswordRecoveryTokenValidityTime(long passwordRecoveryTokenValidityTime) {
		synchronized (passwordRecoveryTokenValidityTimeLock) {
			this.passwordRecoveryTokenValidityTime = passwordRecoveryTokenValidityTime;
		}
	}

	public long getAccountExpirationTime() {
		synchronized (accountExpirationTimeLock) {
			return this.accountExpirationTime;
		}

	}

	public void setAccountExpirationTime(long accountExpirationTime) {
		synchronized (accountExpirationTimeLock) {
			this.accountExpirationTime = accountExpirationTime;
		}
	}

	public boolean isEnabledCheckEnabled() {
		synchronized (enabledCheckEnabledLock) {
			return enabledCheckEnabled;
		}

	}

	public void setEnabledCheckEnabled(boolean enabledCheckEnabled) {
		synchronized (enabledCheckEnabledLock) {
			this.enabledCheckEnabled = enabledCheckEnabled;
		}
	}

	public boolean isAccountExpirationCheckEnabled() {
		synchronized (accountExpirationCheckEnabledLock) {
			return accountExpirationCheckEnabled;
		}
	}

	public void setAccountExpirationCheckEnabled(boolean accountExpirationCheckEnabled) {
		synchronized (accountExpirationCheckEnabledLock) {
			this.accountExpirationCheckEnabled = accountExpirationCheckEnabled;
		}
	}

	public long getBadLoginsUpperBound() {
		synchronized (badLoginsUpperBoundLock) {
			return badLoginsUpperBound;
		}
	}

	public void setBadLoginsUpperBound(long badLoginsUpperBound) {
		synchronized (badLoginsUpperBoundLock) {
			this.badLoginsUpperBound = badLoginsUpperBound;
		}
	}

	public boolean isLockedCheckEnabled() {
		synchronized (lockedCheckEnabledLock) {
			return lockedCheckEnabled;
		}
	}

	public void setLockedCheckEnabled(boolean lockedCheckEnabled) {
		synchronized (lockedCheckEnabledLock) {
			this.lockedCheckEnabled = lockedCheckEnabled;
		}
	}

	public long getLockedTimeAfterBadLogins() {
		synchronized (lockedTimeAfterBadLoginsLock) {
			return lockedTimeAfterBadLogins;
		}
	}

	public void setLockedTimeAfterBadLogins(long lockedTimeAfterBadLogins) {
		synchronized (lockedTimeAfterBadLoginsLock) {
			this.lockedTimeAfterBadLogins = lockedTimeAfterBadLogins;
		}
	}

}
