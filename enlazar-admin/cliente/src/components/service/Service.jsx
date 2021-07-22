import React from 'react'
import { getState } from '../../helpers'

const Service = ({service}) => {

    const {address, date,time, 
      envasesCarton, envasesPlasticos, envasesVidrio,
      comentario } = service;


    const get = getState(service)

    return (
        <tr className= {get.className}>
        <th scope="row" >{date}</th>
        <td>
          {address}
        </td>
        <td>
          {time}
        </td>
        <td>
        {get.stateString}
        </td>
        <td>
          Juan jsjsjjjsssssss
        </td>
        <td className="text-center"> 
          {envasesCarton+envasesPlasticos+envasesVidrio}
        </td>
        <td>
         {comentario}
        </td>
      </tr>
      );
}
 
export default Service;