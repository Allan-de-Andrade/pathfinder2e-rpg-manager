import { HttpClient } from '@angular/common/http';
import { Injectable , inject} from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class CharacterService {
  http = inject(HttpClient);
  private readonly character_url = "http://localhost:8080/api/characters"

  list(){
    return this.http.get(this.character_url)
  }
}
