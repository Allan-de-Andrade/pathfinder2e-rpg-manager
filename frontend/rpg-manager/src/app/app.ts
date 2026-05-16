import { Component, signal } from '@angular/core';
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
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
  protected readonly title = signal('rpg-manager');

  constructor(private readonly authentication: Authentication) {}
}
