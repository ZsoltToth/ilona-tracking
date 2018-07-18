package uni.miskolc.ips.ilona.tracking.controller.model;

import java.util.Date;

import uni.miskolc.ips.ilona.measurement.model.position.Position;

public class PositionTrackDTO {

	private Position position;

	private Date date;

	public PositionTrackDTO() {

	}

	public PositionTrackDTO(Position position, Date date) {
		super();
		this.position = position;
		this.date = date;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
