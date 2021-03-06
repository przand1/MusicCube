import { Injectable } from '@angular/core';
import { Instrument } from '../Class/Instrument';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

import { api_url } from '../Utils/API_URL';

const apiUrl = api_url;

@Injectable({
  providedIn: 'root'
})
export class InstrumentService {
  constructor(private http: HttpClient) {}

  getById(id: number): Observable<any> {
    return this.http.get(`${apiUrl}/instrument/${id}`);
  }
  getByInstrumentName(term: string): Observable<any> {
    return this.http.get(`${apiUrl}/instrument/name/${term}`);
  }

  list(): Observable<any> {
    return this.http.get(`${apiUrl}/instrument`);
  }

  create(instrument: Instrument): Observable<any> {
    return this.http.post(`${apiUrl}/admin/instrument`, instrument);
  }

  edit(instrument: Instrument): Observable<any> {
    return this.http.put(`${apiUrl}/admin/instrument`, instrument);
  }

  delete(id: number): Observable<any> {
    return this.http.delete(`${apiUrl}/admin/instrument/${id}`);
  }
}
