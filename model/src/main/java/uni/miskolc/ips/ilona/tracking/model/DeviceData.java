package uni.miskolc.ips.ilona.tracking.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 
 * @author Patrik / A5USL0
 *
 */
public class DeviceData implements Serializable, Comparable<DeviceData>, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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

	public DeviceData() {

	}

	public DeviceData(String deviceid, String deviceName, String deviceType, String deviceTypeName) {
		super();
		this.deviceid = deviceid;
		this.deviceName = deviceName;
		this.deviceType = deviceType;
		this.deviceTypeName = deviceTypeName;
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
		return "DeviceData [deviceid=" + deviceid + ", deviceName=" + deviceName + ", deviceType=" + deviceType
				+ ", deviceTypeName=" + deviceTypeName + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deviceName == null) ? 0 : deviceName.hashCode());
		result = prime * result + ((deviceType == null) ? 0 : deviceType.hashCode());
		result = prime * result + ((deviceTypeName == null) ? 0 : deviceTypeName.hashCode());
		result = prime * result + ((deviceid == null) ? 0 : deviceid.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DeviceData other = (DeviceData) obj;
		if (deviceName == null) {
			if (other.deviceName != null)
				return false;
		} else if (!deviceName.equals(other.deviceName))
			return false;
		if (deviceType == null) {
			if (other.deviceType != null)
				return false;
		} else if (!deviceType.equals(other.deviceType))
			return false;
		if (deviceTypeName == null) {
			if (other.deviceTypeName != null)
				return false;
		} else if (!deviceTypeName.equals(other.deviceTypeName))
			return false;
		if (deviceid == null) {
			if (other.deviceid != null)
				return false;
		} else if (!deviceid.equals(other.deviceid))
			return false;
		return true;
	}

	public DeviceData shallowCopy() {
		try {
			return (DeviceData) this.clone();
		} catch (Exception e) {
			return null;
		}
	}

	public DeviceData deepCopy() {
		ByteArrayOutputStream bos = null;
		ObjectOutputStream out = null;
		ObjectInputStream ois = null;
		ByteArrayInputStream bis = null;
		DeviceData deviceData = null;

		try {
			/*
			 * Serialization part.
			 */
			bos = new ByteArrayOutputStream();
			out = new ObjectOutputStream(bos);
			out.writeObject(this);
			out.flush();
			// out.close();

			/*
			 * Deserialization part.
			 */
			bis = new ByteArrayInputStream(bos.toByteArray());
			ois = new ObjectInputStream(bis);
			deviceData = (DeviceData) ois.readObject();

		} catch (Exception e) {
			deviceData = null;

		} finally {
			if (bos != null) {
				try {
					bos.close();
				} catch (Exception a) {

				}
			}

			if (out != null) {
				try {
					out.close();
				} catch (Exception a) {

				}
			}

			if (ois != null) {
				try {
					ois.close();
				} catch (Exception a) {

				}
			}

			if (bis != null) {
				try {
					bis.close();
				} catch (Exception a) {

				}
			}
		}

		return deviceData;
	}

	@Override
	public int compareTo(DeviceData o) {
		if (o == null) {
			return 1;
		}

		if (!(o instanceof DeviceData)) {
			return 1;
		}
		return this.deviceid.compareTo(o.deviceid);
	}

}
