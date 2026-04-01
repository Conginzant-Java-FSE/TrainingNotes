
import { useEffect, useState } from "react";
import Output from "./Output";
import InputForm from "./InputForm";
import Profile from "./Profile";

const Display = ()=>{
    const [val, setVal] = useState([1,2,3,4,5,6]);
    const [obj, setObj] = useState();
    const[req, setReq] = useState(false);


    const[searchVal, setSearchVal] = useState("");

    const addValue = (event)=>{
        setVal([...val, val.length+1]);
    }

    const removeValue = (event)=>{
        setVal(val.slice(0,val.length-1))
    }

    const changeName =()=>{
        setObj("")
    }


    useEffect(()=>{
       fetch("https://randomuser.me/api/").then(
        res => res.json()
       ).then(
        (data)=>{
            console.log(data.results[0])
            setObj(data.results[0]);
        }
       )
},[req]);



    return(
        <div>
            <p>

                <Profile info={obj}></Profile>
                <button onClick={()=>{setReq(!req)}}>
                    send reqeuest
                </button>
            </p>
            <div>
                <Output value={searchVal}></Output>
                <InputForm value = {searchVal} setValue = {setSearchVal}></InputForm>
            </div>

            <div>
                <b>{val}</b>
            </div>
            <button onClick={addValue}>add</button>
            <button onClick={removeValue}>remove</button>

            <div>
                {/* <p>{obj?obj.name:null}</p> 
                <button onClick={changeName}>Change Name</button>
                <p>{obj.location}</p>
                <p>{obj.age}</p> */}
            </div>    

            
        </div>
    )
}

export default Display;