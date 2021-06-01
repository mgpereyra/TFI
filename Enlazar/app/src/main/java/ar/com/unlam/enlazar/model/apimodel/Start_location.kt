import com.google.gson.annotations.SerializedName

data class Start_location (
	@SerializedName("lat")var lat : Double,
	@SerializedName("lng")	var lng : Double
)