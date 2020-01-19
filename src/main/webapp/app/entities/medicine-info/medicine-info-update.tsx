import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './medicine-info.reducer';
import { IMedicineInfo } from 'app/shared/model/medicine-info.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IMedicineInfoUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const MedicineInfoUpdate = (props: IMedicineInfoUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { medicineInfoEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/medicine-info' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.refillInfo = convertDateTimeToServer(values.refillInfo);

    if (errors.length === 0) {
      const entity = {
        ...medicineInfoEntity,
        ...values
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="medicManagerApp.medicineInfo.home.createOrEditLabel">
            <Translate contentKey="medicManagerApp.medicineInfo.home.createOrEditLabel">Create or edit a MedicineInfo</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : medicineInfoEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="medicine-info-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="medicine-info-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="medicine-info-name">
                  <Translate contentKey="medicManagerApp.medicineInfo.name">Name</Translate>
                </Label>
                <AvField id="medicine-info-name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="importantInfoLabel" for="medicine-info-importantInfo">
                  <Translate contentKey="medicManagerApp.medicineInfo.importantInfo">Important Info</Translate>
                </Label>
                <AvField id="medicine-info-importantInfo" type="text" name="importantInfo" />
              </AvGroup>
              <AvGroup>
                <Label id="usageLabel" for="medicine-info-usage">
                  <Translate contentKey="medicManagerApp.medicineInfo.usage">Usage</Translate>
                </Label>
                <AvField id="medicine-info-usage" type="text" name="usage" />
              </AvGroup>
              <AvGroup>
                <Label id="initialCountLabel" for="medicine-info-initialCount">
                  <Translate contentKey="medicManagerApp.medicineInfo.initialCount">Initial Count</Translate>
                </Label>
                <AvField id="medicine-info-initialCount" type="text" name="initialCount" />
              </AvGroup>
              <AvGroup>
                <Label id="promisedLabel" for="medicine-info-promised">
                  <Translate contentKey="medicManagerApp.medicineInfo.promised">Promised</Translate>
                </Label>
                <AvField id="medicine-info-promised" type="text" name="promised" />
              </AvGroup>
              <AvGroup>
                <Label id="refillInfoLabel" for="medicine-info-refillInfo">
                  <Translate contentKey="medicManagerApp.medicineInfo.refillInfo">Refill Info</Translate>
                </Label>
                <AvInput
                  id="medicine-info-refillInfo"
                  type="datetime-local"
                  className="form-control"
                  name="refillInfo"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? null : convertDateTimeFromServer(props.medicineInfoEntity.refillInfo)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="pharmacyNotesLabel" for="medicine-info-pharmacyNotes">
                  <Translate contentKey="medicManagerApp.medicineInfo.pharmacyNotes">Pharmacy Notes</Translate>
                </Label>
                <AvField id="medicine-info-pharmacyNotes" type="text" name="pharmacyNotes" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/medicine-info" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  medicineInfoEntity: storeState.medicineInfo.entity,
  loading: storeState.medicineInfo.loading,
  updating: storeState.medicineInfo.updating,
  updateSuccess: storeState.medicineInfo.updateSuccess
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(MedicineInfoUpdate);
