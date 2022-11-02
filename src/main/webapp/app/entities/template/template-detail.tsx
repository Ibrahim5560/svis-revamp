import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './template.reducer';

export const TemplateDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const templateEntity = useAppSelector(state => state.template.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="templateDetailsHeading">
          <Translate contentKey="svisRevampApp.template.detail.title">Template</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{templateEntity.id}</dd>
          <dt>
            <span id="nameAr">
              <Translate contentKey="svisRevampApp.template.nameAr">Name Ar</Translate>
            </span>
          </dt>
          <dd>{templateEntity.nameAr}</dd>
          <dt>
            <span id="nameEn">
              <Translate contentKey="svisRevampApp.template.nameEn">Name En</Translate>
            </span>
          </dt>
          <dd>{templateEntity.nameEn}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="svisRevampApp.template.code">Code</Translate>
            </span>
          </dt>
          <dd>{templateEntity.code}</dd>
          <dt>
            <span id="timeInSec">
              <Translate contentKey="svisRevampApp.template.timeInSec">Time In Sec</Translate>
            </span>
          </dt>
          <dd>{templateEntity.timeInSec}</dd>
          <dt>
            <span id="passScore">
              <Translate contentKey="svisRevampApp.template.passScore">Pass Score</Translate>
            </span>
          </dt>
          <dd>{templateEntity.passScore}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="svisRevampApp.template.status">Status</Translate>
            </span>
          </dt>
          <dd>{templateEntity.status}</dd>
        </dl>
        <Button tag={Link} to="/template" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/template/${templateEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TemplateDetail;
