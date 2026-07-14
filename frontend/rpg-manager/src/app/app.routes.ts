import { Routes } from '@angular/router';
import { Login } from './components/login/login';
import { Register } from './components/register/register';
import { Home } from './components/home/home';
import { authGuard } from './guards/auth-guard';
import { CharacterView } from './components/character-view/character-view';
import { CreateCharacter } from './components/create-character/create-character';
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
        path:'character/:id',
        component:CharacterView,
        canActivate:[authGuard]
    },
    {
        path:'new-character',
        component:CreateCharacter,
        canActivate:[authGuard]
    },
    {
        path:'',
        component:Home,
        canActivate:[authGuard]
    }
];
