import React , {useState, useContext, useEffect} from 'react'
import {Link} from 'react-router-dom';
import alertaContext from '../../context/alerta/alertaContext';
import authContext from '../../context/auth/authContext';
import logo from '../../images/enlazar-ocre.png';

const NewAccount = (props) => {

  const {alerta, mostrarAlerta} = useContext(alertaContext);
  const {registrarUsuario, mensaje, autenticado} = useContext(authContext);
  
  //
  useEffect(() => {
    if(autenticado){
      props.history.push('/dashboard')
    }

    if(mensaje){
      mostrarAlerta(mensaje.msg, mensaje.categoria)
    }
  //eslint-disable-next-line
  }, [mensaje, autenticado, props.history])
  
  //state para iniciar sesion
const [user, setUser] = useState({
  name:'',
  email:'',
  password:'',
  confirmar:''
})

//extraer el usuario
const {name, email, password, confirmar} = user;


const onChangeLogin= e =>{
  setUser({
    ...user,
    [e.target.name] : e.target.value
  })

}
const onSubmitLogin = e =>{
  e.preventDefault();

  //Validar que no haya campo vacios
  if(email.trim() === '' || password.trim() === '' || confirmar.trim() === '' || name.trim() === ''){
    mostrarAlerta("Debes completar todos los campos", "alerta-error")
    return;
  }

  //longitud de pass
  if(password.trim().length < 6){
    mostrarAlerta("La contraseña debe tener un mínimo de 6 caracteres", "alerta-error");
    return;
  }
  //2 pass iguales
  if(password.trim() !== confirmar.trim()){
    mostrarAlerta("Las passwords deben ser iguales", "alerta-error");
    return;
  }

  // action
  registrarUsuario({
    name,
    email, 
    password
  })
  
}

    return (
      <div className='form-usuario'>
        {alerta ? (<div className={`alerta ${alerta.categoria}`}>{alerta.msg}</div>) :null}
      <div className='contenedor-form sombra-dark'>
      <img className="logo-enlazar" src={logo} alt="logo"></img>
      <h3 className="text-center mb-1 mt-0 text-muted">Completá los siguientes campos y enlazate</h3>
        <form onSubmit={onSubmitLogin}> 

        <div className='form-group'>
        <label className="control-label">Nombre</label>
            <input
              type='text'
              className="input-text small"
              id='name'
              name='name'
              placeholder='Tu nombre'
              value={name}
              onChange={onChangeLogin}
            />
          </div>
          <div className='form-group'>
          <label className="control-label">Email</label>
            <input
              type='email'
              id='email'
              className="input-text small"

              name='email'
              placeholder='Tu email'
              value={email}
              onChange={onChangeLogin}
            />
          </div>

          <div className='form-group'>
          <label className="control-label">Password</label>
            <input
              type='password'
              id='password'
              name='password'
              className="input-text small"
              placeholder='Tu password'
              autoComplete='cc-number'
              value={password}
              onChange={onChangeLogin}
            />
          </div>

          <div className='form-group'>
          <label className="control-label">Confirmar Password</label>
            <input
              type='password'
              id='confirmar'
              className="input-text small"
              name='confirmar'
              placeholder='Repite tu password'
              autoComplete='cc-number'
              value={confirmar}
              onChange={onChangeLogin}
            />
</div>
         
            <button
              type='submit'
              className='btn btn-primary btn-block mb-1'
              value='Registrar'
              >Registrar</button>

       
        </form>

        <Link to={'/'} className='enlace-cuenta'>
          Volver a iniciar sesión
        </Link>
      </div>
    </div>
      );
}
 
export default NewAccount;