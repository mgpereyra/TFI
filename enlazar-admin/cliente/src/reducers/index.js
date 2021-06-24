import { combineReducers } from "redux";
import  adviceReducer  from './adviceReducer'

export default combineReducers({
    advices : adviceReducer
});