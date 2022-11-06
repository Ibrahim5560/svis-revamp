import { ITemplateCategories } from 'app/shared/model/template-categories.model';
import { ITemplateFacilitators } from 'app/shared/model/template-facilitators.model';
import { IExam } from 'app/shared/model/exam.model';

export interface ITemplate {
  id?: number;
  nameAr?: string;
  nameEn?: string;
  code?: string;
  timeInSec?: number | null;
  passScore?: number | null;
  status?: number | null;
  templateCategories?: ITemplateCategories[] | null;
  templateFacilitators?: ITemplateFacilitators[] | null;
  exams?: IExam[] | null;
}

export const defaultValue: Readonly<ITemplate> = {};
