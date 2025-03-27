import { Injectable } from '@angular/core';
import { IScheduleService } from './ischedules.service';
import { Observable } from 'rxjs';
import { SaveScheduleRequest, SaveScheduleResponse, ScheduleAppointmentMonthResponse } from './schedules.models';
import { HttpClient } from '@angular/common/http';
import { enviroment } from '../../../../enviroments/enviroments';

@Injectable({
  providedIn: 'root'
})
export class SchedulesService implements IScheduleService{

  private readonly basePath = enviroment.apiUrl

  constructor(private http: HttpClient) { }

  save(request: SaveScheduleRequest): Observable<SaveScheduleResponse> {
    return this.http.post<SaveScheduleResponse>(`${this.basePath}`, request)
  }
  delete(id: number): Observable<void> {
    return this.http.delete<void>(`${this.basePath}/${id}`)
  }
  listInMonth(year: number, month: number): Observable<ScheduleAppointmentMonthResponse> {
    return this.http.get<ScheduleAppointmentMonthResponse>(`${this.basePath}/${year}/${month}`)
  }
}
