import dayjs from 'dayjs';
import { IPerson } from 'app/shared/model/person.model';
import { IItem } from 'app/shared/model/item.model';

export interface ISell {
  id?: number;
  createdAt?: string;
  quantity?: number;
  person?: IPerson | null;
  item?: IItem | null;
}

export const defaultValue: Readonly<ISell> = {};
