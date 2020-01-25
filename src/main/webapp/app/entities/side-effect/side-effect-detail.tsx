import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getSideEntity } from './side-effect.reducer';
import { ISideEffect } from 'app/shared/model/side-effect.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISideEffectDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SideEffectDetail = (props: ISideEffectDetailProps) => {
  useEffect(() => {
    props.getSideEntity(props.match.params.id);
  }, []);

  const { sideEffectEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="medicManagerApp.sideEffect.detail.title">SideEffect</Translate> [<b>{sideEffectEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="sideEffect">
              <Translate contentKey="medicManagerApp.sideEffect.sideEffect">Side Effect</Translate>
            </span>
          </dt>
          <dd>{sideEffectEntity.sideEffect}</dd>
          <dt>
            <Translate contentKey="medicManagerApp.sideEffect.medicineInfo">Medicine Info</Translate>
          </dt>
          <dd>{sideEffectEntity.medicineInfo ? sideEffectEntity.medicineInfo.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/side-effect" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/side-effect/${sideEffectEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ sideEffect }: IRootState) => ({
  sideEffectEntity: sideEffect.entity
});

const mapDispatchToProps = { getSideEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SideEffectDetail);
