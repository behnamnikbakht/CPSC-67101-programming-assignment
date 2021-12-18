import { IUser } from 'app/shared/model/user.model';
import { IShoppingGroup } from 'app/shared/model/shopping-group.model';
import { IItem } from 'app/shared/model/item.model';

export interface IPerson {
  id?: number;
  person?: IUser | null;
  shoppingGroups?: IShoppingGroup[] | null;
  items?: IItem[] | null;
  interests?: IItem[] | null;
  subscriptions?: IShoppingGroup[] | null;
  sells?: IItem[] | null;
}

export const defaultValue: Readonly<IPerson> = {};
