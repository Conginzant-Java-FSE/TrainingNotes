const DisplayUniv = (univ)=>{
    console.log(univ);
    return(
        <div>
            {/* <div>{JSON.stringify(univ.univ[0])}</div> */}
            {univ.univ.map(element => {
              return <p>{element.name}</p>  
            })}
            
        </div>
    )
}

export default DisplayUniv;