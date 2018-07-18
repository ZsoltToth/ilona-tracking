package uni.miskolc.ips.ilona.tracking.persist.mysql.typehandlers;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

public class BooleanToFromTinyintTypeHandler extends BaseTypeHandler<Boolean> {

	@Override
	public Boolean getNullableResult(ResultSet rs, String column) throws SQLException {
		Integer value = rs.getInt(column);
		if (rs.wasNull()) {
			return false;
		} else {

			if (value == 1) {
				return true;
			}
			return false;
		}

	}

	@Override
	public Boolean getNullableResult(ResultSet rs, int column) throws SQLException {
		Integer value = rs.getInt(column);
		if (rs.wasNull()) {
			return false;
		} else {

			if (value == 1) {
				return true;
			}
			return false;
		}
	}

	@Override
	public Boolean getNullableResult(CallableStatement cs, int column) throws SQLException {
		Integer value = cs.getInt(column);
		if (cs.wasNull()) {
			return false;
		} else {

			if (value == 1) {
				return true;
			}
			return false;
		}

		
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int column, Boolean parameter, JdbcType type)
			throws SQLException {
		if (parameter == null) {
			ps.setInt(column, 0);
		} else {
			int value = 0;
			if (parameter == true) {
				value = 1;
			}
			ps.setInt(column, value);
		}
	}

}
