import { Component, inject } from '@angular/core';
import { CharacterService } from '../../services/character-service';

@Component({
  selector: 'app-home',
  imports: [],
  templateUrl: './home.html',
  styleUrl: './home.css',
})
export class Home {
  characterService = inject(CharacterService)
  listCharacter(): void {
    this.characterService.list().subscribe({
      next: (data) => {
        console.log(data.valueOf())
      },
      error: (err) => {
        console.error(err)
      }
    })
  }
}
