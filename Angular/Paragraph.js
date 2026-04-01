import Counter from "./Counter"

const Paragraph = (props)=> {
    const styles = {
        color:"blue",
        fontSize:"30px"
    }
    return(
        <div>
            <h1></h1>
            <ul>
                {
                    props.numbers.map(
                        (n, index)=>{
                            return <li key={index}>{n}</li>
                        }
                    )
                }
            </ul>
            <p style={styles} onClick={handleClick}>
            The OpenJS Foundation’s core purpose is to foster an ecosystem that supports the collaborative and
public development of free and open source software projects (each, a “Project”). This privacy policy
(“Privacy Policy”) describes our policies and procedures about the collection, use, disclosure and sharing,
or other processing of your personal information when you use OpenJS Foundation websites (e.g.,
openjsf.org) ), or participate in or use our Project sites (collectively, the “Sites”), as well as when you
interact with or participate in our events, programs, trainings and our other services and offerings
(collectively, the “Services”)
            </p>
            <Counter></Counter>

            
        </div>
    )
}

const handleClick = (event) => {
    console.log("Clicked");
}

export default Paragraph;