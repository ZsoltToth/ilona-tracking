package uni.miskolc.ips.ilona.tracking.model;

import uni.miskolc.ips.ilona.measurement.model.position.Position;

import java.sql.Timestamp;

public class UserPosition {

    private int id;
    private UserData user;
    private Position position;
    private Timestamp time;

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserData getUser() {
        return user;
    }

    public void setUser(UserData user) {
        this.user = user;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserPosition that = (UserPosition) o;

        if (id != that.id) return false;
        if (!user.equals(that.user)) return false;
        if (!position.equals(that.position)) return false;
        return time.equals(that.time);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + user.hashCode();
        result = 31 * result + position.hashCode();
        result = 31 * result + time.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "UserPosition{" +
                "id=" + id +
                ", user=" + user +
                ", position=" + position +
                ", time=" + time +
                '}';
    }
}
