import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './person.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PersonDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const personEntity = useAppSelector(state => state.person.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="personDetailsHeading">
          <Translate contentKey="shoppingApp.person.detail.title">Person</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{personEntity.id}</dd>
          <dt>
            <Translate contentKey="shoppingApp.person.person">Person</Translate>
          </dt>
          <dd>{personEntity.person ? personEntity.person.login : ''}</dd>
          <dt>
            <Translate contentKey="shoppingApp.person.fullname">Fullname</Translate>
          </dt>
          <dd>{personEntity.person ? personEntity.person.firstName + ' ' : ''} {personEntity.person ? personEntity.person.lastName : ''}</dd>
          <dt>
            <Translate contentKey="shoppingApp.person.interests">Interests</Translate>
          </dt>
          <dd>
            {personEntity.interests
              ? personEntity.interests.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.name}</a>
                    {personEntity.interests && i === personEntity.interests.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="shoppingApp.person.subscriptions">Subscriptions</Translate>
          </dt>
          <dd>
            {personEntity.subscriptions
              ? personEntity.subscriptions.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.name}</a>
                    {personEntity.subscriptions && i === personEntity.subscriptions.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
          <dt>
            <Translate contentKey="shoppingApp.person.sells">Sells</Translate>
          </dt>
          <dd>
            {personEntity.sells
              ? personEntity.sells.map((val, i) => (
                  <span key={val.id}>
                    <a>{val.name}</a>
                    {personEntity.sells && i === personEntity.sells.length - 1 ? '' : ', '}
                  </span>
                ))
              : null}
          </dd>
        </dl>
        <Button tag={Link} to="/person" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/person/${personEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PersonDetail;
