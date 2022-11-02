import { ITemplate } from 'app/shared/model/template.model';
import { ICategories } from 'app/shared/model/categories.model';

export interface ITemplateCategories {
  id?: number;
  code?: string | null;
  noOfQuestions?: number;
  seq?: number;
  status?: number | null;
  template?: ITemplate | null;
  categories?: ICategories | null;
}

export const defaultValue: Readonly<ITemplateCategories> = {};
