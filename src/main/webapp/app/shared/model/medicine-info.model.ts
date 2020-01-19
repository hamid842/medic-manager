import { Moment } from 'moment';
import { ISideEffect } from 'app/shared/model/side-effect.model';
import { ITimeTable } from 'app/shared/model/time-table.model';

export interface IMedicineInfo {
  id?: number;
  name?: string;
  importantInfo?: string;
  usage?: string;
  initialCount?: string;
  promised?: string;
  refillInfo?: Moment;
  pharmacyNotes?: string;
  sideEffects?: ISideEffect[];
  timeTables?: ITimeTable[];
}

export const defaultValue: Readonly<IMedicineInfo> = {};
