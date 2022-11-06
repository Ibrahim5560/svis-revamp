import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './template-facilitators.reducer';

export const TemplateFacilitatorsDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const templateFacilitatorsEntity = useAppSelector(state => state.templateFacilitators.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="templateFacilitatorsDetailsHeading">
          <Translate contentKey="svisRevampApp.templateFacilitators.detail.title">TemplateFacilitators</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{templateFacilitatorsEntity.id}</dd>
          <dt>
            <span id="count">
              <Translate contentKey="svisRevampApp.templateFacilitators.count">Count</Translate>
            </span>
          </dt>
          <dd>{templateFacilitatorsEntity.count}</dd>
          <dt>
            <span id="centerId">
              <Translate contentKey="svisRevampApp.templateFacilitators.centerId">Center Id</Translate>
            </span>
          </dt>
          <dd>{templateFacilitatorsEntity.centerId}</dd>
          <dt>
            <span id="facilitatorType">
              <Translate contentKey="svisRevampApp.templateFacilitators.facilitatorType">Facilitator Type</Translate>
            </span>
          </dt>
          <dd>{templateFacilitatorsEntity.facilitatorType}</dd>
          <dt>
            <Translate contentKey="svisRevampApp.templateFacilitators.template">Template</Translate>
          </dt>
          <dd>{templateFacilitatorsEntity.template ? templateFacilitatorsEntity.template.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/template-facilitators" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/template-facilitators/${templateFacilitatorsEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TemplateFacilitatorsDetail;
