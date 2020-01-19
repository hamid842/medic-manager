import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './time-table.reducer';
import { ITimeTable } from 'app/shared/model/time-table.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITimeTableDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TimeTableDetail = (props: ITimeTableDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { timeTableEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          <Translate contentKey="medicManagerApp.timeTable.detail.title">TimeTable</Translate> [<b>{timeTableEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="date">
              <Translate contentKey="medicManagerApp.timeTable.date">Date</Translate>
            </span>
          </dt>
          <dd>
            <TextFormat value={timeTableEntity.date} type="date" format={APP_DATE_FORMAT} />
          </dd>
          <dt>
            <span id="isTaken">
              <Translate contentKey="medicManagerApp.timeTable.isTaken">Is Taken</Translate>
            </span>
          </dt>
          <dd>{timeTableEntity.isTaken}</dd>
          <dt>
            <Translate contentKey="medicManagerApp.timeTable.medicineInfo">Medicine Info</Translate>
          </dt>
          <dd>{timeTableEntity.medicineInfo ? timeTableEntity.medicineInfo.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/time-table" replace color="info">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/time-table/${timeTableEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ timeTable }: IRootState) => ({
  timeTableEntity: timeTable.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TimeTableDetail);
