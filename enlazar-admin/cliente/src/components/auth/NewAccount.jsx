import React , {useState, useContext, useEffect} from 'react'
import {Link} from 'react-router-dom';
import alertaContext from '../../context/alerta/alertaContext';
import authContext from '../../context/auth/authContext';

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

  }, [mensaje, autenticado, props.history])
  
  //state para iniciar sesion
const [user, setUser] = useState({
  name:'Mar',
  email:'margise@gmail.com',
  password:'123456',
  confirmar:'123456'
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
    mostrarAlerta("Todos los campos son obligatorios", "alerta-error")
    return;
  }

  //longitud de pass
  if(password.trim().length < 6){
    mostrarAlerta("La password debe tener mínimo 6 caracteres", "alerta-error");
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
          <h1>Enlazar</h1>
        <h2>Obtener una cuenta</h2>
        <form onSubmit={onSubmitLogin}> 

        <div className='campo-form'>
            <label htmlFor="name">Nombre</label>
            <input
              type='text'
              id='name'
              name='name'
              placeholder='Tu nombre'
              value={name}
              onChange={onChangeLogin}
            />
          </div>
          <div className='campo-form'>
            <label htmlFor="email">Email</label>
            <input
              type='email'
              id='email'
              name='email'
              placeholder='Tu email'
              value={email}
              onChange={onChangeLogin}
            />
          </div>

          <div className='campo-form'>
            <label htmlFor="password">Password</label>
            <input
              type='password'
              id='password'
              name='password'
              placeholder='Tu password'
              autoComplete='cc-number'
              value={password}
              onChange={onChangeLogin}
            />
          </div>

          <div className='campo-form'>
            <label htmlFor="confirmar">Confirmar Password</label>
            <input
              type='password'
              id='confirmar'
              name='confirmar'
              placeholder='Repite tu password'
              autoComplete='cc-number'
              value={confirmar}
              onChange={onChangeLogin}
            />
          </div>

          <div className='campo-form'>
            <input
              type='submit'
              className='btn btn-primary btn-block'
              value='Registrar'
            />

          </div>
        </form>

        <Link to={'/'} className='enlace-cuenta'>
          Iniciar sesión
        </Link>
      </div>
    </div>
      );
}
 
export default NewAccount;