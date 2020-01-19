import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './medicine-info.reducer';
import { IMedicineInfo } from 'app/shared/model/medicine-info.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IMedicineInfoDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MedicineInfoDetail = (props: IMedicineInfoDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { medicineInfoEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="medicManagerApp.medicineInfo.detail.title">MedicineInfo</Translate> [<b>{medicineInfoEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">
              <Translate contentKey="medicManagerApp.medicineInfo.name">Name</Translate>
            </span>
          </dt>
          <dd>{medicineInfoEntity.name}</dd>
          <dt>
            <span id="importantInfo">
              <Translate contentKey="medicManagerApp.medicineInfo.importantInfo">Important Info</Translate>
            </span>
          </dt>
          <dd>{medicineInfoEntity.importantInfo}</dd>
          <dt>
            <span id="usage">
              <Translate contentKey="medicManagerApp.medicineInfo.usage">Usage</Translate>
            </span>
          </dt>
          <dd>{medicineInfoEntity.usage}</dd>
          <dt>
            <span id="initialCount">
              <Translate contentKey="medicManagerApp.medicineInfo.initialCount">Initial Count</Translate>
            </span>
          </dt>
          <dd>{medicineInfoEntity.initialCount}</dd>
          <dt>
            <span id="promised">
              <Translate contentKey="medicManagerApp.medicineInfo.promised">Promised</Translate>
            </span>
          </dt>
          <dd>{medicineInfoEntity.promised}</dd>
          <dt>
            <span id="refillInfo">
              <Translate contentKey="medicManagerApp.medicineInfo.refillInfo">Refill Info</Translate>
            </span>
          </dt>
          <dd>
            <TextFormat value={medicineInfoEntity.refillInfo} type="date" format={APP_DATE_FORMAT} />
          </dd>
          <dt>
            <span id="pharmacyNotes">
              <Translate contentKey="medicManagerApp.medicineInfo.pharmacyNotes">Pharmacy Notes</Translate>
            </span>
          </dt>
          <dd>{medicineInfoEntity.pharmacyNotes}</dd>
        </dl>
        <Button tag={Link} to="/medicine-info" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/medicine-info/${medicineInfoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ medicineInfo }: IRootState) => ({
  medicineInfoEntity: medicineInfo.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MedicineInfoDetail);
