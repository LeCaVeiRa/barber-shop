import { AfterViewInit, Component, EventEmitter, Inject, Input, OnChanges, OnInit, Output, SimpleChanges, ViewChild } from '@angular/core';
import { SERVICES_TOKEN } from '../../services/token.service';
import { DialogManagerService } from '../../services/dialog-manager.service';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { ClientScheduleAppointmentModel, SaveScheduleModel, ScheduleAppointmentMonthModel, SelectClientModel } from '../schedule.models';
import { FormControl, FormsModule, NgForm } from '@angular/forms';
import { IDialogManagerService } from '../../services/idialog-manager.service';
import { CommonModule } from '@angular/common';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSelectModule } from '@angular/material/select';
import { MatTimepickerModule } from '@angular/material/timepicker';
import { MatTooltipModule } from '@angular/material/tooltip';
import { YesNoDialogComponent } from '../../commons/components/yes-no-dialog/yes-no-dialog.component';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-schedule-calendar',
  imports: [ CommonModule,
    FormsModule,
    MatDatepickerModule,
    MatCardModule,
    MatTableModule,
    MatButtonModule,
    MatIconModule,
    MatPaginatorModule,
    MatTooltipModule,
    MatTimepickerModule,
    MatInputModule,
    MatFormFieldModule,
    MatSelectModule,],
  templateUrl: './schedule-calendar.component.html',
  styleUrl: './schedule-calendar.component.scss',
  providers:[
    {provide:SERVICES_TOKEN.DIALOG,useClass: DialogManagerService}
  ]
})
export class ScheduleCalendarComponent implements AfterViewInit, OnChanges, OnInit{

  private subscription?: Subscription

  private _selected: Date = new Date()

  displayedColumns: string[] = ['startAt', 'endAt', 'client', 'actions']

  dataSource!: MatTableDataSource<ClientScheduleAppointmentModel>

  addingScheduel: boolean = false

  newSchedule: SaveScheduleModel = {startAt: undefined, endAt: undefined, clientId: undefined}

  clientSelectFOrmControl = new FormControl()

  @Input() monthSchedule!: ScheduleAppointmentMonthModel
  @Input() client:SelectClientModel[] = []

  @Output() onDateChange = new EventEmitter<Date>()
  @Output() onConfirmDelete = new EventEmitter<ClientScheduleAppointmentModel>()
  @Output() onScheduleClient = new EventEmitter<SaveScheduleModel>()

  @ViewChild(MatPaginator) paginator!: MatPaginator

  constructor(
    @Inject(SERVICES_TOKEN.DIALOG) private readonly dialogManagerService: IDialogManagerService
  ){}

  get selected(): Date{
    return this._selected
  }

  set selected(selected: Date){
    if(this._selected.getTime() !== selected.getTime()){
      this.onDateChange.emit(selected)
      this.buildTable()
      this._selected = selected
    }
    
  }

  ngOnInit(): void {
    if(this.subscription){
      this.subscription.unsubscribe()
    }
  }

  ngAfterViewInit(): void {
    if(this.dataSource && this.paginator){
      this.dataSource.paginator = this.paginator
    }
  }
  
  ngOnChanges(changes: SimpleChanges): void {
    if(changes['monthSchedule'] && this.monthSchedule){
      this.buildTable()
    }
  }

  requestDelete(schedule: ClientScheduleAppointmentModel){
    this.dialogManagerService.showYesNoDialog(YesNoDialogComponent,
      {title:'Exclusão de agendamento', content:'Tem certeza que deseja excluir o agendamento ?'}
    ).subscribe(result => {
      if(result){
        this.onConfirmDelete.emit(schedule)
        const updatedList = this.dataSource.data.filter( c => c.id !== schedule.id)
        this.dataSource = new MatTableDataSource<ClientScheduleAppointmentModel>(updatedList)

        if(this.paginator){
          this.dataSource.paginator = this.paginator
        }
      }
    })
  }

  onSubmit(form: NgForm){
    const startAt = new Date(this._selected)
    const endAt = new Date(this._selected)

    startAt.setHours(this.newSchedule.startAt!.getHours(),this.newSchedule.startAt!.getMinutes())
    endAt.setHours(this.newSchedule.endAt!.getHours(),this.newSchedule.endAt!.getMinutes())

    const saved: ClientScheduleAppointmentModel = {
      id: -1,
      day: this._selected.getDate(),
      startAt,
      endAt,
      clientId: this.newSchedule.clientId!,
      clientName: this.client.find(c => c.id === this.newSchedule.clientId!)!.name
    }
    this.onScheduleClient.emit(saved)
    this.buildTable()
    form.resetForm()
    this.newSchedule = {startAt: undefined, endAt: undefined, clientId: undefined}
  }

  onTimeChange(time: Date){
    const endAt = new Date(time)
    endAt.setHours(time.getHours() + 1)
    this.newSchedule.endAt = endAt
  }

  private buildTable(){
    const appointments = this.monthSchedule.scheduleAppointments.filter(a => 
      this.monthSchedule.year === this._selected.getFullYear() &&
      this.monthSchedule.month === this._selected.getMonth() &&
      a.day === this._selected.getDate()
    )
    this.dataSource = new MatTableDataSource<ClientScheduleAppointmentModel>(appointments)

    if(this.paginator){
      this.dataSource.paginator = this.paginator
    }
  }

}
