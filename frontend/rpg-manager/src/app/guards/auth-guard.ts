import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { Authentication } from '../services/authentication';

export const authGuard: CanActivateFn = (route, state) => {
  var isLogedIn:Boolean;
  var authService = inject(Authentication);
    var router = inject(Router);

  isLogedIn = authService.isLoggedIn;
  
  if(!isLogedIn){
    console.log("User is not logged in, redirecting to login page.");
    return router.createUrlTree(['/login']);
  }
  console.log("User is logged in, allowing access to the route.");
  return true;
};
