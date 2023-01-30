import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import axios, { AxiosResponse, AxiosError } from 'axios';
import { setCookie, removeCookie } from '../module/cookies';
import type { PayloadAction } from '@reduxjs/toolkit';
import { onLoginSuccess } from '../module/jsonWebToken';

interface Idata {
  userId: number;
}
interface IrejectValue {
  payload: { status: number; message: string };
  meta: any;
}
const loginRequest = createAsyncThunk(
  'loginIdentitySlice/loginRequest',
  async (data: { username: string; password: string }, { rejectWithValue }) => {
    try {
      const response: AxiosResponse<Idata, string> = await axios({
        method: 'POST',
        url: `${process.env.NEXT_PUBLIC_SERVER_URL}/login`,
        data,
      }).then((res) => res);
      const accessToken: string = response.headers.authorization;
      const refreshToken: string = response.headers.refresh;
      onLoginSuccess(accessToken);
      setCookie('refreshJwtToken', refreshToken, { path: '/' });
      const userId: number = response.data.userId;
      return userId;
    } catch (err) {
      if (err instanceof AxiosError) {
        return rejectWithValue(err.response.data);
      }
    }
  },
);

interface loginIdentity {
  isLogin: boolean;
  userId: number | null;
  notificationToken: string | null;
}
const initialState: loginIdentity = {
  isLogin: false,
  userId: null,
  notificationToken: null,
};

export const loginIdentitySlice = createSlice({
  name: 'loginIdentitySlice',
  initialState,
  reducers: {
    initLoginIdentity: (state: loginIdentity): void => {
      state.isLogin = false;
      state.userId = null;
      state.notificationToken = null;
      // removeCookie('accessJwtToken');
      removeCookie('refreshJwtToken');
    },
    oauthLogin: (state, action): void => {
      state.isLogin = true;
      state.userId = action.payload;
    },
    notificationToken: (state, action: PayloadAction<string>): void => {
      state.notificationToken = action.payload;
    },
  },
  extraReducers: (builder): void => {
    builder.addCase(
      loginRequest.fulfilled,
      (state: loginIdentity, action: PayloadAction<number>): void => {
        state.isLogin = true;
        state.userId = action.payload;
      },
    );
  },
});
export const { initLoginIdentity, oauthLogin, notificationToken } =
  loginIdentitySlice.actions;
export { loginRequest };
