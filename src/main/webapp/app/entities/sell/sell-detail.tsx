import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './sell.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const SellDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const sellEntity = useAppSelector(state => state.sell.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="sellDetailsHeading">
          <Translate contentKey="shoppingApp.sell.detail.title">Sell</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{sellEntity.id}</dd>
          <dt>
            <span id="createdAt">
              <Translate contentKey="shoppingApp.sell.createdAt">Created At</Translate>
            </span>
          </dt>
          <dd>{sellEntity.createdAt ? <TextFormat value={sellEntity.createdAt} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="quantity">
              <Translate contentKey="shoppingApp.sell.quantity">Quantity</Translate>
            </span>
          </dt>
          <dd>{sellEntity.quantity}</dd>
          <dt>
            <Translate contentKey="shoppingApp.sell.person">Person</Translate>
          </dt>
          <dd>{sellEntity.person ? sellEntity.person.id : ''}</dd>
          <dt>
            <Translate contentKey="shoppingApp.sell.item">Item</Translate>
          </dt>
          <dd>{sellEntity.item ? sellEntity.item.name : ''}</dd>
        </dl>
        <Button tag={Link} to="/sell" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/sell/${sellEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SellDetail;
