import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction, ICrudGetAllData } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IMedicineInfo, defaultValue } from 'app/shared/model/medicine-info.model';

export const ACTION_TYPES = {
  FETCH_MEDICINEINFO_LIST: 'medicineInfo/FETCH_MEDICINEINFO_LIST',
  FETCH_MEDICINEINFO_ALL: 'medicineInfo/FETCH_MEDICINEINFO_ALL',
  FETCH_MEDICINEINFO: 'medicineInfo/FETCH_MEDICINEINFO',
  CREATE_MEDICINEINFO: 'medicineInfo/CREATE_MEDICINEINFO',
  UPDATE_MEDICINEINFO: 'medicineInfo/UPDATE_MEDICINEINFO',
  DELETE_MEDICINEINFO: 'medicineInfo/DELETE_MEDICINEINFO',
  RESET: 'medicineInfo/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IMedicineInfo>,
  allEntities: [],
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type MedicineInfoState = Readonly<typeof initialState>;

// Reducer

export default (state: MedicineInfoState = initialState, action): MedicineInfoState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_MEDICINEINFO_ALL):
    case REQUEST(ACTION_TYPES.FETCH_MEDICINEINFO_LIST):
    case REQUEST(ACTION_TYPES.FETCH_MEDICINEINFO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_MEDICINEINFO):
    case REQUEST(ACTION_TYPES.UPDATE_MEDICINEINFO):
    case REQUEST(ACTION_TYPES.DELETE_MEDICINEINFO):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_MEDICINEINFO_LIST):
    case FAILURE(ACTION_TYPES.FETCH_MEDICINEINFO):
    case FAILURE(ACTION_TYPES.CREATE_MEDICINEINFO):
    case FAILURE(ACTION_TYPES.UPDATE_MEDICINEINFO):
    case FAILURE(ACTION_TYPES.DELETE_MEDICINEINFO):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_MEDICINEINFO_LIST):
    case SUCCESS(ACTION_TYPES.FETCH_MEDICINEINFO_ALL):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        allEntities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };

    case SUCCESS(ACTION_TYPES.FETCH_MEDICINEINFO):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_MEDICINEINFO):
    case SUCCESS(ACTION_TYPES.UPDATE_MEDICINEINFO):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_MEDICINEINFO):
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

const apiUrl = 'api/medicine-infos';

// Actions
export const getAllEntities = () => {
  return {
    type: ACTION_TYPES.FETCH_MEDICINEINFO_ALL,
    payload: axios.get<IMedicineInfo>(apiUrl)
  };
};
export const getEntities: ICrudGetAllAction<IMedicineInfo> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_MEDICINEINFO_LIST,
    payload: axios.get<IMedicineInfo>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<IMedicineInfo> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_MEDICINEINFO,
    payload: axios.get<IMedicineInfo>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IMedicineInfo> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_MEDICINEINFO,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IMedicineInfo> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_MEDICINEINFO,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IMedicineInfo> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_MEDICINEINFO,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
