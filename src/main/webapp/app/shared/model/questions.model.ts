import { ICategories } from 'app/shared/model/categories.model';

export interface IQuestions {
  id?: number;
  descAr?: string;
  descEn?: string;
  code?: string;
  imgPath?: string | null;
  timeInSec?: number | null;
  type?: number;
  weight?: number;
  status?: number | null;
  categories?: ICategories | null;
}

export const defaultValue: Readonly<IQuestions> = {};
