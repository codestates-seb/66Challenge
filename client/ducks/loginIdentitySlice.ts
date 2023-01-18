import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import axios, { AxiosResponse, AxiosError } from 'axios';
import { setCookie, removeCookie } from '../module/cookies';
import type { PayloadAction } from '@reduxjs/toolkit';
const loginRequest = createAsyncThunk(
  'loginIdentitySlice/loginRequest',
  async (data: { username: string; password: string }, { rejectWithValue }) => {
    try {
      const response: AxiosResponse = await axios({
        method: 'POST',
        url: `${process.env.NEXT_PUBLIC_SERVER_URL}/login`,
        data,
      }).then((res) => res);

      const jwtToken: string = response.headers.authorization;
      setCookie('accessJwtToken', jwtToken, { path: '/' });

      const userId: number = response.data.userId;
      return userId;
    } catch (err) {
      return rejectWithValue(err.response.data);
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
export const { initLoginIdentity } = loginIdentitySlice.actions;
export { loginRequest };
