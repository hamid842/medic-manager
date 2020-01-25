import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Table } from 'reactstrap';
import { Translate, ICrudGetAllAction, TextFormat, getSortState, IPaginationBaseState, JhiPagination, JhiItemCount } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';
import { getAllEntities, getEntity } from './medicine-info.reducer';
import { getSideEntity } from '../side-effect/side-effect.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { ITEMS_PER_PAGE } from 'app/shared/util/pagination.constants';
import SelectListGroup from './selectListGriup';

export interface IMedicineInfoProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const MedicineInfo = (props: IMedicineInfoProps) => {
  const [paginationState, setPaginationState] = useState(getSortState(props.location, ITEMS_PER_PAGE));

  const [id1, setId] = useState();

  const getAllEntities1 = () => {
    props.getAllEntities(paginationState.activePage - 1, paginationState.itemsPerPage, `${paginationState.sort},${paginationState.order}`);
  };

  const getOneEntity = () => {
    props.getEntity(id1);
  };
  const getSideEffect = () => {
    props.getSideEntity(id1);
  };
  useEffect(() => {
    getOneEntity();
  }, [id1]);

  useEffect(() => {
    getSideEffect();
  }, [id1]);

  useEffect(() => {
    getAllEntities1();
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

  const { medicineInfoList, medicineInfoList2, medicineInfo1, match, totalItems, sideEffectEntity } = props;

  const selectedItem = medicineInfoList2.map(item => {
    return <option key={item.id}>{item.name}</option>;
  });

  const optional = (
    <tbody>
      {/* {medicineInfoList.map((medicineInfo, i) => ( */}
      {/* key={`entity-${i}`} */}
      <tr>
        <td>
          <Button tag={Link} to={`${match.url}/${medicineInfo1.id}`} color="link" size="sm">
            {medicineInfo1.id}
          </Button>
        </td>
        <td>{medicineInfo1.name}</td>
        <td>{medicineInfo1.importantInfo}</td>
        <td>{medicineInfo1.usage}</td>
        <td>{medicineInfo1.initialCount}</td>
        <td>{medicineInfo1.promised}</td>
        <td>
          <TextFormat type="date" value={medicineInfo1.refillInfo} format={APP_DATE_FORMAT} />
        </td>
        <td>{medicineInfo1.pharmacyNotes}</td>
        <td className="text-right">
          <div className="btn-group flex-btn-group-container">
            <Button tag={Link} to={`${match.url}/${medicineInfo1.id}`} color="info" size="sm">
              <FontAwesomeIcon icon="eye" />{' '}
              <span className="d-none d-md-inline">
                <Translate contentKey="entity.action.view">View</Translate>
              </span>
            </Button>
            <Button
              tag={Link}
              to={`${match.url}/${medicineInfo1.id}/edit?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
              to={`${match.url}/${medicineInfo1.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
      <h2 id="side-effect-heading">
        <Translate contentKey="medicManagerApp.sideEffect.home.title">Side Effect</Translate>
      </h2>
      {sideEffectEntity.sideEffect}
    </tbody>
  );

  const all = (
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
                to={`${match.url}/${medicineInfo1.id}/delete?page=${paginationState.activePage}&sort=${paginationState.sort},${paginationState.order}`}
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
  );

  return (
    <div>
      <SelectListGroup name="select" value="" onChange={onchange} options={selectedItem} />
      <h2 id="medicine-info-heading">
        <Translate contentKey="medicManagerApp.medicineInfo.home.title">Medicine Infos</Translate>
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp;
          <Translate contentKey="medicManagerApp.medicineInfo.home.createLabel">Create new Medicine Info</Translate>
        </Link>
      </h2>
      <div className="table-responsive">
        {/* {medicineInfoList && medicineInfoList.length > 0 ? ( */}
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
                <Translate contentKey="medicManagerApp.medicineInfo.initialCount">Initial Count</Translate> <FontAwesomeIcon icon="sort" />
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
          {id1 === 0 || id1 === undefined || id1 === null ? all : optional}
        </Table>
        {/* ) : ( */}
        {/* <div className="alert alert-warning">
          <Translate contentKey="medicManagerApp.medicineInfo.home.notFound">No Medicine Infos found</Translate>
        </div> */}
        {/* )} */}
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

const mapStateToProps = ({ medicineInfo, sideEffect }: IRootState) => ({
  medicineInfoList: medicineInfo.entities,
  totalItems: medicineInfo.totalItems,
  medicineInfo1: medicineInfo.entity,
  sideEffectEntity: sideEffect.entity,
  medicineInfoList2: medicineInfo.entities
});

const mapDispatchToProps = {
  getAllEntities,
  getEntity,
  getSideEntity
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MedicineInfo);
