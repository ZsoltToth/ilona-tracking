package uni.miskolc.ips.ilona.tracking.controller.model;

public class LoginAttemptFormStorage {

	private String formatDate;

	private long formatMilliseconds;

	public LoginAttemptFormStorage() {

	}

	public LoginAttemptFormStorage(String formatDate, long formatMilliseconds) {
		super();
		this.formatDate = formatDate;
		this.formatMilliseconds = formatMilliseconds;
	}

	public String getFormatDate() {
		return formatDate;
	}

	public void setFormatDate(String formatDate) {
		this.formatDate = formatDate;
	}

	public long getFormatMilliseconds() {
		return formatMilliseconds;
	}

	public void setFormatMilliseconds(long formatMilliseconds) {
		this.formatMilliseconds = formatMilliseconds;
	}

}
