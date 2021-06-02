import com.google.gson.annotations.SerializedName


data class Geocoded_waypoints (

	@SerializedName("geocoder_status") var geocoder_status : String,
	@SerializedName("place_id") var place_id : String,
	@SerializedName("types") 	var types : List<String>
)