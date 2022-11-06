import { ITemplateCategories } from 'app/shared/model/template-categories.model';
import { IQuestions } from 'app/shared/model/questions.model';

export interface ICategories {
  id?: number;
  nameAr?: string;
  nameEn?: string;
  code?: string;
  status?: number | null;
  tempCategories?: ITemplateCategories[] | null;
  questions?: IQuestions[] | null;
}

export const defaultValue: Readonly<ICategories> = {};
