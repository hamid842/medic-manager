import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './medicine-info.reducer';
import { IMedicineInfo } from 'app/shared/model/medicine-info.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';

export interface IMedicineInfoProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const MedicineInfo = (props: IMedicineInfoProps) => {
  const [paginationState, setPaginationState] = useState(getSortState(props.location, ITEMS_PER_PAGE));

  const getAllEntities = () => {
    props.getEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
  };

  useEffect(() => {
    getAllEntities();
  }, []);

  const sortEntities = () => {
    getAllEntities();
    props.history.push(
      `${props.location.pathname}?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`
    );
  };

  useEffect(() => {
    sortEntities();
  }, [paginationState.activePage, paginationState.order, paginationState.sort]);

  const sort = p => () => {
    setPaginationState({
      ...paginationState,
      order: paginationState.order === 'asc' ? 'desc' : 'asc',
      sort: p
    });
  };

  const handlePagination = currentPage =>
    setPaginationState({
      ...paginationState,
      activePage: currentPage
    });

  const { medicineInfoList, match, totalItems } = props;
  return (
    <div>
      <h2 id="medicine-info-heading">
        <Translate contentKey="medicManagerApp.medicineInfo.home.title">Medicine Infos</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="medicManagerApp.medicineInfo.home.createLabel">Create new Medicine Info</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {medicineInfoList && medicineInfoList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th className="hand" onClick={sort('id')}>
                  <Translate contentKey="global.field.id">ID</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('name')}>
                  <Translate contentKey="medicManagerApp.medicineInfo.name">Name</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('importantInfo')}>
                  <Translate contentKey="medicManagerApp.medicineInfo.importantInfo">Important Info</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('usage')}>
                  <Translate contentKey="medicManagerApp.medicineInfo.usage">Usage</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('initialCount')}>
                  <Translate contentKey="medicManagerApp.medicineInfo.initialCount">Initial Count</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('promised')}>
                  <Translate contentKey="medicManagerApp.medicineInfo.promised">Promised</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('refillInfo')}>
                  <Translate contentKey="medicManagerApp.medicineInfo.refillInfo">Refill Info</Translate> <FontAwesomeIcon icon="sort" />
                </th>
                <th className="hand" onClick={sort('pharmacyNotes')}>
                  <Translate contentKey="medicManagerApp.medicineInfo.pharmacyNotes">Pharmacy Notes</Translate>{' '}
                  <FontAwesomeIcon icon="sort" />
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {medicineInfoList.map((medicineInfo, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${medicineInfo.id}`} color="link" size="sm">
                      {medicineInfo.id}
                    </Button>
                  </td>
                  <td>{medicineInfo.name}</td>
                  <td>{medicineInfo.importantInfo}</td>
                  <td>{medicineInfo.usage}</td>
                  <td>{medicineInfo.initialCount}</td>
                  <td>{medicineInfo.promised}</td>
                  <td>
                    <TextFormat type="date" value={medicineInfo.refillInfo} format={APP_DATE_FORMAT} />
                  </td>
                  <td>{medicineInfo.pharmacyNotes}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${medicineInfo.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${medicineInfo.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="primary"
                        size="sm"
                      >
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button
                        tag={Link}
                        to={`${match.url}/${medicineInfo.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
                        color="danger"
                        size="sm"
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
          <div className="alert alert-warning">
            <Translate contentKey="medicManagerApp.medicineInfo.home.notFound">No Medicine Infos found</Translate>
          </div>
        )}
      </div>
      <div className={medicineInfoList && medicineInfoList.length > 0 ? '' : 'd-none'}>
        <Row className="justify-content-center">
          <JhiItemCount page={paginationState.activePage} total={totalItems} itemsPerPage={paginationState.itemsPerPage} i18nEnabled />
        </Row>
        <Row className="justify-content-center">
          <JhiPagination
            activePage={paginationState.activePage}
            onSelect={handlePagination}
            maxButtons={5}
            itemsPerPage={paginationState.itemsPerPage}
            totalItems={props.totalItems}
          />
        </Row>
      </div>
    </div>
  );
};

const mapStateToProps = ({ medicineInfo }: IRootState) => ({
  medicineInfoList: medicineInfo.entities,
  totalItems: medicineInfo.totalItems
});

const mapDispatchToProps = {
  getEntities
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MedicineInfo);
