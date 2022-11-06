import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './categories.reducer';

export const CategoriesDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const categoriesEntity = useAppSelector(state => state.categories.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="categoriesDetailsHeading">
          <Translate contentKey="svisRevampApp.categories.detail.title">Categories</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{categoriesEntity.id}</dd>
          <dt>
            <span id="nameAr">
              <Translate contentKey="svisRevampApp.categories.nameAr">Name Ar</Translate>
            </span>
          </dt>
          <dd>{categoriesEntity.nameAr}</dd>
          <dt>
            <span id="nameEn">
              <Translate contentKey="svisRevampApp.categories.nameEn">Name En</Translate>
            </span>
          </dt>
          <dd>{categoriesEntity.nameEn}</dd>
          <dt>
            <span id="code">
              <Translate contentKey="svisRevampApp.categories.code">Code</Translate>
            </span>
          </dt>
          <dd>{categoriesEntity.code}</dd>
          <dt>
            <span id="status">
              <Translate contentKey="svisRevampApp.categories.status">Status</Translate>
            </span>
          </dt>
          <dd>{categoriesEntity.status}</dd>
        </dl>
        <Button tag={Link} to="/categories" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/categories/${categoriesEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CategoriesDetail;
