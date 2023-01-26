import axios from 'axios';
import { getCookie } from './cookies';
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

export async function deleteHabitAuth({ authId }: deleteAuthProps) {
  try {
    const response = await axios
      .delete(`${process.env.NEXT_PUBLIC_SERVER_URL}/auths/${authId}`, {
        headers: {
          Authorization: getCookie('accessJwtToken'),
        },
      })
      .then((res) => console.log(res));
    return response;
  } catch (e) {
    console.error(e);
  }
}
