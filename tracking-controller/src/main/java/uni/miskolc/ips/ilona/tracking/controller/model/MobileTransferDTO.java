package uni.miskolc.ips.ilona.tracking.controller.model;

import uni.miskolc.ips.ilona.measurement.model.measurement.Measurement;

public class MobileTransferDTO {

	private String deviceid;

	private Measurement measurement;

	public MobileTransferDTO() {

	}

	public MobileTransferDTO(String deviceid, Measurement measurement) {
		super();
		this.deviceid = deviceid;
		this.measurement = measurement;
	}

	public String getDeviceid() {
		return deviceid;
	}

	public void setDeviceid(String deviceid) {
		this.deviceid = deviceid;
	}

	public Measurement getMeasurement() {
		return measurement;
	}

	public void setMeasurement(Measurement measurement) {
		this.measurement = measurement;
	}

}
