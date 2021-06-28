import { combineReducers } from "redux";
import  adviceReducer  from './adviceReducer'
import meetingReducer from "./meetingReducer";

export default combineReducers({
    advices : adviceReducer,
    meetings : meetingReducer
});