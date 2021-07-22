import React, {  useEffect } from "react";
import { Link } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { getListMeetings } from "../../actions/meetingAction";
import Meeting from "./Meeting";
import Spinner from "../Spinner";
import Sidebar from "../layout/Sidebar";
import Header from "../layout/Header";
import Footer from "../layout/Footer";

const ListMeeting = () => {
  const meetings = useSelector((state) => state.meetings.meetings);
  const error = useSelector((state) => state.meetings.error);
  const loading = useSelector((state) => state.meetings.loading);

  const dispatch = useDispatch();
  const dispatchListMeetings = () => dispatch(getListMeetings());

  useEffect(() => {
    dispatchListMeetings();
    //eslint-disable-next-line
  }, []);

  return (
    <div className="contenedor-app">
      <Sidebar />
      <div className="seccion-principal">
        <Header />
        <main>
          <div className="d-flex justify-content-between px-4 mb-5">
            <h1>
              <i className="fas fa-hand-holding-heart pr-2"></i>Listado de
              puntos de encuentro
            </h1>
            <Link to={"/create-meeting"} className="btn btn-primary mb-3">
              <i className="fas fa-plus-circle pr-2"></i>
              Nuevo encuentro
            </Link>
          </div>

          {loading ? (
            <Spinner />
          ) : meetings.length === 0 && !error ? (
            <div className="alert alert-info text-center p-3">
              <i className="fas fa-exclamation-circle"></i>No hay puntos de
              encuentro creados
            </div>
          ) : (
            <div className="row">
              {meetings.map((meeting) => (
                <Meeting key={meeting.id} meeting={meeting} />
              ))}
            </div>
          )}
        </main>
        <Footer />
      </div>
    </div>
  );
};

export default ListMeeting;
