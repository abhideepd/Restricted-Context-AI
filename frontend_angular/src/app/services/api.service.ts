import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { CricketResponse } from '../models/cricket.response';

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private http: HttpClient) {

  }

  baseUrl='http://localhost:8080/api/v1';

  getRandomeResponse(prompt:string):Observable<string>{
    // console.log(prompt);
    return this.http.get(`${this.baseUrl}/chat?inputText=${prompt}`, {
      responseType:'text'
    });
  }

  getCricketResponse(cricketPrompt:string):Observable<CricketResponse>{
    return this.http.get<CricketResponse>(
      `${this.baseUrl}/cricketbot?inputText=${cricketPrompt}`,{responseType:'json'}
    );
  }

  getCricketResponse1(cricketPrompt:string):Observable<string>{
    return this.http.get(
      `${this.baseUrl}/cricketbot?inputText=${cricketPrompt}`,{responseType:'text'}
    );
  }

  getImageResponse(imageDescription:string):Observable<string[]>{
    return this.http.get<string[]>(
      `${this.baseUrl}/images?imageDescription=${imageDescription}`
    );
  }
}
