import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import TemplateCategories from './template-categories';
import TemplateCategoriesDetail from './template-categories-detail';
import TemplateCategoriesUpdate from './template-categories-update';
import TemplateCategoriesDeleteDialog from './template-categories-delete-dialog';

const TemplateCategoriesRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<TemplateCategories />} />
    <Route path="new" element={<TemplateCategoriesUpdate />} />
    <Route path=":id">
      <Route index element={<TemplateCategoriesDetail />} />
      <Route path="edit" element={<TemplateCategoriesUpdate />} />
      <Route path="delete" element={<TemplateCategoriesDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TemplateCategoriesRoutes;
