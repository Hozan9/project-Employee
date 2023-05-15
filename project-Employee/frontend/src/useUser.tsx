import {useState} from "react";
import axios from "axios";
export default function useUser() {

    const [user, setUser] = useState<string>()

    function login(username: string, password: string,image:File| undefined,url:string) {
    const data = new FormData()
        if (image){
            data.append("file",image)

        }
        data.append("data",new Blob([JSON.stringify(username)],{'type':"application/json"}))
          return  axios.post("/api/users/login", undefined,{auth:{username, password}})
            .then(response => {
                setUser(response.data)
            })
    }
     return {user, login}
}

