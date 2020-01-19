import { Moment } from 'moment';
import { IMedicineInfo } from 'app/shared/model/medicine-info.model';

export interface ITimeTable {
  id?: number;
  date?: Moment;
  isTaken?: string;
  medicineInfo?: IMedicineInfo;
}

export const defaultValue: Readonly<ITimeTable> = {};
