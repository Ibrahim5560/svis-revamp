import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './exam-questions.reducer';

export const ExamQuestionsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const examQuestionsEntity = useAppSelector(state => state.examQuestions.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="examQuestionsDetailsHeading">
          <Translate contentKey="svisRevampApp.examQuestions.detail.title">ExamQuestions</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{examQuestionsEntity.id}</dd>
          <dt>
            <span id="descAr">
              <Translate contentKey="svisRevampApp.examQuestions.descAr">Desc Ar</Translate>
            </span>
          </dt>
          <dd>{examQuestionsEntity.descAr}</dd>
          <dt>
            <span id="descEn">
              <Translate contentKey="svisRevampApp.examQuestions.descEn">Desc En</Translate>
            </span>
          </dt>
          <dd>{examQuestionsEntity.descEn}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="svisRevampApp.examQuestions.code">Code</Translate>
            </span>
          </dt>
          <dd>{examQuestionsEntity.code}</dd>
          <dt>
            <span id="imgPath">
              <Translate contentKey="svisRevampApp.examQuestions.imgPath">Img Path</Translate>
            </span>
          </dt>
          <dd>{examQuestionsEntity.imgPath}</dd>
          <dt>
            <span id="timeInSec">
              <Translate contentKey="svisRevampApp.examQuestions.timeInSec">Time In Sec</Translate>
            </span>
          </dt>
          <dd>{examQuestionsEntity.timeInSec}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="svisRevampApp.examQuestions.type">Type</Translate>
            </span>
          </dt>
          <dd>{examQuestionsEntity.type}</dd>
          <dt>
            <span id="weight">
              <Translate contentKey="svisRevampApp.examQuestions.weight">Weight</Translate>
            </span>
          </dt>
          <dd>{examQuestionsEntity.weight}</dd>
          <dt>
            <span id="score">
              <Translate contentKey="svisRevampApp.examQuestions.score">Score</Translate>
            </span>
          </dt>
          <dd>{examQuestionsEntity.score}</dd>
          <dt>
            <span id="startAt">
              <Translate contentKey="svisRevampApp.examQuestions.startAt">Start At</Translate>
            </span>
          </dt>
          <dd>
            {examQuestionsEntity.startAt ? <TextFormat value={examQuestionsEntity.startAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="submitAt">
              <Translate contentKey="svisRevampApp.examQuestions.submitAt">Submit At</Translate>
            </span>
          </dt>
          <dd>
            {examQuestionsEntity.submitAt ? <TextFormat value={examQuestionsEntity.submitAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="categoryId">
              <Translate contentKey="svisRevampApp.examQuestions.categoryId">Category Id</Translate>
            </span>
          </dt>
          <dd>{examQuestionsEntity.categoryId}</dd>
          <dt>
            <span id="questionId">
              <Translate contentKey="svisRevampApp.examQuestions.questionId">Question Id</Translate>
            </span>
          </dt>
          <dd>{examQuestionsEntity.questionId}</dd>
          <dt>
            <span id="seq">
              <Translate contentKey="svisRevampApp.examQuestions.seq">Seq</Translate>
            </span>
          </dt>
          <dd>{examQuestionsEntity.seq}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="svisRevampApp.examQuestions.status">Status</Translate>
            </span>
          </dt>
          <dd>{examQuestionsEntity.status}</dd>
          <dt>
            <Translate contentKey="svisRevampApp.examQuestions.exam">Exam</Translate>
          </dt>
          <dd>{examQuestionsEntity.exam ? examQuestionsEntity.exam.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/exam-questions" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/exam-questions/${examQuestionsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ExamQuestionsDetail;
