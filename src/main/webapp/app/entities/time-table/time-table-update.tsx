import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { Translate, translate, ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IMedicineInfo } from 'app/shared/model/medicine-info.model';
import { getEntities as getMedicineInfos } from 'app/entities/medicine-info/medicine-info.reducer';
import { getEntity, updateEntity, createEntity, reset } from './time-table.reducer';
import { ITimeTable } from 'app/shared/model/time-table.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITimeTableUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TimeTableUpdate = (props: ITimeTableUpdateProps) => {
  const [medicineInfoId, setMedicineInfoId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { timeTableEntity, medicineInfos, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/time-table' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getMedicineInfos();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.date = convertDateTimeToServer(values.date);

    if (errors.length === 0) {
      const entity = {
        ...timeTableEntity,
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
          <h2 id="medicManagerApp.timeTable.home.createOrEditLabel">
            <Translate contentKey="medicManagerApp.timeTable.home.createOrEditLabel">Create or edit a TimeTable</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : timeTableEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="time-table-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="time-table-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="dateLabel" for="time-table-date">
                  <Translate contentKey="medicManagerApp.timeTable.date">Date</Translate>
                </Label>
                <AvInput
                  id="time-table-date"
                  type="datetime-local"
                  className="form-control"
                  name="date"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? null : convertDateTimeFromServer(props.timeTableEntity.date)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="isTakenLabel" for="time-table-isTaken">
                  <Translate contentKey="medicManagerApp.timeTable.isTaken">Is Taken</Translate>
                </Label>
                <AvField id="time-table-isTaken" type="text" name="isTaken" />
              </AvGroup>
              <AvGroup>
                <Label for="time-table-medicineInfo">
                  <Translate contentKey="medicManagerApp.timeTable.medicineInfo">Medicine Info</Translate>
                </Label>
                <AvInput id="time-table-medicineInfo" type="select" className="form-control" name="medicineInfo.id">
                  <option value="" key="0" />
                  {medicineInfos
                    ? medicineInfos.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/time-table" replace color="info">
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
  medicineInfos: storeState.medicineInfo.entities,
  timeTableEntity: storeState.timeTable.entity,
  loading: storeState.timeTable.loading,
  updating: storeState.timeTable.updating,
  updateSuccess: storeState.timeTable.updateSuccess
});

const mapDispatchToProps = {
  getMedicineInfos,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TimeTableUpdate);
