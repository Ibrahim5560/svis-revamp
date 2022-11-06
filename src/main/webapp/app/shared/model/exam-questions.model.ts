import dayjs from 'dayjs';
import { IExam } from 'app/shared/model/exam.model';

export interface IExamQuestions {
  id?: number;
  descAr?: string;
  descEn?: string;
  code?: string;
  imgPath?: string | null;
  timeInSec?: number | null;
  type?: number;
  weight?: number;
  score?: number | null;
  startAt?: string | null;
  submitAt?: string | null;
  categoryId?: number | null;
  questionId?: number | null;
  seq?: number | null;
  status?: number | null;
  exam?: IExam | null;
}

export const defaultValue: Readonly<IExamQuestions> = {};
