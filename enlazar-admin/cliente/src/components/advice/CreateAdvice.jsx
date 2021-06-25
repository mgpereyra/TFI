import React, { Fragment, useState, useContext } from 'react'
import { Button, Col, Row} from 'react-bootstrap'
import {createNewAdvice} from '../../actions/adviceAction'
import {   useSelector, useDispatch } from 'react-redux'
import alertaContext from '../../context/alerta/alertaContext'

const CreateAdvice = ({history}) => {
    const {alerta, mostrarAlerta} = useContext(alertaContext);

    //state del componente 
    const [advice, setAdvice] = useState({
        img:'',
        tipe: '',
        title:'',
        content:''
    });

    const dispatch = useDispatch();
    const addAdvice = advice => dispatch (createNewAdvice(advice));

    //acceder al state
    const loading = useSelector(state => state.advices.loading)
  
    const handleChange = e =>{
        setAdvice({
            ...advice,
            [e.target.name]: e.target.value
        })
    }

    const {img, tipe, title, content} = advice;

    const handleSubmit = e =>{
        e.preventDefault();

        //Validar
        if(tipe.trim() === '' || img.trim() ==='' || title.trim() ==='' || content.trim() ===''){
            mostrarAlerta("Por favor complete todos lo campos", "alerta-error")
            return;
        }

        addAdvice(advice);
        
        //reiniciar el form
        setAdvice({
            img:'',
            tipe: '',
            title:'',
            content:''
        })

        //redireccion
        history.push('/list-advice')
    }
    return (
        <Fragment>
             {alerta ? (<div className={`alerta ${alerta.categoria}`}>{alerta.msg}</div>) :null}
            <h2>Crear un nuevo consejo</h2>
            <div className="card bg-gris">
                <div className="card-body">
                    <form
                        onSubmit={handleSubmit}
                    >
                        <Row>
                            <Col lg={4}>
                                <div className="form-group">
                                    <label className="control-label">Selecciona una imagen</label>
                                        <input 
                                            type="file"
                                            className="input-text"
                                            id="img" 
                                            name="img"
                                            onChange={handleChange}
                                            value={img} />
                                </div>
                            </Col>
                            <Col>
                                <div className="form-group">
                                    <label className="control-label">Tipo</label>
                                    <input
                                        type="text" 
                                        className="input-text"
                                        placeholder="Consejo semanal"
                                        name="tipe"
                                        onChange={handleChange}
                                        value={tipe} />
                                </div>
                                
                                <div className="form-group">
                                    <label className="control-label">Titulo</label>
                                    <input
                                        type="text" 
                                        className="input-text"
                                        placeholder="Por ejemplo, 'Sobre el reciclaje'"
                                        name="title"
                                        onChange={handleChange} 
                                        value={title} />
                                </div>

                                <div className="form-group">
                                    <label className="control-label">Descripcion</label>
                                    <textarea
                                        className="input-text"
                                        placeholder="Ingresa una descripcion..."
                                        name="content"
                                        onChange={handleChange}
                                        value={content}  />
                                </div>
                            </Col>
                            </Row>
                            <Button 
                                className="btn btn-block"
                                type="submit"
                                variant="primary"
                            >Crear consejo
                            </Button>
                    </form>    
                </div>
            </div>
            </Fragment>
      );
}
 
export default CreateAdvice;