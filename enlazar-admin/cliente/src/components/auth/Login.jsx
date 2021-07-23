import React , {useState, useContext, useEffect} from 'react'
import {Link} from 'react-router-dom';
import alertaContext from '../../context/alerta/alertaContext';
import authContext from '../../context/auth/authContext';
import logo from '../../images/enlazar-ocre.png';

const Login = (props) => {

  const {alerta, mostrarAlerta} = useContext(alertaContext);
  const {iniciarSesion, mensaje, autenticado} = useContext(authContext);
  
  //en caso de q el password o usuario no existe
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
    mostrarAlerta("Por favor, completa tu email y password", "alerta-error")
    return;
  }

  //
  iniciarSesion({email, password});
}

    return (
      <div className='form-usuario'>
        {alerta ? (<div className={`alerta ${alerta.categoria}`}>{alerta.msg}</div>) :null}
      <div className='contenedor-form sombra-dark'>
        <img className="logo-enlazar" src={logo} alt="logo"></img>
        <h3 className="text-center mb-4">¡Hola! Inicia sesión para comenzar </h3>
        <form onSubmit={onSubmitLogin}> 
        <div className='campo-form'>
            <label htmlFor="email">Email</label>
            <input
              type='email'
              id='email'
              name='email'
               className="input-text small"
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
              className="input-text small"
              placeholder='Tu password'
              autoComplete='cc-number'
              value={password}
              onChange={onChangeLogin}
            />
          </div>

             <button
                className="btn btn-primary btn-block mt-3"
                type="submit"
                variant="primary"
              >Iniciar sesión</button>

        </form>

        <Link to={'/new-account'} className='enlace-cuenta'>
          Obtener nueva cuenta
        </Link>
      
      </div>
    </div>
      );
}
 
export default Login;