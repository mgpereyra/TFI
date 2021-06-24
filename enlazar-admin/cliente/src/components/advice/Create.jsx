import React, { Fragment, useState } from 'react'
import { Button, Col, Row} from 'react-bootstrap'

const Create = ({createAdvices}) => {
    const [advice, setAdvice] = useState({
        img:'',
        tipe: '',
        title:'',
        description:''
    });

    //const [error, setError] = useState(false);

    const handleChange = e =>{
        setAdvice({
            ...advice,
            [e.target.name]: e.target.value
        })
    }

    const {img, tipe, title, description} = advice;

    const handleSubmit = e =>{
        e.preventDefault();

        //Validar
        if(tipe.trim() === ''){
            //setError(true);
            return;
        }

       // setError(false);
        createAdvices(advice);

        //reiniciar el form
        setAdvice({
            img:'',
            tipe: '',
            title:'',
            description:''
        })

    }
    return (
        <Fragment>
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
                                        name="description"
                                        onChange={handleChange}
                                        value={description}  />
                                </div>
                            </Col>
                            </Row>
                            <Button 
                                className="btn btn-block"
                                type="submit"
                                variant="primary">
                                    Crear consejo
                            </Button>
                    </form>               
                </div>
            </div>
            </Fragment>
      );
}
 
export default Create;