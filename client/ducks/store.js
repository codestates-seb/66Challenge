import { configureStore, ThunkAction, Action } from '@reduxjs/toolkit';
import loginIdentitySlice from './loginIdentitySlice';

const store = configureStore({
  reducer: {
    loginIdentity: loginIdentitySlice,
  },
});

export default store;

// import { configureStore, combineReducers } from '@reduxjs/toolkit';
// import { createWrapper } from 'next-redux-wrapper';
// import { persistReducer, persistStore } from 'redux-persist';
// import storage from 'redux-persist/lib/storage';
// import loginIdentitySlice from './loginIdentitySlice';
// import logger from 'redux-logger';
// import { setIsLogin, loginRequest } from './loginIdentitySlice';

// const reducers = combineReducers({
//   setIsLogin, loginRequest
// });

// const persistConfig = {
//   // localStorage 내 key name
//   key: 'root',
//   // redux-persist로 관리하는 state들을 localStorage에 저장
//   storage,
//   // redux-persist로 관리할 reducer를 지정
//   whitelist: ['loginIdentitySlice'],
// };

// const persistReducers = persistReducer(persistConfig, reducers);

// export const store = configureStore({
//   reducer: persistReducers,
//   middleware: (getDefaultMiddleware) => getDefaultMiddleware().concat(logger),
// });

// export const persistor = persistStore(store);
// export const wrapper = createWrapper(persistor);
