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
import { getEntity, updateEntity, createEntity, reset } from './side-effect.reducer';
import { ISideEffect } from 'app/shared/model/side-effect.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ISideEffectUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SideEffectUpdate = (props: ISideEffectUpdateProps) => {
  const [medicineInfoId, setMedicineInfoId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { sideEffectEntity, medicineInfos, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/side-effect' + props.location.search);
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
    if (errors.length === 0) {
      const entity = {
        ...sideEffectEntity,
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
          <h2 id="medicManagerApp.sideEffect.home.createOrEditLabel">
            <Translate contentKey="medicManagerApp.sideEffect.home.createOrEditLabel">Create or edit a SideEffect</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : sideEffectEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="side-effect-id">
                    <Translate contentKey="global.field.id">ID</Translate>
                  </Label>
                  <AvInput id="side-effect-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="sideEffectLabel" for="side-effect-sideEffect">
                  <Translate contentKey="medicManagerApp.sideEffect.sideEffect">Side Effect</Translate>
                </Label>
                <AvField id="side-effect-sideEffect" type="text" name="sideEffect" />
              </AvGroup>
              <AvGroup>
                <Label for="side-effect-medicineInfo">
                  <Translate contentKey="medicManagerApp.sideEffect.medicineInfo">Medicine Info</Translate>
                </Label>
                <AvInput id="side-effect-medicineInfo" type="select" className="form-control" name="medicineInfo.id">
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
              <Button tag={Link} id="cancel-save" to="/side-effect" replace color="info">
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
  sideEffectEntity: storeState.sideEffect.entity,
  loading: storeState.sideEffect.loading,
  updating: storeState.sideEffect.updating,
  updateSuccess: storeState.sideEffect.updateSuccess
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

export default connect(mapStateToProps, mapDispatchToProps)(SideEffectUpdate);
