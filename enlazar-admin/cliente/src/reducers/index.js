import { combineReducers } from "redux";
import  adviceReducer  from './adviceReducer'
import meetingReducer from "./meetingReducer";
import mapsReducer from "./mapsReducer";
import couponReducer from "./couponReducer";
import recyclerReducer from "./recyclerReducer";
import serviceReducer from "./serviceReducer";



export default combineReducers({
    advices : adviceReducer,
    meetings : meetingReducer,
    maps: mapsReducer,
    coupons: couponReducer,
    recyclers: recyclerReducer,
    services: serviceReducer
});