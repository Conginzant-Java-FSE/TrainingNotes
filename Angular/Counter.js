import { useState } from "react";

const Counter = ()=>{
    const [person, setPerson] = useState({ name: 'John', age: 25 });

    const updateName = () => {
      setPerson({ ...person, name: 'Jane' });
    };
  
    const incrementAge = () => {
      setPerson({ ...person, age: person.age + 1 });
    };
  
    const [data , setData] = useState({
        name:"arul",
        exp:3
    });
    const handleClick = (event)=>{
        setData({
            name:"guru",
            exp:5
        });

    }

    return(
        <div>
            <h2>INFO: {data.name}</h2>
            <button className="btn btn-primary" onClick={handleClick}>change</button>
            <div>
            <button onClick={updateName}>Change Name</button>
            <button onClick={incrementAge}>Increase Age</button>
            <p>{person.name}</p>
            <p>{person.age}</p>
            </div>
        </div>
    )
}

export default Counter;