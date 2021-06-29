import { combineReducers } from "redux";
import  adviceReducer  from './adviceReducer'
import meetingReducer from "./meetingReducer";
import mapsReducer from "./mapsReducer";

export default combineReducers({
    advices : adviceReducer,
    meetings : meetingReducer,
    maps: mapsReducer
});