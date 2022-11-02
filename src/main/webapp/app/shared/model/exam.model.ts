import dayjs from 'dayjs';
import { IExamQuestions } from 'app/shared/model/exam-questions.model';
import { ITemplate } from 'app/shared/model/template.model';

export interface IExam {
  id?: number;
  passScore?: number | null;
  score?: number | null;
  timeInSec?: number | null;
  validfrom?: string;
  validto?: string;
  startAt?: string | null;
  submitAt?: string | null;
  userId?: number | null;
  examinerId?: number | null;
  examCode?: number | null;
  examDate?: string | null;
  examResult?: number | null;
  exported?: boolean | null;
  status?: number;
  examQuestions?: IExamQuestions[] | null;
  template?: ITemplate | null;
}

export const defaultValue: Readonly<IExam> = {
  exported: false,
};
