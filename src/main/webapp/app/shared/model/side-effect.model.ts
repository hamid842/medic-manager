import { IMedicineInfo } from 'app/shared/model/medicine-info.model';

export interface ISideEffect {
  id?: number;
  sideEffect?: string;
  medicineInfo?: IMedicineInfo;
}

export const defaultValue: Readonly<ISideEffect> = {};
