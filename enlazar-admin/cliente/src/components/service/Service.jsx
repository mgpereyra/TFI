import React from 'react'
import { getState } from '../../helpers'

const Service = ({service}) => {

    const {address, id, date,time, estado} = service;


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
      </tr>
      );
}
 
export default Service;