package uni.miskolc.ips.ilona.tracking.controller.model;

public class UserDeviceDataDTO {

	/**
	 * 
	 */
	private String userid;
	
	/**
	 * 
	 */
	private String deviceid;

	/**
	 * 
	 */
	private String deviceName;

	/**
	 * 
	 */
	private String deviceType;

	/**
	 * 
	 */
	private String deviceTypeName;
	
	
	
	public UserDeviceDataDTO() {

	}



	public UserDeviceDataDTO(String userid, String deviceid, String deviceName, String deviceType,
			String deviceTypeName) {
		super();
		this.userid = userid;
		this.deviceid = deviceid;
		this.deviceName = deviceName;
		this.deviceType = deviceType;
		this.deviceTypeName = deviceTypeName;
	}



	public String getUserid() {
		return userid;
	}



	public void setUserid(String userid) {
		this.userid = userid;
	}



	public String getDeviceid() {
		return deviceid;
	}



	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}



	public String getDeviceName() {
		return deviceName;
	}



	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}



	public String getDeviceType() {
		return deviceType;
	}



	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}



	public String getDeviceTypeName() {
		return deviceTypeName;
	}



	public void setDeviceTypeName(String deviceTypeName) {
		this.deviceTypeName = deviceTypeName;
	}



	@Override
	public String toString() {
		return "UserDeviceDataDTO [userid=" + userid + ", deviceid=" + deviceid + ", deviceName=" + deviceName
				+ ", deviceType=" + deviceType + ", deviceTypeName=" + deviceTypeName + "]";
	}
	
	
}
