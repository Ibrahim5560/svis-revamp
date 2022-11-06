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
import { IExam } from 'app/shared/model/exam.model';
import { getEntity, updateEntity, createEntity, reset } from './exam.reducer';

export const ExamUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const templates = useAppSelector(state => state.template.entities);
  const examEntity = useAppSelector(state => state.exam.entity);
  const loading = useAppSelector(state => state.exam.loading);
  const updating = useAppSelector(state => state.exam.updating);
  const updateSuccess = useAppSelector(state => state.exam.updateSuccess);

  const handleClose = () => {
    navigate('/exam' + location.search);
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
    values.validfrom = convertDateTimeToServer(values.validfrom);
    values.validto = convertDateTimeToServer(values.validto);
    values.startAt = convertDateTimeToServer(values.startAt);
    values.submitAt = convertDateTimeToServer(values.submitAt);
    values.examDate = convertDateTimeToServer(values.examDate);

    const entity = {
      ...examEntity,
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
      ? {
          validfrom: displayDefaultDateTime(),
          validto: displayDefaultDateTime(),
          startAt: displayDefaultDateTime(),
          submitAt: displayDefaultDateTime(),
          examDate: displayDefaultDateTime(),
        }
      : {
          ...examEntity,
          validfrom: convertDateTimeFromServer(examEntity.validfrom),
          validto: convertDateTimeFromServer(examEntity.validto),
          startAt: convertDateTimeFromServer(examEntity.startAt),
          submitAt: convertDateTimeFromServer(examEntity.submitAt),
          examDate: convertDateTimeFromServer(examEntity.examDate),
          template: examEntity?.template?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="svisRevampApp.exam.home.createOrEditLabel" data-cy="ExamCreateUpdateHeading">
            <Translate contentKey="svisRevampApp.exam.home.createOrEditLabel">Create or edit a Exam</Translate>
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
                  id="exam-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('svisRevampApp.exam.passScore')}
                id="exam-passScore"
                name="passScore"
                data-cy="passScore"
                type="text"
              />
              <ValidatedField label={translate('svisRevampApp.exam.score')} id="exam-score" name="score" data-cy="score" type="text" />
              <ValidatedField
                label={translate('svisRevampApp.exam.timeInSec')}
                id="exam-timeInSec"
                name="timeInSec"
                data-cy="timeInSec"
                type="text"
              />
              <ValidatedField
                label={translate('svisRevampApp.exam.validfrom')}
                id="exam-validfrom"
                name="validfrom"
                data-cy="validfrom"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('svisRevampApp.exam.validto')}
                id="exam-validto"
                name="validto"
                data-cy="validto"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('svisRevampApp.exam.startAt')}
                id="exam-startAt"
                name="startAt"
                data-cy="startAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('svisRevampApp.exam.submitAt')}
                id="exam-submitAt"
                name="submitAt"
                data-cy="submitAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField label={translate('svisRevampApp.exam.userId')} id="exam-userId" name="userId" data-cy="userId" type="text" />
              <ValidatedField
                label={translate('svisRevampApp.exam.examinerId')}
                id="exam-examinerId"
                name="examinerId"
                data-cy="examinerId"
                type="text"
              />
              <ValidatedField
                label={translate('svisRevampApp.exam.examCode')}
                id="exam-examCode"
                name="examCode"
                data-cy="examCode"
                type="text"
              />
              <ValidatedField
                label={translate('svisRevampApp.exam.examDate')}
                id="exam-examDate"
                name="examDate"
                data-cy="examDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('svisRevampApp.exam.examResult')}
                id="exam-examResult"
                name="examResult"
                data-cy="examResult"
                type="text"
              />
              <ValidatedField
                label={translate('svisRevampApp.exam.exported')}
                id="exam-exported"
                name="exported"
                data-cy="exported"
                check
                type="checkbox"
              />
              <ValidatedField
                label={translate('svisRevampApp.exam.status')}
                id="exam-status"
                name="status"
                data-cy="status"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                id="exam-template"
                name="template"
                data-cy="template"
                label={translate('svisRevampApp.exam.template')}
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
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/exam" replace color="info">
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

export default ExamUpdate;
