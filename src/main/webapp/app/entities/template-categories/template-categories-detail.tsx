import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './template-categories.reducer';

export const TemplateCategoriesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const templateCategoriesEntity = useAppSelector(state => state.templateCategories.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="templateCategoriesDetailsHeading">
          <Translate contentKey="svisRevampApp.templateCategories.detail.title">TemplateCategories</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{templateCategoriesEntity.id}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="svisRevampApp.templateCategories.code">Code</Translate>
            </span>
          </dt>
          <dd>{templateCategoriesEntity.code}</dd>
          <dt>
            <span id="noOfQuestions">
              <Translate contentKey="svisRevampApp.templateCategories.noOfQuestions">No Of Questions</Translate>
            </span>
          </dt>
          <dd>{templateCategoriesEntity.noOfQuestions}</dd>
          <dt>
            <span id="seq">
              <Translate contentKey="svisRevampApp.templateCategories.seq">Seq</Translate>
            </span>
          </dt>
          <dd>{templateCategoriesEntity.seq}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="svisRevampApp.templateCategories.status">Status</Translate>
            </span>
          </dt>
          <dd>{templateCategoriesEntity.status}</dd>
          <dt>
            <Translate contentKey="svisRevampApp.templateCategories.template">Template</Translate>
          </dt>
          <dd>{templateCategoriesEntity.template ? templateCategoriesEntity.template.id : ''}</dd>
          <dt>
            <Translate contentKey="svisRevampApp.templateCategories.categories">Categories</Translate>
          </dt>
          <dd>{templateCategoriesEntity.categories ? templateCategoriesEntity.categories.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/template-categories" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/template-categories/${templateCategoriesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TemplateCategoriesDetail;
