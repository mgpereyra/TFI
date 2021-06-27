import React, { Fragment, useState, useContext } from 'react'
import {  Col, Row} from 'react-bootstrap'
import {createNewAdvice} from '../../actions/adviceAction'
import {  useDispatch } from 'react-redux'
import alertaContext from '../../context/alerta/alertaContext'

const CreateAdvice = ({history}) => {
    const {alerta, mostrarAlerta} = useContext(alertaContext);

    //state del componente 
    const [advice, setAdvice] = useState({
        img:'',
        tipe: '',
        title:'',
        content:'',
        imagen:null
    });

    const {img, tipe, title, content } = advice;
    
    const dispatch = useDispatch();
    const addAdvice = ( advice ) => dispatch (createNewAdvice(advice));
    
    const handleChange = e =>{
        setAdvice({
            ...advice,
            [e.target.name]: e.target.value
        })
    }

    const handleImg = e =>{
        let f = new FormData();
        f.append('file', e.target.files[0]);

        setAdvice({
            ...advice,
            imagen : f,
            img : e.target.files[0].name    
        });
    }

    const handleSubmit = e =>{
        e.preventDefault();

        //Validar
        if(tipe.trim() === '' ||  img.trim() === '' || title.trim() ==='' || content.trim() ===''){
            mostrarAlerta("Por favor complete todos lo campos", "alerta-error")
            return;
        }

        addAdvice(advice);
        
        //reiniciar el form
        setAdvice({
            img:'',
            tipe: '',
            title:'',
            content:'',
            imagen:null,
            uri:''
        })

        //redireccion
        setTimeout( function(){
            history.push('/list-advice')
        },2500)
    }
    return (
        <Fragment>
             {alerta ? (<div className={`alerta ${alerta.categoria}`}>{alerta.msg}</div>) :null}
             <div class="d-flex justify-content-between">
                <h2><i className="fas fa-plus-circle"></i>Crear un nuevo consejo</h2>
              </div>
            <div className="card bg-gris">
                <div className="card-body">
                    <form
                        onSubmit={handleSubmit}
                        enctype="multipart/form-data"
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
                                            onChange={handleImg}
                                            />
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
                            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                                <button 
                                    className="btn btn-primary me-md-2"
                                    type="submit"
                                    variant="primary"
                                ><i className="far fa-check"></i>
                                Crear consejo
                                </button>
                            </div>
                    </form>    
                </div>
            </div>
            </Fragment>
      );
}
 
export default CreateAdvice;