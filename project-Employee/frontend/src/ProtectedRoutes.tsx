import {Navigate, Outlet} from "react-router-dom";

type Props ={
user: string | undefined
}
export default function ProtectedRoutes(props: Props){
    const authenticated = props.user !== undefined && props.user !== 'anonymousUser' //rega nadait 3luj bit ua sfr bit
return(  //aua if und else kom la Re aydait ba dwatr
    authenticated ? <Outlet/>
        : <Navigate to={"/login"}/>
)

}
