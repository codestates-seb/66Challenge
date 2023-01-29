import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import axios, { AxiosResponse, AxiosError } from 'axios';
import { setCookie, removeCookie } from '../module/cookies';
import type { PayloadAction } from '@reduxjs/toolkit';
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
      setCookie('accessJwtToken', accessToken, { path: '/' });
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
}
const initialState: loginIdentity = {
  isLogin: false,
  userId: null,
};

export const loginIdentitySlice = createSlice({
  name: 'loginIdentitySlice',
  initialState,
  reducers: {
    initLoginIdentity: (state: loginIdentity): void => {
      state.isLogin = false;
      state.userId = null;
      removeCookie('accessJwtToken');
    },
    oauthLogin: (state, action): void => {
      state.isLogin = true;
      state.userId = action.payload;
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
export const { initLoginIdentity, oauthLogin } = loginIdentitySlice.actions;
export { loginRequest };
