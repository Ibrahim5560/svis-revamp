import { ITemplate } from 'app/shared/model/template.model';

export interface ITemplateFacilitators {
  id?: number;
  count?: number;
  centerId?: number;
  facilitatorType?: number;
  template?: ITemplate | null;
}

export const defaultValue: Readonly<ITemplateFacilitators> = {};
