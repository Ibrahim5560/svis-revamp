import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './exam.reducer';

export const ExamDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const examEntity = useAppSelector(state => state.exam.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="examDetailsHeading">
          <Translate contentKey="svisRevampApp.exam.detail.title">Exam</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{examEntity.id}</dd>
          <dt>
            <span id="passScore">
              <Translate contentKey="svisRevampApp.exam.passScore">Pass Score</Translate>
            </span>
          </dt>
          <dd>{examEntity.passScore}</dd>
          <dt>
            <span id="score">
              <Translate contentKey="svisRevampApp.exam.score">Score</Translate>
            </span>
          </dt>
          <dd>{examEntity.score}</dd>
          <dt>
            <span id="timeInSec">
              <Translate contentKey="svisRevampApp.exam.timeInSec">Time In Sec</Translate>
            </span>
          </dt>
          <dd>{examEntity.timeInSec}</dd>
          <dt>
            <span id="validfrom">
              <Translate contentKey="svisRevampApp.exam.validfrom">Validfrom</Translate>
            </span>
          </dt>
          <dd>{examEntity.validfrom ? <TextFormat value={examEntity.validfrom} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="validto">
              <Translate contentKey="svisRevampApp.exam.validto">Validto</Translate>
            </span>
          </dt>
          <dd>{examEntity.validto ? <TextFormat value={examEntity.validto} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="startAt">
              <Translate contentKey="svisRevampApp.exam.startAt">Start At</Translate>
            </span>
          </dt>
          <dd>{examEntity.startAt ? <TextFormat value={examEntity.startAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="submitAt">
              <Translate contentKey="svisRevampApp.exam.submitAt">Submit At</Translate>
            </span>
          </dt>
          <dd>{examEntity.submitAt ? <TextFormat value={examEntity.submitAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="userId">
              <Translate contentKey="svisRevampApp.exam.userId">User Id</Translate>
            </span>
          </dt>
          <dd>{examEntity.userId}</dd>
          <dt>
            <span id="examinerId">
              <Translate contentKey="svisRevampApp.exam.examinerId">Examiner Id</Translate>
            </span>
          </dt>
          <dd>{examEntity.examinerId}</dd>
          <dt>
            <span id="examCode">
              <Translate contentKey="svisRevampApp.exam.examCode">Exam Code</Translate>
            </span>
          </dt>
          <dd>{examEntity.examCode}</dd>
          <dt>
            <span id="examDate">
              <Translate contentKey="svisRevampApp.exam.examDate">Exam Date</Translate>
            </span>
          </dt>
          <dd>{examEntity.examDate ? <TextFormat value={examEntity.examDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="examResult">
              <Translate contentKey="svisRevampApp.exam.examResult">Exam Result</Translate>
            </span>
          </dt>
          <dd>{examEntity.examResult}</dd>
          <dt>
            <span id="exported">
              <Translate contentKey="svisRevampApp.exam.exported">Exported</Translate>
            </span>
          </dt>
          <dd>{examEntity.exported ? 'true' : 'false'}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="svisRevampApp.exam.status">Status</Translate>
            </span>
          </dt>
          <dd>{examEntity.status}</dd>
          <dt>
            <Translate contentKey="svisRevampApp.exam.template">Template</Translate>
          </dt>
          <dd>{examEntity.template ? examEntity.template.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/exam" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/exam/${examEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ExamDetail;
