import React, { useState, useEffect } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat, getSortState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IExam } from 'app/shared/model/exam.model';
import { getEntities } from './exam.reducer';

export const Exam = () => {
  const dispatch = useAppDispatch();

  const location = useLocation();
  const navigate = useNavigate();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getSortState(location, ITEMS_PER_PAGE, 'id'), location.search)
  );

  const examList = useAppSelector(state => state.exam.entities);
  const loading = useAppSelector(state => state.exam.loading);
  const totalItems = useAppSelector(state => state.exam.totalItems);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      })
    );
  };

  const sortEntities = () => {
    getAllEntities();
    const endURL = `?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`;
    if (location.search !== endURL) {
      navigate(`${location.pathname}${endURL}`);
    }
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  useEffect(() => {
    const params = new URLSearchParams(location.search);
    const page = params.get('page');
    const sort = params.get(SORT);
    if (page && sort) {
      const sortSplit = sort.split(',');
      setPaginationState({
        ...paginationState,
        activePage: +page,
        sort: sortSplit[0],
        order: sortSplit[1],
      });
    }
  }, [location.search]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage,
    });

  const handleSyncList = () => {
    sortEntities();
  };

  return (
    <div>
      <h2 id="exam-heading" data-cy="ExamHeading">
        <Translate contentKey="svisRevampApp.exam.home.title">Exams</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="svisRevampApp.exam.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/exam/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="svisRevampApp.exam.home.createLabel">Create new Exam</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {examList && examList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="svisRevampApp.exam.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('passScore')}>
                  <Translate contentKey="svisRevampApp.exam.passScore">Pass Score</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('score')}>
                  <Translate contentKey="svisRevampApp.exam.score">Score</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('timeInSec')}>
                  <Translate contentKey="svisRevampApp.exam.timeInSec">Time In Sec</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('validfrom')}>
                  <Translate contentKey="svisRevampApp.exam.validfrom">Validfrom</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('validto')}>
                  <Translate contentKey="svisRevampApp.exam.validto">Validto</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('startAt')}>
                  <Translate contentKey="svisRevampApp.exam.startAt">Start At</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('submitAt')}>
                  <Translate contentKey="svisRevampApp.exam.submitAt">Submit At</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('userId')}>
                  <Translate contentKey="svisRevampApp.exam.userId">User Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('examinerId')}>
                  <Translate contentKey="svisRevampApp.exam.examinerId">Examiner Id</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('examCode')}>
                  <Translate contentKey="svisRevampApp.exam.examCode">Exam Code</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('examDate')}>
                  <Translate contentKey="svisRevampApp.exam.examDate">Exam Date</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('examResult')}>
                  <Translate contentKey="svisRevampApp.exam.examResult">Exam Result</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('exported')}>
                  <Translate contentKey="svisRevampApp.exam.exported">Exported</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('status')}>
                  <Translate contentKey="svisRevampApp.exam.status">Status</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th>
                  <Translate contentKey="svisRevampApp.exam.template">Template</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {examList.map((exam, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`/exam/${exam.id}`} color="link" size="sm">
                      {exam.id}
                    </Button>
                  </td>
                  <td>{exam.passScore}</td>
                  <td>{exam.score}</td>
                  <td>{exam.timeInSec}</td>
                  <td>{exam.validfrom ? <TextFormat type="date" value={exam.validfrom} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{exam.validto ? <TextFormat type="date" value={exam.validto} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{exam.startAt ? <TextFormat type="date" value={exam.startAt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{exam.submitAt ? <TextFormat type="date" value={exam.submitAt} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{exam.userId}</td>
                  <td>{exam.examinerId}</td>
                  <td>{exam.examCode}</td>
                  <td>{exam.examDate ? <TextFormat type="date" value={exam.examDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{exam.examResult}</td>
                  <td>{exam.exported ? 'true' : 'false'}</td>
                  <td>{exam.status}</td>
                  <td>{exam.template ? <Link to={`/template/${exam.template.id}`}>{exam.template.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`/exam/${exam.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/exam/${exam.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                        data-cy="entityEditButton"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`/exam/${exam.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
                        data-cy="entityDeleteButton"
                      >
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="svisRevampApp.exam.home.notFound">No Exams found</Translate>
            </div>
          )
        )}
      </div>
      {totalItems ? (
        <div className={examList && examList.length > 0 ? '' : 'd-none'}>
          <div className="justify-content-center d-flex">
            <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
          </div>
          <div className="justify-content-center d-flex">
            <JhiPagination
              activePage={paginationState.activePage}
              onSelect={handlePagination}
              maxButtons={5}
              itemsPerPage={paginationState.itemsPerPage}
              totalItems={totalItems}
            />
          </div>
        </div>
      ) : (
        ''
      )}
    </div>
  );
};

export default Exam;
