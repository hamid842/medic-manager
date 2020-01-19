import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { ISideEffect, defaultValue } from 'app/shared/model/side-effect.model';

export const ACTION_TYPES = {
  FETCH_SIDEEFFECT_LIST: 'sideEffect/FETCH_SIDEEFFECT_LIST',
  FETCH_SIDEEFFECT: 'sideEffect/FETCH_SIDEEFFECT',
  CREATE_SIDEEFFECT: 'sideEffect/CREATE_SIDEEFFECT',
  UPDATE_SIDEEFFECT: 'sideEffect/UPDATE_SIDEEFFECT',
  DELETE_SIDEEFFECT: 'sideEffect/DELETE_SIDEEFFECT',
  RESET: 'sideEffect/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<ISideEffect>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type SideEffectState = Readonly<typeof initialState>;

// Reducer

export default (state: SideEffectState = initialState, action): SideEffectState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_SIDEEFFECT_LIST):
    case REQUEST(ACTION_TYPES.FETCH_SIDEEFFECT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_SIDEEFFECT):
    case REQUEST(ACTION_TYPES.UPDATE_SIDEEFFECT):
    case REQUEST(ACTION_TYPES.DELETE_SIDEEFFECT):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_SIDEEFFECT_LIST):
    case FAILURE(ACTION_TYPES.FETCH_SIDEEFFECT):
    case FAILURE(ACTION_TYPES.CREATE_SIDEEFFECT):
    case FAILURE(ACTION_TYPES.UPDATE_SIDEEFFECT):
    case FAILURE(ACTION_TYPES.DELETE_SIDEEFFECT):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_SIDEEFFECT_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_SIDEEFFECT):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_SIDEEFFECT):
    case SUCCESS(ACTION_TYPES.UPDATE_SIDEEFFECT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_SIDEEFFECT):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/side-effects';

// Actions

export const getEntities: ICrudGetAllAction<ISideEffect> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_SIDEEFFECT_LIST,
    payload: axios.get<ISideEffect>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<ISideEffect> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_SIDEEFFECT,
    payload: axios.get<ISideEffect>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<ISideEffect> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_SIDEEFFECT,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<ISideEffect> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_SIDEEFFECT,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<ISideEffect> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_SIDEEFFECT,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
