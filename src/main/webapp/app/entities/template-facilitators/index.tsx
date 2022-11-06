import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import TemplateFacilitators from './template-facilitators';
import TemplateFacilitatorsDetail from './template-facilitators-detail';
import TemplateFacilitatorsUpdate from './template-facilitators-update';
import TemplateFacilitatorsDeleteDialog from './template-facilitators-delete-dialog';

const TemplateFacilitatorsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<TemplateFacilitators />} />
    <Route path="new" element={<TemplateFacilitatorsUpdate />} />
    <Route path=":id">
      <Route index element={<TemplateFacilitatorsDetail />} />
      <Route path="edit" element={<TemplateFacilitatorsUpdate />} />
      <Route path="delete" element={<TemplateFacilitatorsDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TemplateFacilitatorsRoutes;
