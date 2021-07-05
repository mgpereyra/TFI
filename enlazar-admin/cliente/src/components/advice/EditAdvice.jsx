import React, {Fragment, useState, useEffect} from 'react'
import {  Col, Row} from 'react-bootstrap'
import { useDispatch, useSelector } from 'react-redux'
import {  modifyAdviceAction } from '../../actions/adviceAction'
import { useHistory } from 'react-router-dom'

const EditAdvice = () => {

    const dispatch = useDispatch();
    const history = useHistory()

    const [advice, setAdvice] = useState({
        img:'',
        tipe: '',
        title:'',
        description:'',
        uri:''
    });
    
    const adviceToModify = useSelector(state => state.advices.adviceToModify)
    //if(!adviceToModify) return null; 
    
    const {img, tipe, title, content, uri} = advice;

    //carga los datos del elemento a modificar la 1ra vez
    useEffect(() => {
        setAdvice(adviceToModify)
            
     }, [adviceToModify])

     const handleChange = e =>{
         setAdvice({
             ...advice,
             [e.target.name] : e.target.value
         })
     }

    const submitForm = (e) =>{
        e.preventDefault()
        dispatch(modifyAdviceAction(advice))
        history.push('/list-advice')
    }
    return (
        <Fragment>
            <div className="d-flex justify-content-between">
                <h2><i className="far fa-edit"></i>Editar consejo</h2>
            </div>
        <div className="card bg-gris py-4">
            <div className="card-body">
                <form
                    onSubmit={submitForm}>
                    <Row>
                        <Col lg={4}>
                            <div className="form-group">
                                    <label className="control-label">Imagen seleccionada</label>
                                <div className="bg-white fondo-imagen  m-1 row align-items-center">
                                    <img className="img-edit " src={uri} alt={img}></img>
                                </div>
                            </div>
                        </Col>
                        <Col lg={8}>
                            <div className="form-group">
                                <label className="control-label">Categoría</label>
                                <input
                                    type="text" 
                                    className="input-text"
                                    placeholder="Consejo semanal"
                                    name="tipe"
                                    value={tipe} 
                                    onChange={handleChange}/>
                            </div>
                            
                            <div className="form-group">
                                <label className="control-label">Título</label>
                                <input
                                    type="text" 
                                    className="input-text"
                                    placeholder="Por ejemplo, 'Sobre el reciclaje'"
                                    name="title"
                                    value={title}
                                    onChange={handleChange} />
                            </div>

                            <div className="form-group">
                                <label className="control-label">Descripción</label>
                                <textarea
                                    className="input-text"
                                    placeholder="Ingresa una descripción..."
                                    name="content"
                                    value={content}
                                    onChange={handleChange}  />
                            </div>
                        </Col>
                        </Row>
                        <div className="d-grid gap-2 d-md-flex justify-content-md-end mr-3">
                            <button 
                                className="btn btn-primary me-md-2"
                                type="submit"
                                variant="primary">
                                    <i className="far fa-check"></i>
                                    Guardar cambios
                            </button>
                        </div>
                </form>               
            </div>
        </div>
        </Fragment>
      );
}
 
export default EditAdvice;