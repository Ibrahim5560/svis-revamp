import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IExam } from 'app/shared/model/exam.model';
import { getEntities as getExams } from 'app/entities/exam/exam.reducer';
import { IExamQuestions } from 'app/shared/model/exam-questions.model';
import { getEntity, updateEntity, createEntity, reset } from './exam-questions.reducer';

export const ExamQuestionsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const exams = useAppSelector(state => state.exam.entities);
  const examQuestionsEntity = useAppSelector(state => state.examQuestions.entity);
  const loading = useAppSelector(state => state.examQuestions.loading);
  const updating = useAppSelector(state => state.examQuestions.updating);
  const updateSuccess = useAppSelector(state => state.examQuestions.updateSuccess);

  const handleClose = () => {
    navigate('/exam-questions' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(id));
    }

    dispatch(getExams({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.startAt = convertDateTimeToServer(values.startAt);
    values.submitAt = convertDateTimeToServer(values.submitAt);

    const entity = {
      ...examQuestionsEntity,
      ...values,
      exam: exams.find(it => it.id.toString() === values.exam.toString()),
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
          startAt: displayDefaultDateTime(),
          submitAt: displayDefaultDateTime(),
        }
      : {
          ...examQuestionsEntity,
          startAt: convertDateTimeFromServer(examQuestionsEntity.startAt),
          submitAt: convertDateTimeFromServer(examQuestionsEntity.submitAt),
          exam: examQuestionsEntity?.exam?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="svisRevampApp.examQuestions.home.createOrEditLabel" data-cy="ExamQuestionsCreateUpdateHeading">
            <Translate contentKey="svisRevampApp.examQuestions.home.createOrEditLabel">Create or edit a ExamQuestions</Translate>
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
                  id="exam-questions-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('svisRevampApp.examQuestions.descAr')}
                id="exam-questions-descAr"
                name="descAr"
                data-cy="descAr"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('svisRevampApp.examQuestions.descEn')}
                id="exam-questions-descEn"
                name="descEn"
                data-cy="descEn"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('svisRevampApp.examQuestions.code')}
                id="exam-questions-code"
                name="code"
                data-cy="code"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('svisRevampApp.examQuestions.imgPath')}
                id="exam-questions-imgPath"
                name="imgPath"
                data-cy="imgPath"
                type="text"
              />
              <ValidatedField
                label={translate('svisRevampApp.examQuestions.timeInSec')}
                id="exam-questions-timeInSec"
                name="timeInSec"
                data-cy="timeInSec"
                type="text"
              />
              <ValidatedField
                label={translate('svisRevampApp.examQuestions.type')}
                id="exam-questions-type"
                name="type"
                data-cy="type"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('svisRevampApp.examQuestions.weight')}
                id="exam-questions-weight"
                name="weight"
                data-cy="weight"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('svisRevampApp.examQuestions.score')}
                id="exam-questions-score"
                name="score"
                data-cy="score"
                type="text"
              />
              <ValidatedField
                label={translate('svisRevampApp.examQuestions.startAt')}
                id="exam-questions-startAt"
                name="startAt"
                data-cy="startAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('svisRevampApp.examQuestions.submitAt')}
                id="exam-questions-submitAt"
                name="submitAt"
                data-cy="submitAt"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                label={translate('svisRevampApp.examQuestions.categoryId')}
                id="exam-questions-categoryId"
                name="categoryId"
                data-cy="categoryId"
                type="text"
              />
              <ValidatedField
                label={translate('svisRevampApp.examQuestions.questionId')}
                id="exam-questions-questionId"
                name="questionId"
                data-cy="questionId"
                type="text"
              />
              <ValidatedField
                label={translate('svisRevampApp.examQuestions.seq')}
                id="exam-questions-seq"
                name="seq"
                data-cy="seq"
                type="text"
              />
              <ValidatedField
                label={translate('svisRevampApp.examQuestions.status')}
                id="exam-questions-status"
                name="status"
                data-cy="status"
                type="text"
              />
              <ValidatedField
                id="exam-questions-exam"
                name="exam"
                data-cy="exam"
                label={translate('svisRevampApp.examQuestions.exam')}
                type="select"
              >
                <option value="" key="0" />
                {exams
                  ? exams.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/exam-questions" replace color="info">
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

export default ExamQuestionsUpdate;
