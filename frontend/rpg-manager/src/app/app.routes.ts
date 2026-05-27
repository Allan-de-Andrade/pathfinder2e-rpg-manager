import { Routes } from '@angular/router';
import { Login } from './components/login/login';
import { Register } from './components/register/register';
import { Home } from './components/home/home';
import { authGuard } from './guards/auth-guard';
export const routes: Routes = [
    {
        path: 'login',
        component: Login
    },
    {
        path:'register',
        component:Register
    },
    {
        path:'',
        component:Home,
        canActivate:[authGuard]
    }
];
