import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './questions.reducer';

export const QuestionsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const questionsEntity = useAppSelector(state => state.questions.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="questionsDetailsHeading">
          <Translate contentKey="svisRevampApp.questions.detail.title">Questions</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{questionsEntity.id}</dd>
          <dt>
            <span id="descAr">
              <Translate contentKey="svisRevampApp.questions.descAr">Desc Ar</Translate>
            </span>
          </dt>
          <dd>{questionsEntity.descAr}</dd>
          <dt>
            <span id="descEn">
              <Translate contentKey="svisRevampApp.questions.descEn">Desc En</Translate>
            </span>
          </dt>
          <dd>{questionsEntity.descEn}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="svisRevampApp.questions.code">Code</Translate>
            </span>
          </dt>
          <dd>{questionsEntity.code}</dd>
          <dt>
            <span id="imgPath">
              <Translate contentKey="svisRevampApp.questions.imgPath">Img Path</Translate>
            </span>
          </dt>
          <dd>{questionsEntity.imgPath}</dd>
          <dt>
            <span id="timeInSec">
              <Translate contentKey="svisRevampApp.questions.timeInSec">Time In Sec</Translate>
            </span>
          </dt>
          <dd>{questionsEntity.timeInSec}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="svisRevampApp.questions.type">Type</Translate>
            </span>
          </dt>
          <dd>{questionsEntity.type}</dd>
          <dt>
            <span id="weight">
              <Translate contentKey="svisRevampApp.questions.weight">Weight</Translate>
            </span>
          </dt>
          <dd>{questionsEntity.weight}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="svisRevampApp.questions.status">Status</Translate>
            </span>
          </dt>
          <dd>{questionsEntity.status}</dd>
          <dt>
            <Translate contentKey="svisRevampApp.questions.categories">Categories</Translate>
          </dt>
          <dd>{questionsEntity.categories ? questionsEntity.categories.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/questions" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/questions/${questionsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default QuestionsDetail;
