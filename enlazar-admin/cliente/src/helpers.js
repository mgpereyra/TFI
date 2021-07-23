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


export const categories = [
    { id: "COMO_RECICLAR_BIEN", name: "Como reciclar bien"},
    { id: "ECOINFORME", name: "Ecoinforme"},
    { id: "CONSEJO_DE_LA_SEMANA", name: "Consejo de la semana"}
]

export const getCategory = (category) => {
    if(category === "COMO_RECICLAR_BIEN"){
        return( "Como reciclar bien")
    }
    else if(category === "ECOINFORME"){
        return( "Ecoinforme")
    }
    else if(category === "CONSEJO_DE_LA_SEMANA"){
        return( "Consejo de la semana")
    }
    return "Categoria no encontrada"
}