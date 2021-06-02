import ar.com.unlam.mapexample.geoClases.LegArray
import com.google.gson.annotations.SerializedName



data class Legs (
	@SerializedName("distance")	var distance : Distance,
	@SerializedName("duration")var duration : Duration,
	@SerializedName("end_address")var end_address : String,
	@SerializedName("end_location")	var end_location : End_location,
	@SerializedName("start_address")	var start_address : String,
	@SerializedName("start_location")	var start_location : Start_location,
	@SerializedName("steps")	var steps : ArrayList<Steps>,
	@SerializedName("traffic_speed_entry")	var traffic_speed_entry : List<String>,
	@SerializedName("via_waypoint")	var via_waypoint : List<String>
)