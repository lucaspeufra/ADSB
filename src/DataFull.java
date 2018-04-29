public class DataFull {

	protected String icao24;
	protected int last_contact;
	protected boolean on_ground;
	
	protected String callsign;
	protected String origin_country;
	protected int time_position;
	protected float longitude;
	protected float latitude;
	protected float geo_altitude;
	protected float velocity;
	protected float heading;
	protected float vertical_rate;
	protected float baro_altitude;
	protected String squawk;
	protected boolean spi;
	protected int position_source;
	
	
	public String getIcao24() {
		return icao24;
	}
	public void setIcao24(String icao24) {
		this.icao24 = icao24;
	}
	public int getLast_contact() {
		return last_contact;
	}
	public void setLast_contact(int last_contact) {
		this.last_contact = last_contact;
	}
	public boolean isOn_ground() {
		return on_ground;
	}
	public void setOn_ground(boolean on_ground) {
		this.on_ground = on_ground;
	}
	public String getCallsign() {
		return callsign;
	}
	public void setCallsign(String callsign) {
		this.callsign = callsign;
	}
	public String getOrigin_country() {
		return origin_country;
	}
	public void setOrigin_country(String origin_country) {
		this.origin_country = origin_country;
	}
	public int getTime_position() {
		return time_position;
	}
	public void setTime_position(int time_position) {
		this.time_position = time_position;
	}
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	public float getGeo_altitude() {
		return geo_altitude;
	}
	public void setGeo_altitude(float geo_altitude) {
		this.geo_altitude = geo_altitude;
	}
	public float getVelocity() {
		return velocity;
	}
	public void setVelocity(float velocity) {
		this.velocity = velocity;
	}
	public float getHeading() {
		return heading;
	}
	public void setHeading(float heading) {
		this.heading = heading;
	}
	public float getVertical_rate() {
		return vertical_rate;
	}
	public void setVertical_rate(float vertical_rate) {
		this.vertical_rate = vertical_rate;
	}
	public float getBaro_altitude() {
		return baro_altitude;
	}
	public void setBaro_altitude(float baro_altitude) {
		this.baro_altitude = baro_altitude;
	}
	public String getSquawk() {
		return squawk;
	}
	public void setSquawk(String squawk) {
		this.squawk = squawk;
	}
	public boolean isSpi() {
		return spi;
	}
	public void setSpi(boolean spi) {
		this.spi = spi;
	}
	public int getPosition_source() {
		return position_source;
	}
	public void setPosition_source(int position_source) {
		this.position_source = position_source;
	}
	@Override
	public String toString() {
		return "DataFull [icao24=" + icao24 + ", callsign=" + callsign + ", origin_country=" + origin_country
				+ ", time_position=" + time_position + ", last_contact=" + last_contact + ", longitude=" + longitude
				+ ", latitude=" + latitude + ", geo_altitude=" + geo_altitude + ", on_ground=" + on_ground
				+ ", velocity=" + velocity + ", heading=" + heading + ", vertical_rate=" + vertical_rate
				+ ", baro_altitude=" + baro_altitude + ", squawk=" + squawk + ", spi=" + spi + ", position_source="
				+ position_source + "]";
	}
	public DataFull(String icao24, String callsign, String origin_country, int time_position, int last_contact,
			float longitude, float latitude, float geo_altitude, boolean on_ground, float velocity, float heading,
			float vertical_rate, float baro_altitude, String squawk, boolean spi, int position_source) {
		super();
		this.icao24 = icao24;
		this.callsign = callsign;
		this.origin_country = origin_country;
		this.time_position = time_position;
		this.last_contact = last_contact;
		this.longitude = longitude;
		this.latitude = latitude;
		this.geo_altitude = geo_altitude;
		this.on_ground = on_ground;
		this.velocity = velocity;
		this.heading = heading;
		this.vertical_rate = vertical_rate;
		this.baro_altitude = baro_altitude;
		this.squawk = squawk;
		this.spi = spi;
		this.position_source = position_source;
	}
	
	
	
	
	
	
}
