import React, {Fragment, useState} from 'react'
import { Button, Col, Row} from 'react-bootstrap'

const EditAdvice = () => {

    const [advice, setAdvice] = useState({
        img:'',
        tipe: '',
        title:'',
        description:''
    });

    const {img, tipe, title, description} = advice;

    return (
        <Fragment>
        <h2>Editar consejo</h2>
        <div className="card bg-gris">
            <div className="card-body">
                <form
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
                                    value={tipe} />
                            </div>
                            
                            <div className="form-group">
                                <label className="control-label">Titulo</label>
                                <input
                                    type="text" 
                                    className="input-text"
                                    placeholder="Por ejemplo, 'Sobre el reciclaje'"
                                    name="title"
                                    value={title} />
                            </div>

                            <div className="form-group">
                                <label className="control-label">Descripcion</label>
                                <textarea
                                    className="input-text"
                                    placeholder="Ingresa una descripcion..."
                                    name="description"
                                    value={description}  />
                            </div>
                        </Col>
                        </Row>
                        <Button 
                            className="btn btn-block"
                            type="submit"
                            variant="primary">
                                Guardar cambios
                        </Button>
                </form>               
            </div>
        </div>
        </Fragment>
      );
}
 
export default EditAdvice;