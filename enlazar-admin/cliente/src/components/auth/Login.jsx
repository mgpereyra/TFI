import React , {useState, useContext} from 'react'
import {Link} from 'react-router-dom';
import alertaContext from '../../context/alerta/alertaContext';
import authContext from '../../context/auth/authContext';

const Login = () => {

  const {alerta, mostrarAlerta} = useContext(alertaContext);
  const {iniciarSesion, mensaje, autenticado} = useContext(authContext);
  
  //state para iniciar sesion
const [user, setUser] = useState({
  email:'',
  password:''
})

//extraer el usuario
const {email, password} = user;


const onChangeLogin= e =>{
  setUser({
    ...user,
    [e.target.name] : e.target.value
  })

}
const onSubmitLogin = e =>{
  e.preventDefault();

  //Validar que no haya campo vacios
  if(email.trim() === '' || password.trim() === ''){
    mostrarAlerta("Todos los campos son obligatorios", "alerta-error")
    return;
  }

  //
}

    return (
      <div className='form-usuario'>
      <div className='contenedor-form sombra-dark'>
          <h1>Enlazar</h1>
        <h2>Iniciar Sesión</h2>
        <form onSubmit={onSubmitLogin}> 
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
            <input
              type='submit'
              className='btn btn-primario btn-block'
              value='Iniciar Sesión'
            />

          </div>
        </form>

        <Link to={'/new-account'} className='enlace-cuenta'>
          Obtener nueva cuenta
        </Link>
      
      </div>
    </div>
      );
}
 
export default Login;