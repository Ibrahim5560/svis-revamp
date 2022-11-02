import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import ExamQuestions from './exam-questions';
import ExamQuestionsDetail from './exam-questions-detail';
import ExamQuestionsUpdate from './exam-questions-update';
import ExamQuestionsDeleteDialog from './exam-questions-delete-dialog';

const ExamQuestionsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<ExamQuestions />} />
    <Route path="new" element={<ExamQuestionsUpdate />} />
    <Route path=":id">
      <Route index element={<ExamQuestionsDetail />} />
      <Route path="edit" element={<ExamQuestionsUpdate />} />
      <Route path="delete" element={<ExamQuestionsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ExamQuestionsRoutes;
