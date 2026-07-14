import { ChangeDetectorRef, Component, inject, OnInit } from '@angular/core';
import { CharacterService } from '../../services/character-service';
import { MatButtonModule } from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import { Router } from '@angular/router';
import { MatProgressSpinnerModule } from '@angular/material/progress-spinner';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
        MatButtonModule,
        MatCardModule,
        MatProgressSpinnerModule
  ],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home implements OnInit{
   private cdr = inject(ChangeDetectorRef);
   private router = inject(Router);
   characterService = inject(CharacterService)
   characters: any[] = [];
  
  ngOnInit(): void {
    this.listCharacters();
  }

  listCharacters(): void {
    this.characterService.list().subscribe({
      next: (data) => {
        this.characters = data;
        this.cdr.detectChanges()
      },
      error:(err) =>{
        console.log(err)
      }
    });
  }
  createCharacter(): void{
    this.router.navigate(["new-character"])
  }
  getFullCharacter(id:any): void{
    this.router.navigate(["character",id])
  }
}
