export const getState = (service) => {
    let state = {
        stateString: '',
        className:''
    };

    let estado = service.estado;

    if (estado === 0) {
        state.stateString ="Pendiente"
        state.className = "table-warning alert-warning"
    } else if (estado === 1){
        state.stateString ="Asignado"
        state.className = "table-success alert-success"
    } else if (estado === 2 ){
        state.stateString ="En curso"
        state.className = "table-primary alert-primary"
    } else if (estado === 3 ){
        state.stateString ="Finalizado"
        state.className = "table-secondary alert-secondary"
    } else if (estado === 4 ){
        state.stateString ="Cancelado"
        state.className = "table-danger alert-danger"
    } else {
        state.stateString ="-"
        
    }

    return state
}