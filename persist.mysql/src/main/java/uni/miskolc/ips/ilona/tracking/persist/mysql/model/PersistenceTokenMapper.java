package uni.miskolc.ips.ilona.tracking.persist.mysql.model;

public class PersistenceTokenMapper {

	private String username;

	private String series;

	private String tokenValue;

	private long date;

	public PersistenceTokenMapper() {

	}

	public PersistenceTokenMapper(String username, String series, String tokenValue, long date) {
		super();
		this.username = username;
		this.series = series;
		this.tokenValue = tokenValue;
		this.date = date;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getTokenValue() {
		return tokenValue;
	}

	public void setTokenValue(String tokenValue) {
		this.tokenValue = tokenValue;
	}

	public long getDate() {
		return date;
	}

	public void setDate(long date) {
		this.date = date;
	}

}
