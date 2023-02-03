import axios, { AxiosError } from 'axios';
import { getAuthProps } from './moduleInterface';
import { deleteAuthProps } from './moduleInterface';

export async function getHabitAuths({ habitId, page, size }: getAuthProps) {
  try {
    const response = await axios
      .get(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/habits/${habitId}/auths?page=${page}&size=${size}`,
      )
      .then((res) => console.log(res));
    return response;
  } catch (e) {
    console.error(e);
  }
}

export async function deleteHabitAuth({ habitId, authId }) {
  console.log(habitId, authId);
  try {
    const response = await axios
      .delete(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/habits/${habitId}/auths/${authId}`,
      )
      .then((res) => console.log(res));
    return response;
  } catch (e) {
    console.error(e);
  }
}
export async function patchHabitAuth({ habitId, authId, body }) {
  try {
    const response = await axios
      .patch(
        `${process.env.NEXT_PUBLIC_SERVER_URL}/habits/${habitId}/auths/${authId}`,
        body,
        {
          headers: {
            'Content-Type': 'multipart/form-data',
          },
        },
      )
      .then((res) => res.data);
    return response;
  } catch (e) {
    if (e instanceof AxiosError) {
      return e.response.status;
    }
  }
}
