import { Injectable } from '@angular/core';
import { ArtistInstrument } from '../Class/ArtistInstrument';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

const apiUrl = 'http://localhost:8080/api';

@Injectable({
  providedIn: 'root'
})

export class ArtistInstrumentService {

  constructor(private http: HttpClient) { }

  getById(id: number): Observable<any> {
    return this.http.get(`${apiUrl}/artistInstrument${id}`);
  }

  list(): Observable<any> {
    return this.http.get(`${apiUrl}/artartistInstrumentsts`);
  }

  create(artartistInstrumentsts: ArtistInstrument): Observable<any> {
    return this.http.post(`${apiUrl}/artistInstrument`, artartistInstrumentsts);
  }

  edit(artartistInstrumentsts: ArtistInstrument): Observable<any> {
    return this.http.put(`${apiUrl}/artistInstrument`, artartistInstrumentsts);
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`${apiUrl}/artistInstrument/${id}`);
  }

}
