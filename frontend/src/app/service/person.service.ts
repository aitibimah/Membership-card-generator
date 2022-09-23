import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Personne } from '../model/personne';
import { Observable } from 'rxjs';




@Injectable({
  providedIn: 'root'
})
export class PersonService {


  private personUrl: string;

  constructor(private http: HttpClient) { 
    this.personUrl = 'http://localhost:8080/api/personnes/';
    
  }


  public save(person: Personne) {
   
  
    let headers = new HttpHeaders();
    headers = headers.set('Accept', 'application/pdf');
    return this.http.post(this.personUrl,person, { headers: headers, responseType: 'blob' });
  }
  
  

}
