import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Template from './template';
import Categories from './categories';
import TemplateCategories from './template-categories';
import TemplateFacilitators from './template-facilitators';
import Questions from './questions';
import Exam from './exam';
import ExamQuestions from './exam-questions';
/* jhipster-needle-add-route-import - JHipster will add routes here */

export default () => {
  return (
    <div>
      <ErrorBoundaryRoutes>
        {/* prettier-ignore */}
        <Route path="template/*" element={<Template />} />
        <Route path="categories/*" element={<Categories />} />
        <Route path="template-categories/*" element={<TemplateCategories />} />
        <Route path="template-facilitators/*" element={<TemplateFacilitators />} />
        <Route path="questions/*" element={<Questions />} />
        <Route path="exam/*" element={<Exam />} />
        <Route path="exam-questions/*" element={<ExamQuestions />} />
        {/* jhipster-needle-add-route-path - JHipster will add routes here */}
      </ErrorBoundaryRoutes>
    </div>
  );
};
