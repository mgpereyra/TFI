import React from 'react'
import Advice from './Advice'

const List = () => {

const advices = [
    {title:'Advice 1'},
    {title:'Advice 2'},
    {title:'Advice 3'},
    {title:'Advice 4'},
    {title:'Advice 5'}


]

    return (  
        <div className="row">
                {advices.map( advice => (
                <Advice
                    key={advice.title}
                    advice={advice}                
                />
            ))
            }
        </div>
    );
}
 
export default List;