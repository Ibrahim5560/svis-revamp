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
import { ITemplateFacilitators } from 'app/shared/model/template-facilitators.model';
import { getEntity, updateEntity, createEntity, reset } from './template-facilitators.reducer';

export const TemplateFacilitatorsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const templates = useAppSelector(state => state.template.entities);
  const templateFacilitatorsEntity = useAppSelector(state => state.templateFacilitators.entity);
  const loading = useAppSelector(state => state.templateFacilitators.loading);
  const updating = useAppSelector(state => state.templateFacilitators.updating);
  const updateSuccess = useAppSelector(state => state.templateFacilitators.updateSuccess);

  const handleClose = () => {
    navigate('/template-facilitators' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getTemplates({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...templateFacilitatorsEntity,
      ...values,
      template: templates.find(it => it.id.toString() === values.template.toString()),
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
          ...templateFacilitatorsEntity,
          template: templateFacilitatorsEntity?.template?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="svisRevampApp.templateFacilitators.home.createOrEditLabel" data-cy="TemplateFacilitatorsCreateUpdateHeading">
            <Translate contentKey="svisRevampApp.templateFacilitators.home.createOrEditLabel">
              Create or edit a TemplateFacilitators
            </Translate>
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
                  id="template-facilitators-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('svisRevampApp.templateFacilitators.count')}
                id="template-facilitators-count"
                name="count"
                data-cy="count"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('svisRevampApp.templateFacilitators.centerId')}
                id="template-facilitators-centerId"
                name="centerId"
                data-cy="centerId"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('svisRevampApp.templateFacilitators.facilitatorType')}
                id="template-facilitators-facilitatorType"
                name="facilitatorType"
                data-cy="facilitatorType"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                id="template-facilitators-template"
                name="template"
                data-cy="template"
                label={translate('svisRevampApp.templateFacilitators.template')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/template-facilitators" replace color="info">
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

export default TemplateFacilitatorsUpdate;
