import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { ITemplate } from 'app/shared/model/template.model';
import { getEntities as getTemplates } from 'app/entities/template/template.reducer';
import { ICategories } from 'app/shared/model/categories.model';
import { getEntities as getCategories } from 'app/entities/categories/categories.reducer';
import { ITemplateCategories } from 'app/shared/model/template-categories.model';
import { getEntity, updateEntity, createEntity, reset } from './template-categories.reducer';

export const TemplateCategoriesUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const templates = useAppSelector(state => state.template.entities);
  const categories = useAppSelector(state => state.categories.entities);
  const templateCategoriesEntity = useAppSelector(state => state.templateCategories.entity);
  const loading = useAppSelector(state => state.templateCategories.loading);
  const updating = useAppSelector(state => state.templateCategories.updating);
  const updateSuccess = useAppSelector(state => state.templateCategories.updateSuccess);

  const handleClose = () => {
    navigate('/template-categories' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getTemplates({}));
    dispatch(getCategories({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...templateCategoriesEntity,
      ...values,
      template: templates.find(it => it.id.toString() === values.template.toString()),
      categories: categories.find(it => it.id.toString() === values.categories.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...templateCategoriesEntity,
          template: templateCategoriesEntity?.template?.id,
          categories: templateCategoriesEntity?.categories?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="svisRevampApp.templateCategories.home.createOrEditLabel" data-cy="TemplateCategoriesCreateUpdateHeading">
            <Translate contentKey="svisRevampApp.templateCategories.home.createOrEditLabel">Create or edit a TemplateCategories</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="template-categories-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('svisRevampApp.templateCategories.code')}
                id="template-categories-code"
                name="code"
                data-cy="code"
                type="text"
              />
              <ValidatedField
                label={translate('svisRevampApp.templateCategories.noOfQuestions')}
                id="template-categories-noOfQuestions"
                name="noOfQuestions"
                data-cy="noOfQuestions"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('svisRevampApp.templateCategories.seq')}
                id="template-categories-seq"
                name="seq"
                data-cy="seq"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('svisRevampApp.templateCategories.status')}
                id="template-categories-status"
                name="status"
                data-cy="status"
                type="text"
              />
              <ValidatedField
                id="template-categories-template"
                name="template"
                data-cy="template"
                label={translate('svisRevampApp.templateCategories.template')}
                type="select"
              >
                <option value="" key="0" />
                {templates
                  ? templates.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <ValidatedField
                id="template-categories-categories"
                name="categories"
                data-cy="categories"
                label={translate('svisRevampApp.templateCategories.categories')}
                type="select"
              >
                <option value="" key="0" />
                {categories
                  ? categories.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/template-categories" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default TemplateCategoriesUpdate;
