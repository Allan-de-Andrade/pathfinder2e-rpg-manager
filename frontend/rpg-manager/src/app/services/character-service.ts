import { HttpClient } from '@angular/common/http';
import { Injectable , inject} from '@angular/core';
import { Observable } from 'rxjs';
import { Attribute } from '../models/Attributes';

@Injectable({
  providedIn: 'root',
})
export class CharacterService {
  http = inject(HttpClient);
  private readonly character_url = "http://localhost:8080/api/character"
  private readonly search_options = "http://localhost:8080/api/options"
  
  list():Observable<any>{
    return this.http.get(`${this.character_url}/all`)
  }
  
  listClasses():Observable<any>{
    return this.http.get(`${this.search_options}/classes`)
  }

  listRaces():Observable<any>{
    return this.http.get(`${this.search_options}/ancestries`)
  }
  
  listBibliographies():Observable<any>{
    return this.http.get(`${this.search_options}/bibliographies`)
  }
  
  findById(characterId:string):Observable<any>{ 
    return this.http.get(`${this.character_url}/get/${characterId}`)
  }  
  
  create(requestBody:any):Observable<any>{
    return this.http.post(`${this.character_url}/save`, requestBody)
  }
  generateModifiers(attributes:Record<Attribute,Number>){
    return this.http.post(`${this.character_url}/modifiers`,attributes)
  }
  delete(id:string){
    return this.http.delete(`${this.character_url}/delete/${id}`)
  }
}
