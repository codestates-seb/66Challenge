import { configureStore, combineReducers } from '@reduxjs/toolkit';
import { createWrapper } from 'next-redux-wrapper';
import { persistReducer, persistStore } from 'redux-persist';
import storage from 'redux-persist/lib/storage';
import { loginIdentitySlice } from './loginIdentitySlice';
// import logger from 'redux-logger';

const reducers = combineReducers({
  loginIdentity: loginIdentitySlice.reducer,
});

const persistConfig = {
  // localStorage 내 key name
  key: 'root',
  // redux-persist로 관리하는 state들을 localStorage에 저장
  storage,
  // redux-persist로 관리할 reducer를 지정
  whitelist: ['loginIdentity'],
};

const persistReducers = persistReducer(persistConfig, reducers);

const store = configureStore({
  reducer: persistReducers,
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: false,
    }),
});
export const persistor = persistStore(store);
export const wrapper = createWrapper(() => {
  return { persistor, ...store };
});
