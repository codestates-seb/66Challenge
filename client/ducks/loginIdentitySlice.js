import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import axios from 'axios';
import { setCookie } from '../module/cookies';

const loginRequest = createAsyncThunk(
  'loginIdentitySlice/loginRequest',
  async (data) => {
    try {
      const response = await axios({
        method: 'POST',
        url: `${process.env.NEXT_PUBLIC_SERVER_URL}/login`,
        data,
      }).then((res) => res);

      const jwtToken = response.headers.get('Authorization');
      setCookie('accessJwtToken', jwtToken);

      const userId = response.data.userId;
      return userId;
    } catch (err) {
      console.error(err);
    }
  },
);

const initialState = {
  isLogin: false,
  userId: '',
};

export const loginIdentitySlice = createSlice({
  name: 'loginIdentitySlice',
  initialState,

  extraReducers: (builder) => {
    builder.addCase(loginRequest.fulfilled, (state, action) => {
      console.log(action.payload);
      state.isLogin = true;
      state.userId = action.payload;
    });
  },
});

export { loginRequest };
