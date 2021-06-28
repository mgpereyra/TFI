import React, { Fragment, useEffect } from "react";
import { Link } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { getListMeetings } from "../../actions/meetingAction";
import Meeting from './Meeting'

const ListMeeting = () => {

  const meetings = useSelector((state) => state.meetings.meetings);
  const error = useSelector((state) => state.meetings.error);
  const dispatch = useDispatch();
  const dispatchListMeetings = () => dispatch(getListMeetings());

  useEffect(() => {
    if (Object.keys(meetings) !== 0) {
      dispatchListMeetings();
    }
  }, []);

  return (
    <Fragment>
      <div className="d-flex justify-content-between px-4 mb-5">
        <h1>
          <i className="fas fa-hand-holding-heart"></i>Listado de puntos de
          encuentro
        </h1>
        <Link to={"/create-meeting"} className="btn btn-primary mb-3">
          <i className="fas fa-plus-circle"></i>
          Crear un nuevo encuentro
        </Link>
      </div>

      {meetings.length === 0 && !error ? (
        <div className="alert alert-info text-center p-3">
          <i className="fas fa-exclamation-circle"></i>No hay puntos de encuentro creados
        </div>
      ) : (
        <div className="row">
          {meetings.map((meeting) => (
            <Meeting 
                key={meeting.id}
                meeting={meeting} />
          ))}
        </div>
      )}
    </Fragment>
  );
};

export default ListMeeting;
