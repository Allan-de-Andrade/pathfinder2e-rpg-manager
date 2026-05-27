import { Component, signal } from '@angular/core';
<<<<<<< HEAD
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet],
=======
import { HttpClient } from '@angular/common/http';
import { RouterOutlet } from '@angular/router';
import { Authentication } from './services/authentication';
@Component({
  selector: 'app-root',
  imports: [
    RouterOutlet,
  ],
  providers: [
  ],
>>>>>>> desenvolvimento
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('rpg-manager');
<<<<<<< HEAD
=======

  constructor(private readonly authentication: Authentication) {}
>>>>>>> desenvolvimento
}
