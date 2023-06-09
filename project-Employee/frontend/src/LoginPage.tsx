import {FormEvent, useState} from "react";
import {useNavigate} from "react-router-dom";
import "./LoginPage.css"
type Props = {
    onLogin: (username: string, password: string, image: File | undefined,
              url: string) => Promise<void>
}
export default function LoginPage(props: Props){
    const [username,setUsername] = useState<string>("")
    const [password,setPassword] = useState<string>("")
    const navigate = useNavigate()
    const [image,setImage] = useState<File>();
   const [url,setUrl] = useState<string>( "");
    function onSubmit(event: FormEvent<HTMLFormElement>) {
        event.preventDefault()
        props.onLogin(username, password,image,url)
            .then(() => {navigate("/employees")})
    }
     return(
             <form onSubmit={onSubmit} action={"action_page.html"}
                   method={"post"}>
             <div className="container">
                 <p className={"username"}>Username:</p>
                 <input className={"input"} value={username}
                        placeholder="Enter username" type="text"
                        onChange={e => setUsername(e.target.value)}/>

                 <p className={"password"}>Password:</p>
                 <input className={"input2"} value={password}
                        placeholder="Enter password" type="password"
                        onChange={e => setPassword(e.target.value)}/>

                 <button className={"login"} type="submit">login</button>
             </div>
            </form>
    );
}