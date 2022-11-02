import React from 'react';
import { Translate } from 'react-jhipster';

import MenuItem from 'app/shared/layout/menus/menu-item';

const EntitiesMenu = () => {
  return (
    <>
      {/* prettier-ignore */}
      <MenuItem icon="asterisk" to="/template">
        <Translate contentKey="global.menu.entities.template" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/categories">
        <Translate contentKey="global.menu.entities.categories" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/template-categories">
        <Translate contentKey="global.menu.entities.templateCategories" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/template-facilitators">
        <Translate contentKey="global.menu.entities.templateFacilitators" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/questions">
        <Translate contentKey="global.menu.entities.questions" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/exam">
        <Translate contentKey="global.menu.entities.exam" />
      </MenuItem>
      <MenuItem icon="asterisk" to="/exam-questions">
        <Translate contentKey="global.menu.entities.examQuestions" />
      </MenuItem>
      {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
    </>
  );
};

export default EntitiesMenu;
