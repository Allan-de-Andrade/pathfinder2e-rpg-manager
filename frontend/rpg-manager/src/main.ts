import { bootstrapApplication } from '@angular/platform-browser';
import { appConfig } from './app/app.config';
import { App } from './app/app';
<<<<<<< HEAD

bootstrapApplication(App, appConfig)
  .catch((err) => console.error(err));
=======
import { provideHttpClient } from '@angular/common/http';
import {provideOAuthClient} from  'angular-oauth2-oidc'
bootstrapApplication(App, appConfig)
  .catch((err) => console.error(err));
 
>>>>>>> desenvolvimento
