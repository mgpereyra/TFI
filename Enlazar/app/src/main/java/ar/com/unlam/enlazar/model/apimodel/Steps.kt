import com.google.gson.annotations.SerializedName


data class Steps (

	@SerializedName("distance")	var distance : Distance,
	@SerializedName("duration")var duration : Duration,
	@SerializedName("end_location")var end_location : End_location,
	@SerializedName("html_instructions")var html_instructions : String,
	@SerializedName("polyline")	var polyline : Polyline,
	@SerializedName("start_location")	var start_location : Start_location,
	@SerializedName("travel_mode")	var travel_mode : String
)